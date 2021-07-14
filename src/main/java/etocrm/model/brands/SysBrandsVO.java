package etocrm.model.brands;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: dkx
 * @Date: 13:34 2021/1/24
 * @Desc:
 */
@ApiModel(value = "saas品牌表 save VO")
@Data
public class SysBrandsVO {
    @ApiModelProperty("品牌列表")
    private List<SysOrgBrandsSaveVO> brandsList;
}
