package etocrm.model.org;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.model.brands.SysOrgBrandsVO;

import java.util.List;

@Data
public class SysOrgDetailInfo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "机构url头像")
    private String orgLogoUrl;

    @ApiModelProperty(value = "行业名称")
    private String industryName;

    @ApiModelProperty(value = "行业id")
    private Long industryId;

    @ApiModelProperty(value = "机房id")
    private Long machineId ;

    @ApiModelProperty(value = "机房名称")
    private String machineName ;

    @ApiModelProperty("品牌列表")
    private List<SysOrgBrandsVO> brandsList;
}
