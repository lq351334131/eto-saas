package etocrm.service;

import org.etocrm.model.qrcode.QrcodeVO;
import org.etocrm.model.user.LoginUser;
import org.etocrm.model.user.LoginUserVO;
import org.etocrm.model.wechatInfo.PosWechatInfoLoginVO;
import org.etocrm.model.wechatInfo.WechatBindUserInfoVO;
import org.etocrm.model.wechatInfo.WechatInfoLoginVO;
import org.etocrm.model.wechatInfo.WechatInfoVO;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface WechatLoginService {
    WechatInfoVO getWechatInfo(String ticket, String code, Long userId) ;

    LoginUserVO bindWechat(WechatBindUserInfoVO wechatBindUserInfoVO);

    LoginUserVO determineLogin(String ticket, Long userId, String code);

    QrcodeVO getCode(Long userId, String token, HttpServletRequest request);

    WechatInfoLoginVO detailByUserName(LoginUser user);

    String scanCode(Long userId, String ticket, HttpServletRequest request)throws UnsupportedEncodingException;

    String getRedirect(String code, Long userId, String ticket);

    String getLoginCode();

    PosWechatInfoLoginVO posLogin(LoginUser user);
}
