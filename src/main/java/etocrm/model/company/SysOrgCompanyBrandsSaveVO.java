package etocrm.model.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "公司品牌关联信息")
@Data
public class SysOrgCompanyBrandsSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构id")
    @NotNull(message = "不可为空")
    private Long orgId;

    @ApiModelProperty(value = "公司id")
    @NotNull(message = "不可为空")
    private Long companyId;

    @ApiModelProperty(value = "关联品牌id")
    @NotNull(message = "不可为空")
    private Long brandsId;

}