package etocrm.model.permission;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("菜单")
@Data
public class SysPermissionVO implements Serializable {
    private static final long serialVersionUID = -2099147126084213856L;

    @ApiModelProperty("菜单编号")
    private Long id;

    @ApiModelProperty("父id")
    private Long parentId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单类型 菜单1/ 组件按钮 2")
    private Integer isButton;

    @ApiModelProperty(value = "0 启用 1禁用")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedTime;

    @ApiModelProperty("最后操作人")
    private String updateUser;

    private List<SysPermissionVO> subMenu;

    private boolean hasMenu = false;
}

