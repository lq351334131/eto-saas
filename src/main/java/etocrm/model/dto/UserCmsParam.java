package etocrm.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserCmsParam {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("名称")
    private String loginUid;

    @ApiModelProperty("email")
    private String email;

    @ApiModelProperty("机构id")
    private Long uamOrgId;

    @ApiModelProperty("机构id")
    private Long crmOrgId;

    @ApiModelProperty("机房")
    private String machineCode;

    @ApiModelProperty("是否是管理员")
    private Integer isAdmin;

    @ApiModelProperty("电话号码")
    private String contactsPhone;
    @ApiModelProperty("职位")
    private String position;
    @ApiModelProperty("用户id")
    private Long userId;


}
