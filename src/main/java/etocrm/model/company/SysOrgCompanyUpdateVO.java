package etocrm.model.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "机构公司信息")
@Data
public class SysOrgCompanyUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "0总公司1分公司")
    private Integer type;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "1 系统生成 2 自定义编码")
    private Integer codeType;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系方式")
    private String contactsPhone;

    @ApiModelProperty(value = "上属公司")
    private Long parentId;

    @ApiModelProperty(value = "1启用 0禁用")
    private Integer status;

    @ApiModelProperty(value = "线上店铺 0 开启 1 禁用")
    private Integer openShop;

//    @ApiModelProperty(value = "关联品牌ids")
//    private List<Long> brandsIds;

    @ApiModelProperty("品牌id")
    private Long brandId;

    @ApiModelProperty("小程序id")
    private Long miniAppId;

    @ApiModelProperty("公众号id")
    private Long serviceId;
}