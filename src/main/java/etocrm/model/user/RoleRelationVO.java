package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleRelationVO {


    @ApiModelProperty("角色id")
    private Long id;
    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("管理小程序")
    private String miniApp;
    @ApiModelProperty("管理公众号")
    private String service;

    @ApiModelProperty("管理分公司")
    private String company;

    @ApiModelProperty("系统名称")
    private String systemName;


}
