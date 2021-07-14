package etocrm.model.wechatInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WechatInfoVO {

    @ApiModelProperty("1 返回选择机构账号登录 2 返回绑定用户详情 3 返回登录用的token 4 账号尚未绑定,请先绑定")
    Integer type;

    WechatInfoTypDetailVO wechatInfoTypDetailVO;

}
