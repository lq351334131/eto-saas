package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.PageBounds;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.database.util.SysUserRedisVO;
import org.etocrm.entity.BrandsWechatInfo;
import org.etocrm.entity.BrandsWechatRelation;
import org.etocrm.enums.BusinessEnum;
import org.etocrm.exception.UamException;
import org.etocrm.model.brands.SysBrandsWechatInfoVO;
import org.etocrm.model.enums.JoinWay;
import org.etocrm.model.enums.WechatType;
import org.etocrm.model.minapp.*;
import org.etocrm.repository.BrandsWechatInfoRepository;
import org.etocrm.repository.BrandsWechatRelationRepository;
import org.etocrm.service.BrandsWechatInfoService;
import org.etocrm.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 品牌公众号小程序信息 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Service
@Slf4j
public class BrandsWechatInfoServiceImpl implements BrandsWechatInfoService {

    @Resource
    private BrandsWechatInfoRepository brandsWechatInfoRepository;
    @Resource
    private BrandsWechatRelationRepository brandsWechatRelationRepository;
    @Autowired
    private SecurityUtil securityUtil;


    @Override
    public Long saveBrandsWechatService(BrandsWechatInfoSaveVO brandsWechatService) {

        //appId唯一
        BrandsWechatInfo queryInfo =  brandsWechatInfoRepository.findByAppid(brandsWechatService.getAppid());
        if(queryInfo!=null) {
            throw new UamException(ResponseEnum.FAILD.getCode(),"appid已存在");
        }
        BrandsWechatInfo brandsWechatInfo = new BrandsWechatInfo();
        BeanUtil.copyProperties(brandsWechatService, brandsWechatInfo);
        brandsWechatInfoRepository.save(brandsWechatInfo);
        return brandsWechatInfo.getId();
    }

    @Override
    public Long updateByPk(BrandsWechatInfoUpdateVO brandsWechatService) throws MyException {

        //appId唯一
        BrandsWechatInfo queryInfo =  brandsWechatInfoRepository.findByAppidAndIdNot(brandsWechatService.getAppid(),brandsWechatService.getId());
        if(queryInfo!=null) {
            throw new UamException(ResponseEnum.FAILD.getCode(),"appid已存在");
        }

        BrandsWechatInfo brandsWechatInfo = new BrandsWechatInfo();
        BeanUtil.copyProperties(brandsWechatService, brandsWechatInfo);
        brandsWechatInfoRepository.update(brandsWechatInfo);
        return brandsWechatInfo.getId();
    }

    @Override
    public void updateStatus(UpdateMinAppStatusVO pk) throws MyException {
        BrandsWechatInfo brandsWechatInfo = new BrandsWechatInfo();
        BeanUtil.copyProperties(pk, brandsWechatInfo);
        brandsWechatInfoRepository.update(brandsWechatInfo);
    }

    @Override
    public BrandsWechatInfoUpdateVO detailByPk(Long pk) {
        Optional<BrandsWechatInfo> byId = brandsWechatInfoRepository.findById(pk);
        BrandsWechatInfoUpdateVO vo = new BrandsWechatInfoUpdateVO();
        if (byId.isPresent()) {
            BeanUtil.copyProperties(byId.get(), vo);
        }
        return vo;
    }

    @Override
    public BasePage<BrandsWechatListVO> list(Integer curPage, Integer size, BrandsWechatSelectVO brandsWechatService) throws MyException {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        brandsWechatService.setOrganizationId(loginUser.getUamOrgId());
        PageBounds pageBounds = new PageBounds(curPage, size);
        Page<BrandsWechatInfo> brandsWechatServices = brandsWechatInfoRepository.findAll((r, q, c) ->
                new QueryConditionUtil().where(brandsWechatService, r, c), PageRequest.of(pageBounds.getOffset(), pageBounds.getLimit()));
        BasePage basePage = new BasePage<>(brandsWechatServices);
        List<BrandsWechatInfo> records = (List<BrandsWechatInfo>) basePage.getRecords();
        List<BrandsWechatListVO> transformation = this.sysOrgListVOS(records);
        basePage.setRecords(transformation);
        return basePage;
    }

    @Override
    public  List<BrandWechatInfoVO> getDetailByType(SysBrandsWechatInfoVO vo) {
        List<BrandsWechatRelation> relations = brandsWechatRelationRepository.findByType(vo.getType());
        List<BrandsWechatInfo> wechatInfos ;
        if(vo.getBrandsId()==null) {
           // wechatInfos =brandsWechatInfoRepository.findByTypeAndStatus(vo.getType(), BusinessEnum.USING.getCode());
            wechatInfos=brandsWechatInfoRepository.findByOrganizationIdAndTypeAndStatus(securityUtil.getCurrentLoginUser().getUamOrgId(),vo.getType(), BusinessEnum.USING.getCode());
        }else {
            wechatInfos =brandsWechatInfoRepository. findByOrganizationIdAndBrandsIdAndTypeAndStatus(securityUtil.getCurrentLoginUser().getUamOrgId(),vo.getBrandsId(),vo.getType(), BusinessEnum.USING.getCode());
        }
        List<Long> filertIds = relations.stream().map(BrandsWechatRelation::getCommonId).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(relations)) {
            wechatInfos = wechatInfos.stream().filter(item -> !filertIds.contains(item.getId())).collect(Collectors.toList());
        }
        List<BrandWechatInfoVO> res= Lists.newArrayList();
        for (BrandsWechatInfo wechatInfo:wechatInfos) {
            BrandWechatInfoVO vo1 = new BrandWechatInfoVO();
            BeanUtil.copyProperties(wechatInfo,vo1);
            res.add(vo1);
        }
        return res;
    }

    @Override
    public Long bindBrandsAndMiniApp(BindParam bindParam) {
        //删除之前绑定数据
        List<BrandsWechatRelation> brandsId = brandsWechatRelationRepository.findByBrandsIdAndType(bindParam.getBrandsId(),bindParam.getType());
        brandsWechatRelationRepository.deleteAll(brandsId);
        if(CollectionUtil.isNotEmpty(bindParam.getCommonId())) {
            for (Long commonId : bindParam.getCommonId()) {
                BrandsWechatRelation brandsWechatRelation = new BrandsWechatRelation();
                brandsWechatRelation.setBrandsId(bindParam.getBrandsId());
                brandsWechatRelation.setType(bindParam.getType());
                brandsWechatRelation.setCommonId(commonId);
                //绑定小程序
                brandsWechatRelationRepository.save(brandsWechatRelation);
            }
        }

        return 1L;
    }

    @Override
    public List<BrandWechatInfoVO> getBindedWechatInfo(Long brandId,String type) {

        List<BrandsWechatRelation> relations =brandsWechatRelationRepository.findByBrandsIdAndType(brandId,type);
        List<Long> ids = relations.stream().map(BrandsWechatRelation::getCommonId).collect(Collectors.toList());
        List<BrandsWechatInfo> allById = brandsWechatInfoRepository.findAllById(ids);
        List<BrandWechatInfoVO> res = Lists.newArrayList();
        for (BrandsWechatInfo b:allById) {
            BrandWechatInfoVO brandWechatInfoVO = new BrandWechatInfoVO();
            BeanUtil.copyProperties(b,brandWechatInfoVO);
            res.add(brandWechatInfoVO);

        }
        return res;
    }

    @Override
    public   List<BrandsWechatInfoUpdateVO>  detailByBrandsId(Long pk,String type) {
        List<BrandsWechatInfoUpdateVO> res= new ArrayList<>();
        List<BrandsWechatRelation> byBrandsId = brandsWechatRelationRepository.findByBrandsIdAndType(pk,type);
        for (BrandsWechatRelation wechatRelation: byBrandsId) {
            Optional<BrandsWechatInfo> byId = brandsWechatInfoRepository.findById(wechatRelation.getCommonId());
            BrandsWechatInfoUpdateVO vo = new BrandsWechatInfoUpdateVO();
            if(byId.isPresent()) {
                BeanUtil.copyProperties(byId.get(), vo);
                res.add(vo);
            }

        }
        return res;
    }

    @Override
    public Long dismissBrandsWechatInfo(DissmissBindParam bindParam) {

        List<BrandsWechatRelation> deleteList = brandsWechatRelationRepository.findByBrandsIdAndCommonId(bindParam.getBrandsId(), bindParam.getCommonId());
        brandsWechatRelationRepository.deleteAll(deleteList);
        return 1L;
    }

    @Override
    public List<BrandsWechatInfo> findAll(BrandsWechatInfo brandsWechatService) {
        return brandsWechatInfoRepository.findAll((r, q, c) -> {
            return (new QueryConditionUtil()).where(brandsWechatService, r, c);
        });
    }

    /**
     * 集合返回数据
     *
     * @param all
     * @return
     */
    private List<BrandsWechatListVO> sysOrgListVOS(List<BrandsWechatInfo> all) throws MyException {
        List<BrandsWechatListVO> listVOS = new ArrayList<>();
        BrandsWechatListVO sysOrgListVO;
        try {
            for (BrandsWechatInfo org : all) {
                sysOrgListVO = new BrandsWechatListVO();
                BeanUtil.copyProperties(org, sysOrgListVO);
                sysOrgListVO.setJoinWay(JoinWay.get(org.getJoinWay()));
                sysOrgListVO.setType(WechatType.get(org.getType()));
                listVOS.add(sysOrgListVO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException(ResponseEnum.FAILD.getCode(), "返回微信列表错误");
        }
        return listVOS;
    }
}
