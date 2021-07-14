package etocrm.controller;

import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.model.qrcode.QrcodeVO;
import org.etocrm.model.user.LoginUser;
import org.etocrm.model.user.LoginUserVO;
import org.etocrm.model.wechatInfo.*;
import org.etocrm.service.WechatLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/wechat")
@Api(value = "sass微信登录", tags = "sass微信登录")
public class WechatLoginController {

    @Autowired
    private WechatLoginService wechatLoginService;

    @GetMapping(value = "/create/qrcode")
    @ResponseBody
    @ApiOperation("生成带参数的二维码")
    public ResponseVO getCode(Long  userId, String token, HttpServletRequest request) {

        QrcodeVO res = wechatLoginService.getCode(userId, token, request);
        return ResponseVO.success(res);
    }

    @GetMapping(value = "/scan/qrcode")
    @ApiOperation("扫码接口")
    public String scanCode(@RequestParam(required = false) Long userId,String ticket,HttpServletRequest request) throws UnsupportedEncodingException {
     String url=   wechatLoginService.scanCode(userId,ticket,request);
        return "redirect:"+url;
    }

    @GetMapping(value = "/redirect")
    public String redirect(String code,Long userId,String ticket){
        String redirectUrl = wechatLoginService.getRedirect(code,userId,ticket);
        return "redirect:"+redirectUrl;
    }


    @GetMapping("/get/wechatInfo")
    @ResponseBody
    @ApiOperation("根据userId查询用户昵称")
    public ResponseVO<WechatInfoVO> getWechatInfo(String ticket,String code, Long userId){

        Preconditions.checkNotNull(code,"code 不能为空");
        WechatInfoVO userWechatInfo = wechatLoginService.getWechatInfo(ticket,code,userId);
        return ResponseVO.success(userWechatInfo);
    }

    @PostMapping("/bind/wechatInfo")
    @ResponseBody
    @ApiOperation("绑定微信用户")
    public ResponseVO bindWechat(@RequestBody @Valid WechatBindUserInfoVO wechatBindUserInfoVO){

        LoginUserVO res = wechatLoginService.bindWechat(wechatBindUserInfoVO);
        return ResponseVO.success(res);
    }


    @PostMapping("/determine/login")
    @ApiOperation("确定登录")
    @ResponseBody
    public ResponseVO determineLogin(@RequestBody @Valid WechatDetermineLoginVO vo) {

        LoginUserVO res = wechatLoginService.determineLogin(vo.getTicket(),vo.getUserId(),vo.getCode());

        return ResponseVO.success(res);
    }

    @ApiOperation("微信账号密码登录")
    @PostMapping("/login")
    @ResponseBody
    public ResponseVO<WechatInfoLoginVO> login(@RequestBody LoginUser user) {

        WechatInfoLoginVO sysUser =  wechatLoginService.detailByUserName(user);
        return  ResponseVO.success(sysUser);
    }

    @ApiOperation("POS系统的微信账号密码登录")
    @PostMapping("/pos/login")
    @ResponseBody
    public ResponseVO<PosWechatInfoLoginVO> posLogin(@RequestBody LoginUser user) {

        PosWechatInfoLoginVO sysUser =  wechatLoginService.posLogin(user);
        return  ResponseVO.success(sysUser);
    }

    @ApiOperation("获取用户换取token的code")
    @PostMapping("/get/login/code")
    @ResponseBody
    public ResponseVO<String> getLoginCode() {
       String s =  wechatLoginService.getLoginCode();
        return  ResponseVO.success(s);
    }
}
