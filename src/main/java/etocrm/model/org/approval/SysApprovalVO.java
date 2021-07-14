package etocrm.model.org.approval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "审批表 vo")
@Data
public class SysApprovalVO implements Serializable {

    private static final long serialVersionUID = 4080733943309151834L;

    @ApiModelProperty(value = "机构id")
    @NotNull(message = "机构不可为空")
    private Long orgId;

    @ApiModelProperty(value = "0 认证 1 审核")
    @NotNull(message = "方式不可为空")
    private Integer type;

    @ApiModelProperty(value = "同意 驳回")
    @NotNull(message = "不可为空")
    private Long status;

    @ApiModelProperty(value = "备注")
    private String remarks;


}