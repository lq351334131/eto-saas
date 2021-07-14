package etocrm.model.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;

@Data
public class PermissionConditionVO {

    @ApiModelProperty("所属系统Id")
    @QueryFileds(type = QueryType.EQUAL, field = "systemId")
    private Long systemId;

    @ApiModelProperty("status")
    @QueryFileds(type = QueryType.EQUAL, field = "status")
    private Integer status;
}

