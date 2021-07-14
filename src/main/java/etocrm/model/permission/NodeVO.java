package etocrm.model.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;

@Data
public class NodeVO {

    @ApiModelProperty("所属系统Id")
    @QueryFileds(type = QueryType.EQUAL, field = "systemId")
    private Long systemId;

    @ApiModelProperty("1启用/0禁用")
    @QueryFileds(type = QueryType.EQUAL, field = "status")
    private Integer status;
}
