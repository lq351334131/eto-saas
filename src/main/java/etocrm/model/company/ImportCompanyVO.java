package etocrm.model.company;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImportCompanyVO {
    @ApiModelProperty(value = "公司类型 2-分公司 3-经销商*")
    private String type;

    @ApiModelProperty(value = "公司名称*")
    private String name;

    @ApiModelProperty(value = "公司ID")
    private String companyId;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系方式")
    private String contactsPhone;

    @ApiModelProperty(value = "上属公司ID")
    private String parentId;

    @ApiModelProperty(value = "商家编号")
    private String code;

    @ApiModelProperty(value = "是否开通线上店铺1-开通  2-不开通")
    private String openShop;

    @ApiModelProperty(value = "关联品牌ID多个品牌英文逗号分割")
    private String brandsId;


}
