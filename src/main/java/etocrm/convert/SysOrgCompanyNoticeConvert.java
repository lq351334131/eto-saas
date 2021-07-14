package etocrm.convert;

import org.etocrm.entity.SysOrgCompany;
import org.etocrm.model.company.SysOrgCompanyNoticeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @description: 分公司 新增修改 映射器
 * @author xingxing.xie
 * @date 2021/6/8 18:05
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface SysOrgCompanyNoticeConvert {

    /**
     * 转换对应的vo类
     * @param sysOrgCompany
     * @return
     */
    @Mappings({
            @Mapping(source = "orgId",target = "uamOrgId"),
            @Mapping(source = "orgId",target = "crmOrgId"),
            @Mapping(source = "id",target = "crmCompanyId"),
    })
    SysOrgCompanyNoticeVO doToVo(SysOrgCompany sysOrgCompany);




}
