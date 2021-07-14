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
@ApiModel(value = "机构表")
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
public class SysOrg extends BaseDO implements Serializable {


    private static final long serialVersionUID = 2362177801250991068L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "crm机构id")
    private Long crmOrgId;

    @ApiModelProperty(value = "机房id")
    private Long machineId ;

    @ApiModelProperty(value = "机构介绍")
    private String introduce;

    @ApiModelProperty(value = "省")
    private Long province;

    @ApiModelProperty(value = "市")
    private Long city;

    @ApiModelProperty(value = "县区")
    private Long area;

    @ApiModelProperty(value = "机构所在地")
    private String address;

    @ApiModelProperty(value = "机构电话")
    private String orgPhone;

    @ApiModelProperty(value = "机构url")
    private String orgLogoUrl;

    @ApiModelProperty(value = "来源 0后台添加 1 用户注册")
    private Long source;

    @ApiModelProperty(value = "审核状态 0待审核 1 审核 2 审核驳回")
    private Long approvalStatus;

    @ApiModelProperty(value = "备注")
    private String remarks;

}