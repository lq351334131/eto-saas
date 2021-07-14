package etocrm.model.brands;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "saas品牌表 save VO")
@Data
public class SysOrgBrandsVO implements Serializable {


    private static final long serialVersionUID = 7400295990816630747L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态 1 启用 0 禁用")
    private Integer status;

    @ApiModelProperty(value = "品牌名字")
    private String name;

}