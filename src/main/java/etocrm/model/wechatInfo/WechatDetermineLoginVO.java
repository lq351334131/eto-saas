package etocrm.model.wechatInfo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WechatDetermineLoginVO {
    @NotBlank(message = "ticket不能为空")
    private String ticket;
    @NotNull(message = "userId不能为空")
    private Long userId;
    @NotBlank(message = "code不能为空")
    private String code;
}
