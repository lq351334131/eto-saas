package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysOrgIndustry;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 认证表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysOrgAuthRepository extends BaseRepository<SysOrgIndustry, Long> {

    SysOrgIndustry findByOrgId(Long pk);

    List<SysOrgIndustry> findByAuthStatus(Long authStatus);
}