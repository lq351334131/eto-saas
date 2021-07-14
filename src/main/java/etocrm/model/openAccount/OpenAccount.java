package etocrm.model.openAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.model.company.SysOrgCompanyTreeVO;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: dkx
 * @Date: 18:14 2021/6/21
 * @Desc:
 */
@Data
public class OpenAccount implements Serializable {

    private static final long serialVersionUID = -5588404427556062505L;

    @ApiModelProperty(value = "uam机构id")
    private Long uamOrgId;

    @ApiModelProperty(value = "uam行业id")
    private String industryId;

    @ApiModelProperty(value = "uam行业名称")
    private String industryName;

    @ApiModelProperty(value = "crm机构id")
    private Long crmOrgId;

    @ApiModelProperty(value = "uam机构名称")
    private String name;

    @ApiModelProperty(value = "uam机构头像")
    private String orgLogoUrl;

    @ApiModelProperty(value = "uam机房名称")
    private String machineName;

    @ApiModelProperty(value = "uam机房code")
    private String machineCode;

    @ApiModelProperty(value = "uam机房id")
    private Long machineId;

    @ApiModelProperty(value = "uam回调")
    private String callbackUrl;

    @ApiModelProperty(value = "uam管理员信息")
    private OrgAdminInfo orgAdminInfo;

    @ApiModelProperty(value = "uam机构品牌信息")
    private List<OrgBrandInfos> orgBrandInfos;

    @ApiModelProperty(value = "uam机构公司信息")
    private List<SysOrgCompanyTreeVO> orgCompany;

    @ApiModelProperty(value = "uam机构数据库信息")
    private List<DbInfo> dbInfo;
}
