package etocrm.model.user.org;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "用户详情信息")
@Data
public class SysOrgUserDetailsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "人员id")
    private Long userId;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "unionid")
    private String unionid;

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "头像")
    private String headimgUrl;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "绑定时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

}