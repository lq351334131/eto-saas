package etocrm.repository;

import cn.hutool.core.date.DateTime;
import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.uam.SysOrderMeal;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 订单套餐关系表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysOrderMealRepository extends BaseRepository<SysOrderMeal, Long> {


    List<SysOrderMeal> findByOrderIdIn(List<Long> ids);

  //  List<SysOrderMeal> findByOrderIdInAndEnableTimeEndLessThanEqual(List<Long> collect, Date format);

    List<SysOrderMeal> findByOrderIdInAndEnableTimeEndGreaterThanEqual(List<Long> collect, DateTime parseDate);
}