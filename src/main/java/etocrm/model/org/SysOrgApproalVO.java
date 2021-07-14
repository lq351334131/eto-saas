package etocrm.model.org;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysOrgApproalVO {

    @ApiModelProperty("机构名称")
    private String name;
    @ApiModelProperty("机构id")
    private Long id;
}
