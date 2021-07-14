package etocrm.model.brands;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.util.PageVO;

import java.io.Serializable;

/**
 * @Author: dkx
 * @Date: 13:35 2021/1/24
 * @Desc:
 */
@ApiModel(value = "品牌表 VO")
@Data
public class SysOrgBrandsSelectVO extends PageVO implements Serializable {

    private static final long serialVersionUID = -3149332351213267093L;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "品牌名字")
    private String name;
}
