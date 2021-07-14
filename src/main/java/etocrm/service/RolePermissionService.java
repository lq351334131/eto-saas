package etocrm.service;

import org.etocrm.database.util.BasePage;
import org.etocrm.entity.RolePermission;

import java.util.List;

/**
 * <p>
 * 角色权限信息 服务类
 * </p>
 * @author admin
 * @since 2021-01-22
 */
public interface RolePermissionService {


    /**
     * 添加
     */
    Long saveRolePermission(RolePermission rolePermission);

    /**
     * 修改
     */
    Long updateByPk(RolePermission rolePermission);

    /**
     * 删除
     */
    void deleteByPk(Long pk);

    /**
     * 详情
     */
    RolePermission detailByPk(Long pk);

    /**
     * 全查列表
     * @return
     */
    List<RolePermission> findAll(RolePermission rolePermission);

    /**
     * 分页查询
     */
    BasePage<RolePermission> list(Integer curPage, Integer pageSize, RolePermission rolePermission);

}