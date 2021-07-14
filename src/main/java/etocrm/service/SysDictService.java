package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.model.dict.SysDictVO;

import java.util.List;

public interface SysDictService {


    Long updateByPk(SysDictVO sysDict) throws MyException;


    void deleteByPk(Long pk);


    List<SysDictVO> findAll(SysDictVO sysDict);


    Long findByCode(String code);

    SysDictVO findById(Long id);

    String findDictNameById(Long id);


}