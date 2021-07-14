package etocrm.model.industry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "行业表ListVO")
@Data
public class SysIndustryListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @ApiModelProperty(value = "行业code")
    private String code;

    @ApiModelProperty(value = "行业名称")
    private String name;

}