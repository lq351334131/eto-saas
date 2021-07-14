package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserUpdateVO implements Serializable {

    private static final long serialVersionUID = 7741319937311777898L;

    @ApiModelProperty(value = "用户Id")
    @NotNull(message = "用户Id不能为空")
    private Long id;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

//    @ApiModelProperty(value = "部门")
//    @NotBlank(message = "登录名")
//    private String uid;

    @ApiModelProperty(value = "密码 加密")
    private String password;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态1代表启用，0代表禁用")
    private Integer status;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty("用户头像")
    private String avatar;

}
