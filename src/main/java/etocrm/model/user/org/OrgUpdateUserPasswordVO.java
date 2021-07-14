package etocrm.model.user.org;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "saas人员表save VO")
@Data
public class OrgUpdateUserPasswordVO implements Serializable {


    private static final long serialVersionUID = -7389551361193516077L;

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "不可为空")
    private Long id;

    @ApiModelProperty(value = "机构id")
    @NotNull(message = "不可为空")
    private Long orgId;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "不可为空")
    private String password;

    @NotNull(message = "不可为空")
    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;

}