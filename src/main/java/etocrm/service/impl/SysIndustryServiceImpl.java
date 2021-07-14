package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.entity.SysIndustry;
import org.etocrm.model.industry.SysIndustrySaveVO;
import org.etocrm.model.industry.SysIndustryVO;
import org.etocrm.repository.SysIndustryRepository;
import org.etocrm.service.SysIndustryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 行业表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Service
@Slf4j
public class SysIndustryServiceImpl implements SysIndustryService {

    @Resource
    private SysIndustryRepository sysIndustryRepository;


    @Override
    public Long saveSysIndustry(SysIndustrySaveVO sysIndustry) throws MyException {
        checkName(sysIndustry.getName().trim());
        SysIndustry sysIndustry1 = new SysIndustry();
        BeanUtil.copyProperties(sysIndustry, sysIndustry1);
        sysIndustryRepository.save(sysIndustry1);
        return sysIndustry1.getId();
    }

    @Override
    public Long updateByPk(SysIndustryVO sysIndustry) throws MyException {
        checkName(sysIndustry.getName().trim());
        SysIndustry sysIndustry1 = new SysIndustry();
        BeanUtil.copyProperties(sysIndustry, sysIndustry1);
        sysIndustryRepository.update(sysIndustry1);
        return sysIndustry1.getId();
    }

    @Override
    public void deleteByPk(Long pk) {
        sysIndustryRepository.logicDelete(pk);
    }

    @Override
    public List<SysIndustryVO> findAll(SysIndustryVO sysIndustry) {
        List<SysIndustry> all = sysIndustryRepository.findAll((r, q, c) -> new QueryConditionUtil().where(sysIndustry, r, c));
        List<SysIndustryVO> list = new ArrayList<>();
        SysIndustryVO vo;
        for (SysIndustry industry : all) {
            vo = new SysIndustryVO();
            BeanUtil.copyProperties(industry, vo);
            list.add(vo);
        }
        return list;
    }

    /**
     * 校验名字
     *
     * @param name
     * @throws MyException
     */
    private void checkName(String name) throws MyException {
        List<SysIndustry> all = sysIndustryRepository.findByName(name);
        if (CollectionUtil.isNotEmpty(all)) {
            throw new MyException(ResponseEnum.FAILD.getCode(), "名字已经存在");
        }
    }
}
