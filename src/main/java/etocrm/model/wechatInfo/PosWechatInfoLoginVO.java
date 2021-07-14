package etocrm.model.wechatInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.model.user.TokenValue;

@Data
public class PosWechatInfoLoginVO extends TokenValue {

    @ApiModelProperty("userId")
    private Long userId;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("status 0 没有绑定 1已绑定")
    private Integer bindStatus;

    private Long id;

    private Long orgId;

    private String name;

    private String uid;
}
