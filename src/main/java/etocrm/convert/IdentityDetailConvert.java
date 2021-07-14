package etocrm.convert;

import org.etocrm.entity.SysOrgCompany;
import org.etocrm.model.CompanyDetailVO;
import org.etocrm.model.OnLineShopVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @Author xingxing.xie
 * @Date 2021/6/18 13:56
 */
@Mapper(componentModel = "spring")
public interface IdentityDetailConvert {

    /**
     *  转换为 线上店铺 vo
     * @param sysOrgCompany
     * @return
     */
    @Mappings({
            @Mapping(source = "name",target = "shopName"),
            @Mapping(source = "id",target = "crmShopId")
    })
    OnLineShopVo toShopVo(SysOrgCompany sysOrgCompany);

    /**
     *  转换为 线上店铺 voList
     * @param sysOrgCompanyList
     * @return
     */
    List<OnLineShopVo> toShopVoList(List<SysOrgCompany> sysOrgCompanyList);

    /**
     *  转公司 vo
     * @param sysOrgCompany
     * @return
     */
    @Mappings({
            @Mapping(source = "name",target = "companyName"),
            @Mapping(source = "id",target = "companyId"),
            @Mapping(source = "type",target = "companyType"),
    })
    CompanyDetailVO toCompanyVo(SysOrgCompany sysOrgCompany);

    /**
     *  转换为 公司voList
     * @param sysOrgCompanyList
     * @return
     */
    List<CompanyDetailVO> toCompanyVoList(List<SysOrgCompany> sysOrgCompanyList);
}
