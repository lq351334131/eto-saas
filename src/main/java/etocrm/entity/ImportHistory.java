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

@ApiModel(value = "saas权限资源")
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
public class ImportHistory extends BaseDO implements Serializable {

    @ApiModelProperty(value = "导入历史主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "导入批次")
    private String importBatch;

    @ApiModelProperty(value = "成功数量")
    private Integer successNumber;

    @ApiModelProperty(value = "失败数量")
    private Integer failNumber;

    @ApiModelProperty(value = "错误文件url")
    private String errorFileUrl;
}
