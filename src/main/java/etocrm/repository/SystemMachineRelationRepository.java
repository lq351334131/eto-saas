package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SystemMachineRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemMachineRelationRepository extends BaseRepository<SystemMachineRelation, Long> {
    List<SystemMachineRelation> findByMachineIdAndSystemIdIn(Long machineId, List<Long> ids);
}
