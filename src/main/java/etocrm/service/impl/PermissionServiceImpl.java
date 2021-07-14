package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.util.*;
import org.etocrm.entity.*;
import org.etocrm.entity.uam.SysOrder;
import org.etocrm.entity.uam.SysOrderMeal;
import org.etocrm.enums.BusinessEnum;
import org.etocrm.enums.OrderEnum;
import org.etocrm.enums.PayStatusEnum;
import org.etocrm.exception.UamException;
import org.etocrm.model.dto.UserRoleDTO;
import org.etocrm.model.permission.*;
import org.etocrm.repository.*;
import org.etocrm.service.PermissionService;
import org.etocrm.utils.DateUtil;
import org.etocrm.utils.SaasTreeUtil;
import org.etocrm.utils.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * saas权限资源 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionRepository permissionRepository;
    @Resource
    private SystemInfoRepository systemInfoRepository;
    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private SysOrderRepository sysOrderRepository;
    @Autowired
    private SysOrderMealRepository sysOrderMealRepository;
    @Autowired
    private SysMealPermissionRepository sysMealPermissionRepository;

    @Override
    public Long updateByPk(PermissionUpdateVO permissionUpdateVO) throws Exception {
        //  validatePermissionName(permissionUpdateVO.getMenuName());
        Permission sysPermission = permissionRepository.findByMenuNameAndIdNot(permissionUpdateVO.getMenuName(), permissionUpdateVO.getId());
        if (sysPermission != null) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "菜单名称已经存在");
        }
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionUpdateVO, permission);
        permissionRepository.update(permission);
        return permission.getId();
    }

    @Override
    public void deleteByPk(Long pk) {
        permissionRepository.logicDelete(pk);
    }

    @Override
    public Permission detailByPk(Long pk) {
        Optional<Permission> byId = permissionRepository.findById(pk);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    @Override
    public BasePage<Permission> list(Integer curPage, Integer size, Permission permission) {
        PageBounds pageBounds = new PageBounds(curPage, size);
        Page<Permission> permissions = permissionRepository.findAll((r, q, c) -> {
            return (new QueryConditionUtil()).where(permission, r, c);
        }, PageRequest.of(pageBounds.getOffset(), pageBounds.getLimit()));
        return new BasePage(permissions);
    }

    @Override
    public List<PermissionResponseVO> findAll(PermissionFindVO permission) {
        permission.setParentId(0L);
        List<Permission> all = permissionRepository.findAll((r, q, c) -> {
            return (new QueryConditionUtil()).where(permission, r, c);
        });
        return all.stream().map(s -> {
            PermissionResponseVO permissionResponseVO = new PermissionResponseVO();
            BeanUtil.copyProperties(s, permissionResponseVO);
            Optional<SystemInfo> byId = systemInfoRepository.findById(s.getSystemId());
            byId.ifPresent(systemInfo -> permissionResponseVO.setSystemName(systemInfo.getName()));
            return permissionResponseVO;
        }).collect(Collectors.toList());
    }


    @Override
    public List<PermissionVO> getNodes(NodeVO vo) {

        List<PermissionVO> voList = Lists.newArrayList();
        PermissionConditionVO condition = new PermissionConditionVO();
        condition.setSystemId(vo.getSystemId());
        List<Permission> permissions = permissionRepository.findAll((r, q, c) ->
                new QueryConditionUtil().where(condition, r, c));

        SystemInfo byId = null;
        for (Permission resource : permissions) {
            PermissionVO resourceVO = new PermissionVO();

            if (byId == null) {
                Optional<SystemInfo> byId1 = systemInfoRepository.findById(resource.getSystemId());
                if (byId1.isPresent()) {
                    byId = byId1.get();
                }
            }
            BeanUtils.copyProperties(resource, resourceVO);
            if (byId != null) {
                resourceVO.setSystemCode(byId.getCode());
            }
            voList.add(resourceVO);
        }
        List<PermissionVO> res = SaasTreeUtil.getChildMenuVos(voList, 0L);
        return res;
    }

    @Override
    public ThirdSystemPermissionVO getCurrentLoginUserPermissions() {
        SysUserRedisVO currentLoginUser = securityUtil.getCurrentLoginUser();

        //    Optional<User> byId = userRepository.findById(currentLoginUser.getUserId());
        ThirdSystemPermissionVO res = new ThirdSystemPermissionVO();
//        CurrentUserInfo userInfo = new CurrentUserInfo();
//        if(byId.isPresent()) {
//            BeanUtil.copyProperties(byId.get(),userInfo);
//          //  res.setUserInfo(userInfo);
//        }
        List<UserRoleDTO> roles = userRepository.selectRolesByUserId(currentLoginUser.getUserId());

        List<Long> roleIds = roles.stream().map(UserRoleDTO::getId).collect(Collectors.toList());


        List<RolePermission> rp = rolePermissionRepository.findByRoleIdIn(roleIds);

        List<Permission> permissions = permissionRepository.findAllById(rp.stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));
        List<Permission> menus = permissions.stream().filter(item -> item.getIsButton() == 0 && item.getStatus().equals(BusinessEnum.USING.getCode())).collect(Collectors.toList());
        List<PermissionVO> voList = Lists.newArrayList();
        for (Permission p : menus) {
            PermissionVO vo = new PermissionVO();

            BeanUtils.copyProperties(p, vo);
            if (p.getIsButton() != null && p.getIsButton() == 0) {
                vo.setButton(true);
            }
            voList.add(vo);
        }

        //     List<Role> role = roleRepository.findAllById(roleIds);
//        for (Role r:role) {
//           if("SuperAdmin".equals(r.getCode())&&byId.isPresent()) {
//               userInfo.setAdmin(true);
//            }
//        }
        // res.setPermissionList(permissionsList);
        // res.setRoleList(roleVOList);
        List<PermissionVO> res1 = SaasTreeUtil.getChildMenuVos(voList, 0L);
        res.setPermissionList(res1);
        return res;
    }

    @Override
    public List<PermissionVO> getCurrentLoginUserPermissionList(String systemCode, Integer isButton) {
        Integer menuType = BusinessEnum.MENU.getCode();
        if (BusinessEnum.NO_MENU.getCode().equals(isButton)) {
            menuType = BusinessEnum.NO_MENU.getCode();
        }

        SysUserRedisVO currentLoginUser = securityUtil.getCurrentLoginUser();
        List<Permission> permissions;
        SystemInfo systemInfo = systemInfoRepository.findByCode(systemCode);
        //如果拥有管理员权限，拥有全部套餐里面的菜单
        if (currentLoginUser.getAdminFlag()) {
            List<SysOrder> orders = sysOrderRepository.findByOrgIdAndIsCancelAndPayStatus(currentLoginUser.getUamOrgId(), OrderEnum.INFORCE.getCode(), PayStatusEnum.PAID.getCode());
            //   去查开通的套餐
            List<SysOrderMeal> mealList = sysOrderMealRepository.findByOrderIdInAndEnableTimeEndGreaterThanEqual(orders.stream().map(SysOrder::getId).collect(Collectors.toList()), DateUtils.parseDate(DateUtil.format(new Date(), DateUtil.default_dateformat)));

            List<SysMealPermission> sysMealPermissions = sysMealPermissionRepository.findByMealIdIn(mealList.stream().map(SysOrderMeal::getMealId).collect(Collectors.toList()));
            permissions = permissionRepository.findByStatusAndIsButtonAndIdInOrderByMenuOrderAsc(BusinessEnum.USING.getCode(),menuType,sysMealPermissions.stream().map(SysMealPermission::getMenuId).collect(Collectors.toList()));
        } else {
            List<UserRoleDTO> roles = userRepository.selectRolesByUserId(currentLoginUser.getUserId());

            List<Long> roleIds = roles.stream().map(UserRoleDTO::getId).collect(Collectors.toList());

            List<RolePermission> rp = rolePermissionRepository.findByRoleIdIn(roleIds);

            permissions = permissionRepository.findByStatusAndIsButtonAndIdInOrderByMenuOrderAsc(BusinessEnum.USING.getCode(),menuType,rp.stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));
        }

        if (systemInfo != null) {
            permissions = permissions.stream().filter(item -> item.getSystemId().equals(systemInfo.getId())).collect(Collectors.toList());
        }
        List<PermissionVO> voList = Lists.newArrayList();
        if(BusinessEnum.NO_MENU.getCode().equals(menuType)) {
            for (Permission p : permissions) {
                PermissionVO resourceVO = new PermissionVO();
                BeanUtil.copyProperties(p, resourceVO);
                resourceVO.setAuthFlag(true);
                voList.add(resourceVO);
            }
            //如果是按钮，需要返回全部按钮
            List<Long> collect = voList.stream().map(PermissionVO::getId).collect(Collectors.toList());
            List<Permission> allButton   = permissionRepository.findBySystemIdAndStatusAndIsButton(systemInfo.getId(), BusinessEnum.USING.getCode(), menuType);
            for (Permission b:allButton) {
                if(!collect.contains(b.getId())) {
                    PermissionVO resourceVO = new PermissionVO();
                    BeanUtil.copyProperties(b, resourceVO);
                    resourceVO.setAuthFlag(false);
                    voList.add(resourceVO);
                }
            }
            return voList;
        }
        //List<Permission> menus = permissions.stream().filter(item -> item.getStatus().equals(BusinessEnum.USING.getCode())).collect(Collectors.toList());
        //如果非父节点的查出父节点加入到当前list中
      //  List<Permission> handleMenus = Lists.newArrayList();
        //   List<Long> parentIds = permissions.stream().filter(item -> item.getId() != 0&&item.getStatus()==1).map(Permission::getParentId).distinct().collect(Collectors.toList());

//        for (Permission p : permissions) {
//            List<Long> filterIds = handleMenus.stream().map(Permission::getId).collect(Collectors.toList());
//            if (!filterIds.contains(p.getParentId())) {
//                if (p.getParentId() != 0) {
//                    Optional<Permission> byId = permissionRepository.findById(p.getParentId());
//                    if (byId.isPresent()) {
//                        if (!byId.get().getId().equals(p.getId())) {
//                            handleMenus.add(byId.get());
//                        }
//                    }
//                }
//            }
//            if (!filterIds.contains(p.getId())) {
//                handleMenus.add(p);
//            }
//        }

        for (Permission p : permissions) {
            PermissionVO resourceVO = new PermissionVO();
            BeanUtil.copyProperties(p, resourceVO);
            resourceVO.setAuthFlag(true);
            voList.add(resourceVO);
        }
            //构建菜单树
            List<PermissionVO> res = SaasTreeUtil.getChildMenuVos(voList, 0L);
            return res;
    }

    @Override
    public List<PermissionVO> getSaasLoginUserPermissionList() {

        //查找saas系统
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        SystemInfo etosaas = systemInfoRepository.findByCode("ETOSAAS");

        List<Permission> permissions = Lists.newArrayList();
        if (etosaas != null) {

            permissions = permissionRepository.findBySystemIdAndStatusAndIsButton(etosaas.getId(), BusinessEnum.USING.getCode(), BusinessEnum.MENU.getCode());
            if (!loginUser.getAdminFlag()) {
                permissions = permissions.stream().filter(p -> "工作台".equals(p.getMenuName()) || "仪表盘".equals(p.getMenuName())).collect(Collectors.toList());
            }
        }
        List<PermissionVO> voList = Lists.newArrayList();
        for (Permission p : permissions) {
            PermissionVO resourceVO = new PermissionVO();
            BeanUtil.copyProperties(p, resourceVO);
            voList.add(resourceVO);
        }
        //构建菜单树
        List<PermissionVO> res = SaasTreeUtil.getChildMenuVos(voList, 0L);
        return res;
    }

    @Override
    public List<PermissionVO> getCurrentLoginUserAllPermissionList(String systemCode) {
        SysUserRedisVO currentLoginUser = securityUtil.getCurrentLoginUser();
        List<Permission> permissions;
        SystemInfo systemInfo = systemInfoRepository.findByCode(systemCode);
        //如果拥有管理员权限，拥有全部套餐里面的菜单
        if (currentLoginUser.getAdminFlag()) {
            List<SysOrder> orders = sysOrderRepository.findByOrgIdAndIsCancelAndPayStatus(currentLoginUser.getUamOrgId(), OrderEnum.INFORCE.getCode(), PayStatusEnum.PAID.getCode());
            //   去查开通的套餐
            List<SysOrderMeal> mealList = sysOrderMealRepository.findByOrderIdInAndEnableTimeEndGreaterThanEqual(orders.stream().map(SysOrder::getId).collect(Collectors.toList()), DateUtils.parseDate(DateUtil.format(new Date(), DateUtil.default_dateformat)));

            List<SysMealPermission> sysMealPermissions = sysMealPermissionRepository.findByMealIdIn(mealList.stream().map(SysOrderMeal::getMealId).collect(Collectors.toList()));
            permissions = permissionRepository.findByStatusAndIdInOrderByMenuOrderAsc(BusinessEnum.USING.getCode(),sysMealPermissions.stream().map(SysMealPermission::getMenuId).collect(Collectors.toList()));
        } else {
            List<UserRoleDTO> roles = userRepository.selectRolesByUserId(currentLoginUser.getUserId());

            List<Long> roleIds = roles.stream().map(UserRoleDTO::getId).collect(Collectors.toList());

            List<RolePermission> rp = rolePermissionRepository.findByRoleIdIn(roleIds);

            permissions = permissionRepository.findByStatusAndIdInOrderByMenuOrderAsc(BusinessEnum.USING.getCode(),rp.stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));
        }

        if (systemInfo != null) {
            permissions = permissions.stream().filter(item -> item.getSystemId().equals(systemInfo.getId())).collect(Collectors.toList());
        }
        List<PermissionVO> voList = Lists.newArrayList();
        //如果非父节点的查出父节点加入到当前list中
        //List<Permission> handleMenus = Lists.newArrayList();

//        for (Permission p : permissions) {
//            List<Long> filterIds = handleMenus.stream().map(Permission::getId).collect(Collectors.toList());
//            if (!filterIds.contains(p.getParentId())) {
//                if (p.getParentId() != 0) {
//                    Optional<Permission> byId = permissionRepository.findById(p.getParentId());
//                    if (byId.isPresent()) {
//                        if (!byId.get().getId().equals(p.getId())) {
//                            handleMenus.add(byId.get());
//                        }
//                    }
//                }
//            }
//            if (!filterIds.contains(p.getId())) {
//                handleMenus.add(p);
//            }
//        }

        for (Permission p : permissions) {
            PermissionVO resourceVO = new PermissionVO();
            BeanUtil.copyProperties(p, resourceVO);
            resourceVO.setAuthFlag(true);
            voList.add(resourceVO);
        }
        //构建菜单树
        List<PermissionVO> res = SaasTreeUtil.getChildMenuVos(voList, 0L);
        return res;
    }

    @Override
    public List<PermissionVO> getPermissionListByOrgId(Long orgId) {
        List<SysOrder> orders = sysOrderRepository.findByOrgIdAndIsCancelAndPayStatus(orgId, OrderEnum.INFORCE.getCode(), PayStatusEnum.PAID.getCode());
        //   去查开通的套餐
        List<SysOrderMeal> mealList = sysOrderMealRepository.findByOrderIdInAndEnableTimeEndGreaterThanEqual(orders.stream().map(SysOrder::getId).collect(Collectors.toList()), DateUtils.parseDate(DateUtil.format(new Date(), DateUtil.default_dateformat)));

        List<SysMealPermission> sysMealPermissions = sysMealPermissionRepository.findByMealIdIn(mealList.stream().map(SysOrderMeal::getMealId).collect(Collectors.toList()));
        List<Permission> permissions = permissionRepository.findByStatusAndIdInOrderByMenuOrderAsc(BusinessEnum.USING.getCode(),sysMealPermissions.stream().map(SysMealPermission::getMenuId).collect(Collectors.toList()));
        List<PermissionVO> voList = Lists.newArrayList();
        for (Permission p : permissions) {
            PermissionVO resourceVO = new PermissionVO();
            BeanUtil.copyProperties(p, resourceVO);
            resourceVO.setAuthFlag(true);
            voList.add(resourceVO);
        }
        //构建菜单树
        List<PermissionVO> res = SaasTreeUtil.getChildMenuVos(voList, 0L);
        return res;
    }
}
