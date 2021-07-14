package etocrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.PageBounds;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.entity.UserRole;
import org.etocrm.model.role.RoleSelectListVO;
import org.etocrm.model.role.RoleSelectVO;
import org.etocrm.repository.UserRepository;
import org.etocrm.repository.UserRoleRepository;
import org.etocrm.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * saas角色人员信息 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Service
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private UserRepository userRepository;


    @Override
    public Long saveUserRole(UserRole userRole){
        // todo 做你想做的事
        userRoleRepository.save(userRole);
        return userRole.getId();
    }

    @Override
    public Long updateByPk(UserRole userRole){
        // todo 做你想做的事
        userRoleRepository.save(userRole);
        return userRole.getId();
    }

    @Override
    public void deleteByPk(Long pk){
        // todo 做你想做的事
        userRoleRepository.logicDelete(pk);
    }

    @Override
    public UserRole detailByPk(Long pk){
        // todo 做你想做的事
        Optional<UserRole> byId = userRoleRepository.findById(pk);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    @Override
    public BasePage<RoleSelectListVO> list(Integer curPage, Integer size, RoleSelectVO userRole){
        PageBounds pageBounds = new PageBounds(curPage, size);
        //处理返回结果

        return null;
    }

    @Override
    public List<UserRole> findAll(UserRole userRole){
        // todo 做你想做的事
        return userRoleRepository.findAll((r, q, c) -> {
        return (new QueryConditionUtil()).where(userRole, r, c);
        });
    }
}
