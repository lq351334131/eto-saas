package etocrm.entity.uam;

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
import java.math.BigDecimal;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "订单表")
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
public class SysOrder extends BaseDO implements Serializable {


    private static final long serialVersionUID = -6911071972301133270L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "机构")
    private Long orgId;

    @ApiModelProperty(value = "订单编号")
    private String code;

    @ApiModelProperty(value = "作废原因")
    private String reason;

    @ApiModelProperty(value = "订单类型 0 免费  1付费")
    private Integer orderType;

    @ApiModelProperty(value = "应付金额")
    private BigDecimal amountPayable;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal preferentialAmount;


    @ApiModelProperty(value = "支付状态0未支付1已支付")
    private Integer payStatus;

    @ApiModelProperty(value = "0未作废1已作废")
    private Integer isCancel;


    @ApiModelProperty(value = "合同号")
    private String contractNo;

    @ApiModelProperty(value = "备注")
    private String remarks;


    @ApiModelProperty(value = "开票状态 0未开票  1已开票 2不开发票")
    private Integer billingStatus;

    @ApiModelProperty(value = "订单来源")
    private Long source;


}