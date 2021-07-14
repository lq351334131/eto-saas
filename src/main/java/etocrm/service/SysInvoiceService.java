package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.entity.SysOrgInvoice;
import org.etocrm.model.org.invoice.SysInvoiceSaveVO;
import org.etocrm.model.org.invoice.SysInvoiceUpdateVO;

/**
 * <p>
 * 发票表 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
public interface SysInvoiceService {

    Long saveSysInvoice(SysInvoiceSaveVO sysInvoice, Long id) throws MyException;

    Long updateByPk(SysInvoiceUpdateVO sysInvoice) throws MyException;


    SysInvoiceUpdateVO detailByOrgId(Long pk) throws MyException;

    SysOrgInvoice getInvoiceByOrgId(Long orgId);


}