package etocrm.model.brands;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;

import java.io.Serializable;

/**
 * @Author: dkx
 * @Date: 13:35 2021/1/24
 * @Desc:
 */
@ApiModel(value = "品牌表 VO")
@Data
public class SysOrgBrandsAllVO implements Serializable {

    private static final long serialVersionUID = -3149332351213267093L;

    @ApiModelProperty(value = "机构id")
    @QueryFileds(type = QueryType.EQUAL, field = "orgId")
    private Long orgId;

    @ApiModelProperty(value = "品牌名字")
    @QueryFileds(type = QueryType.LIKE, field = "name")
    private String name;

    @ApiModelProperty(value = "品牌状态")
    @QueryFileds(type = QueryType.EQUAL, field = "status")
    private Integer status;
}
