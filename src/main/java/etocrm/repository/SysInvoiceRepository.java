package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysOrgInvoice;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 发票表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysInvoiceRepository extends BaseRepository<SysOrgInvoice, Long> {

    SysOrgInvoice findByOrgId(Long pk);
}