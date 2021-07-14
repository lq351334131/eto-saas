package etocrm.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "文件表save vo")
@Data
public class SysAttachmentSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "下载地址（全）")
    @NotBlank(message = "地址不可为空")
    private String url;
    @ApiModelProperty(value = "typeCode")
    private String typeCode;
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "externalId")
    private Long externalId;

}