package etocrm.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: dkx
 * @Date: 19:25 2021/1/18
 * @Desc:
 */
@Data
public class RequestID {

    @ApiModelProperty(value = "ID")
    @NotNull(message = "ID不可为空")
    private Long id;

}
