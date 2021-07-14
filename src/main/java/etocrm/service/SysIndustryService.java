package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.model.industry.SysIndustrySaveVO;
import org.etocrm.model.industry.SysIndustryVO;

import java.util.List;

/**
 * <p>
 * 行业表 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
public interface SysIndustryService {


    /**
     * 添加
     */
    Long saveSysIndustry(SysIndustrySaveVO sysIndustry) throws MyException ;

    /**
     * 修改
     */
    Long updateByPk(SysIndustryVO sysIndustry) throws MyException;

    /**
     * 删除
     */
    void deleteByPk(Long pk);


    /**
     * 全查列表
     *
     * @return
     */
    List<SysIndustryVO> findAll(SysIndustryVO sysIndustry);

}