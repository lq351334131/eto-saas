package etocrm.model.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PermissionGenerateIdListVO implements Serializable {
    private static final long serialVersionUID = -7386698125988820434L;

    @ApiModelProperty("系统name")
    private String systemName;

    @ApiModelProperty("菜单code")
    private String code;

    @ApiModelProperty("最后操作人")
    private String updateUser;

    @ApiModelProperty("最后操作时间")
    private Date updatedTime;
}
