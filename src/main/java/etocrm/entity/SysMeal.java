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
import java.math.BigDecimal;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "套餐表")
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
public class SysMeal extends BaseDO implements Serializable {


    private static final long serialVersionUID = 4218804346867737307L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;


    @ApiModelProperty(value = "下单并发量")
    private BigDecimal concurrency;

    @ApiModelProperty(value = "存储空间")
    private BigDecimal space;

    @ApiModelProperty(value = "导购数量")
    private Integer shopNumber;

    @ApiModelProperty(value = "状态  0 启用 1 禁用/停用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "类型 0 试用 1 付费套餐")
    private Integer type;

    @ApiModelProperty(value = "年收费")
    private BigDecimal year;

    @ApiModelProperty(value = "季度收费")
    private BigDecimal quarter;

    @ApiModelProperty(value = "月收费")
    private BigDecimal month;

}