package etocrm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xingxing.xie
 * @Date 2021/6/17 15:01
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "账号身份信息返回实体类")
public class AccountIdentityVo implements Serializable {
    private static final long serialVersionUID = 1476785814435042311L;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "系统id")
    private Long systemId;

    @ApiModelProperty(value = "系统名称")
    private String systemName;

    @ApiModelProperty(value = "身份详情")
    List<IdentityDetail> identityDetails;

}
