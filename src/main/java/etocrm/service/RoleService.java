package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.entity.Role;
import org.etocrm.model.role.*;

import java.util.List;

/**
 * <p>
 * saas角色信息 服务类
 * </p>
 * @author admin
 * @since 2021-01-22
 */
public interface RoleService {


    /**
     * 添加
     */
    Long saveRole(RoleSaveVO role);

    /**
     * 修改
     */
    Long updateByPk(RoleUpdateVO role) throws MyException;

    /**
     * 删除
     */
    void deleteByPk(Long pk);

    /**
     * 详情
     */
    RoleDetailVO detailByPk(Long pk);

    /**
     * 全查列表
     * @return
     */
    List<Role> findAll(Role role);

    /**
     * 分页查询
     */
    BasePage<RoleSelectListVO> list(Integer curPage, Integer pageSize, RoleSelectVO role);

    Long relationRole(RoleRelationVO relationVO) throws MyException;

    List<RoleSelectListVO> pullDownRoleList(Long systemId);

    Long updateStatus(Long id, Integer status) throws MyException;
}