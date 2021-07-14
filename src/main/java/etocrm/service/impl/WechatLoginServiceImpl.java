package etocrm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.config.WechatConfig;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.util.RedisUtil;
import org.etocrm.database.util.SysUserRedisVO;
import org.etocrm.entity.SysOrg;
import org.etocrm.entity.SysOrgBrands;
import org.etocrm.entity.User;
import org.etocrm.entity.UserWechatInfo;
import org.etocrm.enums.BusinessEnum;
import org.etocrm.exception.UamException;
import org.etocrm.feign.AuthFeignClient;
import org.etocrm.model.qrcode.QrcodeVO;
import org.etocrm.model.user.LoginUser;
import org.etocrm.model.user.LoginUserVO;
import org.etocrm.model.user.TokenValue;
import org.etocrm.model.wechatInfo.*;
import org.etocrm.repository.*;
import org.etocrm.server.ScanQRCodeServer;
import org.etocrm.service.WechatLoginService;
import org.etocrm.utils.HttpUtils;
import org.etocrm.utils.Md5Utils;
import org.etocrm.utils.QRCodeUtil;
import org.etocrm.utils.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WechatLoginServiceImpl implements WechatLoginService {

    @Autowired
    private UserWechatInfoRepository userWechatInfoRepository;
    @Autowired
    private WechatConfig wechatConfig;
    @Autowired
    private ScanQRCodeServer scanQRCodeServer;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthFeignClient authFeignClient;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysOrgRepository sysOrgRepository;
    @Autowired
    private SysOrgBrandsRepository sysOrgBrandsRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public WechatInfoVO getWechatInfo(String ticket, String code, Long userId) {
        String ackey ;
        String resAck = redisUtil.get("ackey", String.class);
        if(!StringUtils.isEmpty(resAck)) {
            ackey= resAck;
        }else {
            String ackeyUrl = String.format(wechatConfig.getAckeyUrl(),
                    wechatConfig.getOpenAppid(), wechatConfig.getOpenAppSecret());
            Map<String, Object> ackResult = HttpUtils.doGet(ackeyUrl);
            ackey = (String) ackResult.get("ackey");
            redisUtil.set("ackey",ackey,3600);
        }
        //调用微信接口获取openId和accessToken
        String accessTokenUrl = String.format(wechatConfig.getAccessTokenUrl(), ackey, code);
        Map<String, Object> result =
                HttpUtils.doGet(accessTokenUrl);
        log.error("accessTokenUrl========" + result );
        String access_token = (String) result.get("access_token");
        String openId = (String) result.get("openid");
        log.error("当前openId=====================:" + openId);
        if (openId == null) {
            return null;
        }
        redisUtil.set(code, code, 60);
        log.error("存放code=====================:" + code);
        //userId为空的静默授权
        if (userId == null) {
            List<UserWechatInfo> userInfo = userWechatInfoRepository.findByOpenid(openId);
            if (userInfo.size() <= 0) {
                WechatInfoVO wechatInfoVO = new WechatInfoVO();
                wechatInfoVO.setType(4);
                return wechatInfoVO;
            }
            //给出选择登录账号列表
            List<WechatOrgUserVO> res = Lists.newArrayList();
            List<Long> userIds = userInfo.stream().map(UserWechatInfo::getUserId).collect(Collectors.toList());
            //用openId查询多个机构账号
            List<User> users = userRepository.findAllById(userIds);
            WechatInfoVO wechatInfoVO = new WechatInfoVO();
            for (User u : users) {
                WechatOrgUserVO wechatOrgUserVO = new WechatOrgUserVO();
                BeanUtils.copyProperties(u, wechatOrgUserVO);
                Optional<SysOrg> org = sysOrgRepository.findById(u.getOrgId());
                if (org.isPresent()) {
                    wechatOrgUserVO.setOrgLogoUrl(org.get().getOrgLogoUrl());
                    wechatOrgUserVO.setOrgName(org.get().getName());
                    wechatOrgUserVO.setUserId(u.getId());
                }
                res.add(wechatOrgUserVO);
            }

            WechatInfoTypDetailVO wechatInfoTypDetailVO = new WechatInfoTypDetailVO();
            wechatInfoTypDetailVO.setWechatOrgUserVOList(res);
            if (res.size() <= 0) {
                wechatInfoVO.setType(4);
            } else {
                wechatInfoVO.setType(1);
            }
            wechatInfoVO.setWechatInfoTypDetailVO(wechatInfoTypDetailVO);
            log.error("type---==="+wechatInfoVO);
            return wechatInfoVO;
        }

        //获取用户基本信息
        String userInfoUrl = String.format(wechatConfig.getUserUrl(),access_token, openId);
        Map<String, Object> map = HttpUtils.doGet(userInfoUrl);
        String nickname = (String) map.get("nickname");
        //不为空的userId静默授权
        //通过userId查询已经绑定的用户
        List<UserWechatInfo> byUserId = userWechatInfoRepository.findByUserId(userId);

        if (CollectionUtils.isEmpty(byUserId)) {

            //未绑定返回全部值
            //不为空，未绑定任何账号，需要插入基本信息
            Double sexTemp = (Double) map.get("sex");
            int sex = 0;
            if (sexTemp != null) {
                sex = sexTemp.intValue();
            }
            log.error("map======="+map);
            String province = (String) map.get("province");
            String language = (String) map.get("language");
            String city = (String) map.get("city");
            String country = (String) map.get("country");
            String unionid = (String) result.get("unionid");
            String headimgurl = (String) map.get("headimgurl");
            WechatInfoVO vo = new WechatInfoVO();
            WechatInfoTypDetailVO res = new WechatInfoTypDetailVO();
            Optional<User> byId = userRepository.findById(userId);
            if (byId.isPresent()) {
                res.setUid(byId.get().getUid());
            }
            res.setUnionid(unionid);
            res.setCity(city);
            res.setCountry(country);
            res.setUserId(userId);
            res.setNickName(nickname);
            res.setHeadimgUrl(headimgurl);
            res.setProvince(province);
            res.setCity(city);
            res.setCountry(country);
            res.setSex(sex);
            res.setOpenid(openId);

            res.setLanguage(language);
            vo.setWechatInfoTypDetailVO(res);
            vo.setType(2);
            return vo;
        } else {
            //当前绑定用户是否是这个openId
            if (!byUserId.get(0).getOpenid().equals(openId)) {

                WechatInfoVO wechatInfoVO = new WechatInfoVO();
                wechatInfoVO.setType(5);
                log.error("type==5"+wechatInfoVO);
                return wechatInfoVO;
            }
            WechatInfoVO res = new WechatInfoVO();
            res.setType(3);
            WechatInfoTypDetailVO typDetailVO = new WechatInfoTypDetailVO();
            BeanUtils.copyProperties(byUserId.get(0), typDetailVO);
            Optional<User> byId = userRepository.findById(userId);
            if (byId.isPresent()) {
                typDetailVO.setUid(byId.get().getUid());
            }
            res.setWechatInfoTypDetailVO(typDetailVO);

            return res;
        }
    }

    @Override
    public LoginUserVO bindWechat(WechatBindUserInfoVO wechatBindUserInfoVO) {
        log.error("绑定的code======================" + wechatBindUserInfoVO.getCode());
        String s = redisUtil.get(wechatBindUserInfoVO.getCode(), String.class);
        if (!StringUtils.isEmpty(wechatBindUserInfoVO.getCode()) && !wechatBindUserInfoVO.getCode().equals(s)) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "凭证不对，请重新登录");
        }

        UserWechatInfo byUserIdAndOpenid = userWechatInfoRepository.findByUserIdAndOpenid(wechatBindUserInfoVO.getUserId(), wechatBindUserInfoVO.getOpenid());
        if (byUserIdAndOpenid == null) {
            UserWechatInfo userWechatInfo = new UserWechatInfo();
            BeanUtils.copyProperties(wechatBindUserInfoVO, userWechatInfo);
            userWechatInfoRepository.save(userWechatInfo);
        }
        LoginUserVO userVO = getLoginUserVO(wechatBindUserInfoVO.getUserId());
        //websocke通知前端token
        scanQRCodeServer.responseToekn(wechatBindUserInfoVO.getTicket(), 0, userVO);
        return userVO;
    }

    @Override
    public LoginUserVO determineLogin(String ticket, Long userId, String code) {
        log.error("登录的code======================" + code);
        String s = redisUtil.get(code, String.class);
        if (!StringUtils.isEmpty(code) && !code.equals(s)) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "凭证不对，请重新登录");
        }
        LoginUserVO loginUserVO = getLoginUserVO(userId);
        if (loginUserVO != null) {
            scanQRCodeServer.responseToekn(ticket, 0, loginUserVO);
        }
        return loginUserVO;
    }

    @Override
    public QrcodeVO getCode(Long userId, String token, HttpServletRequest request) {
        if (userId != null) {
            Optional<User> byId = userRepository.findById(userId);
            if (byId.isPresent()) {
                if (!token.equals(Md5Utils.encode(byId.get().getUid() + byId.get().getPassword()))) {
                    throw new UamException(ResponseEnum.FAILD.getCode(), "token有误，请先登录");
                }
            }
        }
        // String ticket = RandomGenerator.generate(32, RandomGenerator.LETTER | RandomGenerator.NUMBER);
        String ticket = UUID.randomUUID().toString();
        //String domain = request.getScheme()+"://"+wechatConfig.getGatewayUrl() + "/saas/";
        String domain = "https://"+wechatConfig.getGatewayUrl() + "/saas/";
        String redirectUrl = (domain + "wechat/scan/qrcode");
        if (userId != null) {
            String a = "?userId=" + userId + "&ticket=" + ticket;
            redirectUrl += a;
        } else {
            redirectUrl += "?ticket=" + ticket;
        }
        String qr = QRCodeUtil.genBase64QR(redirectUrl, 200, 200);
        QrcodeVO qrcodeVO = new QrcodeVO();
        qrcodeVO.setQrcode("data:image\\/png;base64," + qr);
        qrcodeVO.setTicket(ticket);
        redisUtil.set("ticket:" + ticket, 0, 120);
        return qrcodeVO;
    }

    @Override
    public WechatInfoLoginVO detailByUserName(LoginUser loginUser) {
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

        WechatInfoLoginVO loginVO = new WechatInfoLoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setToken(Md5Utils.encode(loginUser.getUserName() + user.getPassword()));
        return loginVO;
    }

    @Override
    public String scanCode(Long userId, String ticket,HttpServletRequest request) throws UnsupportedEncodingException {
        scanQRCodeServer.responseCodeMessage(ticket, 200, "扫码成功");
      //  String redirectUrl = request.getScheme()+"://"+getWebUrl + "/saas/wechat/redirect";
       String redirectUrl = wechatConfig.getWebUrl();
        redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        if (userId != null) {
            String a = "?userId=" + userId + "&ticket=" + ticket;
            a = URLEncoder.encode(a, "utf-8");
            redirectUrl += a;
        } else {
            String a = "?ticket=" + ticket;
            a = URLEncoder.encode(a, "utf-8");
            redirectUrl += a;
        }

        String url = String.format(wechatConfig.getWechatUrl(), wechatConfig.getOpenAppid(), redirectUrl);
        redisUtil.deleteCache("ticket:" + ticket);
        return url;
    }

    @Override
    public String getRedirect(String code, Long userId, String ticket) {
        String redirectUrl = wechatConfig.getWebUrl();
        if (userId != null) {
            String a = "?userId=" + userId + "&ticket=" + ticket + "&code=" + code;
            redirectUrl += a;
        } else {
            String a = "?ticket=" + ticket + "&code=" + code;
            redirectUrl += a;
        }
        log.error("==================================redirectUrl==============================" + redirectUrl);
        return redirectUrl;

    }

    @Override
    public String getLoginCode() {

        SysUserRedisVO VO = securityUtil.getCurrentLoginUser();
        log.error("VO==" + VO.getUserId());
        String code = UUID.randomUUID().toString();
        log.info("uuid" + code);
        Optional<User> byId = userRepository.findById(VO.getUserId());
        if (byId.isPresent()) {
            redisUtil.set(code, byId.get().getId(), 1800);
        }
        return code;
    }

    @Override
    public PosWechatInfoLoginVO posLogin(LoginUser loginUser) {
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
        PosWechatInfoLoginVO userVO = new PosWechatInfoLoginVO();
        List<UserWechatInfo> byUserId = userWechatInfoRepository.findByUserId(user.getId());
        if(CollectionUtil.isNotEmpty(byUserId)) {
            userVO.setBindStatus(1);
        }else {
            userVO.setBindStatus(0);
        }
        //请求发放token
        TokenValue tokenValue = authFeignClient.login(loginUser);
        BeanUtils.copyProperties(tokenValue, userVO);
        userVO.setId(user.getId());
        userVO.setName(user.getName());
        Optional<SysOrg> byId = sysOrgRepository.findById(user.getOrgId());
        if(byId.isPresent()) {
            userVO.setOrgId(byId.get().getCrmOrgId());
        }
        userVO.setUid(user.getUid());
        SysUserRedisVO redisVO = new SysUserRedisVO();
        redisVO.setUserId(user.getId());
        redisVO.setName(user.getName());
        redisVO.setUid(user.getUid());
        if(byId.isPresent()) {
            SysOrg sysOrg = byId.get();
            List<SysOrgBrands> brands = sysOrgBrandsRepository.findByOrgId(sysOrg.getId());
            if(CollectionUtil.isNotEmpty(brands)) {
                redisVO.setCrmBrandId(brands.get(0).getCrmBrandId());
            }
            redisVO.setOrgId(sysOrg.getCrmOrgId());
        }
        redisVO.setUamOrgId(user.getOrgId());
        redisVO.setUpdatePasswordTime(user.getUpdatePasswordTime());
        if(user.getIsAdmin()==1) {
            redisVO.setAdminFlag(true);
        }
        redisUtil.set(user.getUid() + ":" + Md5Utils.encode(tokenValue.getAccessToken()), redisVO, tokenValue.getExpiresIn());
        userVO.setUserId(user.getId());
        userVO.setToken(Md5Utils.encode(loginUser.getUserName() + user.getPassword()));

        return  userVO;
    }

    private LoginUserVO getLoginUserVO(Long userId) {
        LoginUser loginUser = new LoginUser();
        Optional<User> byId =
                userRepository.findById(userId);
        User user = byId.get();
        loginUser.setPassword(byId.get().getPassword());
        loginUser.setUserName(byId.get().getUid());
        LoginUserVO userVO = new LoginUserVO();
        //请求发放token
        TokenValue tokenValue = authFeignClient.login(loginUser);
        BeanUtils.copyProperties(tokenValue, userVO);
        userVO.setId(user.getId());
        userVO.setName(user.getName());
        Optional<SysOrg> sysOrgOptional= sysOrgRepository.findById(user.getOrgId());
        if(sysOrgOptional.isPresent()) {
            userVO.setOrgId(sysOrgOptional.get().getCrmOrgId());
        }
        userVO.setUid(user.getUid());
        SysUserRedisVO redisVO = new SysUserRedisVO();
        redisVO.setUserId(user.getId());
        redisVO.setName(user.getName());
        redisVO.setUid(user.getUid());
        if(sysOrgOptional.isPresent()) {
            SysOrg sysOrg1 = sysOrgOptional.get();
            List<SysOrgBrands> brands = sysOrgBrandsRepository.findByOrgId(sysOrg1.getId());
            if(CollectionUtil.isNotEmpty(brands)) {
                redisVO.setCrmBrandId(brands.get(0).getCrmBrandId());
            }
            redisVO.setOrgId(sysOrg1.getCrmOrgId());
        }
        redisVO.setUamOrgId(user.getOrgId());
        redisVO.setUpdatePasswordTime(user.getUpdatePasswordTime());
        if (user.getIsAdmin() == 1) {
            redisVO.setAdminFlag(true);
        }
        redisUtil.set(user.getUid() + ":" + Md5Utils.encode(tokenValue.getAccessToken()), redisVO, tokenValue.getExpiresIn());

        redisUtil.set(user.getUid(), userVO, tokenValue.getExpiresIn());
        return userVO;
    }

}
