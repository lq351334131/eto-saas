package etocrm.model.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "机构公司信息")
@Data
public class SysOrgCompanySaveVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "CRM机构id")
    private Long crmOrgId;

    @ApiModelProperty(value = "0总公司1分公司")
    private Integer type;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "编码type")
    private Integer codeType;

    @ApiModelProperty(value = "名字")
    @NotNull(message = "不可为空")
    private String name;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系方式")
    private String contactsPhone;

    @ApiModelProperty(value = "上属公司")
    private Long parentId;

    @ApiModelProperty(value = "线上店铺 0 开启 1 禁用")
    private Integer openShop;

//    @ApiModelProperty(value = "关联品牌ids")
//    private List<Long> brandsIds;

    @ApiModelProperty("品牌id")
    private Long brandId;

    @ApiModelProperty("CRM品牌id")
    private Long crmBrandId;

    @ApiModelProperty("品牌id")
    private String brandName;

    @ApiModelProperty("小程序id")
    private Long miniAppId;

    @ApiModelProperty(value = "小程序名称")
    private String miniAppName;

    @ApiModelProperty("公众号id")
    private Long serviceAppId;

    @ApiModelProperty(value = "公众号名称")
    private String serviceAppName;

}