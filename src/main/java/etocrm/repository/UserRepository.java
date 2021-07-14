package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.User;
import org.etocrm.model.dto.UserRoleDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * saas人员表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long>{

    Integer countByUid(String uid);

    User findByUid(String uid);

    List<User> findAllByOrgId(Long orgId);

    List<User> findByOrgIdAndStatus(Long orgId, Integer status);

    @Query(value = "SELECT a.id as id,a.code as code FROM Role a  inner join UserRole b ON a.id=b.roleId where b.userId =?1 and a.status=1")
    List<UserRoleDTO> selectRolesByUserId(Long id);
}