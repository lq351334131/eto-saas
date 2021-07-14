package etocrm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author xingxing.xie
 * @Date 2021/6/17 15:01
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "线上店铺实体类")
public class OnLineShopVo implements Serializable {

    private static final long serialVersionUID = 3555627163948851702L;
    @ApiModelProperty(value = "店铺id")
    private Long id;

    @ApiModelProperty(value = "crm对应店铺id")
    private Long crmShopId;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty("状态（启用禁用）")
    private Integer status ;


}
