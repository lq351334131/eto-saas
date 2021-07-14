package etocrm.model.org.orgAuth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "认证save VO")
@Data
public class SysOrgAuthSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行业code")
    @NotBlank(message = "不可为空")
    private String industryCode;

    @ApiModelProperty(value = "营业执照")
    private String busLicenseUrl;

    @ApiModelProperty(value = "其他执照")
    private List<String> otherUrls;


}