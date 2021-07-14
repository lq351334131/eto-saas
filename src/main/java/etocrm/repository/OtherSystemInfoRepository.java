package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.OtherSystemInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtherSystemInfoRepository extends BaseRepository<OtherSystemInfo, Long> {


    List<OtherSystemInfo> findByUrlTypeAndIdIn(Integer code, List<Long> otherSysIds);
}
