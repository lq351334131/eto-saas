package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;

import java.io.Serializable;

@Data
public class UserLikeVO implements Serializable {


    private static final long serialVersionUID = 752469324159412647L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    @QueryFileds(field = "name", type = QueryType.LIKE)
    private String name;

    @ApiModelProperty(value = "邮箱")
    @QueryFileds(field = "email", type = QueryType.LIKE)
    protected String email;

    @ApiModelProperty(value = "联系方式")
    @QueryFileds(field = "contactsPhone", type = QueryType.LIKE)
    private String contactsPhone;

    @ApiModelProperty(value = "机构")
    @QueryFileds(field = "orgId")
    private Long orgId;

    @ApiModelProperty(value = "职位")
    @QueryFileds(field = "position", type = QueryType.LIKE)
    private String position;

}
