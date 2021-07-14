package etocrm.repository.uam;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.uam.SysUser;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 人员表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysUserRepository extends BaseRepository<SysUser, Long> {
    Integer countByUid(String uid);
}