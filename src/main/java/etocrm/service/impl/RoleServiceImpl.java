package etocrm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.PageBounds;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.database.util.SysUserRedisVO;
import org.etocrm.entity.*;
import org.etocrm.enums.BusinessEnum;
import org.etocrm.exception.UamException;
import org.etocrm.model.dto.RoleCountDTO;
import org.etocrm.model.role.*;
import org.etocrm.repository.*;
import org.etocrm.service.RoleService;
import org.etocrm.utils.SecurityUtil;
import org.etocrm.utils.UamRedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * saas角色信息 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private RolePermissionRepository rolePermissionRepository;
    @Resource
    private UamRedisUtil uamRedisUtil;
    @Resource
    private RoleRelationRepository roleRelationRepository;
    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private PermissionRepository permissionRepository;

    private static final String PRE_KEY = "auth:uam:";
    @Autowired
    private SystemInfoRepository systemInfoRepository;

    @Override
    public Long saveRole(RoleSaveVO roleSaveVO) {
        SysUserRedisVO currentLoginUser = securityUtil.getCurrentLoginUser();
        validRoleName(roleSaveVO.getName(),currentLoginUser.getUamOrgId());

        //插入角色
        Role role = new Role();
        String pinyin = PinyinUtil.getPinyin(roleSaveVO.getName()).replaceAll(" ","");

        BeanUtils.copyProperties(roleSaveVO, role);
        role.setOrgId(currentLoginUser.getUamOrgId());
        role.setCode(pinyin);
        roleRepository.save(role);

        //插入角色拥有的资源
        List<RolePermission> saveDo = Lists.newArrayList();
        List<Long> resourceIds = roleSaveVO.getPermissionIds();
        if (CollectionUtil.isNotEmpty(resourceIds)) {
            resourceIds.forEach(ids -> {
                RolePermission roleResources = new RolePermission();
                roleResources.setRoleId(role.getId());
                roleResources.setPermissionId(ids);
                saveDo.add(roleResources);
            });
            rolePermissionRepository.saveAll(saveDo);
        }
        return role.getId();
    }

    private void validRoleName(String name,Long orgId) {

        Role sysRoleDO = roleRepository.findByNameAndOrgId(name,orgId);
        if (sysRoleDO != null) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "角色名称已经存在");
        }
    }

    @Override
    public Long updateByPk(RoleUpdateVO roleUpdateVO) throws MyException {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        Role roleDO = roleRepository.findByNameAndIdNotAndOrgId(roleUpdateVO.getName(), roleUpdateVO.getId(),loginUser.getUamOrgId());
        if (roleDO != null) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "角色名称已经存在");
        }

        //插入角色
        Role role = new Role();
        BeanUtils.copyProperties(roleUpdateVO, role);
        roleRepository.update(role);

        //处理关系

        List<RolePermission> byRoleId = rolePermissionRepository.findByRoleId(roleUpdateVO.getId());
        rolePermissionRepository.deleteAll(byRoleId);
        //rolePermissionRepository.deleteLogicByRoleId(roleUpdateVO.getId());
        List<RolePermission> savePermissions = Lists.newArrayList();
        List<Long> permissionIds = roleUpdateVO.getPermissionIds();
        if (CollectionUtil.isNotEmpty(permissionIds)) {
            for (Long  id:  permissionIds) {
                //删除关联表的之前信息，新增新的关联
                RolePermission permission = new RolePermission();
                permission.setRoleId(roleUpdateVO.getId());
                permission.setPermissionId(id);
                savePermissions.add(permission);
            }
            rolePermissionRepository.saveAll(savePermissions);
        }
        //修改成功，删除redis，鉴权走数据库
        uamRedisUtil.deletePrefixKey(PRE_KEY);
        return role.getId();
    }

    @Override
    public void deleteByPk(Long pk) {

        roleRepository.logicDelete(pk);
    }

    @Override
    public RoleDetailVO detailByPk(Long pk) {

        Optional<Role> byId = roleRepository.findById(pk);
        if (byId.isPresent()) {
            Role role = byId.get();
            RoleDetailVO vo = new RoleDetailVO();
            BeanUtils.copyProperties(role, vo);

            //角色权限
            Optional<SystemInfo> byId1 = systemInfoRepository.findById(byId.get().getSystemId());
            if(byId1.isPresent()) {
                vo.setSystemCode(byId1.get().getCode());
            }

            List<RolePermission> rolePermissions= rolePermissionRepository.findByRoleId(pk);

            List<Permission> permissions= permissionRepository.findAllById(rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));

            List<Long> permissionsId = permissions.stream().map(Permission::getId).collect(Collectors.toList());

            vo.setPermissionIds(permissionsId);
            return vo;
        }
        return null;
    }

    @Override
    public BasePage<RoleSelectListVO> list(Integer curPage, Integer size, RoleSelectVO roleSelectVO) {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        PageBounds pageBounds = new PageBounds(curPage, size);
        Page<Role> roles;
        roles = roleRepository.findAll((Specification<Role>) (root, query, cb) -> {
                    //集合 用于封装查询条件
                    List<Predicate> list = new ArrayList<>();
                    //简单单表查询
                    if (!StringUtils.isEmpty(roleSelectVO.getName())) {
                        Predicate code = cb.like(root.get("name").as(String.class), "%" + roleSelectVO.getName().trim() + "%");
                        list.add(code);
                    }
                    if (null != roleSelectVO.getStatus()) {
                        Predicate payStatus = cb.equal(root.get("status").as(Long.class), roleSelectVO.getStatus());
                        list.add(payStatus);
                    }

                    Predicate orgId = cb.equal(root.get("orgId").as(Long.class), loginUser.getUamOrgId());
                    list.add(orgId);
                    Predicate equal = cb.notEqual(root.get("code"), "SuperAdmin");
                    list.add(equal);
                    return cb.and(list.toArray(new Predicate[0]));
                }
                , PageRequest.of(pageBounds.getOffset(), pageBounds.getLimit(),
                        Sort.by(Sort.Direction.DESC, "updatedTime")));
        BasePage basePage = new BasePage(roles);
        List<Role> records = basePage.getRecords();

        List<RoleSelectListVO> res = Lists.newArrayList();
        List<Long> roleIds = records.stream().map(record -> record.getId()).collect(Collectors.toList());
        List<RoleCountDTO> roleCounts = userRoleRepository.countByRoleId(roleIds);
        List<User> allUsers = userRepository.findAllById(records.stream().map(Role::getUpdatedBy).collect(Collectors.toList()));
        if (CollectionUtil.isNotEmpty(records)) {
            records.forEach(role -> {
                        RoleSelectListVO vo = new RoleSelectListVO();
                        allUsers.forEach(user -> {
                            if (user.getId().equals(role.getUpdatedBy())) {
                                vo.setUpdateUser(user.getName());
                            }
                        });
                        List<RoleCountDTO> filterDO = roleCounts.stream().filter(roleCount -> roleCount.getRoleId().equals(role.getId())).collect(Collectors.toList());
                        BeanUtils.copyProperties(role, vo);
                        if (CollectionUtil.isNotEmpty(filterDO) && filterDO.size() > 0) {
                            vo.setEmpCount(filterDO.get(0).getCount());
                        }

                        if(role.getSystemId()!=null) {
                            Optional<SystemInfo> byId = systemInfoRepository.findById(role.getSystemId());
                            if(byId.isPresent()) {
                                vo.setSystemCode(byId.get().getCode());
                            }
                        }
                        res.add(vo);
                  }
            );
        }
        basePage.setRecords(res);
        return basePage;
    }

    @Override
    public Long relationRole(RoleRelationVO relationVO) throws MyException {

        RoleRelation relation = roleRelationRepository.findByRoleId(relationVO.getRoleId());
        if (relation != null) {
            //删除之前的数据
            relation.setDeleted(true);
            roleRelationRepository.update(relation);
        }
        RoleRelation roleRelation = new RoleRelation();
        BeanUtils.copyProperties(relationVO, roleRelation);
        RoleRelation save = roleRelationRepository.save(roleRelation);
        //插入新的数据
        return save.getId();
    }

    @Override
    public List<RoleSelectListVO> pullDownRoleList(Long systemId) {
        SysUserRedisVO currentLoginUser = securityUtil.getCurrentLoginUser();
        List<RoleSelectListVO> vos = new ArrayList<>();
        List<Role> roles = null;
        if(systemId!=null) {
             roles = roleRepository.findByOrgIdAndSystemIdAndStatus(currentLoginUser.getUamOrgId(), systemId, BusinessEnum.USING.getCode());
        }else {
            roles = roleRepository.findByOrgIdAndStatus(currentLoginUser.getUamOrgId(),  BusinessEnum.USING.getCode());
        }
        for (Role s:roles) {
            if("SuperAdmin".equals(s.getCode())) {
                continue;
            }
            RoleSelectListVO vo = new RoleSelectListVO();
            BeanUtils.copyProperties(s, vo);
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public Long updateStatus(Long id, Integer status) throws MyException{
        List<Long> ids = Lists.newArrayList();
        ids.add(id);
        List<RoleCountDTO> roleCounts = userRoleRepository.countByRoleId(ids);
        if(CollectionUtil.isNotEmpty(roleCounts)) {
            throw new MyException(ResponseEnum.FAILD.getCode(),"已关联账号角色不可禁用");
        }
        Role role = new Role();
        role.setId(id);
        role.setStatus(status);
        roleRepository.update(role);
        return id;
    }

    @Override
    public List<Role> findAll(Role role) {
        return roleRepository.findAll((r, q, c) -> {
            return (new QueryConditionUtil()).where(role, r, c);
        });
    }

}
