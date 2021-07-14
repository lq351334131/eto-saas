package etocrm.model.openAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dkx
 * @Date: 18:12 2021/6/21
 * @Desc:
 */
@Data
public class OrgBrandInfos implements Serializable {

    private static final long serialVersionUID = -2652528986824594640L;

    @ApiModelProperty(value = "uam品牌id")
    private Long uamBrandId;

    @ApiModelProperty(value = "uam品牌名称")
    private String brandName;

    @ApiModelProperty(value = "crm品牌id")
    private Long crmBrandId;

    @ApiModelProperty(value = "uam机构code")
    private String uamBrandCode;

    @ApiModelProperty(value = "0启用 1禁用")
    private Integer status;
}
