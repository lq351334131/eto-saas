package etocrm.model.minapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DissmissBindParam {
    @ApiModelProperty("品牌Id")
    private Long brandsId;

    @ApiModelProperty("小程序Id/公众号id")
    private Long commonId;
}
