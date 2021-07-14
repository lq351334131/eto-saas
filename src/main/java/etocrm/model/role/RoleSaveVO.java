package etocrm.model.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleSaveVO implements Serializable {


    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "状态1代表启用，0代表禁用")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "系统Id")
    private Long systemId;

    private List<Long> permissionIds;
}
