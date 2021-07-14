package etocrm.model.org;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.model.brands.SysBrandsVO;
import org.etocrm.model.org.invoice.SysInvoiceSaveVO;
import org.etocrm.model.org.orgAuth.SysOrgAuthSaveVO;
import org.etocrm.model.user.org.ResUserSaveVO;

/**
 * @Author: dkx
 * @Date: 15:27 2021/1/21
 * @Desc:
 */
@ApiModel(value = "机构表 save vo")
@Data
public class SysOrgVO {

    @ApiModelProperty(value = "基本信息")
    private SysOrgSaveVO basics;

    @ApiModelProperty(value = "认证信息")
    private SysOrgAuthSaveVO auth;

    @ApiModelProperty(value = "开票信息")
    private SysInvoiceSaveVO invoice;

    @ApiModelProperty(value = "品牌信息")
    private SysBrandsVO sysOrgBrands;

    @ApiModelProperty(value = "用户信息")
    ResUserSaveVO userSaveVO;
}
