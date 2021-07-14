package etocrm.model.org;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: dkx
 * @Date: 19:25 2021/1/18
 * @Desc:
 */
@Data
public class SysOrgStatusVO {

    @ApiModelProperty(value = "ID")
    @NotNull(message = "ID不可为空")
    private Long id;

    @NotNull(message= "不可为空")
    @ApiModelProperty(value = "状态 0启用 1禁用 ")
    private Integer status;
}
