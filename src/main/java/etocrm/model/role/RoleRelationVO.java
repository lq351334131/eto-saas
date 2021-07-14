package etocrm.model.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleRelationVO  {

    @ApiModelProperty(value = "角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty(value = "小程序id")
    private String miniAppId;

    @ApiModelProperty(value = "公众号id")
    private String serviceId;

    @ApiModelProperty(value = "公司id")
    private String companyId;
}

