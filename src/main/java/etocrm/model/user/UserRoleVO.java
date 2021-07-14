package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.model.MiniAppRoleListVO;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserRoleVO {

    @NotNull(message = "用户id")
    @ApiModelProperty(value = "用户id")
    private Long id;

   // @NotNull(message = "角色id")
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "系统id")
    private Long systemId;

    @ApiModelProperty(value = "1 管理员 2 分公司运营账号")
    private Integer accountType;

    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @ApiModelProperty(value = "店铺id")
    private String shopId;

    @ApiModelProperty(value = "小程序或者公众号id")
    private String wechatInfoId;

    @ApiModelProperty(value = "关联list")
    private List<MiniAppRoleListVO> relationList;
}
