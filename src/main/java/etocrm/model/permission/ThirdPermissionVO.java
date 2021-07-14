package etocrm.model.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdPermissionVO {

    @ApiModelProperty("菜单id")
    private Long id;
    @ApiModelProperty("菜单编号")
    private String code;

    @ApiModelProperty("父id")
    private Long parentId;

    @ApiModelProperty("所属系统id")
    private String systemId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty(value = "1菜单 2组件按钮 ")
    private Integer isButton;

    @ApiModelProperty(value = "启用/禁用 1启用/0禁用")
    private Integer status;

    @ApiModelProperty(value = "层级")
    private Integer level;

}
