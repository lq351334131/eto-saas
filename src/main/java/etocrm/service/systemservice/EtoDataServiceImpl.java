package etocrm.service.systemservice;

import lombok.extern.slf4j.Slf4j;
import org.etocrm.convert.IdentityDetailConvert;
import org.etocrm.entity.SysOrgCompany;
import org.etocrm.entity.UserRole;
import org.etocrm.model.AccountIdentityVo;
import org.etocrm.model.CompanyDetailVO;
import org.etocrm.model.IdentityDetail;
import org.etocrm.repository.SysOrgCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * etoShop data后台
 * @Author xingxing.xie
 * @Date 2021/6/18 10:58
 */
@Component(value = "ETODATA")
@Slf4j
public class EtoDataServiceImpl implements SystemTypeService {
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
                List<SysOrgCompany> byOpenShopAndOrgId = repository.findByOpenShopAndOrgId(2, result.getOrgId());
                List<CompanyDetailVO> companyDetailVOS = convert.toCompanyVoList(byOpenShopAndOrgId);
                identityDetail.setCompanyDetailVOList(companyDetailVOS);
            }else {
                //若不是管理员
                //查询店铺列表
                Long companyId = userRole.getCompanyId();
                if(null!=companyId){
                    SysOrgCompany sysOrgCompany = repository.findByOpenShopAndId(2,companyId);
                    if(null!=sysOrgCompany){
                        identityDetail.setCompanyId(sysOrgCompany.getId());
                        identityDetail.setCompanyName(sysOrgCompany.getName());
                        identityDetail.setCompanyType(sysOrgCompany.getType());
                    }
                }

            }
            identityDetails.add(identityDetail);
        }
        result.setIdentityDetails(identityDetails);

    }
}
