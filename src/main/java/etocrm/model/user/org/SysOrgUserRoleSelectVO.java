package etocrm.model.user.org;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.util.PageVO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "用户详情信息")
@Data
public class SysOrgUserRoleSelectVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构id")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @ApiModelProperty(value = "账户名")
    private String name;

    @ApiModelProperty(value = "角色code 管理员：SuperAdmin 运营 ：OperateAdmin")
    @NotBlank(message = "角色code不能为空")
    private String roleCode;

    @ApiModelProperty(value = "状态 0启用 1禁用 ")
    private Integer status;

}