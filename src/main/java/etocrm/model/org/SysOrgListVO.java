package etocrm.model.org;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "机构查询vo")
@Data
public class SysOrgListVO implements Serializable {

    private static final long serialVersionUID = 7719303996986926461L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "机构电话")
    private String orgPhone;

    @ApiModelProperty(value = "机构url")
    private String orgLogoUrl;

    @ApiModelProperty(value = "来源 字典org_source 后台添加  用户注册")
    private String source;

    @ApiModelProperty(value = "认证状态 字典orgApprovalType 0未认证 1 认证2驳回")
    private Long authStatus;

    @ApiModelProperty(value = "审核状态 字典orgApproval 0待审核 1 审核 2 审核驳回")
    private String approvalStatus;

    @ApiModelProperty(value = "最后操作人")
    private String updateUser;

    @ApiModelProperty(value = "最后操作时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updatedTime;
}