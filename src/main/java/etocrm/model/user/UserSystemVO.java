package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserSystemVO {

    private Long systemId;

    @ApiModelProperty(value = "系统名称")
    private String systemName;

    @ApiModelProperty(value = "系统core")
    private String code;

    @ApiModelProperty(value = "系统登录地址")
    private String systemUrl;


    @ApiModelProperty(value = "系统登录地址")
    private String icon;

    @ApiModelProperty(value = "系统描述")
    private String description;
}
