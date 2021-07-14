package etocrm.model.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleDetailVO implements Serializable {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "角色状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "系统Id")
    private Long systemId;
    private List<Long> permissionIds;
    @ApiModelProperty(value = "系统Code")
    private String systemCode;
}
