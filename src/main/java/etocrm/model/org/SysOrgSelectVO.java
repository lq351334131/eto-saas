package etocrm.model.org;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.util.PageVO;

import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "机构查询vo")
@Data
public class SysOrgSelectVO extends PageVO implements Serializable   {

    private static final long serialVersionUID = 7719303996986926461L;

    @ApiModelProperty(value = "机构id")
    //@QueryFileds(type = QueryType.EQUAL, field = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    //@QueryFileds(type = QueryType.LIKE, field = "name")
    private String name;

    @ApiModelProperty(value = "认证状态 字典orgApprovalType  未认证 认证 驳回")
    //@QueryFileds(type = QueryType.EQUAL, field = "authStatus")
    private Long authStatus;

    @ApiModelProperty(value = "审核状态 字典orgApproval 待审核  审核 审核驳回")
   // @QueryFileds(type = QueryType.EQUAL, field = "approvalStatus")
    private Long approvalStatus;



}