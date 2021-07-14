package etocrm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xingxing.xie
 */
@ApiModel(value = "机构公司分发结果详情表VO")
@Data
public class CompanyDistributeDetailVO implements Serializable {


    private static final long serialVersionUID = -4918733250045988320L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "机构id",required = true)
    private Long orgId;
    @ApiModelProperty(value = "系统code",required =true )
    private String systemCode;
    @ApiModelProperty(value = "公司id",required =true )
    private Long companyId;

    @ApiModelProperty(value = "通知状态：1 同步成功，0 同步失败",required =true )
    private Integer status;

    @ApiModelProperty(value = "动作标识：1-新增、2-修改",required =true )
    private Integer actionType;
}
