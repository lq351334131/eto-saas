package etocrm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xingxing.xie
 * @Date 2021/6/18 11:25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "账号身份信息返回实体类")
public class IdentityDetail implements Serializable {
    private static final long serialVersionUID = -6920634863770226221L;
    @ApiModelProperty(value = "身份类型(角色id)")
    private Long roleId;

    @ApiModelProperty(value = "身份类型名称(角色名称)")
    private String roleName;

    @ApiModelProperty(value = "账号类型：1-管理员/2-分公司运营账号(线上数据运营账号)")
    private Integer accountType;

    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @ApiModelProperty(value = "公司名称名称")
    private String companyName;

    @ApiModelProperty(value = "公司类型(0总公司1分公司2分销商)")
    private Integer companyType;

    /**
     * todo 公司参数 结构记得修改
     */
    @ApiModelProperty(value = "机构下所有公司信息")
    List<CompanyDetailVO> companyDetailVOList;

    @ApiModelProperty(value = "小程序列表")
    private List<MiniAppRoleListVO> miniAppList;
    @ApiModelProperty(value = "公众号列表")
    private List<MiniAppRoleListVO> subscriptionList;
    @ApiModelProperty(value = "已选店铺")
    private List<OnLineShopVo> shopList;
}
