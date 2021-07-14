package etocrm.model.user.org;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "用户详情信息")
@Data
public class SysOrgUserRoleListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号Id")
    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "账户")
    protected String uid;

    @ApiModelProperty(value = "用户角色")
    private String userRoleName;

    @ApiModelProperty("最后操作人")
    private String updateUser;

    @ApiModelProperty( "0 启用 1 禁用/停用")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "最后操作时间")
    private Date updatedTime;

}