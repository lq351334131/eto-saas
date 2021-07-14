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
@ApiModel(value = "机构品牌信息")
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
public class SysOrgBrands extends BaseDO implements Serializable {


    private static final long serialVersionUID = -7428054653275187232L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "crmBrandId")
    private Long crmBrandId;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "公众号")
    private String serverAppid;

    @ApiModelProperty(value = "1启用 0禁用")
    private Integer status;

}