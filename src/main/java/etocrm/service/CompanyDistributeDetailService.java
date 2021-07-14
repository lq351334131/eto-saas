package etocrm.service;

import org.etocrm.entity.CompanyDistributeDetail;
import org.etocrm.model.CompanyDistributeDetailVO;

import java.util.List;

/**
 * @Author xingxing.xie
 * @Date 2021/6/28 14:46
 */
public interface CompanyDistributeDetailService {

    /**
     *   save  company  distribute data
     * @param companyDistributeDetailVOs
     * @return
     */
    List<CompanyDistributeDetail> save(List<CompanyDistributeDetailVO> companyDistributeDetailVOs);
}
