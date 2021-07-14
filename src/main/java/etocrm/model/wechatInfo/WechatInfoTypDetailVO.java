package etocrm.model.wechatInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.model.user.LoginUserVO;

import java.util.List;

@Data
public class WechatInfoTypDetailVO {

    @ApiModelProperty("用户userId")
    private Long userId;
    @ApiModelProperty(value = "昵称")
    private String nickName;

    private String unionid;

    private String openid;

    @ApiModelProperty(value = "1男2女")
    private Integer sex;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "头像")
    private String headimgUrl;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "账号id")
    private String uid;

    //选择登录账号的列表
    List<WechatOrgUserVO> wechatOrgUserVOList;

    //发放token
    LoginUserVO loginUserVO;
}
