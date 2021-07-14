package etocrm.model.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PermissionUpdateVO implements Serializable {

    private static final long serialVersionUID = 4203513679021665311L;
    @NotNull(message = "菜单Id不能为空")
    @ApiModelProperty(value = "菜单Id")
    private Long id;

    @NotNull(message = "所属系统不能为空")
    @ApiModelProperty(value = "所属系统")
    private Integer systemId;

    @NotNull(message="菜单类型不能为空")
    @ApiModelProperty(value = "菜单、组件按钮")
    private Integer isButton;

    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value = "名称")
    private String menuName;

    @NotBlank(message = "路由地址不能为空")
    @ApiModelProperty(value = "路由地址")
    private String menuRoute;

    @ApiModelProperty(value = "图标")
    private String icon;

    @NotNull(message = "排序不能为空")
    private Integer menuOrder;

    @ApiModelProperty(value = "显示类型 0无权限隐藏 1无权限禁用")
    private Integer showType;

    @ApiModelProperty(value = "功能类型")
    private Integer functionType;

    @NotNull(message = "功能状态不能为空")
    @ApiModelProperty(value = "0启用、1禁用、2新功能预告")
    private Integer status;

    @NotNull(message = "功能定价不能为空")
    @ApiModelProperty(value = "功能定价")
    private Integer functionPricing;

    @ApiModelProperty(value = "功能描述")
    private String functionDescribe;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "绑定类型  0无 1 营销活动 2 货架组件")
    private Integer boundType;

    @ApiModelProperty(value = "绑定值")
    private String boundValue;
}
