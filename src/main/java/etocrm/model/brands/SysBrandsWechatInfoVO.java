package etocrm.model.brands;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysBrandsWechatInfoVO {

    @ApiModelProperty("小程序和公众号type")
    private String type;

    @ApiModelProperty("品牌id")
    private Long brandsId;


}
