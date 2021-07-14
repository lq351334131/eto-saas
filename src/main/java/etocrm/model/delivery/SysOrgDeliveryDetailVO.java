package etocrm.model.delivery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.model.org.invoice.SysInvoiceUpdateVO;

import java.io.Serializable;

@ApiModel(value = "物流开票详情")
@Data
public class SysOrgDeliveryDetailVO implements Serializable {
    private static final long serialVersionUID = 3883441211701627549L;

    @ApiModelProperty(value = "物流信息")
    private SysOrgDeliveryResponseVO sysOrgDeliveryResponseVO;

    @ApiModelProperty(value = "开票信息")
    private SysInvoiceUpdateVO sysInvoiceUpdateVO;
}
