package etocrm.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class WechatConfig {


    @Value("${wxopen.appid}")
    private String openAppid;

    @Value("${wxopen.appsecret}")
    private String openAppSecret;

    @Value("${wxopen.redirectUrl}")
    private String webUrl;

    @Value("${wxopen.gatewayUrl}")
    private String gatewayUrl;
    @Value("${wxopen.wechatUrl}")
    private String wechatUrl;
    @Value("${wxopen.accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${wxopen.ackeyUrl}")
    private String ackeyUrl;

    @Value("${wxopen.userUrl}")
    private String userUrl;

//    /*微信开放平台二维码连接*/
//    public final static String OPEN_QRCODE_URL="https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
//
//    /*微信开放平台获取access_token地址*/
//    public final static String OPEN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
//
//    /*获取用户信息*/
//    public final static String OPEN_USER_INFO_URL="https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
//
//    /**
//     *
//     */
//    public final static String REDIRECT_URL=
//     "https://open.weixin.qq.com/connect/oauth2/authorize?" +
//             "appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=fck3v6IrsK&connect_redirect=1#wechat_redirect";

}
