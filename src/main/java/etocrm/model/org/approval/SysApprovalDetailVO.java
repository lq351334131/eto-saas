package etocrm.model.org.approval;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "审批表 vo")
@Data
public class SysApprovalDetailVO implements Serializable {

    private static final long serialVersionUID = 7736933161833525800L;

    @ApiModelProperty(value = "机构id")
    @NotNull(message = "机构不可为空")
    private Long orgId;

    @ApiModelProperty(value = "0 认证 1 审核")
    @NotNull(message = "方式不可为空")
    private Integer type;

    @ApiModelProperty(value = "状态 1同意 2驳回")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "审批人id")
    private String userId;

    @ApiModelProperty(value = "审批人名字")
    private String name;

    @ApiModelProperty(value = "审批时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createdTime;


}