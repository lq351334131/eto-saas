package etocrm.model.openAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dkx
 * @Date: 19:02 2021/6/21
 * @Desc:
 */
@Data
public class CallbackInfo implements Serializable {

    private static final long serialVersionUID = 4855129767705663138L;

    @ApiModelProperty(value = "uam机构id")
    private Long uamOrgId;

    @ApiModelProperty(value = "uam系统id")
    private Long systemId;

    @ApiModelProperty(value = "数据库地址")
    private String slaveIPAddress;

    @ApiModelProperty(value = "库名")
    private String dbName;

    @ApiModelProperty(value = "数据库类型 1 mysql  2 tidb ")
    private Integer dbType;

    @ApiModelProperty(value = "版本")
    private String dbVersion;

    @ApiModelProperty(value = "是否主数据库 0 从 1主")
    private Integer isMaster;

    @ApiModelProperty("0 成功，1失败")
    private Integer status;
}
