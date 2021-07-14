package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysOrgCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 机构公司信息 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-23
 */
@Repository
public interface SysOrgCompanyRepository extends BaseRepository<SysOrgCompany, Long> {

    List<SysOrgCompany> findByOrgId(Long orgId);

    Integer countByOrgIdAndName(Long orgId, String name);

    Integer countByNameAndIdNot(String name, Long id);

    List<SysOrgCompany> findByOrgIdAndStatus(Long orgId, Integer status);

    /**
     *  根据 shopid 查询
     * @param openShop 1  标识 店铺 其他的标识 分公司
     * @param ids
     * @return
     */
    List<SysOrgCompany> findByOpenShopAndIdIn(Integer openShop, List<Long> ids);
    /**
     *  根据 shopid 查询
     * @param openShop 1  标识 店铺 其他的标识 分公司
     * @param id
     * @return
     */
    SysOrgCompany findByOpenShopAndId(Integer openShop, Long id);

    /**
     *  根据 机构 id  查询店铺信息
     * @param openShop
     * @param orgId
     * @return
     */
    List<SysOrgCompany> findByOpenShopAndOrgId(Integer openShop, Long orgId);

    List<SysOrgCompany> findByOrgIdAndStatusAndOpenShop(Long uamOrgId, Integer status, Integer openShop);
}