package etocrm.model.user.org;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "saas人员表save VO")
@Data
public class ResUserSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构id")
    @NotNull(message = "机构不可为空")
    private Long orgId;

    @ApiModelProperty(value = "登录名")
    @NotBlank(message = "登录名不可为空")
    private String uid;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不可为空")
    private String password;

    @ApiModelProperty(value = "联系方式")
    private String contactsPhone;

    @ApiModelProperty(value = "备注")
    private String remarks;

}