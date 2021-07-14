package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.uam.SysOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysOrderRepository extends BaseRepository<SysOrder, Long> {


    List<SysOrder> findByOrgIdAndIsCancelAndPayStatus(Long orgId, Integer isCancel, Integer payStatus);



}