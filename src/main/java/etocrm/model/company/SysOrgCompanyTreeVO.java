package etocrm.model.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "机构公司信息")
@Data
public class SysOrgCompanyTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "0总公司1分公司")
    private Integer type;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "关联品牌")
    private String relatedBrands;

    @ApiModelProperty(value = "关联品牌")
    private Long uamBrandId;

    @ApiModelProperty(value = "关联crm品牌")
    private Long crmBrandId;

    @ApiModelProperty(value = "线下门店数量")
    private Integer offlineShopNumber;

    @ApiModelProperty(value = "上属公司")
    private Long parentId;

    @ApiModelProperty(value = "1启用 0禁用")
    private Integer status;

    @ApiModelProperty(value = "线上店铺 1 开启 2 禁用")
    private Integer openShop;

    @ApiModelProperty(value = "子公司")
    private List<SysOrgCompanyTreeVO> children;

}