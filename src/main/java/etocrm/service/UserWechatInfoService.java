package etocrm.service;

import org.etocrm.database.util.BasePage;
import org.etocrm.entity.UserWechatInfo;

import java.util.List;

/**
 * <p>
 * 用户详情信息 服务类
 * </p>
 * @author admin
 * @since 2021-01-22
 */
public interface UserWechatInfoService {


    /**
     * 添加
     */
    Long saveUserDetails(UserWechatInfo userDetails);

    /**
     * 修改
     */
    Long updateByPk(UserWechatInfo userDetails);

    /**
     * 删除
     */
    void deleteByPk(Long pk);

    /**
     * 详情
     */
    UserWechatInfo detailByPk(Long pk);

    /**
     * 全查列表
     * @return
     */
    List<UserWechatInfo> findAll(UserWechatInfo userDetails);

    /**
     * 分页查询
     */
    BasePage<UserWechatInfo> list(Integer curPage, Integer pageSize, UserWechatInfo userDetails);

}