package etocrm.model.brands;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "saas品牌表 save VO")
@Data
public class SysOrgBrandsSaveVO implements Serializable {


    private static final long serialVersionUID = -5697542846718467425L;

    @ApiModelProperty(value = "机构id")
    @NotNull(message = "机构不可为空")
    private Long orgId;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "品牌名字")
    @NotBlank(message = "名称不可为空")
    private String name;

}