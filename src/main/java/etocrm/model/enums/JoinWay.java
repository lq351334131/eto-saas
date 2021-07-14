package etocrm.model.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum  JoinWay {

    BASE("base","开发模式接入"),
    OPEN("open"," 第三方接入");

    private String value;
    private String code;



    JoinWay(String code,String value) {
        this.code=code;
        this.value=value;
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
        JoinWay e = customBizMap().get(code);
        if (null != e) {
            return e.getValue();
        }
        return "";
    }

    public static Map<String, JoinWay> customBizMap() {
        return Stream.of(values())
                .collect(Collectors.toMap(JoinWay::getCode, customBizTypeEnum -> customBizTypeEnum));
    }
}
