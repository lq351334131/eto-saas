package etocrm.model.minapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BrandWechatInfoVO {

    @ApiModelProperty("微信昵称")
    private String wechatName;
    @ApiModelProperty("微信id")
    private Long  id;

    @ApiModelProperty("logo")
    private String logo;
}
