package etocrm.model.openAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: dkx
 * @Date: 18:12 2021/6/21
 * @Desc:
 */
@Data
public class OrgCompany implements Serializable {

    private static final long serialVersionUID = -7765438392384969318L;

    @ApiModelProperty(value = "子公司")
    private List<OrgCompany> children;

    @ApiModelProperty(value = "uam公司编码")
    private String code;

    @ApiModelProperty(value = "uam公司id")
    private Long id;

    @ApiModelProperty(value = "uam公司名称")
    private String name;

    @ApiModelProperty(value = "线下门店数量")
    private int offlineShopNumber;

    @ApiModelProperty(value = "线上店铺 0 开启 1 禁用")
    private int openShop;

    @ApiModelProperty(value = "上属公司")
    private Long parentId;

    @ApiModelProperty(value = "关联品牌")
    private String relatedBrands;

    @ApiModelProperty(value = "状态 0启用 1禁用")
    private int status;

    @ApiModelProperty(value = " 0总公司 1分公司")
    private int type;
}
