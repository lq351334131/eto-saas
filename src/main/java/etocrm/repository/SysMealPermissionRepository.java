package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysMealPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 套餐、权限 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysMealPermissionRepository extends BaseRepository<SysMealPermission, Long> {

    List<SysMealPermission> findByMealId(Long id);

    List<SysMealPermission> findByMealIdIn(List<Long> collect);
}