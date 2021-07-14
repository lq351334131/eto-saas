package etocrm.model.user;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CurrentUserInfo {

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("职位")
    private String position;

    @ApiModelProperty("登录名")
    @JSONField(name = "loginName")
    private String uid;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户email")
    private String email;

    @ApiModelProperty("用户联系方式")
    private String contactsPhone;

    @JSONField(name = "isAdmin")
    private boolean isAdmin;

    @ApiModelProperty("机构信息")
    private Long orgId;

    @ApiModelProperty("uam机构信息")
    private Long uamOrgId;

    @ApiModelProperty("机构名称")
    private String orgName;

    @ApiModelProperty("机房id")
    private Long roomId;

    @ApiModelProperty("机房名称")
    private String roomName;

    @ApiModelProperty("机房编码  beijing  suhzou  test  wuxi")
    private String code;

}