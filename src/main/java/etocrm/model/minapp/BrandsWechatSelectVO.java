package etocrm.model.minapp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;
import org.etocrm.database.util.PageVO;

import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "品牌公众号小程序信息")
@Data
public class BrandsWechatSelectVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构id")
    @QueryFileds(type = QueryType.EQUAL, field = "organizationId")
    private Long organizationId;

    @ApiModelProperty(value = "类型：miniapp（小程序）、service（公众号）")
    @QueryFileds(type = QueryType.EQUAL, field = "type")
    private String type;

}