package etocrm.model.org;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "机构表update vo")
@Data
public class SysOrgUpdateVO implements Serializable {

    private static final long serialVersionUID = 7719303996986926461L;

    @Id
    @ApiModelProperty(value = "id")
    @NotNull(message = "不可为空")
    private Long id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不可为空")
    private String name;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "机构介绍")
    private String introduce;

    @ApiModelProperty(value = "省")
    @NotNull(message = "不可为空")
    private Long province;

    @ApiModelProperty(value = "市")
    @NotNull(message = "不可为空")
    private Long city;

    @ApiModelProperty(value = "县区")
    @NotNull(message = "不可为空")
    private Long area;

    @ApiModelProperty(value = "机构所在地")
    @NotBlank(message = "机构所在地不可为空")
    private String address;

    @ApiModelProperty(value = "机构电话")
    private String orgPhone;

    @ApiModelProperty(value = "机构url")
    private String orgLogoUrl;
}