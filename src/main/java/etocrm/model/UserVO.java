package etocrm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "人员信息")
@Data
public class UserVO  implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("用户Id")
    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "登录名")
    private String uid;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "联系方式")
    private String contactsPhone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态1代表启用，0代表禁用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;

    @ApiModelProperty("用户头像")
    private String avatar;


}