package etocrm.enums;

/**
 * @Author: dkx
 * @Date: 16:18 2021/2/7
 * @Desc:
 */
public enum AttachTypeEnum {

    //code 一定要保证唯一性

    OEG("org", "机构其他资质"),
    ORDERAYEPDTAIL("order_pay_detail","支付凭证"),
    ;

    private String code;
    private String message;

    AttachTypeEnum(String code, String message) {
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
