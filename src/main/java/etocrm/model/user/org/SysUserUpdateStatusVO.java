package etocrm.model.user.org;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SysUserUpdateStatusVO implements Serializable {

    @ApiModelProperty("主键Id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("状态1代表启用，0代表禁用")
    @NotNull(message = "启用/禁用状态不能为空")
    private Integer status;
}
