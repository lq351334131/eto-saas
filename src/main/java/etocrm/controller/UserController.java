package etocrm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.model.UserVO;
import org.etocrm.model.user.*;
import org.etocrm.service.UserService;
import org.etocrm.utils.ParamId;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @since 2021-01-22
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "SAAS人员信息", tags = "SAAS人员信息")
public class UserController {

    @Resource
    private UserService userService;


    @ApiOperation(value = "用户分页列表", notes = "用户分页列表")
    @GetMapping("/list")
    public ResponseVO<BasePage<UserSelectListVO>> list(UserSelectVO userVO) {
        BasePage<UserSelectListVO> list = userService.list(userVO.getOffset(), userVO.getLimit(), userVO);
        return ResponseVO.success(list);
    }

    @ApiOperation(value = "根据机构id获取所有用户", notes = "根据机构id获取所有用户")
    @GetMapping("/user/orgId")
    public ResponseVO<List<UserVO>> getUserInfoByOrgId(@RequestParam Long orgId) {
        List<UserVO> list = userService.getUserInfoByOrgId(orgId);
        return ResponseVO.success(list);
    }


    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PostMapping("/save")
    public ResponseVO save(@Valid @RequestBody UserSaveVO user) {
        Long res = userService.saveUser(user);
        return res > 0 ? ResponseVO.success(ResponseEnum.SUCCESS.getCode(), "保存成功") : ResponseVO.error(ResponseEnum.FAILD.getCode(), "保存失败");
    }

    @ApiOperation(value = "修改人员信息", notes = "修改人员信息")
    @PostMapping("/update")
    public ResponseVO update(@Valid @RequestBody UserUpdateVO user) throws MyException {
        Long res = userService.updateByPk(user);
        return res > 0 ? ResponseVO.success(ResponseEnum.SUCCESS.getCode(), "修改成功") : ResponseVO.error(ResponseEnum.FAILD.getCode(), "修改失败");
    }


    @ApiOperation(value = "删除用户和角色的关系", notes = "删除用户和角色的关系")
    @PostMapping("/delete")
    public ResponseVO deleteUserRole(@Valid @RequestBody UserRoleRelationVO userRoleVO) {


        Integer res = userService.deleteUserRole(userRoleVO);
        return res > 0 ? ResponseVO.success(ResponseEnum.SUCCESS.getCode(), "删除成功") : ResponseVO.error(ResponseEnum.FAILD.getCode(), "删除失败");
    }

    @ApiOperation(value = "人员信息详情", notes = "人员信息详情")
    @PostMapping(value = "/detail")
    public ResponseVO<UserDetail> detail(@Valid @RequestBody ParamId paramId) {
        UserDetail user = userService.detailByPk(paramId.getId());
        return ResponseVO.success(user);
    }



    @ApiOperation(value = "根据用户id获取用户集合", notes = "根据用户id获取用户集合")
    @GetMapping(value = "/getUserInfoByIds")
    public ResponseVO<List<UserVO>> getUserInfoByIds(@RequestParam Set<Long> userIds) {
        List<UserVO> users = userService.getUserInfoByIds(userIds);
        return ResponseVO.success(users);
    }
    @ApiOperation(value = "关联角色详情", notes = "关联角色详情")
    @GetMapping(value = "/role/detail")
    public ResponseVO<UserRoleVO> getUserRole(Long userId,Long roleId) {
        UserRoleVO roles = userService.getUserRole(userId,roleId);
        return ResponseVO.success(roles);
    }

    @ApiOperation(value = "关联角色编辑", notes = "关联角色编辑")
    @PostMapping(value = "/role/update")
    public ResponseVO<Long> updateUserRole(@RequestBody UserRoleVO vo)throws MyException {
        Long res =userService.updateUserRole(vo);
        return ResponseVO.success(res);
    }


    @ApiOperation(value = "修改用户状态  状态1代表启用，0代表禁用", notes = "修改用户状态  状态1代表启用，0代表禁用")
    @PostMapping(value = "/updateStatus")
    public ResponseVO<Long> updateStatus(@Valid @RequestBody UserUpdateStatusVO vo) throws MyException {
        Long updateStatus = userService.updateStatus(vo.getId(), vo.getStatus());
        return ResponseVO.success(updateStatus);
    }

    @ApiOperation(value = "用户关联角色", notes = "用户关联角色")
    @PostMapping(value = "/userRelationRole")
    public ResponseVO<Long> userRelationRole(@Valid @RequestBody UserRoleVO vo) throws MyException {
        Long updateStatus = userService.userRelationRole(vo);
        return ResponseVO.success(updateStatus);
    }

    @ApiOperation(value = "用户管理系统类型", notes = "用户管理系统类型")
    @PostMapping(value = "/manage/system")
    public ResponseVO<List<UserSystemVO>> manageSystem()  {
        List<UserSystemVO> res = userService.manageSystem();
        return ResponseVO.success(res);
    }

    @ApiOperation(value = "角色管理系统类型", notes = "角色管理系统类型")
    @PostMapping(value = "/role/manage/system")
    public ResponseVO<List<UserSystemVO>> roleManageSystem()  {
        List<UserSystemVO> res = userService.roleManageSystem();
        return ResponseVO.success(res);
    }

    @ApiOperation("解除绑定")
    @PostMapping("/dismiss/wehchatInfo")
    public ResponseVO<String> dismissionWechatInfo(@Valid @RequestBody ParamId paramId) {

        Integer res = userService.dismissionWechatInfo(paramId.getId());
        return res > 0 ? ResponseVO.success(ResponseEnum.SUCCESS.getCode(),"解除成功") :ResponseVO.error(ResponseEnum.FAILD.getCode(),"解除失败");
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/update/userInfo")
    public ResponseVO<String> updateUserInfo(@Valid @RequestBody UserVO userVO)throws MyException {

        Long res = userService.updateUserInfo(userVO);
        return res > 0 ? ResponseVO.success(ResponseEnum.SUCCESS.getCode(),"修改成功") :ResponseVO.error(ResponseEnum.FAILD.getCode(),"修改失败");
    }


    @ApiOperation("获取个人信息详情")
    @GetMapping("/get/userInfo")
    public ResponseVO<UserDetail> getCurrentUserDetail(){

        UserDetail userDetail = userService.getCurrentUserDetail();
        return  ResponseVO.success(userDetail);
    }

    @ApiOperation("获取当前用户的信息")
    @GetMapping("/current/userInfo")
    public ResponseVO<CurrentUserInfo>  getCurrentLoginUserInfo(){
        CurrentUserInfo res = userService.getCurrentLoginUserInfo();
        return ResponseVO.success(res);
    }


    @ApiOperation("模糊查询用户信息")
    @PostMapping("/userInfoLike")
    public ResponseVO<List<UserLikeVO>> userInfoLike(@RequestBody UserLikeVO userLikeVO){

        List<UserLikeVO> userDetail = userService.userInfoLike(userLikeVO);
        return  ResponseVO.success(userDetail);
    }


    @ApiOperation("发送邮箱验证码")
    @PostMapping("/send/email/code")
    public ResponseVO<String>  sendVerifyCode(@RequestBody EmailParam emailParam)throws MyException{

        userService.sendVerifyCode(emailParam);
        return  ResponseVO.success();
    }

    @ApiOperation("验证邮箱")
    @PostMapping("/verify/email")
    public ResponseVO  verifyEmail(@RequestBody EmailParam emailParam)throws MyException{

        Long res = userService.verifyEmail(emailParam);
        return  res>0?ResponseVO.success("验证成功"):ResponseVO.errorParams("验证失败");
    }

    @ApiOperation("重置密码")
    @PostMapping("/reset/password")
    public ResponseVO  resetPasswordByEmail(@RequestBody EmailPasswordParam emailPasswordParam)throws MyException{

        Long res = userService.resetPasswordByEmail(emailPasswordParam);
        return  res>0?ResponseVO.success("重置成功"):ResponseVO.errorParams("重置失败");
    }

    @ApiOperation("修改密码")
    @PostMapping("/update/password")
    public ResponseVO  updatePassword(@RequestBody UpdatePasswordParam updatePasswordParam)throws MyException{

        Long res = userService.updatePassword(updatePasswordParam);
        return  res>0?ResponseVO.success("修改成功"):ResponseVO.errorParams("修改失败");
    }

}