package etocrm.enums;

/**
 * @author xingxing.xie
 */

public enum WeChatAppTypeEnum {


    MINI_APP("miniapp","小程序"),
    SERVICE("service","公众号"),
    SUBSCRIBE("subscribe","订阅号");

    private String code;
    private String message;

    WeChatAppTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
