package etocrm.model.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "用户角色")
public interface SysUserRoleDTO {
    String getName();
    Long getId();
    Long getUserId();
}
