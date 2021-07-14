package etocrm.model.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleStatusUpdateVO {


    @NotNull(message = "用户id")
    @ApiModelProperty(value = "用户id")
    protected Long id;

    @NotNull(message = "启用/禁用状态不能为空")
    @ApiModelProperty(value = "状态1代表启用，0代表禁用")
    private Integer status;
}
