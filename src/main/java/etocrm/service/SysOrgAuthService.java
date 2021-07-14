package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.entity.SysOrgIndustry;
import org.etocrm.model.org.orgAuth.SysOrgAuthSaveVO;
import org.etocrm.model.org.orgAuth.SysOrgAuthUpdateVO;
import org.etocrm.model.org.orgAuth.SysOrgAuthVO;

import java.util.List;

/**
 * <p>
 * 认证表 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
public interface SysOrgAuthService {



    Long saveSysOrgAuth(SysOrgAuthSaveVO sysOrgAuth, Long id) throws MyException;


    Long updateByPk(SysOrgAuthUpdateVO sysOrgAuth) throws MyException;

    SysOrgAuthVO detailByOrgId(Long pk) ;

    List<SysOrgIndustry> findByAuthStatus(Long authStatus);
}