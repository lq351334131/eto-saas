package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.RoleRelation;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRelationRepository extends BaseRepository<RoleRelation, Long> {


    RoleRelation findByRoleId(Long roleId);
}
