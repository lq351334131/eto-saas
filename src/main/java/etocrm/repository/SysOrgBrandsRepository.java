package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysOrgBrands;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 机构品牌信息 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-23
 */
@Repository
public interface SysOrgBrandsRepository extends BaseRepository<SysOrgBrands, Long> {

    Integer countByOrgIdAndName(Long orgId, String name);

    Integer countByNameAndIdNot(String name, Long id);

    List<SysOrgBrands> findByOrgId(Long orgId);
}