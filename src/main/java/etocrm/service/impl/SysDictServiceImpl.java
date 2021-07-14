package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.entity.SysDict;
import org.etocrm.model.dict.SysDictVO;
import org.etocrm.repository.SysDictRepository;
import org.etocrm.service.SysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Service
@Slf4j
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private SysDictRepository sysDictRepository;

    @Override
    public Long updateByPk(SysDictVO sysDict) throws MyException {
        checkName(sysDict.getCode(), sysDict.getValue());
        SysDict sysDict1 = new SysDict();
        BeanUtil.copyProperties(sysDict, sysDict1);
        sysDictRepository.update(sysDict1);
        return sysDict1.getId();
    }

    @Override
    public void deleteByPk(Long pk) {
        sysDictRepository.logicDelete(pk);
    }


    @Override
    public List<SysDictVO> findAll(SysDictVO sysDict) {
        List<SysDict> all = sysDictRepository.findAll((r, q, c) ->
                new QueryConditionUtil().where(sysDict, r, c));
        List<SysDictVO> list = new ArrayList<>();
        SysDictVO vo;
        for (SysDict s : all) {
            vo = new SysDictVO();
            BeanUtil.copyProperties(s, vo);
            list.add(vo);
        }
        return list;
    }

    @Override
    public Long findByCode(String code) {
        return Long.valueOf(sysDictRepository.findByCode(code).getValue());
    }

    @Override
    public SysDictVO findById(Long id) {
        Optional<SysDict> byId = sysDictRepository.findById(id);
        if (byId.isPresent()) {
            SysDictVO vo = new SysDictVO();
            SysDict sysDict = byId.get();
            BeanUtil.copyProperties(sysDict, vo);
            return vo;

        }
        return null;
    }

    @Override
    public String findDictNameById(Long id) {
        if (null == id) {
            return "";
        }
        SysDictVO byId = this.findById(id);
        if (byId != null) {
            return byId.getName();
        }
        return "";
    }

    /**
     * 校验名字
     *
     * @param code
     * @throws MyException
     */
    private void checkName(String code, String value) throws MyException {
        List<SysDict> all = sysDictRepository.findByCodeAndValue(code, value);
        if (CollectionUtil.isNotEmpty(all)) {
            throw new MyException(ResponseEnum.FAILD.getCode(), "编码已经存在");
        }
    }
}
