package etocrm.model.openAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dkx
 * @Date: 18:13 2021/6/21
 * @Desc:
 */
@Data
public class DbInfo implements Serializable {

    private static final long serialVersionUID = -7764744764409958894L;

    @ApiModelProperty(value = "uam系统id")
    private Long systemId;

    @ApiModelProperty(value = "uam系统编码")
    private String systemCode;

    @ApiModelProperty(value = "uam系统名字")
    private String systemName;

    @ApiModelProperty(value = "从库地址")
    private String slaveIPAddress;

    @ApiModelProperty(value = "库名")
    private String dbName;

    @ApiModelProperty(value = "数据库类型 1 mysql  2 tidb")
    private Integer dbType;

    @ApiModelProperty(value = "数据库版本")
    private String dbVersion;

    @ApiModelProperty(value = "是否主数据库 0 从 1主")
    private Integer isMaster;
}
