package etocrm.model.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleVO implements Serializable {
    private static final long serialVersionUID = 7931538781425788567L;
    @ApiModelProperty("用户角色名称")
    private String name;

    @ApiModelProperty("用户角色Id")
    private Long id;

}
