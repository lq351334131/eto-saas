package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * saas权限资源 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Repository
public interface PermissionRepository extends BaseRepository<Permission, Long>{

    Permission findByMenuNameAndIdNot(String menuName, Long id);

    List<Permission> findBySystemIdAndStatusAndIsButton(Long id, Integer code, Integer code1);

    List<Permission> findByStatusAndIsButtonAndIdInOrderByMenuOrderAsc(Integer status, Integer menuType, List<Long> collect);

    List<Permission> findByStatusAndIdInOrderByMenuOrderAsc(Integer code, List<Long> collect);
}