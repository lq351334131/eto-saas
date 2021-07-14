package etocrm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.model.permission.NodeVO;
import org.etocrm.model.permission.PermissionVO;
import org.etocrm.model.permission.ThirdSystemPermissionVO;
import org.etocrm.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author admin
 * @since 2021-01-22
 */
@Slf4j
@RestController
@RequestMapping("/permission")
@Api(value = "SAAS权限资源", tags = "SAAS权限资源")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @ApiOperation(value = "根据系统id获取套餐菜单树列表", notes = "根据系统id获取套餐菜单树列表")
    @GetMapping(value = "/nodes")
    public ResponseVO<List<PermissionVO>> getNodes(NodeVO vo) {
        List<PermissionVO> res = permissionService.getNodes(vo);
        return ResponseVO.success(res);
    }

    @ApiOperation("获取当前用户的权限菜单")
    @GetMapping("/get/menus")
    public ResponseVO<ThirdSystemPermissionVO> getCurrentLoginUserPermissions() {
        ThirdSystemPermissionVO permission = permissionService.getCurrentLoginUserPermissions();
        return ResponseVO.success(permission);
    }


    @ApiOperation("获取其他系统登录的人的权限")
    @GetMapping("/login/menus")
    public ResponseVO<List<PermissionVO>> getCurrentLoginUserPermissionList(@RequestParam String systemCode, Integer isButton) {
        List<PermissionVO> permission = permissionService.getCurrentLoginUserPermissionList(systemCode, isButton);
        return ResponseVO.success(permission);
    }

    @ApiOperation("获取机构的权限")
    @GetMapping("/org/menus")
    public ResponseVO<List<PermissionVO>> getCurrentLoginUserPermissionList(Long orgId) {
        List<PermissionVO> permission = permissionService.getPermissionListByOrgId(orgId);
        return ResponseVO.success(permission);
    }

    @ApiOperation("获取其他系统登录的人的所有")
    @GetMapping("/all/menus")
    public ResponseVO<List<PermissionVO>> getCurrentLoginUserAllPermissionList(@RequestParam String systemCode) {
        List<PermissionVO> permission = permissionService.getCurrentLoginUserAllPermissionList(systemCode);
        return ResponseVO.success(permission);
    }

    @ApiOperation("获取saas系统登录的人的权限")
    @GetMapping("/saas/login/menus")
    public ResponseVO<List<PermissionVO>> getSaasLoginUserPermissionList() {
        List<PermissionVO> permission = permissionService.getSaasLoginUserPermissionList();
        return ResponseVO.success(permission);
    }


}