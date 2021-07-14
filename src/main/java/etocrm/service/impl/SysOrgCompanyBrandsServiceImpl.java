package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.exception.MyException;
import org.etocrm.entity.SysOrgCompanyBrands;
import org.etocrm.model.company.SysOrgCompanyBrandsSaveVO;
import org.etocrm.repository.SysOrgCompanyBrandsRepository;
import org.etocrm.service.SysOrgCompanyBrandsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 公司品牌关联信息 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-23
 */
@Service
@Slf4j
public class SysOrgCompanyBrandsServiceImpl implements SysOrgCompanyBrandsService {

    @Resource
    private SysOrgCompanyBrandsRepository sysOrgCompanyBrandsRepository;


    @Override
    public Long saveSysOrgCompanyBrands(SysOrgCompanyBrandsSaveVO sysOrgCompany) {
        SysOrgCompanyBrands sysOrgCompany1 = new SysOrgCompanyBrands();
        BeanUtil.copyProperties(sysOrgCompany, sysOrgCompany1);
        sysOrgCompanyBrandsRepository.save(sysOrgCompany1);
        return sysOrgCompany1.getId();
    }

    @Override
    public Long updateByPk(SysOrgCompanyBrandsSaveVO sysOrgCompany) throws MyException {
        //todo 关联品牌 先删除之前的关联
        SysOrgCompanyBrands sysOrgCompany1 = new SysOrgCompanyBrands();
        BeanUtil.copyProperties(sysOrgCompany, sysOrgCompany1);
        sysOrgCompanyBrandsRepository.update(sysOrgCompany1);
        return sysOrgCompany1.getId();
    }

    @Override
    public List<SysOrgCompanyBrands> findByCompanyId(Long companyId) {
        return sysOrgCompanyBrandsRepository.findByCompanyId(companyId);
    }

}
