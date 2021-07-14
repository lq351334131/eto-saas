package etocrm.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRoleDo {

    public UserRoleDo(String name, Long id, Long userId) {
        this.name = name;
        this.id = id;
        this.userId = userId;
    }

    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("userId")
    private Long userId;
}
