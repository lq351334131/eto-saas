package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * saas角色信息 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, Long>{



    Role findByNameAndIdNot(String name, Long id);

    List<Role> findByOrgIdAndSystemIdAndStatus(Long orgId, Long systemId, Integer code);

    List<Role> findByOrgIdAndStatus(Long orgId, Integer code);

    Role findByNameAndOrgId(String name, Long orgId);

    Role findByNameAndIdNotAndOrgId(String name, Long id, Long orgId);

    /**
     *  根据系统id  查询对应启用角色
     * @param systemId
     * @param status
     * @return
     */
    List<Role> findBySystemIdAndStatus(Long systemId, Integer status);
}