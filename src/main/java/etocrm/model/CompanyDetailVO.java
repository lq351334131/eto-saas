package etocrm.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xingxing.xie
 */
@Data
@Accessors(chain = true)
public class CompanyDetailVO implements Serializable {

    private static final long serialVersionUID = 120510128467784059L;

    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @ApiModelProperty(value = "公司名称名称")
    private String companyName;

    @ApiModelProperty(value = "公司类型(0总公司1分公司2分销商)")
    private Integer companyType;
}
