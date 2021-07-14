package etocrm.repository;


import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.OrgSystemRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgSystemRelationRepository extends BaseRepository<OrgSystemRelation, Long> {

    List<OrgSystemRelation> findByOrgId(Long id);

    OrgSystemRelation findByOrgIdAndSystemId(Long uamOrgId, Long systemId);

    /**
     *  根据 机构id  查询数据
     * @param uamOrgId
     * @return
     */
    List<OrgSystemRelation> findAllByOrgId(Long uamOrgId);

}
