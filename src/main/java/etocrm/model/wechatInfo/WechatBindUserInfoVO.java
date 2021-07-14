package etocrm.model.wechatInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class WechatBindUserInfoVO {
    private String id;
    @ApiModelProperty(value = "用户Id")
    @NotNull(message = "userId 不能为空")
    private Long userId;

    private String ticket;

    @NotBlank(message = "用户接口凭证不能为空")
    @ApiModelProperty(value = "用户接口凭证")
    private String code;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    private String unionid;

    private String openid;

    @ApiModelProperty(value = "1男2女")
    private Integer sex;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "头像")
    private String headimgUrl;

    @ApiModelProperty(value = "语言")
    private String language;
    @ApiModelProperty(value = "绑定时间")
    private Date createdTime;
}
