package etocrm.convert;

import org.etocrm.entity.CompanyDistributeDetail;
import org.etocrm.model.CompanyDistributeDetailVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @description: 公司分发详情映射器
 * @author xingxing.xie
 * @date 2021/6/8 18:05
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface CompanyDistributeConvert {

    /**
     * 转换对应的vo类
     * @param companyDistributeDetail
     * @return
     */
    CompanyDistributeDetailVO doToVo(CompanyDistributeDetail companyDistributeDetail);

    /**
     * 转换对应的do类
     * @param vo
     * @return
     */
    CompanyDistributeDetail voToDo(CompanyDistributeDetailVO vo);

    /**
     *  voList 转换
     * @param voList
     * @return
     */
    List<CompanyDistributeDetail> voListToDo(List<CompanyDistributeDetailVO> voList);




}
