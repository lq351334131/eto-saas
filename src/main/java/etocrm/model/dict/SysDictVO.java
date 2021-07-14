package etocrm.model.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;

import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "字典表")
@Data
public class SysDictVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "编码")
    @QueryFileds(type = QueryType.EQUAL, field = "code")
    private String code;

    @ApiModelProperty(value = "名称")
    @QueryFileds(type = QueryType.EQUAL, field = "name")
    private String name;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "父级code")
    @QueryFileds(type = QueryType.EQUAL, field = "typeCode")
    private String typeCode;

}