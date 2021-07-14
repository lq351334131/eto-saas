package etocrm.model.minapp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "品牌公众号小程序信息")
@Data
public class BrandsWechatInfoUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "不可为空")
    private Long id;
    @ApiModelProperty(value = "机构id")
    @NotNull(message = "不可为空")
    private Long organizationId;
    @ApiModelProperty(value = "appid")
    @NotBlank(message = "不可为空")
    private String appid;
    @ApiModelProperty(value = "app_secret")
    @NotBlank(message = "不可为空")
    private String appkey;
    @ApiModelProperty(value = "accesstoken_fetch")
    private String accesstokenFetch;
    @ApiModelProperty(value = "token")
    @NotBlank(message = "不可为空")
    private String token;
    @ApiModelProperty(value = "加密密钥")
    @NotBlank(message = "不可为空")
    private String aeskey;
    @NotBlank(message = "不可为空")
    private String  encryptCode;

    @ApiModelProperty(value = "接入方式 'base' 开发者模式  ,'open'  第三方接入")
    private String joinWay;

    @ApiModelProperty(value = "类型： service 服务号  subscribe 订阅号  miniapp 小程序 ")
    private String type;

    @ApiModelProperty(value = "认证情况 0 微信认证 1 未认证")
    private Integer isAuth;

    @ApiModelProperty(value = "微信支付 0 已开通 1未开通")
    private Integer isPay;

    @ApiModelProperty(value = "微信号")
    private String wxid;

    @ApiModelProperty(value = "原始id")
    @NotBlank(message = "不可为空")
    private String wechatOriginalId;

    @ApiModelProperty(value = "名称")
    private String wechatName;

    @ApiModelProperty(value = "头像")
    private String logo;
    @ApiModelProperty(value = "服务器地址")
    private String connectUrl;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "支付宝公钥")
    private String aliPublicKey;
    @ApiModelProperty(value = "支付宝小程序应用公钥")
    private String aliApplicationPublicKey;
    @ApiModelProperty(value = "支付宝小程序应用私钥")
    private String aliApplicationPrivateKey;

}