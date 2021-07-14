package etocrm.model.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;

import java.io.Serializable;

@Data
@ApiModel("菜单表")
public class PermissionFindVO implements Serializable {
    private static final long serialVersionUID = -7812019413831442943L;

    @ApiModelProperty(value = "所属系统")
    @QueryFileds(type = QueryType.IN, field = "systemId")
    private Long systemId;


    @ApiModelProperty(value = "名称")
    @QueryFileds(type = QueryType.LIKE, field = "menuName")
    private String menuName;


    @ApiModelProperty(value = "启用、禁用、新功能预告")
    @QueryFileds(type = QueryType.IN, field = "status")
    private Integer status;

    @QueryFileds(type = QueryType.IN, field = "parentId")
    private Long parentId=0L;
}
