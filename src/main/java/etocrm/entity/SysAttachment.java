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
@ApiModel(value = "附件表")
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
public class SysAttachment extends BaseDO implements Serializable {


    private static final long serialVersionUID = 7642299453915004927L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private String typeCode;

    @ApiModelProperty(value = "下载地址（全）")
    private String url;
    @ApiModelProperty(value = "externalId")
    private Long externalId;


}