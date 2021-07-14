package etocrm.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginCurrentVO {

    @ApiModelProperty("登录账号")
    private String uid;

    @ApiModelProperty("套餐名称")
    private String mealName;

    @ApiModelProperty("到期时间")
    private String  dateLine;
    @ApiModelProperty("剩余多少天")
    private Long days;

    private Long mealId;
    @ApiModelProperty("是否是管理员")
    private Integer type;

    @ApiModelProperty("姓名")
    private String name;
}
