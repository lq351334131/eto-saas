package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysDict;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysDictRepository extends BaseRepository<SysDict, Long> {

    List<SysDict> findByCodeAndValue(String code, String value);

    SysDict findByCode(String code);
}