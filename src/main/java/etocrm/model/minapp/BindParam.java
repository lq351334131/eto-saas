package etocrm.model.minapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BindParam {
    @ApiModelProperty("品牌Id")
    private Long brandsId;

    @ApiModelProperty("小程序Id/公众号id")
    private List<Long> commonId;
    @ApiModelProperty("公众号小程序type")
    private String type;

}
