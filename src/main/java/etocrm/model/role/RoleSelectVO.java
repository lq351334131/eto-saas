package etocrm.model.role;

import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;
import org.etocrm.database.util.PageVO;

import java.io.Serializable;

@Data
public class RoleSelectVO extends PageVO implements Serializable {
    private static final long serialVersionUID = 667904323926641755L;
    @QueryFileds(type = QueryType.LIKE, field = "name")
    private String name;

    @QueryFileds(type = QueryType.EQUAL, field = "status")
    private Integer status;
}
