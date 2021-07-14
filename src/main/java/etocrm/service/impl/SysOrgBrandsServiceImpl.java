package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.database.util.SysUserRedisVO;
import org.etocrm.entity.BrandsWechatInfo;
import org.etocrm.entity.BrandsWechatRelation;
import org.etocrm.entity.SysOrg;
import org.etocrm.entity.SysOrgBrands;
import org.etocrm.enums.OtherSystemUrlTypeEnum;
import org.etocrm.model.brands.*;
import org.etocrm.model.openAccount.GrantBrandInfo;
import org.etocrm.repository.BrandsWechatInfoRepository;
import org.etocrm.repository.BrandsWechatRelationRepository;
import org.etocrm.repository.SysOrgBrandsRepository;
import org.etocrm.repository.SysOrgRepository;
import org.etocrm.service.SysOrgBrandsService;
import org.etocrm.utils.OtherSystemNoticeUtil;
import org.etocrm.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 机构品牌信息 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-23
 */
@Service
@Slf4j
public class SysOrgBrandsServiceImpl implements SysOrgBrandsService {

    @Resource
    private SysOrgBrandsRepository sysOrgBrandsRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Resource
    private SysOrgRepository sysOrgRepository;
    @Resource
    private BrandsWechatRelationRepository brandsWechatRelationRepository;
    @Resource
    private BrandsWechatInfoRepository brandsWechatInfoRepository;
    @Resource
    private OtherSystemNoticeUtil otherSystemNoticeUtil;


    @Override
    @Transactional
    public void saveSysOrgBrands(SysBrandsVO sysOrgBrands) throws MyException {
        List<SysOrgBrands> s = new ArrayList<>();
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        for (SysOrgBrandsSaveVO orgBrandsSaveVO : sysOrgBrands.getBrandsList()) {
            //校验品牌
            if (sysOrgBrandsRepository.countByOrgIdAndName(loginUser.getUamOrgId(), orgBrandsSaveVO.getName()) > 0) {
                throw new MyException(ResponseEnum.FAILD.getCode(), "当前机构下，品牌名称重复");
            }
            SysOrgBrands sysOrgBrands1 = new SysOrgBrands();
            BeanUtil.copyProperties(orgBrandsSaveVO, sysOrgBrands1);
            sysOrgBrands1.setOrgId(loginUser.getUamOrgId());
            sysOrgBrands1.setStatus(1);
            sysOrgBrandsRepository.save(sysOrgBrands1);
            sysOrgBrands1.setCrmBrandId(sysOrgBrands1.getId());
            sysOrgBrandsRepository.update(sysOrgBrands1);
            s.add(sysOrgBrands1);
        }
        //下发
        grantBrandInfo(loginUser.getUamOrgId(), s);
    }

    /**
     * @param orgId
     * @param s
     */
    private void grantBrandInfo(Long orgId, List<SysOrgBrands> s) throws MyException {
        List<GrantBrandInfo> grantBrandInfos = new ArrayList<>();
        for (SysOrgBrands sysOrgBrands : s) {
            GrantBrandInfo grantBrandInfo = new GrantBrandInfo();
            grantBrandInfo.setCrmBrandId(sysOrgBrands.getId());
            grantBrandInfo.setUamBrandId(sysOrgBrands.getId());
            grantBrandInfo.setCrmOrgId(orgId);
            grantBrandInfo.setStatus(sysOrgBrands.getStatus());
            grantBrandInfo.setUamOrgId(orgId);
            grantBrandInfo.setName(sysOrgBrands.getName());
            grantBrandInfos.add(grantBrandInfo);
        }
        log.info("下发品牌参数：{}", JSON.toJSONString(grantBrandInfos));
        otherSystemNoticeUtil.restTemplate(orgId, null, JSON.toJSONString(grantBrandInfos), OtherSystemUrlTypeEnum.BRAND_NOTICE_REDIRECT);

    }

    @Override
    public Long updateByPk(SysOrgBrands sysOrgBrands) throws MyException {
        //校验品牌
        if (sysOrgBrandsRepository.countByNameAndIdNot(sysOrgBrands.getName(), sysOrgBrands.getId()) > 0) {
            throw new MyException(ResponseEnum.FAILD.getCode(), "当前机构下，品牌名称重复");
        }
        SysOrgBrands update = sysOrgBrandsRepository.update(sysOrgBrands);
        //下发
        List<SysOrgBrands> s = new ArrayList<>();
        s.add(update);
        grantBrandInfo(update.getOrgId(), s);
        return sysOrgBrands.getId();
    }

    @Override
    public void deleteByPk(Long pk) {
        sysOrgBrandsRepository.logicDelete(pk);
    }

    @Override
    public SysOrgBrands detailByPk(Long pk) {
        Optional<SysOrgBrands> byId = sysOrgBrandsRepository.findById(pk);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    @Override
    public BasePage<SysOrgBrandsListVO> list(Integer curPage, Integer size, SysOrgBrandsSelectVO sysOrgBrands) {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        Set<Long> collect = new HashSet<>();
        if (StringUtils.isNotBlank(sysOrgBrands.getOrgName())) {
            List<SysOrg> byName = sysOrgRepository.findByNameLike("%" + sysOrgBrands.getOrgName() + "%");
            collect = byName.stream().map(SysOrg::getId).collect(Collectors.toSet());
        }
        Set<Long> finalCollect = collect;
        Page<SysOrgBrands> sysParamIPage = sysOrgBrandsRepository.findAll((Specification<SysOrgBrands>) (root, query, cb) -> {
                    //集合 用于封装查询条件
                    List<Predicate> list = new ArrayList<>();
                    //简单单表查询
                    if (StringUtils.isNotEmpty(sysOrgBrands.getName())) {
                        Predicate code = cb.like(root.get("name").as(String.class), "%" + sysOrgBrands.getName() + "%");
                        list.add(code);
                    }
                    if (null != loginUser.getUamOrgId()) {
                        Predicate billingStatus = cb.equal(root.get("orgId").as(Integer.class), loginUser.getUamOrgId());
                        list.add(billingStatus);
                    }
                    if (!finalCollect.isEmpty()) {
                        CriteriaBuilder.In<Long> orgId = cb.in(root.get("orgId"));
                        for (Long id : finalCollect) {
                            orgId.value(id);
                        }
                        list.add(orgId);
                    }
                    return cb.and(list.toArray(new Predicate[0]));
                }
                , PageRequest.of(curPage, size,
                        Sort.by(Sort.Direction.DESC, "updatedTime")));
        BasePage basePage = new BasePage<>(sysParamIPage);
        List<SysOrgBrands> records = (List<SysOrgBrands>) basePage.getRecords();
        List<SysOrgBrandsListVO> transformation = this.sysOrgBrandsListVOS(records);
        basePage.setRecords(transformation);
        return basePage;
    }

    @Override
    public void updateStatus(UpdateBrandsStatusVO vo) throws MyException {
        try {
            SysOrgBrands sysOrgBrands1 = new SysOrgBrands();
            BeanUtil.copyProperties(vo, sysOrgBrands1);
            SysOrgBrands update = sysOrgBrandsRepository.update(sysOrgBrands1);
            //下发
            List<SysOrgBrands> s = new ArrayList<>();
            s.add(update);
            grantBrandInfo(update.getOrgId(), s);
        } catch (MyException e) {
            log.error(e.getMessage(), e);
            throw new MyException(ResponseEnum.FAILD.getCode(), "更新品牌状态失败");
        }
    }

    @Override
    public List<SysOrgBrandsListVO> findAll(SysOrgBrandsAllVO sysOrgBrandsSelectVO) {
        SysUserRedisVO currentLoginUser = securityUtil.getCurrentLoginUser();
        sysOrgBrandsSelectVO.setOrgId(currentLoginUser.getUamOrgId());
        List<SysOrgBrands> all = sysOrgBrandsRepository.findAll((r, q, c) -> new QueryConditionUtil().where(sysOrgBrandsSelectVO, r, c));
        return this.sysOrgBrandsListVOS(all);
    }

    private List<SysOrgBrandsListVO> sysOrgBrandsListVOS(List<SysOrgBrands> records) {
        List<SysOrgBrandsListVO> listVOS = new ArrayList<>();
        records.forEach(s -> {
            SysOrgBrandsListVO vo = new SysOrgBrandsListVO();
            BeanUtil.copyProperties(s, vo);
            vo.setAppServiceName(this.getAppName(s.getId()));
            vo.setMinAppName(this.getMinAppName(s.getId()));
            if (null != s.getOrgId()) {
                Optional<SysOrg> byId = sysOrgRepository.findById(s.getOrgId());
                if (byId.isPresent()) {
                    vo.setOrgName(byId.get().getName());
                }
            }
            listVOS.add(vo);

        });

        return listVOS;

    }

    /**
     * 公众号
     *
     * @param brandsId
     * @return
     */
    private String getAppName(Long brandsId) {
        List<BrandsWechatRelation> service = brandsWechatRelationRepository.findByBrandsIdAndType(brandsId, "service");
        List<Long> filterIds = service.stream().map(BrandsWechatRelation::getCommonId).collect(Collectors.toList());
        List<BrandsWechatInfo> all = brandsWechatInfoRepository.findAllById(filterIds);
        if (CollectionUtil.isNotEmpty(all)) {
            List<String> collect = all.stream().map(BrandsWechatInfo::getWechatName).collect(Collectors.toList());
            return String.join(",", collect);
        }
        return "";
    }


    /**
     * 小程序
     *
     * @param brandsId
     * @return
     */
    private String getMinAppName(Long brandsId) {
        List<BrandsWechatRelation> service = brandsWechatRelationRepository.findByBrandsIdAndType(brandsId, "miniapp");
        List<Long> filterIds = service.stream().map(BrandsWechatRelation::getCommonId).collect(Collectors.toList());
        List<BrandsWechatInfo> all = brandsWechatInfoRepository.findAllById(filterIds);
        if (CollectionUtil.isNotEmpty(all)) {
            List<String> collect = all.stream().map(BrandsWechatInfo::getWechatName).collect(Collectors.toList());
            return String.join(",", collect);
        }
        return "";
    }
}
