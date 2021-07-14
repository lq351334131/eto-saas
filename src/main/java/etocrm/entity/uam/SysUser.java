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
@ApiModel(value = "人员表")
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
public class SysUser extends BaseDO implements Serializable {


    private static final long serialVersionUID = 5657009460259657283L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "部门")
    private String dept;

    @ApiModelProperty(value = "登录名")
    private String uid;

    @ApiModelProperty(value = "密码 加密")
    private String password;

    @ApiModelProperty(value = "联系方式")
    private String contacts;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "状态1代表启用，0代表禁用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;


}