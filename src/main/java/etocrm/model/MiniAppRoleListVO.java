package etocrm.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xingxing.xie
 */
@Data
@Accessors(chain = true)
public class MiniAppRoleListVO implements Serializable {

    private static final long serialVersionUID = -2944604548001776613L;
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("小程序/公众号名称")
    private String name;
    @ApiModelProperty("小程序/公众号id")
    private String appId;
    @ApiModelProperty("Status")
    private Integer status ;
    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty(value = "身份类型名称(角色名称)")
    private String roleName;
}
