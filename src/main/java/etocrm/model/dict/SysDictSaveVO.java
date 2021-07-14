package etocrm.model.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "字典表")
@Data
public class SysDictSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    @NotBlank(message = "不可为空")
    private String code;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "不可为空")
    private String name;

    @ApiModelProperty(value = "值")
    @NotBlank(message = "不可为空")
    private String value;

    @ApiModelProperty(value = "父级code")
    @NotBlank(message = "不可为空")
    private String typeCode;

}