package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.entity.SysOrgInvoice;
import org.etocrm.model.org.invoice.SysInvoiceSaveVO;
import org.etocrm.model.org.invoice.SysInvoiceUpdateVO;
import org.etocrm.repository.SysInvoiceRepository;
import org.etocrm.service.SysInvoiceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 发票表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Service
@Slf4j
public class SysInvoiceServiceImpl implements SysInvoiceService {

    @Resource
    private SysInvoiceRepository sysInvoiceRepository;


    @Override
    public Long saveSysInvoice(SysInvoiceSaveVO sysInvoice, Long id) throws MyException {
        SysOrgInvoice sysInvoice1 = new SysOrgInvoice();
        try {
            BeanUtil.copyProperties(sysInvoice, sysInvoice1);
            sysInvoice1.setOrgId(id);
            sysInvoiceRepository.save(sysInvoice1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException(ResponseEnum.FAILD.getCode(), "新增发票失败");
        }
        return sysInvoice1.getId();
    }

    @Override
    public Long updateByPk(SysInvoiceUpdateVO sysInvoice) throws MyException {
        SysOrgInvoice sysInvoice1 = new SysOrgInvoice();
        BeanUtil.copyProperties(sysInvoice, sysInvoice1);
        sysInvoiceRepository.update(sysInvoice1);
        return sysInvoice1.getId();
    }

    @Override
    public SysInvoiceUpdateVO detailByOrgId(Long pk) throws MyException {
        SysInvoiceUpdateVO sysInvoiceUpdateVO = new SysInvoiceUpdateVO();
        SysOrgInvoice byId = sysInvoiceRepository.findByOrgId(pk);
        if(byId!=null) {
            BeanUtil.copyProperties(byId, sysInvoiceUpdateVO);
            return sysInvoiceUpdateVO;
        }
        return null;
    }

    @Override
    public SysOrgInvoice getInvoiceByOrgId(Long orgId) {
        return sysInvoiceRepository.findByOrgId(orgId);
    }
}
