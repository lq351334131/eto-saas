package etocrm.model.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PermissionResponseVO implements Serializable {

 private static final long serialVersionUID = 2616685276181219382L;
 @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "所属系统")
    private Long systemId;

    @ApiModelProperty(value = "系统名称")
    private String systemName;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "名称")
    private String menuName;

    @ApiModelProperty(value = "菜单编码")
    private String code;

    @ApiModelProperty(value = "对应父级")
    private Long parentId;

    @ApiModelProperty(value = "路由地址")
    private String menuRoute;

    @ApiModelProperty(value = "启用、禁用、新功能预告")
    private Integer status;

    @ApiModelProperty(value = "菜单级别")
    private Integer level;

    @ApiModelProperty(value = "排序")
    private Integer menuOrder;


    @ApiModelProperty(value = "菜单、组件按钮")
    private Integer isButton;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "功能类型")
    private Integer functionType;

    @ApiModelProperty(value = "功能定价")
    private Integer functionPricing;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "描述")
    private String functionDescribe;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "显示类型 0无权限隐藏 1无权限禁用")
    private Integer showType;

   @ApiModelProperty(value = "绑定类型  0无 1 营销活动 3 货架组件")
   private Integer boundType;

   @ApiModelProperty(value = "绑定值")
   private String boundValue;


}
