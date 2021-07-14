package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmailParam {

    @ApiModelProperty("邮箱地址")
    @NotNull(message = "email不能为空")
    private String email;

    @ApiModelProperty("邮箱code")
    private String code;
}
