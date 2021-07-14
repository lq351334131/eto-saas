package etocrm.service;

import org.etocrm.database.util.BasePage;
import org.etocrm.entity.UserRole;
import org.etocrm.model.role.RoleSelectListVO;
import org.etocrm.model.role.RoleSelectVO;

import java.util.List;

/**
 * <p>
 * saas角色人员信息 服务类
 * </p>
 * @author admin
 * @since 2021-01-22
 */
public interface UserRoleService {


    /**
     * 添加
     */
    Long saveUserRole(UserRole userRole);

    /**
     * 修改
     */
    Long updateByPk(UserRole userRole);

    /**
     * 删除
     */
    void deleteByPk(Long pk);

    /**
     * 详情
     */
    UserRole detailByPk(Long pk);

    /**
     * 全查列表
     * @return
     */
    List<UserRole> findAll(UserRole userRole);

    /**
     * 分页查询
     */
    BasePage<RoleSelectListVO> list(Integer curPage, Integer pageSize, RoleSelectVO userRole);

}