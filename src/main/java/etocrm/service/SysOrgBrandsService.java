package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.entity.SysOrgBrands;
import org.etocrm.model.brands.*;

import java.util.List;

/**
 * <p>
 * 机构品牌信息 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-23
 */
public interface SysOrgBrandsService {


    void saveSysOrgBrands(SysBrandsVO sysOrgBrands) throws MyException;

    Long updateByPk(SysOrgBrands sysOrgBrands) throws MyException;

    void deleteByPk(Long pk);

    SysOrgBrands detailByPk(Long pk);

    List<SysOrgBrandsListVO> findAll(SysOrgBrandsAllVO sysOrgBrandsSelectVO);

    BasePage<SysOrgBrandsListVO> list(Integer curPage, Integer pageSize, SysOrgBrandsSelectVO sysOrgBrands);

    void updateStatus(UpdateBrandsStatusVO id) throws MyException;
}