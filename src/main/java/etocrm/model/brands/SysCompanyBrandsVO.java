package etocrm.model.brands;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysCompanyBrandsVO {

    @ApiModelProperty(value = "品牌id")
    private Long id;


    @ApiModelProperty(value = "品牌名字")
    private String name;
}
