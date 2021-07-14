package etocrm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.model.AccountIdentityVo;
import org.etocrm.model.EtoShopAccountVo;
import org.etocrm.model.permission.PermissionResponseVO;
import org.etocrm.service.AccountIdentityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 账号身份相关
 * @author xingxing.xie
 * @date 2021/6/8 16:33
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/accountIdentity")
@Api(value = "账号身份相关信息", tags = "账号身份相关信息")
public class AccountIdentityController {

    @Resource
    private AccountIdentityService accountIdentityService;

    @ApiOperation(value = "根据角色id获取菜单信息", notes = "根据角色id获取菜单信息")
    @GetMapping(value = "/menus")
    public ResponseVO<List<PermissionResponseVO>> getMenuByRoleId(Long roleId) {
        List<PermissionResponseVO> menuByRoleId = accountIdentityService.getMenuByRoleId(roleId);
        return ResponseVO.success(menuByRoleId);
    }

    @ApiOperation(value = "根据机构查询账号身份信息", notes = "根据机构查询账号身份信息")
    @GetMapping(value = "/getEtoShopsByOrgId")
    public ResponseVO<EtoShopAccountVo> getEtoShopsByOrgId(Long orgId) throws MyException {
        EtoShopAccountVo etoShopAccountVo = accountIdentityService.getEtoShopsByOrgId(orgId);
        return ResponseVO.success(etoShopAccountVo);
    }
    @ApiOperation(value = "查询账号详情信息", notes = "查询账号详情信息")
    @GetMapping(value = "/detail")
    public ResponseVO<AccountIdentityVo> getAccountInfo(Long accountId,String systemCode) throws MyException {
        AccountIdentityVo accountInfo = accountIdentityService.getAccountInfo(accountId,systemCode);
        return ResponseVO.success(accountInfo);
    }




}