package etocrm.model.delivery;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "物流信息")
@Data
public class SysOrgDeliveryResponseVO implements Serializable {


    private static final long serialVersionUID = -855863959569672730L;
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "快递公司")
    private String company;

    @ApiModelProperty(value = "快递号")
    private String number;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @ApiModelProperty(value = "最后操作时间")
    private Date lastOperationTime;

    @ApiModelProperty(value = "创建人")
    private String createPerson;

}