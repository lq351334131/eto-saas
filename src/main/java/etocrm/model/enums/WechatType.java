package etocrm.model.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum WechatType {

    SERVICE("service", "公众号"),
    SUBSCRIBE("subscribe", "订阅号"),
    MINIAPP("miniapp", "小程序");

    private String code;
    private String value;


    WechatType(String code, String value) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

     void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }
    void setCode(String code) {
        this.code = code;
    }

    public static String get(String code) {
        WechatType e = customBizMap().get(code);
        if (null != e) {
            return e.getValue();
        }
        return "";
    }

    public static Map<String, WechatType> customBizMap() {
       return  Stream.of(values())
                .collect(Collectors.toMap(WechatType::getCode, customBizTypeEnum -> customBizTypeEnum));
    }
}
