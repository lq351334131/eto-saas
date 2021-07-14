package etocrm.model.minapp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "品牌公众号小程序信息")
@Data
public class BrandsWechatListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long organizationId;

    @ApiModelProperty(value = "类型：：miniapp（小程序）、service（公众号）")
    private String type;

    @ApiModelProperty(value = "头像")
    private String logo;

    @ApiModelProperty(value = "微信名称")
    private String wechatName;

    @ApiModelProperty(value = "状态0启用1禁用")
    private Integer status;

    @ApiModelProperty(value = "认证情况 0 微信认证 1 未认证")
    private Integer isAuth;

    @ApiModelProperty(value = "接入方式 base 开发模式接入 open 第三方接入")
    private String joinWay;

}