package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;
import org.etocrm.database.util.PageVO;

import java.io.Serializable;

@Data
public class UserSelectVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty( "1代表启用，0代表禁用")
    @QueryFileds(type = QueryType.EQUAL, field = "status")
    private Integer status;

    @ApiModelProperty(value = "账号名称")
    @QueryFileds(type = QueryType.LIKE, field = "name")
    private String name;

    @ApiModelProperty( "职位")
    @QueryFileds(type = QueryType.LIKE, field = "position")
    private String position;

    @ApiModelProperty( "角色id")
    private Long roleId;


}
