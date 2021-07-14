package etocrm.enums;

/**
 * @Author: dkx
 * @Date: 16:24 2021/2/7
 * @Desc:
 */
public enum OrgAuthEnum {

    //========机构认证======orgApprovalType=====


    NOT_AUTH("not_auth","未认证"),
    AUTH("auth","认证通过"),
    REJECT("reject","认证驳回"),

    ;

    private String code;
    private String message;

    OrgAuthEnum(String code, String message) {
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
