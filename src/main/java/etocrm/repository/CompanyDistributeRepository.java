package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.CompanyDistributeDetail;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 公司分发 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Repository
public interface CompanyDistributeRepository extends BaseRepository<CompanyDistributeDetail, Long> {
}