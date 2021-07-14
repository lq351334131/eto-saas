package etocrm.model.industry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "行业")
@Data
public class SysIndustryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "不可为空")
    private Long id;

    @ApiModelProperty(value = "code")
    @NotBlank(message = "编码不可为空")
    private String code;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "编码不可为空")
    private String name;


}