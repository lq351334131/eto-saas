package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.UserRole;
import org.etocrm.model.dto.RoleCountDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * saas角色人员信息 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Repository
public interface UserRoleRepository extends BaseRepository<UserRole, Long>{
    @Query(value = "SELECT roleId as roleId,count(id) as count  from  UserRole where deleted=0 and  roleId IN (:ids) group by roleId")
    List<RoleCountDTO> countByRoleId(List<Long> ids);

    List<UserRole> findByUserId(Long id);

    List<UserRole> findByRoleId(Long roleId);

    List<UserRole> findByRoleIdAndUserId(Long roleId, Long id);

    List<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    /**
     *  根据userIds  查询 数据
     * @param ids
     * @return
     */
    List<UserRole> findByUserIdIn(List<Long> ids);

    List<UserRole> findByUserIdAndRoleIdIn(Long id, List<Long> roleIds);
}