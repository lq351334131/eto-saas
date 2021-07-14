package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePasswordParam {

    @ApiModelProperty("账号密码")
    private String password;

    @ApiModelProperty("旧账号密码")
    private String oldPassword;
}
