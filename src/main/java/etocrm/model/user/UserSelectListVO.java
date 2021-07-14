package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserSelectListVO implements Serializable {
    private static final long serialVersionUID = -4400759548139788955L;


    @ApiModelProperty(value = "账号Id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "账号")
    private String uid;

    @ApiModelProperty(value = "职位名称")
    private String position;

    @ApiModelProperty(value = "角色列表")
    private List<RoleRelationVO> userRoles;

    @ApiModelProperty( "状态1代表启用，0代表禁用")
    private Integer status;

}
