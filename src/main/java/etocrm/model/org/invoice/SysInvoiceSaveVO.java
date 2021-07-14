package etocrm.model.org.invoice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-19
 */
@ApiModel(value = "发票表saveVo")
@Data
public class SysInvoiceSaveVO implements Serializable {

    private static final long serialVersionUID = 4624650118174335770L;

    @ApiModelProperty(value = "发票类型 字典invoiceType 0 增值税普票 1 增值税专票  2 不开发票")
    private Long type;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "纳税人识别号")
    private String number;

    @ApiModelProperty(value = "单位地址")
    private String unitAddress;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "开户银行")
    private String depositBank;

    @ApiModelProperty(value = "银行账户")
    private String bankAccount;

    @ApiModelProperty(value = "邮寄联系人")
    private String mailContact;

    @ApiModelProperty(value = "邮寄联系电话")
    private String mailPhone;

    @ApiModelProperty(value = "邮寄地址")
    private String mailAddress;


}