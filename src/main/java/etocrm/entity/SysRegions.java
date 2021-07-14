package etocrm.entity;

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
 * @Author: dkx
 * @Date: 22:10 2021/1/25
 * @Desc:
 */
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
public class SysRegions extends BaseDO implements Serializable {

    private static final long serialVersionUID = -7202465519578541574L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "区域ID")
    private Integer gid;

    @ApiModelProperty(value = "上级ID")
    private Integer pid;

    @ApiModelProperty(value = "名字")
    private String name;
}
