package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmailPasswordParam {

    @ApiModelProperty("邮箱地址")
    @NotNull(message = "email不能为空")
    private String email;

    @ApiModelProperty("账号密码")
    private String password;
}
