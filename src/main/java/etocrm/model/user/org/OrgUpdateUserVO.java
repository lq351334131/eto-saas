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
public class OrgUpdateUserVO implements Serializable {


    private static final long serialVersionUID = -7389551361193516077L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "不可为空")
    private Long id;

    @ApiModelProperty(value = "机构id")
    @NotNull(message = "不可为空")
    private Long orgId;

    @ApiModelProperty(value = "登录名")
    @NotNull(message = "不可为空")
    private String uid;

    @ApiModelProperty(value = "联系方式")
    private String contactsPhone;

    @ApiModelProperty(value = "状态  0 启用 1禁用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remarks;

}