package etocrm.model.org;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.model.org.invoice.SysInvoiceUpdateVO;
import org.etocrm.model.org.orgAuth.SysOrgAuthVO;

/**
 * @Author: dkx
 * @Date: 15:27 2021/1/21
 * @Desc:
 */
@ApiModel(value = "机构表 save vo")
@Data
public class SysOrgDetailVO {

    @ApiModelProperty(value = "基本信息")
    private SysOrgUpdateVO basics;

    @ApiModelProperty(value = "认证信息")
    private SysOrgAuthVO auth;

    @ApiModelProperty(value = "开票信息")
    private SysInvoiceUpdateVO invoice;
}
