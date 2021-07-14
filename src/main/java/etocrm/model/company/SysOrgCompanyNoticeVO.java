package etocrm.model.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xingxing.xie
 */
@ApiModel(value = "公司新增、修改通知类")
@Data
public class SysOrgCompanyNoticeVO implements Serializable {

    private static final long serialVersionUID = 3474463880774666899L;
    @ApiModelProperty(value = "公司id")
    private Long id;

    @ApiModelProperty(value = "crm公司id")
    private Long crmCompanyId;

    @ApiModelProperty(value = "机构id")
    private Long uamOrgId;

    @ApiModelProperty(value = "CRM机构id")
    private Long crmOrgId;

    @ApiModelProperty(value = "0总公司1分公司")
    @NotNull(message = "不可为空")
    private Integer type;

    @ApiModelProperty(value = "1启用 0禁用")
    private Integer status;

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

    @ApiModelProperty("品牌id")
    private Long uamBrandId;

    @ApiModelProperty("CRM品牌id")
    private Long crmBrandId;

    @ApiModelProperty("品牌id")
    private String brandName;

    @ApiModelProperty("小程序appId")
    private String miniAppId;

    @ApiModelProperty(value = "小程序名称")
    private String miniAppName;

    @ApiModelProperty("公众号appId")
    private String serviceAppId;

    @ApiModelProperty(value = "公众号名称")
    private String serviceAppName;

    @ApiModelProperty("机房ID")
    private Long machineId;

    @ApiModelProperty(value = "机房编码")
    private String machineCode;

    @ApiModelProperty(value = "机房名称")
    private String machineName;
    @ApiModelProperty(value = "行业id")
    private Long industryId;
    @ApiModelProperty(value = "行业名称")
    private String industryName;

    @ApiModelProperty(value = "ETOSHOP回调")
    private String callbackUrl;

}