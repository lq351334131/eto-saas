package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserSaveVO implements Serializable {


    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "登录名不能为空")
    @ApiModelProperty(value = "登录名")
    protected String uid;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码 加密")
    private String password;

    @NotNull(message = "启用/禁用状态不能为空")
    @ApiModelProperty(value = "状态1代表启用，0代表禁用")
    private Integer status;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty("用户头像")
    private String avatar;

}
