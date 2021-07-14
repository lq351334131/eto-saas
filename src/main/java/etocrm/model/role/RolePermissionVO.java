package etocrm.model.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RolePermissionVO {
    @NotNull(message = "角色关联的权限Id不能为空")
    @ApiModelProperty(value = "角色主键Id")
    private Long id;
}
