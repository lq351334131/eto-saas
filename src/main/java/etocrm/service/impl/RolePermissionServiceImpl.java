package etocrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.PageBounds;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.entity.RolePermission;
import org.etocrm.repository.RolePermissionRepository;
import org.etocrm.service.RolePermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 角色权限信息 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Service
@Slf4j
public class RolePermissionServiceImpl implements RolePermissionService {

    @Resource
    private RolePermissionRepository rolePermissionRepository;


    @Override
    public Long saveRolePermission(RolePermission rolePermission){
        // todo 做你想做的事
        rolePermissionRepository.save(rolePermission);
        return rolePermission.getId();
    }

    @Override
    public Long updateByPk(RolePermission rolePermission){
        // todo 做你想做的事
        rolePermissionRepository.save(rolePermission);
        return rolePermission.getId();
    }

    @Override
    public void deleteByPk(Long pk){
        // todo 做你想做的事
        rolePermissionRepository.logicDelete(pk);
    }

    @Override
    public RolePermission detailByPk(Long pk){
        // todo 做你想做的事
        Optional<RolePermission> byId = rolePermissionRepository.findById(pk);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    @Override
    public BasePage<RolePermission> list(Integer curPage, Integer size, RolePermission rolePermission){
        // todo 做你想做的事
        PageBounds pageBounds = new PageBounds(curPage, size);
        Page<RolePermission> rolePermissions = rolePermissionRepository.findAll((r, q, c) -> {
        return (new QueryConditionUtil()).where(rolePermission, r, c);
        }, PageRequest.of(pageBounds.getOffset(), pageBounds.getLimit()));
        return new BasePage(rolePermissions);
    }

    @Override
    public List<RolePermission> findAll(RolePermission rolePermission){
        // todo 做你想做的事
        return rolePermissionRepository.findAll((r, q, c) -> {
        return (new QueryConditionUtil()).where(rolePermission, r, c);
        });
    }
}
