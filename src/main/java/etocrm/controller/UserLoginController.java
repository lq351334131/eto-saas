package etocrm.controller;

import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.database.util.SysUserRedisVO;
import org.etocrm.model.user.LoginCurrentVO;
import org.etocrm.model.user.LoginUser;
import org.etocrm.model.user.LoginUserVO;
import org.etocrm.model.user.UserSystemVO;
import org.etocrm.service.UserService;
import org.etocrm.utils.SecurityUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(value = "sass平台用户登录", tags = "sass平台用户登录")
@Slf4j
public class UserLoginController {

    @Resource
    private UserService userService;

    @Resource
    private SecurityUtil securityUtil;

    @ApiOperation("sass平台用户登录")
    @PostMapping("/login")
    public ResponseVO<LoginUserVO> login(@RequestBody LoginUser user) {

        LoginUserVO sysUser = userService.detailByUserName(user);
        return ResponseVO.success(sysUser);
    }

    @ApiOperation("获取登录用户的个人信息")
    @PostMapping("/get/currentUser")
    public ResponseVO<LoginCurrentVO> getCurrentUserInfo() {

        SysUserRedisVO currentLoginUser = securityUtil.getCurrentLoginUser();
        LoginCurrentVO currentVO = userService.getCurrentUserInfo(currentLoginUser.getUserId());
        return ResponseVO.success(currentVO);
    }

    @ApiOperation("获取登录开通的系统")
    @PostMapping("/get/currentUser/system")
    public ResponseVO<List<UserSystemVO>> getCurrentLoginSystem() {

        List<UserSystemVO> vo = userService.getCurrentLoginSystem();
        return ResponseVO.success(vo);
    }

    @ApiOperation("根据code获取其他系统的token")
    @GetMapping("/get/system/token")
    public ResponseVO getCurrentLoginSystemAccessToken(String code) {
        Preconditions.checkNotNull(code, "code 不能为空");
        LoginUserVO res = userService.getCurrentLoginSystemAccessToken(code);
        return res != null ? ResponseVO.success(res) : ResponseVO.errorParams("code失效");
    }

    @ApiOperation("系统注销")
    @GetMapping("/logout")
    public ResponseVO login(HttpServletRequest request1) {
        userService.logout(request1);
        return ResponseVO.success("注销成功");
    }

}