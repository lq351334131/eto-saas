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
 * @author xingxing.xie
 */
@ApiModel(value = "机构公司分发结果详情表")
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
public class CompanyDistributeDetail extends BaseDO implements Serializable {

    private static final long serialVersionUID = -8579114414887013793L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long orgId;
    @ApiModelProperty(value = "系统id")
    private String systemCode;
    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @ApiModelProperty(value = "通知状态：1 同步成功，0 同步失败")
    private Integer status;

    @ApiModelProperty(value = "动作标识：1-新增、2-修改")
    private Integer actionType;

}