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
import java.math.BigDecimal;

/**
 * @author admin
 * @since 2021-01-22
 */
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
public class Permission extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "所属系统")
    private Long systemId;

    @ApiModelProperty(value = "名称")
    private String menuName;

    @ApiModelProperty(value = "菜单编码")
    private String code;

    @ApiModelProperty(value = "对应父级")
    private Long parentId;

    @ApiModelProperty(value = "路由地址")
    private String menuRoute;

    @ApiModelProperty(value = "启用、禁用、新功能预告")
    private Integer status;

    @ApiModelProperty(value = "菜单级别")
    private Integer level;

    @ApiModelProperty(value = "排序")
    private Integer menuOrder;


    @ApiModelProperty(value = "菜单、组件按钮")
    private Integer isButton;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "功能类型")
    private Integer functionType;

    @ApiModelProperty(value = "功能定价")
    private Integer functionPricing;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "描述")
    private String functionDescribe;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "显示类型 0无权限隐藏 1无权限禁用")
    private Integer showType;

    @ApiModelProperty(value = "显示类型 0隐藏 1显示")
    private boolean isDisplay;

    @ApiModelProperty(value = "显示类型 0不展开 1展开")
    private boolean isOpen;

    @ApiModelProperty(value = "菜单级别 1父级菜单 2 可点击菜单")
    private Integer menuType;

    @ApiModelProperty(value = "是否默认重定向子菜单 1是 0否")
    private boolean redirectFlag;

    @ApiModelProperty(value = "绑定类型  0无 1 营销活动 2 货架组件")
    private Integer boundType;

    @ApiModelProperty(value = "绑定值")
    private String boundValue;
}