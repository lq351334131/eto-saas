package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.*;
import org.etocrm.entity.*;
import org.etocrm.entity.uam.SysOrder;
import org.etocrm.entity.uam.SysOrderMeal;
import org.etocrm.enums.*;
import org.etocrm.exception.UamException;
import org.etocrm.feign.AuthFeignClient;
import org.etocrm.model.MiniAppRoleListVO;
import org.etocrm.model.UserVO;
import org.etocrm.model.dto.UserCmsParam;
import org.etocrm.model.dto.UserRoleDTO;
import org.etocrm.model.openAccount.GrantAdminUserInfo;
import org.etocrm.model.user.*;
import org.etocrm.model.wechatInfo.WechatBindUserInfoVO;
import org.etocrm.repository.*;
import org.etocrm.repository.uam.SysUserRepository;
import org.etocrm.service.UserService;
import org.etocrm.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 人员信息 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private SysUserRepository sysUserRepository;

    @Autowired
    private AuthFeignClient authFeignClient;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysOrderMealRepository sysOrderMealRepository;

    @Autowired
    private SysOrderRepository sysOrderRepository;

    @Autowired
    private SysMealRepository sysMealRepository;

    @Autowired
    private MachineRoomRepository machineRoomRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private UserWechatInfoRepository userWechatInfoRepository;

    @Autowired
    private SysOrgRepository sysOrgRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BrandsWechatInfoRepository brandsWechatInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private SysMealPermissionRepository sysMealPermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private SystemInfoRepository systemInfoRepository;

    @Autowired
    private SysOrgCompanyRepository sysOrgCompanyRepository;

    @Resource
    private OtherSystemInfoRepository otherSystemInfoRepository;

    @Resource
    private UserWechatInfoRepository userDetailsRepository;

    @Autowired
    private SystemMachineRelationRepository systemRelationRepositoryRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SysOrgBrandsRepository sysOrgBrandsRepository;
    //    @Value("${cms.notice.url}")
//    private String cmsUrl;
    @Autowired
    private MailUtils mailUtil;

    @Resource
    private OtherSystemNoticeUtil otherSystemNoticeUtil;

    @Override
    public Long saveUser(UserSaveVO vo) {

        //判断用户两张表是否唯一
        Integer count = 0;
        count += sysUserRepository.countByUid(vo.getUid());
        count += userRepository.countByUid(vo.getUid());
        if (count > 0) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "用户平台已存在此用户");
        }
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        SysUserRedisVO currentLoginUser = securityUtil.getCurrentLoginUser();
        user.setUpdatePasswordTime(new Date());
        user.setOrgId(currentLoginUser.getUamOrgId());
        User save = userRepository.save(user);
        if (StringUtils.isEmpty(user.getName())) {
            user.setName(user.getUid());
        }
        user.setId(save.getId());
        noticeCms(user);
        return user.getId();
    }

    @Override
    public Long updateByPk(UserUpdateVO user) throws MyException {

        Optional<User> byId = userRepository.findById(user.getId());
        if (byId.isPresent()) {
            User user1 = byId.get();
            noticeCms(user1);
        }
        User updateUser = new User();
        BeanUtils.copyProperties(user, updateUser);
        userRepository.update(updateUser);

        return user.getId();
    }

    private void noticeCms(User user1) {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        String systemIds = redisUtil.get("systemId:" + loginUser.getUid(), String.class);
        List<Long> ids = Lists.newArrayList();
        //缓存不存在，查数据库，这个怕前端缓存没存后端的rediskey
        if (StringUtils.isEmpty(systemIds)) {
            ids = getSystems(loginUser).stream().collect(Collectors.toList());
        } else {
            log.error("系统Id" + systemIds);
            JSONArray s = JSONArray.parseArray(systemIds);

            for (Object o : s) {
                ids.add(Long.valueOf(o.toString()));
            }
        }
        List<SystemInfo> systemInfoList = systemInfoRepository.findAllById(ids);
        UserCmsParam userInfo = new UserCmsParam();
        //if ("ETOCMS".equals(systemInfo.getCode())) {
        Optional<SysOrg> byId = sysOrgRepository.findById(loginUser.getUamOrgId());
        if (byId.isPresent()) {
            Optional<MachineRoom> byId1 = machineRoomRepository.findById(byId.get().getMachineId());
            if (byId1.isPresent()) {
                userInfo.setMachineCode(byId1.get().getCode());
            }
        }

        for (SystemInfo systemInfo : systemInfoList) {

            //通知各个系统
            List<Long> systemParam = Lists.newArrayList();
            systemParam.add(systemInfo.getId());
            log.error("machineId---------" + byId.get().getMachineId());
            log.error("sysParam---------" + systemParam);
            List<SystemMachineRelation> relations = systemRelationRepositoryRepository.findByMachineIdAndSystemIdIn(byId.get().getMachineId(), systemParam);
            log.error("relation-------" + relations);
            List<OtherSystemInfo> allSystems = otherSystemInfoRepository.findAllById(relations.stream().map(SystemMachineRelation::getOtherSystemId).collect(Collectors.toList()));
            userInfo.setLoginUid(user1.getUid());
            userInfo.setName(user1.getName());
            userInfo.setEmail(user1.getEmail());
            if (user1.getIsAdmin() == null) {
                userInfo.setIsAdmin(0);
            } else {
                userInfo.setIsAdmin(user1.getIsAdmin());
            }
            userInfo.setContactsPhone(user1.getContactsPhone());
            userInfo.setName(user1.getName());
            userInfo.setCrmOrgId(loginUser.getOrgId());
            userInfo.setUamOrgId(loginUser.getUamOrgId());
            userInfo.setPosition(user1.getPosition());
            userInfo.setUserId(user1.getId());
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");
            HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(userInfo), httpHeaders);
            log.error("allSystem---------" + allSystems);
            Optional<String> first = allSystems.stream().filter(item -> item.getUrlType() == OtherSystemUrlTypeEnum.ACCOUNT_ADD_REDIRECT.getCode()).map(OtherSystemInfo::getUrlDetail).findFirst();
            String url = "";
            if (first.isPresent()) {
                url = first.get();
            }
            if (!StringUtils.isEmpty(url) && (url.contains("http://") || url.contains("https://"))) {
                log.error("通知结果的参数------------" + httpEntity);
                ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url,
                        HttpMethod.POST, httpEntity, JSONObject.class);
                log.error("通知系统结果---------" + responseEntity);
            }
            //} }
        }
    }

    @Override
    public void deleteByPk(Long pk) {
        // todo 做你想做的事
        userRepository.logicDelete(pk);
    }

    @Override
    public UserDetail detailByPk(Long pk) {
        UserDetail userDetail = new UserDetail();
        Optional<User> byId = userRepository.findById(pk);
        if (byId.isPresent()) {
            User user = byId.get();
            BeanUtil.copyProperties(user, userDetail);
            List<UserWechatInfo> byUserId = userDetailsRepository.findByUserId(user.getId());
            List<WechatBindUserInfoVO> vos = new ArrayList<>();
            byUserId.forEach(s -> {
                WechatBindUserInfoVO vo = new WechatBindUserInfoVO();
                BeanUtil.copyProperties(s, vo);
                vos.add(vo);
            });
            userDetail.setWechatInfos(vos);
        }
        return userDetail;
    }

    @Override
    public BasePage<UserSelectListVO> list(Integer curPage, Integer size, UserSelectVO userVO) {
        SysUserRedisVO currentLoginUser = securityUtil.getCurrentLoginUser();
        List<Long> ids = null;
        if (userVO.getRoleId() != null) {
            //查出符合userId
            ids = userRoleRepository.findByRoleId(userVO.getRoleId()).stream().map(UserRole::getUserId).collect(Collectors.toList());
        }
        PageBounds pageBounds = new PageBounds(curPage, size);
        List<Long> finalIds = ids;
        Page<User> users = userRepository.findAll((Specification<User>) (root, query, cb) -> {
                    //集合 用于封装查询条件
                    List<Predicate> list = new ArrayList<>();
                    //简单单表查询
                    if (!StringUtils.isEmpty(userVO.getName())) {
                        Predicate code = cb.like(root.get("name").as(String.class), "%" + userVO.getName().trim() + "%");
                        list.add(code);
                    }
                    if (null != userVO.getStatus()) {
                        Predicate payStatus = cb.equal(root.get("status").as(Long.class), userVO.getStatus());
                        list.add(payStatus);
                    }
                    if (null != userVO.getPosition()) {
                        Predicate position = cb.like(root.get("position").as(String.class), "%" + userVO.getPosition().trim() + "%");
                        list.add(position);
                    }

                    Predicate isAdmin = cb.equal(root.get("isAdmin").as(Integer.class), 0);
                    list.add(isAdmin);

                    if (finalIds != null) {
                        CriteriaBuilder.In<Long> condition = cb.in(root.get("id"));
                        for (Long id : finalIds) {
                            condition.value(id);
                            list.add(condition);
                        }
                    }

                    CriteriaBuilder.In<Long> orgId = cb.in(root.get("orgId"));
                    orgId.value(currentLoginUser.getUamOrgId());
                    list.add(orgId);
                    return cb.and(list.toArray(new Predicate[0]));
                }
                , PageRequest.of(pageBounds.getOffset(), pageBounds.getLimit(),
                        Sort.by(Sort.Direction.DESC, "updatedTime")));

        BasePage bsePage = new BasePage(users);

        List<User> records = bsePage.getRecords();

        List<UserSelectListVO> res = Lists.newArrayList();
        for (User user : records) {
            List<UserRole> byUserId = userRoleRepository.findByUserId(user.getId());
            UserSelectListVO vo = new UserSelectListVO();
            BeanUtils.copyProperties(user, vo);
            List<RoleRelationVO> roleRelationVOList = Lists.newArrayList();
            List<Role> roles = roleRepository.findAllById(byUserId.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
            for (Role role : roles) {
                if (role.getSystemId() != null) {
                    Optional<SystemInfo> byId = systemInfoRepository.findById(role.getSystemId());

                    RoleRelationVO roleRelationVO = new RoleRelationVO();
                    roleRelationVO.setRoleName(role.getName());
                    roleRelationVO.setId(role.getId());
                    if (byId.isPresent()) {
                        roleRelationVO.setSystemName(byId.get().getName());
                    }
                    roleRelationVOList.add(roleRelationVO);
                }
            }
            vo.setUserRoles(roleRelationVOList);
            res.add(vo);
        }
        bsePage.setRecords(res);
        return bsePage;
    }

    @Override
    public LoginUserVO detailByUserName(LoginUser loginUser) {
        User user = userRepository.findByUid(loginUser.getUserName());

        if (user == null) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "用户不存在");
        }
        if (!loginUser.getPassword().equals(user.getPassword())) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "用户名密码错误");
        }
        if (user.getStatus().equals(BusinessEnum.NOTUSE.getCode())) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "此用户被禁用，请联系管理员");
        }
        LoginUserVO userVO = new LoginUserVO();
        //请求发放token
        TokenValue tokenValue = authFeignClient.login(loginUser);
        BeanUtils.copyProperties(tokenValue, userVO);
        userVO.setId(user.getId());
        userVO.setName(user.getName());
        Optional<SysOrg> byId = sysOrgRepository.findById(user.getOrgId());
        if (byId.isPresent()) {
            userVO.setOrgId(byId.get().getCrmOrgId());
        }
        userVO.setUid(user.getUid());
        SysUserRedisVO redisVO = new SysUserRedisVO();
        redisVO.setUserId(user.getId());
        redisVO.setName(user.getName());
        redisVO.setUid(user.getUid());
        if (byId.isPresent()) {
            SysOrg sysOrg = byId.get();
            List<SysOrgBrands> brands = sysOrgBrandsRepository.findByOrgId(sysOrg.getId());
            if (CollectionUtil.isNotEmpty(brands)) {
                redisVO.setCrmBrandId(brands.get(0).getCrmBrandId());
            }
            redisVO.setOrgId(sysOrg.getCrmOrgId());
        }
        redisVO.setUamOrgId(user.getOrgId());
        redisVO.setUpdatePasswordTime(user.getUpdatePasswordTime());
        if (user.getIsAdmin() == 1) {
            redisVO.setAdminFlag(true);
        }
        redisUtil.set(user.getUid() + ":" + Md5Utils.encode(tokenValue.getAccessToken()), redisVO, tokenValue.getExpiresIn());
        return userVO;
    }

    @Override
    public List<UserVO> getUserInfoByIds(Set<Long> userIds) {
        List<User> allById = userRepository.findAllById(userIds);
        List<UserVO> res = Lists.newArrayList();

        allById.forEach(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            res.add(userVO);
        });
        return res;
    }

    @Override
    public List<UserVO> getUserInfoByOrgId(Long orgId) {
        List<User> allById = userRepository.findAllByOrgId(orgId);
        List<UserVO> res = Lists.newArrayList();

        allById.forEach(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            res.add(userVO);
        });
        return res;
    }


    @Override
    public LoginCurrentVO getCurrentUserInfo(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            //查找套餐信息
            Long orgId = byId.get().getOrgId();

            List<SysOrder> orders = sysOrderRepository.findByOrgIdAndIsCancelAndPayStatus(orgId, OrderEnum.INFORCE.getCode(), PayStatusEnum.PAID.getCode());
            List<Long> ids = orders.stream().map(SysOrder::getId).collect(Collectors.toList());
            List<SysOrderMeal> mealList = sysOrderMealRepository.findByOrderIdIn(ids);
            Map<String, LoginCurrentVO> map = new HashMap<>();
            for (SysOrderMeal meal : mealList) {
                LoginCurrentVO res = map.get("meal");
                Date enableTimeEnd = meal.getEnableTimeEnd();
                if (new Date().getTime() - enableTimeEnd.getTime() > 0) {
                    continue;
                }
                //  Date enableTimeStart = meal.getEnableTimeStart();
                Long ms = DateUtils.betweenMs(new Date(), enableTimeEnd) / 1000;
                if (res == null) {
                    LoginCurrentVO currentVO = new LoginCurrentVO();
                    currentVO.setDays(ms / (60 * 60 * 24));
                    currentVO.setMealId(meal.getMealId());
                    currentVO.setDateLine(DateUtils.format(meal.getEnableTimeEnd(), DateUtils.DEFAULT_DATEFORMAT));
                    map.put("meal", currentVO);
                } else {
                    if (res.getDays() < ms / (60 * 60 * 24)) {
                        LoginCurrentVO currentVO = new LoginCurrentVO();
                        currentVO.setDays(ms / (60 * 60 * 24));
                        currentVO.setMealId(meal.getMealId());
                        currentVO.setDateLine(DateUtils.format(meal.getEnableTimeEnd(), DateUtils.DEFAULT_DATEFORMAT));
                        map.put("meal", currentVO);
                    }
                }
            }
            //如果存在套餐的id
            LoginCurrentVO vo = map.get("meal");
            User user = byId.get();
            //  vo.setUid(user.getName());
            if (vo != null) {
                Optional<SysMeal> meal = sysMealRepository.findById(vo.getMealId());
                vo.setUid(byId.get().getUid());
                vo.setMealName(meal.get().getName());
                vo.setName(byId.get().getName());

            } else {
                vo = new LoginCurrentVO();
            }
            vo.setType(user.getIsAdmin());
            return vo;
        }
        return null;
    }

    @Override
    public Long updateStatus(Long id, Integer status) throws MyException {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "用户不存在");
        }
        User user = byId.get();
        user.setStatus(status);
        userRepository.update(user);
        return id;
    }

    @Override
    public Long userRelationRole(UserRoleVO vo) throws MyException {
        Optional<SystemInfo> byId = systemInfoRepository.findById(vo.getSystemId());
        if (byId.isPresent()) {
            SystemInfo systemInfo = byId.get();
            List<UserRole> res = userRoleRepository.findByUserId(vo.getId());
            if (CollectionUtil.isNotEmpty(res)) {
                List<Long> roleIds = res.stream().map(UserRole::getRoleId).collect(Collectors.toList());
                List<Role> all = roleRepository.findAllById(roleIds);
                List<Long> systemIds = all.stream().map(Role::getSystemId).collect(Collectors.toList());
                if (systemIds.contains(vo.getSystemId())) {
                    throw new MyException(ResponseEnum.FAILD.getCode(), "同一个系统，不可重复关联");
                }
            }
            //小程序和公众号需要特殊处理
            if (systemInfo.getCode().equals(AccountTypeEnum.ETO_MINIAPP.getCode()) || systemInfo.getCode().equals(AccountTypeEnum.ETO_SERVICE.getCode())) {
                saveMiniService(vo);
            } else {
                saveUserRole(vo);
            }
            return vo.getId();
        }
        return null;
    }

    private void saveMiniService(UserRoleVO vo) {
        if (CollectionUtil.isNotEmpty(vo.getRelationList())) {
            for (MiniAppRoleListVO u : vo.getRelationList()) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(u.getRoleId());
                userRole.setUserId(vo.getId());
                userRole.setCompanyId(vo.getCompanyId());
                userRole.setWechatInfoId(String.valueOf(u.getId()));
                userRole.setAccountType(vo.getAccountType());
                userRoleRepository.save(userRole);
            }
        }
    }

    private void saveUserRole(UserRoleVO vo) throws MyException {


        UserRole userRole = new UserRole();
        if (CollectionUtil.isNotEmpty(vo.getRelationList())) {
            userRole.setRoleId(vo.getRelationList().get(0).getRoleId());
        }
        if (vo.getRoleId() != null) {
            userRole.setRoleId(vo.getRoleId());
        }
        userRole.setUserId(vo.getId());
        userRole.setCompanyId(vo.getCompanyId());
        userRole.setWechatInfoId(vo.getWechatInfoId());
        userRole.setShopId(vo.getShopId());
        userRole.setAccountType(vo.getAccountType());
        userRoleRepository.save(userRole);
    }

    @Override
    public List<UserSystemVO> manageSystem() {

        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();

        Set<Long> systemIds = getSystems(loginUser);

//        List<Role> roles = roleRepository.findByOrgIdAndCode(loginUser.getOrgId(), "SuperAdmin");
//
//        Set<Long> ids = Sets.newHashSet();
//        for (Long id : systemIds) {
//            if (roles.size() <= 0) {
//                ids.add(id);
//            } else {
//                for (Role r : roles) {
//                    if (!id.equals(r.getSystemId())) {
//                        ids.add(id);
//                    }
//                }
//            }
//        }
        return getUserSystemVOS(loginUser, systemIds);
    }

    private Set<Long> getSystems(SysUserRedisVO loginUser) {
        List<Permission> permissions=new ArrayList<>();
        List<SysOrder> orders =  sysOrderRepository.findByOrgIdAndIsCancelAndPayStatus(loginUser.getUamOrgId(), OrderEnum.INFORCE.getCode(), PayStatusEnum.PAID.getCode());
        if (loginUser.getAdminFlag()) {
            //   去查开通的套餐
            List<SysOrderMeal> mealList = sysOrderMealRepository.findByOrderIdInAndEnableTimeEndGreaterThanEqual(orders.stream().map(SysOrder::getId).collect(Collectors.toList()), DateUtils.parseDate(DateUtil.format(new Date(), DateUtil.default_dateformat)));

            List<SysMealPermission> sysMealPermissions = sysMealPermissionRepository.findByMealIdIn(mealList.stream().map(SysOrderMeal::getMealId).collect(Collectors.toList()));

            permissions = permissionRepository.findAllById(sysMealPermissions.stream().map(SysMealPermission::getMenuId).collect(Collectors.toList()));
            //去重套餐里面的系统

        } else if(CollectionUtil.isNotEmpty(orders)) {
            List<UserRoleDTO> roles = userRepository.selectRolesByUserId(loginUser.getUserId());

            List<Long> roleIds = roles.stream().map(UserRoleDTO::getId).collect(Collectors.toList());

            List<RolePermission> rp = rolePermissionRepository.findByRoleIdIn(roleIds);

            permissions = permissionRepository.findByStatusAndIdInOrderByMenuOrderAsc(BusinessEnum.USING.getCode(), rp.stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));
        }
        return permissions.stream().map(Permission::getSystemId).collect(Collectors.toSet());
    }

    @Override
    public Integer dismissionWechatInfo(Long id) {

        userWechatInfoRepository.deleteById(id);
        return 1;
    }

    @Override
    public Integer deleteUserRole(UserRoleRelationVO userRoleVO) {
        List<UserRole> userRoles = userRoleRepository.findByRoleIdAndUserId(userRoleVO.getRoleId(), userRoleVO.getId());
        if (userRoles.size() > 0) {
            userRoleRepository.deleteAll(userRoles);
            return 1;
        }
        return 0;
    }

    @Override
    public List<UserSystemVO> roleManageSystem() {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        Set<Long> systemIds = getSystems(loginUser);
        return getUserSystemVOS(loginUser, systemIds);

    }

    @Override
    public Long updateUserInfo(UserVO userVO) throws MyException {

        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        User update = userRepository.update(user);
        //下发
        grantAdminUserInfo(userVO.getOrgId(), update);
        return user.getId();
    }

    private void grantAdminUserInfo(Long orgId, User user) throws MyException {
        GrantAdminUserInfo grantAdminUserInfo = new GrantAdminUserInfo();
        grantAdminUserInfo.setContactsPhone(user.getContactsPhone());
        grantAdminUserInfo.setUserId(user.getId());
        grantAdminUserInfo.setCrmOrgId(user.getOrgId());
        grantAdminUserInfo.setUamOrgId(user.getOrgId());
        grantAdminUserInfo.setIsAdmin(user.getIsAdmin());
        grantAdminUserInfo.setLoginUid(user.getUid());
        grantAdminUserInfo.setPassword(user.getPassword());
        grantAdminUserInfo.setStatus(user.getStatus());
        grantAdminUserInfo.setName(user.getName());
        grantAdminUserInfo.setAvatar(user.getAvatar());
        //调接口
        log.info("下发用户参数：{}", JSON.toJSONString(grantAdminUserInfo));
        otherSystemNoticeUtil.restTemplate(orgId, null, JSON.toJSONString(grantAdminUserInfo), OtherSystemUrlTypeEnum.ACCOUNT_UPDATE_NOTICE_REDIRECT);
    }

    @Override
    public UserDetail getCurrentUserDetail() {
        SysUserRedisVO currentLoginUser = securityUtil.getCurrentLoginUser();
        return detailByPk(currentLoginUser.getUserId());
    }

    @Override
    public List<UserLikeVO> userInfoLike(UserLikeVO userLikeVO) {
        List<User> all = userRepository.findAll((r, q, c) -> {
            return (new QueryConditionUtil()).where(userLikeVO, r, c);
        });
        List<UserLikeVO> vos = new ArrayList<>();
        all.forEach(s -> {
            UserLikeVO vo = new UserLikeVO();
            BeanUtil.copyProperties(s, vo);
            vos.add(vo);
        });
        return vos;
    }

    @Override
    public List<UserSystemVO> getCurrentLoginSystem() {

        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();

        Set<Long> systemIds = getSystems(loginUser);

        String systemId = redisUtil.get("systemId:" + loginUser.getUid(), String.class);

        if (CollectionUtil.isNotEmpty(systemIds) && !systemIds.toString().equals(systemId)) {
            //修改redis值
            redisUtil.set("systemId:" + loginUser.getUid(), systemIds.toString(), 7200 * 2);
        }

        return getUserSystemVOS(loginUser, systemIds);
    }

    @Override
    public LoginUserVO getCurrentLoginSystemAccessToken(String code) {

        Long userId = redisUtil.get(code, Long.class);
        if (!StringUtils.isEmpty(userId)) {
            //发放token
            Optional<User> byId = userRepository.findById(userId);
            if (byId.isPresent()) {
//                LoginUser loginUser = new LoginUser();
//                loginUser.setUserName(byId.get().getName());
//                loginUser.setPassword(byId.get().getPassword());
//                LoginUserVO userVO = new LoginUserVO();
//                //请求发放token
//                TokenValue tokenValue = authFeignClient.login(loginUser);
//                BeanUtils.copyProperties(tokenValue, userVO);
//
//                User user = byId.get();
//                userVO.setId(user.getId());
//                userVO.setName(user.getName());
//                userVO.setOrgId(user.getOrgId());
//                userVO.setUid(user.getUid());
                LoginUserVO userVO = redisUtil.get(byId.get().getUid(), LoginUserVO.class);
                int expire = (int) redisUtil.getExpire(byId.get().getUid());
                userVO.setExpiresIn(expire);
                redisUtil.deleteCache(code);
                //redisUtil.deleteCache(byId.get().getUid());
                return userVO;
            }
        }
        return null;
    }

    @Override
    public CurrentUserInfo getCurrentLoginUserInfo() {

        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        CurrentUserInfo userInfo = new CurrentUserInfo();
        BeanUtils.copyProperties(loginUser, userInfo);
        userInfo.setUserId(loginUser.getUserId());
        Optional<SysOrg> byId = sysOrgRepository.findById(loginUser.getUamOrgId());
        if (byId.isPresent()) {
            userInfo.setOrgName(byId.get().getName());
            userInfo.setOrgId(loginUser.getOrgId());
            Optional<MachineRoom> machineRoom = machineRoomRepository.findById(byId.get().getMachineId());
            if (machineRoom.isPresent()) {
                userInfo.setRoomId(machineRoom.get().getId());
                userInfo.setRoomName(machineRoom.get().getRoomName());
                userInfo.setCode(machineRoom.get().getCode());
                userInfo.setUamOrgId(loginUser.getUamOrgId());
                userInfo.setAdmin(loginUser.getAdminFlag());
            }
        }
        return userInfo;
    }

    @Override
    public void logout(HttpServletRequest request1) {
        HttpSession session = request1.getSession();
        session.invalidate();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();
            String authorization = request.getHeader("SYS_SESSION_REDIS_KEY");
            if (org.apache.commons.lang3.StringUtils.isNotBlank(authorization)) {

                SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
                String systemIds = redisUtil.get("systemId:" + loginUser.getUid(), String.class);
                List<Long> ids = Lists.newArrayList();
                //缓存不存在，查数据库，这个怕前端缓存没存后端的rediskey
                if (StringUtils.isEmpty(systemIds)) {
                    ids = getSystems(loginUser).stream().collect(Collectors.toList());
                } else {
                    log.error("系统Id" + systemIds);
                    JSONArray s = JSONArray.parseArray(systemIds);

                    for (Object o : s) {
                        ids.add(Long.valueOf(o.toString()));
                    }
                }

                //查询相关需要通知的系统，从redis获取需要通知系统Id
                String uid = loginUser.getUid();
                Optional<SysOrg> byId = sysOrgRepository.findById(loginUser.getUamOrgId());
                // List<SystemRelation> all = systemRelationRepository.findByMachineIdAndSystemIdIn(byId.get().getMachineId(),ids);
                List<SystemMachineRelation> relations = systemRelationRepositoryRepository.findByMachineIdAndSystemIdIn(byId.get().getMachineId(), ids);
                List<OtherSystemInfo> allSystems = otherSystemInfoRepository.findAllById(relations.stream().map(SystemMachineRelation::getOtherSystemId).collect(Collectors.toList()));
                LoginUserVO userVO = redisUtil.get(uid, LoginUserVO.class);
                //通知系统
                for (OtherSystemInfo mr : allSystems) {
                    if (userVO != null) {
                        if (mr.getUrlDetail() != null && (mr.getUrlDetail().contains("http://") || mr.getUrlDetail().contains("https://")) && mr.getUrlType() == OtherSystemUrlTypeEnum.LOGOUT_URL.getCode()) {
                            String res = restTemplate.getForObject(mr.getUrlDetail() + "?access_token=" + userVO.getAccessToken() + "&uid=" + loginUser.getUserId().toString(), String.class);
                            log.error("通知结果----" + res);
                        }
                    }
                }
                //在删除redis
                redisUtil.deleteCache(authorization);
                //  redisUtil.deleteCache(uid);
            }
        }

    }

    @Override
    public String sendVerifyCode(EmailParam emailParam) throws MyException {
        boolean email = CheckUtils.isEmail(emailParam.getEmail());
        if (!email) {
            throw new MyException(BusinessEnum.DATA_SOURCE_EMAIL_ERROR.getCode(), BusinessEnum.DATA_SOURCE_EMAIL_ERROR.getMessage());
        }
        //查询数据库，没有数据就发送不了
        User byUid = userRepository.findByUid(emailParam.getEmail());
        if (byUid == null) {
            throw new MyException(ResponseEnum.FAILD.getCode(), "用户不存在");
        }
        //发送随机6位数字
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += RandomUtil.getRandom().nextInt(10);
        }
        MailAccount account = mailUtil.getMailAccount();
        MailUtil.send(account, emailParam.getEmail(), "手机邮箱验证码", result, false);
        redisUtil.set(emailParam.getEmail(), result, 60);
        return result;
    }

    @Override
    public Long verifyEmail(EmailParam emailParam) throws MyException {
        boolean email = CheckUtils.isEmail(emailParam.getEmail());
        if (!email) {
            throw new MyException(BusinessEnum.DATA_SOURCE_EMAIL_ERROR.getCode(), BusinessEnum.DATA_SOURCE_EMAIL_ERROR.getMessage());
        }
        String code = redisUtil.get(emailParam.getEmail(), String.class);
        if (code != null && code.equals(emailParam.getCode())) {
            //删除redis
            redisUtil.deleteCache(emailParam.getEmail());
            return 1L;
        }
        return 0L;
    }

    @Override
    public Long resetPasswordByEmail(EmailPasswordParam emailPasswordParam) throws MyException {

        User uid = userRepository.findByUid(emailPasswordParam.getEmail());
        uid.setPassword(emailPasswordParam.getPassword());
        User update = userRepository.update(uid);
        return update.getId();
    }

    @Override
    public Long updatePassword(UpdatePasswordParam updatePasswordParam) throws MyException {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();

        Optional<User> byId = userRepository.findById(loginUser.getUserId());
        if (byId.isPresent()) {
            User user = byId.get();
            if (!user.getPassword().equals(updatePasswordParam.getOldPassword())) {
                throw new MyException(ResponseEnum.FAILD.getCode(), "原密码不正确");
            }
            user.setPassword(updatePasswordParam.getPassword());
            userRepository.update(user);
            return user.getId();
        }
        return 0L;
    }

    @Override
    public Long updateUserRole(UserRoleVO vo) throws MyException {
        //小程序和公众号需要特殊处理
        Optional<SystemInfo> byId = systemInfoRepository.findById(vo.getSystemId());
        if (byId.isPresent()) {
            //小程序公众号特殊处理
            SystemInfo systemInfo = byId.get();
            //   List<UserRole> byUserId = userRoleRepository.findByUserIdAndRoleId(vo.getId(), vo.getRoleId());

            //新增关联
            List<UserRole> res = null;
            if (systemInfo.getCode().equals(AccountTypeEnum.ETO_MINIAPP.getCode()) || systemInfo.getCode().equals(AccountTypeEnum.ETO_SERVICE.getCode())) {
                List<Long> roleIds = vo.getRelationList().stream().map(MiniAppRoleListVO::getRoleId).collect(Collectors.toList());
                res = userRoleRepository.findByUserIdAndRoleIdIn(vo.getId(), roleIds);
            } else {
                res = userRoleRepository.findByRoleIdAndUserId(vo.getRoleId(), vo.getId());
            }
            if (CollectionUtil.isNotEmpty(res)) {
                userRoleRepository.deleteAll(res);
            }
            if (CollectionUtil.isNotEmpty(res)) {
                List<UserRole> byUserId = userRoleRepository.findByUserId(vo.getId());
                if (CollectionUtil.isNotEmpty(byUserId)) {
                    List<Long> roleIds = byUserId.stream().map(UserRole::getRoleId).collect(Collectors.toList());
                    List<Role> all = roleRepository.findAllById(roleIds);
                    List<Long> systemIds = all.stream().map(Role::getSystemId).collect(Collectors.toList());
                    if (systemIds.contains(vo.getSystemId())) {
                        throw new MyException(ResponseEnum.FAILD.getCode(), "同一个系统，不可重复关联");
                    }
                }
            }
            //小程序和公众号需要特殊处理
            if (systemInfo.getCode().equals(AccountTypeEnum.ETO_MINIAPP.getCode()) || systemInfo.getCode().equals(AccountTypeEnum.ETO_SERVICE.getCode())) {
                saveMiniService(vo);
            } else {
                saveUserRole(vo);
            }
            return vo.getId();
        }
        return null;
    }


    @Override
    public UserRoleVO getUserRole(Long userId, Long userRoleId) {
        List<UserRole> byUserId = userRoleRepository.findByUserIdAndRoleId(userId, userRoleId);
        UserRoleVO userRoleRelationVO = new UserRoleVO();
        List<MiniAppRoleListVO> relationList = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(byUserId)) {
            Long roleId = byUserId.get(0).getRoleId();
            Long companyId = byUserId.get(0).getCompanyId();
            String shopIds = byUserId.get(0).getShopId();
            // Object[] objects = byUserId.stream().map(UserRole::getWechatInfoId).toArray();
            String wechatInfoIds = "";
            StringBuilder sb = new StringBuilder();
            for (UserRole u : byUserId) {
                if (!StringUtils.isEmpty(u.getWechatInfoId())) {
                    if (sb.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                        sb.append(",");
                    }
                    sb.append(u.getWechatInfoId());
                }
            }
            wechatInfoIds = sb.toString();
//            if(objects!=null) {
//                 wechatInfoIds = StringUtil.join(",", objects);
//            }
            Optional<Role> role = roleRepository.findById(roleId);

            if (!StringUtils.isEmpty(wechatInfoIds)) {
                String[] infoIds = wechatInfoIds.split(",");
                for (String wechatInfoId : infoIds) {
                    MiniAppRoleListVO miniAppRoleListVO = new MiniAppRoleListVO();
                    Optional<BrandsWechatInfo> byId1 = brandsWechatInfoRepository.findById(Long.valueOf(wechatInfoId));
                    if (byId1.isPresent()) {
                        miniAppRoleListVO.setName(byId1.get().getWechatName());
                    }
                    miniAppRoleListVO.setRoleId(roleId);
                    miniAppRoleListVO.setId(Long.valueOf(wechatInfoId));
                    relationList.add(miniAppRoleListVO);
                }
            }

            if (!StringUtils.isEmpty(shopIds)) {
                String[] shopId = shopIds.split(",");
                for (String shop : shopId) {
                    MiniAppRoleListVO miniAppRoleListVO = new MiniAppRoleListVO();
                    Optional<SysOrgCompany> byId1 = sysOrgCompanyRepository.findById(Long.valueOf(shop));
                    if (byId1.isPresent()) {
                        miniAppRoleListVO.setName(byId1.get().getName());
                        miniAppRoleListVO.setStatus(byId1.get().getStatus());
                        miniAppRoleListVO.setId(Long.valueOf(shop));
                    }
                    relationList.add(miniAppRoleListVO);
                }
            }

            if (role.isPresent()) {
                userRoleRelationVO.setSystemId(role.get().getSystemId());
                userRoleRelationVO.setRoleId(role.get().getId());
                userRoleRelationVO.setCompanyId(companyId);
                if (byUserId != null) {
                    userRoleRelationVO.setAccountType(byUserId.get(0).getAccountType());
                }
                userRoleRelationVO.setRelationList(relationList);
                return userRoleRelationVO;
            }

        }
        return null;
    }

    private List<UserSystemVO> getUserSystemVOS(SysUserRedisVO loginUser, Set<Long> systemIds) {
        List<SystemInfo> systemInfos = systemInfoRepository.findAllById(systemIds);
        Optional<SysOrg> byId = sysOrgRepository.findById(loginUser.getUamOrgId());
        List<UserSystemVO> res = Lists.newArrayList();
        List<Long> systemParam = Lists.newArrayList(systemIds);
        List<SystemMachineRelation> relations = systemRelationRepositoryRepository.findByMachineIdAndSystemIdIn(byId.get().getMachineId(), systemParam);
        for (SystemInfo system : systemInfos) {
            UserSystemVO systemVO = new UserSystemVO();
            List<Long> otherSystemIds = relations.stream().filter(item -> item.getSystemId().equals(system.getId())).map(SystemMachineRelation::getOtherSystemId).collect(Collectors.toList());
            BeanUtil.copyProperties(system, systemVO);
            List<OtherSystemInfo> allSystems = otherSystemInfoRepository.findAllById(otherSystemIds);
            if (CollectionUtil.isNotEmpty(otherSystemIds)) {
                Optional<String> first = allSystems.stream().filter(item -> item.getUrlType() == OtherSystemUrlTypeEnum.SYSTEM_URL.getCode()).map(OtherSystemInfo::getUrlDetail).findFirst();
                if (first.isPresent()) {
                    systemVO.setSystemUrl(first.get());
                }
            }
            systemVO.setSystemId(system.getId());
            systemVO.setSystemName(system.getName());
            //  systemVO.setSystemUrl();
            res.add(systemVO);
        }
        return res;
    }

    @Override
    public List<User> findAll(User user) {
        // todo 做你想做的事
        return userRepository.findAll((r, q, c) -> {
            return (new QueryConditionUtil()).where(user, r, c);
        });
    }

}
