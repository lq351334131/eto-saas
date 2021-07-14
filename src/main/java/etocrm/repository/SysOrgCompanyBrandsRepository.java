package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysOrgCompanyBrands;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 公司品牌关联信息 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-23
 */
@Repository
public interface SysOrgCompanyBrandsRepository extends BaseRepository<SysOrgCompanyBrands, Long> {

    List<SysOrgCompanyBrands> findByCompanyId(Long companyId);

    List<SysOrgCompanyBrands> findByOrgId(Long orgId);
}