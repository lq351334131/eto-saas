package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.PageBounds;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.database.util.SysUserRedisVO;
import org.etocrm.entity.*;
import org.etocrm.entity.uam.SysUser;
import org.etocrm.enums.BusinessEnum;
import org.etocrm.enums.OtherSystemUrlTypeEnum;
import org.etocrm.model.brands.SysOrgBrandsVO;
import org.etocrm.model.openAccount.GrantOrgInfo;
import org.etocrm.model.org.*;
import org.etocrm.model.org.invoice.SysInvoiceUpdateVO;
import org.etocrm.model.org.orgAuth.SysOrgAuthUpdateVO;
import org.etocrm.model.org.orgAuth.SysOrgAuthVO;
import org.etocrm.repository.*;
import org.etocrm.repository.uam.SysUserRepository;
import org.etocrm.service.SysDictService;
import org.etocrm.service.SysInvoiceService;
import org.etocrm.service.SysOrgAuthService;
import org.etocrm.service.SysOrgService;
import org.etocrm.utils.OtherSystemNoticeUtil;
import org.etocrm.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 机构表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysOrgServiceImpl implements SysOrgService {

    @Resource
    private SysOrgRepository sysOrgRepository;

    @Resource
    private SysInvoiceService sysInvoiceService;

    @Resource
    private SysOrgAuthService sysOrgAuthService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private SecurityUtil securityUtil;
    @Resource
    private SysOrgCompanyRepository sysOrgCompanyRepository;

    @Resource
    private SysUserRepository sysUserRepository;

    @Resource
    private SysDictService sysDictService;

    @Resource
    private SysIndustryRepository sysIndustryRepository;

    @Resource
    private MachineRoomRepository machineRoomRepository;

    @Resource
    private SysOrgBrandsRepository sysOrgBrandsRepository;

    @Resource
    private OtherSystemNoticeUtil otherSystemNoticeUtil;

    @Value("${saas.role.code}")
    String roleCode;

    private void saveCompany(Long orgId, String orgName) {
        //总公司
        SysOrgCompany vo = new SysOrgCompany();
        vo.setName(orgName + "总公司");
        vo.setType(BusinessEnum.HEADOFFICE.getCode());
        vo.setOrgId(orgId);
        vo.setParentId(0L);
        SysOrgCompany save = sysOrgCompanyRepository.save(vo);
        //分公司
        SysOrgCompany vo1 = new SysOrgCompany();
        vo1.setName(orgName + "分公司");
        vo1.setType(BusinessEnum.HEADOFFICE.getCode());
        vo1.setOrgId(orgId);
        vo1.setParentId(save.getId());
        sysOrgCompanyRepository.save(vo1);
    }

    @Override
    @Transactional
    public Long updateByPk(SysOrgDetailVO sysOrg) throws MyException {
        SysOrg sysOrg1 = new SysOrg();
        if (sysOrg.getBasics() != null) {
            checkNameAndId(sysOrg.getBasics().getName().trim(), sysOrg.getBasics().getId());
            BeanUtil.copyProperties(sysOrg.getBasics(), sysOrg1);
            sysOrgRepository.update(sysOrg1);
        }
        if (sysOrg.getAuth() != null) {
            SysOrgAuthVO auth = sysOrg.getAuth();
            auth.setOrgId(sysOrg.getBasics().getId());
            SysOrgAuthUpdateVO sysOrgAuthUpdateVO = new SysOrgAuthUpdateVO();
            BeanUtil.copyProperties(auth, sysOrgAuthUpdateVO);
            sysOrgAuthService.updateByPk(sysOrgAuthUpdateVO);
        }
        if (sysOrg.getInvoice() != null) {
            SysInvoiceUpdateVO invoice = sysOrg.getInvoice();
            invoice.setId(invoice.getId());
            invoice.setOrgId(sysOrg.getBasics().getId());
            sysInvoiceService.updateByPk(invoice);
        }
        Optional<SysOrg> byId = sysOrgRepository.findById(sysOrg1.getId());
        //下发
        grantOrgInfo(sysOrg.getBasics().getId(), byId.get().getMachineId(), sysOrg.getBasics(), sysOrg.getAuth());
        return sysOrg.getBasics().getId();
    }

    private void grantOrgInfo(Long orgId, Long machineId, SysOrgUpdateVO basics, SysOrgAuthVO auth) throws MyException {
        GrantOrgInfo grantOrgInfo = new GrantOrgInfo();
        grantOrgInfo.setCrmOrgId(basics.getId());
        grantOrgInfo.setUamOrgId(basics.getId());
        grantOrgInfo.setIndustryId(auth.getIndustryCode());
        Optional<SysIndustry> byId = sysIndustryRepository.findById(grantOrgInfo.getIndustryId());
        if (byId.isPresent()) {
            grantOrgInfo.setIndustryName(byId.get().getName());
        }
        grantOrgInfo.setName(basics.getName());
        grantOrgInfo.setOrgLogoUrl(basics.getOrgLogoUrl());
        grantOrgInfo.setMachineId(machineId);
        Optional<MachineRoom> byId1 = machineRoomRepository.findById(machineId);
        if (byId1.isPresent()) {
            grantOrgInfo.setMachineCode(byId1.get().getCode());
            grantOrgInfo.setMachineName(byId1.get().getRoomName());
        }
        //调接口
        log.info("下发机构参数：{}", JSON.toJSONString(grantOrgInfo));
        otherSystemNoticeUtil.restTemplate(orgId, machineId, JSON.toJSONString(grantOrgInfo), OtherSystemUrlTypeEnum.ORG_ADD_REDIRECT);
    }


    @Override
    public SysOrgDetailVO detailByPk() throws MyException {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        SysOrgDetailVO detailVO = new SysOrgDetailVO();
        SysOrgUpdateVO sysOrgUpdateVO = new SysOrgUpdateVO();
        Optional<SysOrg> byId = sysOrgRepository.findById(loginUser.getUamOrgId());
        if (byId.isPresent()) {
            BeanUtil.copyProperties(byId.get(), sysOrgUpdateVO);
            detailVO.setBasics(sysOrgUpdateVO);
        }

        SysOrgAuthVO sysOrgAuthUpdateVO = sysOrgAuthService.detailByOrgId(loginUser.getUamOrgId());
        detailVO.setAuth(sysOrgAuthUpdateVO);

        SysInvoiceUpdateVO sysInvoiceUpdateVO = sysInvoiceService.detailByOrgId(loginUser.getUamOrgId());
        detailVO.setInvoice(sysInvoiceUpdateVO);
        return detailVO;
    }

    @Override
    public BasePage<SysOrgListVO> list(Integer curPage, Integer size, SysOrgSelectVO sysOrg) throws MyException {
        Set<Long> collect = new HashSet<>();
        if (sysOrg.getAuthStatus() != null) {
            List<SysOrgIndustry> vos = sysOrgAuthService.findByAuthStatus(sysOrg.getAuthStatus());
            collect = vos.stream().map(SysOrgIndustry::getOrgId).collect(Collectors.toSet());
        }
        PageBounds pageBounds = new PageBounds(curPage, size);
        Set<Long> finalCollect = collect;
        Page<SysOrg> sysParamIPage = sysOrgRepository.findAll((Specification<SysOrg>) (root, query, cb) -> {
                    //集合 用于封装查询条件
                    List<Predicate> list = new ArrayList<>();
                    //简单单表查询
                    if (StringUtils.isNotEmpty(sysOrg.getName())) {
                        Predicate code = cb.like(root.get("name").as(String.class), "%" + sysOrg.getName() + "%");
                        list.add(code);
                    }
                    if (null != sysOrg.getId()) {
                        Predicate billingStatus = cb.equal(root.get("id").as(Integer.class), sysOrg.getId());
                        list.add(billingStatus);
                    }
                    if (null != sysOrg.getApprovalStatus()) {
                        Predicate payStatus = cb.equal(root.get("approvalStatus").as(Long.class), sysOrg.getApprovalStatus());
                        list.add(payStatus);
                    }
                    if (!finalCollect.isEmpty()) {
                        CriteriaBuilder.In<Long> orgId = cb.in(root.get("id"));
                        for (Long id : finalCollect) {
                            orgId.value(id);
                        }
                        list.add(orgId);
                    }
                    return cb.and(list.toArray(new Predicate[0]));
                }
                , PageRequest.of(pageBounds.getOffset(), pageBounds.getLimit(),
                        Sort.by(Sort.Direction.DESC, "updatedTime")));
        BasePage basePage = new BasePage<>(sysParamIPage);
        List<SysOrg> records = (List<SysOrg>) basePage.getRecords();
        List<SysOrgListVO> transformation = this.sysOrgListVOS(records);
        basePage.setRecords(transformation);
        return basePage;
    }

    @Override
    public Long updateStatus(SysOrgStatusVO requestId) throws MyException {
        SysOrg sysOrg = new SysOrg();
        BeanUtil.copyProperties(requestId, sysOrg);
        SysOrg update = sysOrgRepository.update(sysOrg);
        return update.getId();
    }

    @Override
    public SysOrgDetailInfo detailInfo() {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        Long orgId = loginUser.getUamOrgId();
        Optional<SysOrg> byId = sysOrgRepository.findById(orgId);
        if (byId.isPresent()) {
            SysOrg sysOrg = byId.get();
            SysOrgDetailInfo sysOrgDetailInfo = new SysOrgDetailInfo();
            BeanUtil.copyProperties(sysOrg, sysOrgDetailInfo);
            //行业
            SysOrgAuthVO sysOrgAuthVO = sysOrgAuthService.detailByOrgId(orgId);
            Optional<SysIndustry> byId1 = sysIndustryRepository.findById(sysOrgAuthVO.getIndustryCode());
            sysOrgDetailInfo.setIndustryId(sysOrgAuthVO.getIndustryCode());
            sysOrgDetailInfo.setIndustryName(byId1.get().getName());
            //品牌
            List<SysOrgBrandsVO> brandsList = new ArrayList<>();
            List<SysOrgBrands> orgBrands = sysOrgBrandsRepository.findByOrgId(orgId);
            for (SysOrgBrands orgBrand : orgBrands) {
                SysOrgBrandsVO sysOrgBrandsVO = new SysOrgBrandsVO();
                BeanUtil.copyProperties(orgBrand, sysOrgBrandsVO);
                brandsList.add(sysOrgBrandsVO);
            }
            sysOrgDetailInfo.setBrandsList(brandsList);
            //机房
            Optional<MachineRoom> byId2 = machineRoomRepository.findById(sysOrg.getMachineId());
            sysOrgDetailInfo.setMachineName(byId2.get().getRoomName());
            return sysOrgDetailInfo;
        }
        return null;
    }


    @Override
    public List<SysOrgListVO> findAll(SysOrgSelectAllVO sysOrg) throws MyException {
        List<SysOrg> all = sysOrgRepository.findAll((r, q, c) -> new QueryConditionUtil().where(sysOrg, r, c));
        return this.sysOrgListVOS(all);
    }

    /**
     * 集合机构返回数据
     *
     * @param all
     * @return
     */
    private List<SysOrgListVO> sysOrgListVOS(List<SysOrg> all) throws MyException {
        List<SysOrgListVO> listVOS = new ArrayList<>();
        Set<Long> collect = all.stream().map(SysOrg::getCreatedBy).collect(Collectors.toSet());
        List<SysUser> allById = sysUserRepository.findAllById(collect);
        try {
            for (SysOrg org : all) {
                SysOrgListVO sysOrgListVO = new SysOrgListVO();
                BeanUtil.copyProperties(org, sysOrgListVO);
                allById.forEach(user -> {
                    if (user.getId().equals(org.getUpdatedBy())) {
                        sysOrgListVO.setUpdateUser(user.getName());
                    }
                });
                //    sysOrgListVO.setApprovalStatus(sysDictService.findDictNameById(org.getApprovalStatus()));
                //查询认证状态
                SysOrgAuthVO sysOrgAuthUpdateVO = sysOrgAuthService.detailByOrgId(org.getId());
                sysOrgListVO.setAuthStatus(sysOrgAuthUpdateVO.getAuthStatus());

                sysOrgListVO.setSource(sysDictService.findDictNameById(org.getSource()));
                listVOS.add(sysOrgListVO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException(ResponseEnum.FAILD.getCode(), "返回机构列表错误");
        }
        return listVOS;
    }


    /**
     * 校验机构名字
     *
     * @param name
     * @throws MyException
     */
    private void checkName(String name) throws MyException {
        List<SysOrg> all = sysOrgRepository.findByName(name);
        if (CollectionUtil.isNotEmpty(all)) {
            throw new MyException(ResponseEnum.FAILD.getCode(), "机构名字已经存在");
        }
    }

    /**
     * 校验机构名字
     *
     * @param name
     * @throws MyException
     */
    private void checkNameAndId(String name, Long id) throws MyException {
        List<SysOrg> all = sysOrgRepository.findByNameAndIdNot(name, id);
        if (CollectionUtil.isNotEmpty(all)) {
            throw new MyException(ResponseEnum.FAILD.getCode(), "机构名字已经存在");
        }
    }

    /**
     * 校验机构名字
     *
     * @param uid
     * @throws MyException
     */
    private void checkUserUid(String uid) throws MyException {
        Integer all = userRepository.countByUid(uid);
        if (all > 0) {
            throw new MyException(ResponseEnum.FAILD.getCode(), "登录名已经存在");
        }
    }
}
