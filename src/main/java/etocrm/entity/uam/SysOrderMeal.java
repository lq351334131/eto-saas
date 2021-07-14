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
import java.util.Date;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "订单套餐关系表")
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
public class SysOrderMeal extends BaseDO implements Serializable {


    private static final long serialVersionUID = -3503923319138734864L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "套餐id")
    private Long mealId;

//    @JsonFormat(
//            pattern = "yyyy-MM-dd HH:mm:ss",
//            timezone = "GMT+8"
//    )
    @ApiModelProperty(value = "开始时间")
    private Date enableTimeStart;

//    @JsonFormat(
//            pattern = "yyyy-MM-dd HH:mm:ss",
//            timezone = "GMT+8"
//    )
    @ApiModelProperty(value = "结束时间")
    private Date enableTimeEnd;

    @ApiModelProperty(value = "字典code")
    private Long dictCode;


}