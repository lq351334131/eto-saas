package etocrm.model.brands;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: dkx
 * @Date: 13:35 2021/1/24
 * @Desc:
 */
@ApiModel(value = "saas品牌表 VO")
@Data
public class SysOrgBrandsListVO {
    @ApiModelProperty(value = "机构id")
    private Long id;

    @ApiModelProperty(value = "机构id", required = true)
    private Long orgId;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "品牌名字")
    private String name;

    @ApiModelProperty(value = "公众号名字")
    private String appServiceName;

    @ApiModelProperty(value = "小程序")
    private String minAppName;

    @ApiModelProperty(value = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    @ApiModelProperty(value = "状态 0启用  1 禁用")
    private Integer status;
}
