package etocrm.service;


import org.etocrm.database.util.BasePage;
import org.etocrm.entity.Permission;
import org.etocrm.model.permission.*;

import java.util.List;

/**
 * <p>
 * saas权限资源 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
public interface PermissionService {


    Long updateByPk(PermissionUpdateVO permission)throws Exception;

    void deleteByPk(Long pk);

    Permission detailByPk(Long pk);

    List<PermissionResponseVO> findAll(PermissionFindVO permissionFindVO);

    BasePage<Permission> list(Integer curPage, Integer pageSize, Permission permission);

    List<PermissionVO> getNodes(NodeVO vo);

     ThirdSystemPermissionVO getCurrentLoginUserPermissions();

    List<PermissionVO> getCurrentLoginUserPermissionList(String systemCode, Integer isButton);

    List<PermissionVO> getSaasLoginUserPermissionList();

    List<PermissionVO> getCurrentLoginUserAllPermissionList(String systemCode);

    List<PermissionVO> getPermissionListByOrgId(Long orgId);
}