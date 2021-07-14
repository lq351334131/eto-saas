package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SystemInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 系统表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SystemInfoRepository extends BaseRepository<SystemInfo, Long>{

    SystemInfo findByCode(String etosaas);

    /**
     *  根据系统名称查找
     * @param codes
     * @return
     */
    List<SystemInfo> findByCodeIn(List<String> codes);
}