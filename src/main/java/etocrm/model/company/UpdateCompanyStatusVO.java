package etocrm.model.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = " save VO")
@Data
public class UpdateCompanyStatusVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "品牌id")
    @NotNull(message = "不可为空")
    private Long id;

    @ApiModelProperty(value = "状态 0启用  1 禁用")
    @NotNull(message = "不可为空")
    private Integer status;

}