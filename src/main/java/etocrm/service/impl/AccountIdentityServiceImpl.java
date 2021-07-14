package etocrm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.convert.IdentityDetailConvert;
import org.etocrm.convert.PermissionConvert;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.entity.*;
import org.etocrm.model.AccountIdentityVo;
import org.etocrm.model.AccountRoleDetail;
import org.etocrm.model.EtoShopAccountVo;
import org.etocrm.model.OnLineShopVo;
import org.etocrm.model.permission.PermissionFindVO;
import org.etocrm.model.permission.PermissionResponseVO;
import org.etocrm.repository.*;
import org.etocrm.service.AccountIdentityService;
import org.etocrm.service.RolePermissionService;
import org.etocrm.service.systemservice.SystemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:  账号身份信息实现类
 * @author xingxing.xie
 * @date 2021/6/8 16:46
 * @version 1.0
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AccountIdentityServiceImpl implements AccountIdentityService {

    @Resource
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private BrandsWechatInfoRepository brandsWechatInfoRepository;
    @Autowired
    private SystemInfoRepository systemInfoRepository;

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionConvert convert;
    @Autowired
    private IdentityDetailConvert identityDetailConvert;
    @Autowired
    private SysOrgCompanyRepository orgCompanyRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Resource
    private RolePermissionService rolePermissionService;
    @Autowired
    private Map<String, SystemTypeService> map;





    @Override
    public AccountIdentityVo getAccountInfo(Long userId,String systemCode) throws MyException {
        /**
         * 根据 userId 查询 user
         * 根据 systemCode 查询 system_info 系统 name   id
         *
         * 根据 systemId   查询role  id  name   status=1  启用的数据
         *  根据userId  roleId  查询 user_role  获得  对应系统
         */
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()){
            throw new MyException(ResponseEnum.INCORRECT_PARAMS.getCode(), "请核对账号id");
        }
        //根据 userId 查询 user
        AccountIdentityVo result = new AccountIdentityVo()
                .setId(userId).setOrgId(userOptional.get().getOrgId());
        //根据 systemCode 查询 system_info 系统 name   id
        SystemInfo systemInfo = systemInfoRepository.findByCode(systemCode);
        if (null==systemInfo) {
            throw new MyException(ResponseEnum.INCORRECT_PARAMS.getCode(),systemCode+"无法匹配，请核对系统编号");
        }
        result.setSystemId(systemInfo.getId()).setSystemName(systemInfo.getName());
        //根据  systemId  查询 role  表  status=1 备用
        List<Role> roleList = roleRepository.findBySystemIdAndStatus(systemInfo.getId(), 1);
        if (CollectionUtil.isEmpty(roleList)) {
            throw new MyException(ResponseEnum.INCORRECT_PARAMS.getCode(),systemCode+"系统暂无任何身份类型信息");
        }
        List<Long> roleIdList = roleList.stream().map(Role::getId).collect(Collectors.toList());
        Map<Long, String> roleNameMap = roleList.stream().collect(Collectors.toMap(Role::getId, Role::getName));
        //根据  userId  查询userRole
        List<UserRole> byUserId = userRoleRepository.findByUserId(userId);
        //过滤掉 对应的userRole 数据
        List<UserRole> filterUserRoleList = byUserId.stream().filter(t -> roleIdList.contains(t.getRoleId())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(filterUserRoleList)) {
            throw new MyException(ResponseEnum.INCORRECT_PARAMS.getCode(),"该账号无任何有效身份信息");
        }
        //封装  角色下相关店铺、公司、小程序信息部分
        SystemTypeService systemTypeService = map.get(systemCode);
        systemTypeService.getResultBySystem(result,filterUserRoleList,roleNameMap);
        return result;
    }

    @Override
    public List<PermissionResponseVO> getMenuByRoleId(Long roleId) {
        List<Long> all = rolePermissionService.findAll(new RolePermission().setRoleId(roleId))
                .stream()
                .map(RolePermission::getPermissionId).collect(Collectors.toList());
        List<Permission> permissionList = permissionRepository.findAll((r, q, c) -> {
            return (new QueryConditionUtil()).where(new PermissionFindVO(), r, c);
        });
        if(CollectionUtil.isEmpty(permissionList)){
            return Lists.newArrayList();

        }
        List<Permission> permissions = permissionList.stream().filter(t -> all.contains(t.getId())).collect(Collectors.toList());
        return convert.doListToVoList(permissions);
    }

    @Override
    public EtoShopAccountVo getEtoShopsByOrgId(Long orgId) throws MyException {
        if(null==orgId){
            throw new MyException(ResponseEnum.INCORRECT_PARAMS.getCode(), "请输入机构ID");
        }
        //根据 机构id  查询  user 表 （status=1）   获得userList 电商传过来的 机构 默认就是 电商
        List<User> userList = userRepository.findByOrgIdAndStatus(orgId,1);
        if(CollectionUtil.isEmpty(userList)){
            log.warn("该机构下无任何账号信息，orgId：{}",orgId);
            return null;
        }
        EtoShopAccountVo result = new EtoShopAccountVo().setOrgId(orgId);
        List<AccountRoleDetail> accountRoleDetails = new ArrayList<>();
        List<Long> userIds = userList.stream().map(User::getId).collect(Collectors.toList());
        //用于 获取账号名称
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        //根据 userId  查询user_role   判断 account_type   为1   正常返回，否则查询其下管理店铺信息
        List<UserRole> userRoles = userRoleRepository.findByUserIdIn(userIds);
        for (UserRole userRole : userRoles) {
            User user = userMap.get(userRole.getUserId());
            AccountRoleDetail accountRoleDetail = new AccountRoleDetail()
                    .setUserId(userRole.getUserId())
                    .setUserName(null==user?null:user.getName())
                    .setLoginName(user.getUid())
                    .setIsAdmin(user.getIsAdmin())
                    .setRoleId(userRole.getRoleId());
            //判断账号类型
            Integer accountType = userRole.getAccountType();
            accountRoleDetail.setAccountType(accountType);
            //判断 是否 管理员
            boolean isManager= null==accountType?false:accountType.equals(1)?true:false;
            if(!isManager){
                //若不是管理员，则查询对应店铺信息
                //查询店铺列表
                String shopIdStr = userRole.getShopId();
                if(StrUtil.isNotEmpty(shopIdStr)){
                    String[] split = shopIdStr.split(",");
                    List<Long> shopIds = Arrays.asList(split).stream().map(t -> Long.valueOf(t)).collect(Collectors.toList());
                    //根据店铺id  查询  sys_org_company    且open_shop=1
                    List<SysOrgCompany> SysOrgCompany = orgCompanyRepository.findByOpenShopAndIdIn(1,shopIds);
                    if(CollectionUtil.isNotEmpty(SysOrgCompany)){
                        List<OnLineShopVo> onLineShopVos = identityDetailConvert.toShopVoList(SysOrgCompany);
                        accountRoleDetail.setShopList(onLineShopVos);
                        //加入
                        accountRoleDetails.add(accountRoleDetail);
                    }
                }

            }else {
                //管理员
                accountRoleDetails.add(accountRoleDetail);
            }

        }
        accountRoleDetails.sort((t1,t2)-> t2.getIsAdmin() - t1.getIsAdmin());
        result.setAccountRoleDetails(accountRoleDetails);
        return result;

    }

}
