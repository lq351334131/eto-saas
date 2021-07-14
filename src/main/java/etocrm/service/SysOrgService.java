package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.model.org.*;

import java.util.List;

/**
 * <p>
 * 机构表 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
public interface SysOrgService {


    Long updateByPk(SysOrgDetailVO sysOrg) throws MyException;

    SysOrgDetailVO detailByPk() throws MyException;

    List<SysOrgListVO> findAll(SysOrgSelectAllVO sysOrg) throws MyException;

    BasePage<SysOrgListVO> list(Integer curPage, Integer pageSize, SysOrgSelectVO sysOrg) throws MyException;


    Long updateStatus(SysOrgStatusVO requestId) throws MyException;

    SysOrgDetailInfo detailInfo();
}