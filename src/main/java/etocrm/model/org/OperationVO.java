package etocrm.model.org;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.model.role.SysRoleVO;
import org.etocrm.model.user.org.SysOrgUserDetailsVO;

import java.util.List;

@Data
public class OperationVO {

    @ApiModelProperty(value = "角色信息")
    private List<SysRoleVO> userRoleList;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "机构名字")
    private String orgName;

    @ApiModelProperty(value = "登录名")
    private String uid;

    @ApiModelProperty(value = "联系方式")
    private String contactsPhone;

    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "状态1代表启用，0代表禁用")
    private Integer status;

    @ApiModelProperty(value = "绑定微信信息")
    private List<SysOrgUserDetailsVO> detailsVOS;
}
