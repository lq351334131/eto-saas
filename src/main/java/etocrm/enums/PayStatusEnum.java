package etocrm.enums;

/**
 * @Author: dkx
 * @Date: 16:21 2021/2/7
 * @Desc:
 */
public enum PayStatusEnum {

    UNPAID(0,"支付状态0未支付"),
    PAID(1,"支付状态1已支付"),
    PARTIALPAYMENT(2,"支付状态2部分支付");

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
