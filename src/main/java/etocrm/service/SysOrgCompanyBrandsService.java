package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.entity.SysOrgCompanyBrands;
import org.etocrm.model.company.SysOrgCompanyBrandsSaveVO;

import java.util.List;

/**
 * <p>
 * 公司品牌关联信息 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-23
 */
public interface SysOrgCompanyBrandsService {


    Long saveSysOrgCompanyBrands(SysOrgCompanyBrandsSaveVO vo);

    Long updateByPk(SysOrgCompanyBrandsSaveVO sysOrgCompany) throws MyException;

    List<SysOrgCompanyBrands> findByCompanyId(Long companyId);
}