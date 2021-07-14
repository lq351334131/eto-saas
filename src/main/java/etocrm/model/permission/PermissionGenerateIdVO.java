package etocrm.model.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PermissionGenerateIdVO implements Serializable {
    private static final long serialVersionUID = -1256519411952609924L;

    @ApiModelProperty("所属系统")
    @NotNull(message = "所属系统Id不能为空")
    private Long systemId;

    @ApiModelProperty("生成数量")
    @NotNull(message = "生成数量不能为空")
    private Integer number;
}
