package etocrm.model.org;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;

import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "机构查询vo")
@Data
public class SysOrgSelectAllVO implements Serializable   {

    private static final long serialVersionUID = 7719303996986926461L;

    @ApiModelProperty(value = "机构id")
    @QueryFileds(type = QueryType.EQUAL, field = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    @QueryFileds(type = QueryType.LIKE, field = "name")
    private String name;

    @ApiModelProperty(value = "审核状态 字典orgApproval 待审核  审核 审核驳回")
   @QueryFileds(type = QueryType.EQUAL, field = "approvalStatus")
    private Long approvalStatus;



}