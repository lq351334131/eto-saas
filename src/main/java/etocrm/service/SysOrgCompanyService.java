package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.entity.ImportTemplate;
import org.etocrm.entity.SysOrgCompany;
import org.etocrm.model.company.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 机构公司信息 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-23
 */
public interface SysOrgCompanyService {


    Long saveSysOrgCompany(SysOrgCompanySaveVO sysOrgCompany) throws MyException;


    Long updateByPk(SysOrgCompanyUpdateVO sysOrgCompany) throws MyException;


    SysOrgCompanyUpdateVO detailByPk(Long pk);

    List<SysOrgCompanyTreeVO> findAll(Integer status, Long id, Integer openShop) throws MyException;

    BasePage<SysOrgCompany> list(Integer curPage, Integer pageSize, SysOrgCompany sysOrgCompany);

    Long updateStatus(UpdateCompanyStatusVO vo) throws MyException;


    Long importCompany(MultipartFile file);

    ImportHistoryVO importErrorCompany(Long id);

    ImportTemplate importTemplate();

    BasePage<SysOrgCompanyVO> getCompanyList(Integer offset, Integer limit, SysCompanyPageListVO v);
}