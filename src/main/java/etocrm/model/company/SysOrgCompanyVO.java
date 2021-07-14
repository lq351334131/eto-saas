package etocrm.model.company;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysOrgCompanyVO implements Serializable {
    @ApiModelProperty(value = "id")
    private Long  id;
    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "name")
    private Integer status;
}
