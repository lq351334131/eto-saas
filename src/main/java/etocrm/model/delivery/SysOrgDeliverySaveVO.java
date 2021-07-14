package etocrm.model.delivery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "物流信息")
@Data
public class SysOrgDeliverySaveVO implements Serializable {


    private static final long serialVersionUID = 5419950631615391145L;
    @ApiModelProperty(value = "订单id")
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @ApiModelProperty(value = "快递公司")
    private String company;

    @ApiModelProperty(value = "快递号")
    private String number;

    @ApiModelProperty(value = "备注")
    private String remarks;

}