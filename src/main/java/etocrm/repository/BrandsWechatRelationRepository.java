package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.BrandsWechatRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandsWechatRelationRepository  extends BaseRepository<BrandsWechatRelation, Long> {

    List<BrandsWechatRelation> findByBrandsIdAndType(Long brandsId, String type);

    List<BrandsWechatRelation> findByBrandsIdAndCommonId(Long brandsId, Long commonId);

    List<BrandsWechatRelation> findByType(String type);
}
