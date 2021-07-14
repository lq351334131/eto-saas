package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysOrg;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 机构表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysOrgRepository extends BaseRepository<SysOrg, Long>{

    List<SysOrg> findByName(String name);

    List<SysOrg> findByNameLike(String name);

    Long countDistinctByIdInAndApprovalStatus(Set<Long> orgIds, Long status);

    List<SysOrg> findByNameAndIdNot(String name, Long id);

    List<SysOrg> findByApprovalStatusAndIdIn(Long i, List<Long> orgIds);
}