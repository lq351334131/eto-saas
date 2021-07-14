package etocrm.model.openAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dkx
 * @Date: 18:10 2021/6/21
 * @Desc:
 */
@Data
public class OrgAdminInfo implements Serializable {

    private static final long serialVersionUID = -2291086972414856536L;

    @ApiModelProperty(value = "uam用户名字")
    private String name;

    @ApiModelProperty(value = "uam用户id")
    private String id;

    @ApiModelProperty(value = "uam用户邮箱")
    private String email;

    @ApiModelProperty(value = "uam用户手机号")
    private String mobile;

    @ApiModelProperty(value = "uam用户uid 唯一")
    private String loginUid;

    @ApiModelProperty(value = "uam用户密码")
    private String password;

    @ApiModelProperty("用户头像")
    private String avatar;

}
