package etocrm.model.permission;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel("菜单")
@Data
public class PermissionVO implements Serializable {
    private static final long serialVersionUID = -2099147126084213856L;

    @ApiModelProperty("菜单id")
    private Long id;
    @ApiModelProperty("菜单编号")
    private String code;

    @ApiModelProperty("父id")
    private Long parentId;

    @ApiModelProperty("icon")
    private String icon;

    @ApiModelProperty("权限flag")
    private boolean authFlag;

    @ApiModelProperty("route")
    private String menuRoute;

    @ApiModelProperty("所属系统id")
    private String systemCode;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单级别 1父级菜单 2 可点击菜单")
    private Integer menuType;

    @ApiModelProperty(value = "是否默认重定向子菜单 1是 0否")
    private boolean redirectFlag;

    @ApiModelProperty(value = "1菜单 2组件按钮 ")
    @JSONField(name = "isButton")
    private boolean isButton;

    @ApiModelProperty(value = "显示类型 0无权限隐藏 1无权限禁用")
    private Integer showType;

    @ApiModelProperty(value = "显示类型 0隐藏 1显示")
    @JSONField(name = "isDisplay")
    private boolean isDisplay;

    @ApiModelProperty(value = "显示类型 0不展开 1展开")
    @JSONField(name = "isOpen")
    private boolean isOpen;

    private List<PermissionVO> subMenu;

    private boolean hasMenu = false;

    @ApiModelProperty(value = "绑定类型  0无 1 营销活动 2 货架组件")
    private Integer boundType;

    @ApiModelProperty(value = "绑定值")
    private String boundValue;
}

