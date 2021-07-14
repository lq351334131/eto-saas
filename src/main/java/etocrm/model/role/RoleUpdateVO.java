package etocrm.model.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleUpdateVO {

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    @NotNull(message = "主键Id不能为空")
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "角色状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "系统Id")
    private Long systemId;

    private List<Long> permissionIds;
}
