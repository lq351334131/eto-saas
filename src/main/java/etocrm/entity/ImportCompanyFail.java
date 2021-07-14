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
public class ImportCompanyFail extends BaseDO implements Serializable {

    @ApiModelProperty(value = "导入失败表主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ApiModelProperty(value = "公司类型 2-分公司 3-经销商*")
    private String type;

    @ApiModelProperty(value = "公司名称*")
    private String name;

    @ApiModelProperty(value = "公司ID")
    private String companyId;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系方式")
    private String contactsPhone;

    @ApiModelProperty(value = "上属公司ID")
    private String parentId;

    @ApiModelProperty(value = "商家编号")
    private String code;

    @ApiModelProperty(value = "是否开通线上店铺1-开通  2-不开通")
    private String openShop;

    @ApiModelProperty(value = "关联品牌ID多个品牌英文逗号分割")
    private String brandsId;

    @ApiModelProperty(value = "错误描述")
    private String errorMsg;

    @ApiModelProperty(value = "错误字段")
    private String errorField;

    @ApiModelProperty(value = "导入批次")
    private String importBatch;

    @ApiModelProperty(value = "一行完整数据的标识")
    private String rowId;
}
