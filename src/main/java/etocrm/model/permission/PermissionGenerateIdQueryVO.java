package etocrm.model.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;
import org.etocrm.database.util.PageVO;

import java.io.Serializable;

@Data
public class PermissionGenerateIdQueryVO extends PageVO implements Serializable {
    private static final long serialVersionUID = -1256519411952609924L;

    @ApiModelProperty("所属系统")
    @QueryFileds(type = QueryType.EQUAL, field = "systemId")
    private Long systemId;
}
