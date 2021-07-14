package etocrm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xingxing.xie
 * @Date 2021/6/18 11:25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "账号角色详情")
public class AccountRoleDetail implements Serializable {
    private static final long serialVersionUID = -7605613248368709532L;

    @ApiModelProperty(value = "账号id")
    private Long userId;
    @ApiModelProperty(value = "账号名称")
    private String userName;
    @ApiModelProperty(value = "登录账号名称")
    private String loginName;

    @ApiModelProperty(value = "是否管理员1-是、0-不是")
    private Integer isAdmin;

    @ApiModelProperty(value = "身份类型(角色id)")
    private Long roleId;

    @ApiModelProperty(value = "账号类型：1-管理员/2-分公司运营账号(线上数据运营账号)/3-线上店铺")
    private Integer accountType;

    @ApiModelProperty(value = "已选店铺")
    private List<OnLineShopVo> shopList;
}
