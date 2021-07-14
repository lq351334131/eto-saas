package etocrm.service.systemservice;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.convert.IdentityDetailConvert;
import org.etocrm.entity.SysOrgCompany;
import org.etocrm.entity.UserRole;
import org.etocrm.model.AccountIdentityVo;
import org.etocrm.model.IdentityDetail;
import org.etocrm.model.OnLineShopVo;
import org.etocrm.repository.SysOrgCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * etoShop 电商后台
 * @Author xingxing.xie
 * @Date 2021/6/18 10:58
 */
@Component(value = "ETOSHOP")
@Slf4j
public class EtoShopServiceImpl implements SystemTypeService {
    @Autowired
    private SysOrgCompanyRepository repository;
    @Autowired
    private IdentityDetailConvert convert;
    @Override
    public void getResultBySystem(AccountIdentityVo result, List<UserRole> userRoleList, Map<Long, String> roleNameMap) {

        List<IdentityDetail> identityDetails = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            //获取账号 类型
            Integer accountType = userRole.getAccountType();
            IdentityDetail identityDetail = new IdentityDetail()
                    .setRoleId(userRole.getRoleId())
                    .setRoleName(roleNameMap.get(userRole.getRoleId()))
                    .setAccountType(accountType);
            //判断 是否 管理员
            boolean isManager= null==accountType?false:accountType.equals(1)?true:false;
            //若是管理员
            if(isManager){
                //查询机构下所有店铺
                List<SysOrgCompany> byOpenShopAndOrgId = repository.findByOpenShopAndOrgId(1, result.getOrgId());
                List<OnLineShopVo> onLineShopVos = convert.toShopVoList(byOpenShopAndOrgId);
                identityDetail.setShopList(onLineShopVos);
            }else {
                //若不是管理员
                //查询店铺列表
                String shopIdStr = userRole.getShopId();
                if(StrUtil.isNotEmpty(shopIdStr)){
                    String[] split = shopIdStr.split(",");
                    List<Long> shopIds = Arrays.asList(split).stream().map(t -> Long.valueOf(t)).collect(Collectors.toList());
                    List<SysOrgCompany> SysOrgCompany = repository.findByOpenShopAndIdIn(1,shopIds);
                    List<OnLineShopVo> onLineShopVos = convert.toShopVoList(SysOrgCompany);
                    identityDetail.setShopList(onLineShopVos);
                }

            }
            identityDetails.add(identityDetail);
        }
        result.setIdentityDetails(identityDetails);

    }
}
