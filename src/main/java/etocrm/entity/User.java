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
import java.util.Date;

/**
 * @author admin
 * @since 2021-01-22
 */
@ApiModel(value = "人员信息")
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
public class User extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long orgId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "是否管理员")
    private Integer isAdmin;

    @ApiModelProperty(value = "1 管理员 2 分公司运营账号")
    private Integer accountType;

    @ApiModelProperty(value = "名称")
    private String position;

    @ApiModelProperty(value = "登录名")
    private String uid;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "联系方式")
    private String contactsPhone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态1代表启用，0代表禁用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "修改密码时间")
    private Date updatePasswordTime;

    @ApiModelProperty("用户头像")
    private String avatar;

}