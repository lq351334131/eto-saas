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
@ApiModel(value = "电商账号信息")
public class EtoShopAccountVo implements Serializable {

    private static final long serialVersionUID = -3967078317017581731L;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "身份详情")
    List<AccountRoleDetail> accountRoleDetails;



}
