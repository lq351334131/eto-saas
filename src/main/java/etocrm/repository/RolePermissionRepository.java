package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色权限信息 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Repository
public interface RolePermissionRepository extends BaseRepository<RolePermission, Long>{

    List<RolePermission> findByRoleId(Long pk);

    List<RolePermission> findByRoleIdIn(List<Long> roleIds);
}