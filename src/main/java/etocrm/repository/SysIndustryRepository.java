package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysIndustry;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 行业表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysIndustryRepository extends BaseRepository<SysIndustry, Long> {

    List<SysIndustry> findByName(String name);
}