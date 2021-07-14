package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.model.AccountIdentityVo;
import org.etocrm.model.EtoShopAccountVo;
import org.etocrm.model.permission.PermissionResponseVO;

import java.util.List;

/**
 * @description: 账号身份相关信息
 * @author xingxing.xie
 * @date 2021/6/8 16:38
 * @version 1.0
 */
public interface AccountIdentityService {
    /**
     *  根据账号id  查询 对应的角色、身份信息
     * @param accountId
     * @param systemCode
     * @return
     */
    AccountIdentityVo getAccountInfo(Long accountId, String systemCode) throws MyException;

    /**
     *  根据 角色id  查询 菜单
     * @param roleId 角色id
     * @return
     */
    List<PermissionResponseVO> getMenuByRoleId(Long roleId);

    /**
     *  根据机构ID，查询该机构下的店铺管理员，店铺运营，以及对应管理的门店列表信息
     * @param orgId
     * @return
     * @throws MyException
     */
    EtoShopAccountVo getEtoShopsByOrgId(Long orgId) throws MyException;
}