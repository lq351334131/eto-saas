package etocrm.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.etocrm.database.entity.BaseDO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "机构发票信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_delete = 0 ")
public class SysOrgInvoice extends BaseDO implements Serializable {


    private static final long serialVersionUID = 8016054259109951119L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "发票类型 0 增值税普票 1 增值税专票  2 不开发票")
    private Integer type;

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