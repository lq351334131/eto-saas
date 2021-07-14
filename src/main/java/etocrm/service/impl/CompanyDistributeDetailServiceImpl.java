package etocrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.etocrm.convert.CompanyDistributeConvert;
import org.etocrm.entity.CompanyDistributeDetail;
import org.etocrm.model.CompanyDistributeDetailVO;
import org.etocrm.repository.CompanyDistributeRepository;
import org.etocrm.service.CompanyDistributeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xingxing.xie
 * @Date 2021/6/28 14:48
 */
@Slf4j
@Service
public class CompanyDistributeDetailServiceImpl implements CompanyDistributeDetailService {
    @Autowired
    private CompanyDistributeRepository repository;
    @Autowired
    private CompanyDistributeConvert convert;

    @Override
    public List<CompanyDistributeDetail> save(List<CompanyDistributeDetailVO> companyDistributeDetailVOs) {

        List<CompanyDistributeDetail> companyDistributeDetails = repository.saveAll(convert.voListToDo(companyDistributeDetailVOs));
        return companyDistributeDetails;
    }
}
