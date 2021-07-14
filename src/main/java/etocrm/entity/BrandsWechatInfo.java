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
@ApiModel(value = "品牌公众号小程序信息")
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
public class BrandsWechatInfo extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ApiModelProperty(value = "品牌id")
    private Long brandsId;

    @ApiModelProperty(value = "状态1启用0禁用")
    private Integer status;

    @ApiModelProperty(value = "机构id")
    private Long organizationId;

    @ApiModelProperty(value = "appid")
    private String appid;

    @ApiModelProperty(value = "加密Key")
    private String appkey;

    @ApiModelProperty(value = "accesstokenFetch")
    private String accesstokenFetch;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "加密密钥")
    private String aeskey;

    @ApiModelProperty(value = "encryptCode")
    private String encryptCode;

    @ApiModelProperty(value = "微信原始Id")
    private String wechatOriginalId;

    @ApiModelProperty(value = "'service','subscribe','miniapp'")
    private String type;

    @ApiModelProperty(value = "认证情况 0 微信认证 1 未认证")
    private Integer isAuth;

    @ApiModelProperty(value = "微信支付 0 已开通 1未开通")
    private Integer isPay;

    @ApiModelProperty(value = "'base','open'")
    private String joinWay;

    @ApiModelProperty(value = "微信号")
    private String wxid;

    @ApiModelProperty(value = "公众号/小程序名称")
    private String wechatName;

    @ApiModelProperty(value = "服务器地址")
    private String connectUrl;

    @ApiModelProperty(value = "公众号/小程序头像")
    private String logo;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "支付宝公钥")
    private String aliPublicKey;

    @ApiModelProperty(value = "支付宝小程序应用公钥")
    private String aliApplicationPublicKey;

    @ApiModelProperty(value = "支付宝小程序应用私钥")
    private String aliApplicationPrivateKey;

}