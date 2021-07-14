package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysOrgApproval;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 审批表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysApprovalRepository extends BaseRepository<SysOrgApproval, Long> {

    List<SysOrgApproval> findByOrgIdAndType(Long pk, Integer type);

    List<SysOrgApproval> findByTypeAndStatus(Integer type, Long status);

    List<SysOrgApproval> findByOrgIdAndTypeOrderByCreatedTimeDesc(Long pk, Integer type);
}