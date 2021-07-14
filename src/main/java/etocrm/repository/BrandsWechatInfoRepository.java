package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.BrandsWechatInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 品牌公众号小程序信息 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Repository
public interface BrandsWechatInfoRepository extends BaseRepository<BrandsWechatInfo, Long> {


    BrandsWechatInfo findByAppid(String appid);

    BrandsWechatInfo findByAppidAndIdNot(String appid, Long id);

    List<BrandsWechatInfo> findByTypeAndStatus(String type, Integer code);

    List<BrandsWechatInfo> findByOrganizationIdAndTypeAndStatus(Long orgId, String type, Integer code);

    List<BrandsWechatInfo> findByOrganizationIdAndBrandsIdAndTypeAndStatus(Long uamOrgId, Long brandsId, String type, Integer code);

    /**
     *  根据  type   status ids查询
     * @param type
     * @param ids
     * @return
     */
    List<BrandsWechatInfo> findByTypeAndIdIn(String type, List<Long> ids);

//    BrandsWechatInfo findById(Long id);
}