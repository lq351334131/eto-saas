package etocrm.model.org.orgAuth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "认证update VO")
@Data
public class SysOrgAuthVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @NotNull(message = "不可为空")
    private Long id;


    @NotNull(message = "机构不可为空")
    private Long orgId;

    @ApiModelProperty(value = "行业id")
    @NotNull(message = "名称不可为空")
    private Long industryCode;

    @ApiModelProperty(value = "营业执照")
    private String busLicenseUrl;

    @ApiModelProperty(value = "其他执照")
    private List<String> otherUrls;

    @ApiModelProperty(value = "认证状态 字典orgApprovalType 未认证 认证  驳回")
    private Long authStatus;

    @ApiModelProperty(value = "认证状态 字典orgApprovalType 未认证  认证   驳回")
    private String authStatusName;


}