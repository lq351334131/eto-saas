package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysRegions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysRegionsRepository extends BaseRepository<SysRegions, Long>{

    List<SysRegions> findByPid(Integer pid);

    SysRegions findByGid(Integer gid);
}