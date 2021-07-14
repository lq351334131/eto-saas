package etocrm.model.industry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "行业")
@Data
public class SysIndustrySaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "code")
    @NotBlank(message = "编码不可为空")
    private String code;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不可为空")
    private String name;


}