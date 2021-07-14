package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRoleRelationVO {


    @NotNull(message = "用户id")
    @ApiModelProperty(value = "用户id")
    private Long id;

    @NotNull(message = "角色id")
    @ApiModelProperty(value = "角色id")
    private Long roleId;
}
