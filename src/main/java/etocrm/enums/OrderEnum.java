package etocrm.enums;

/**
 * @Author: dkx
 * @Date: 16:17 2021/2/7
 * @Desc:
 */
public enum OrderEnum {

    NOTINFORCE(0,"订单状态 0未生效"),
    INVALID(1,"订单状态 1已作废"),
    INFORCE(2,"订单状态2已生效"),

    FREEADMISSION(0,"订单类型 0免费"),
    PAY(1,"订单类型 1付费"),

    NOTINVOICED(0,"开票状态 0未开票 "),
    INVOICED(1,"开票状态 1已开票 "),
    NOINVOICE(2,"开票状态 2不开发票")

        ;

    private Integer code;
    private String message;

    OrderEnum(Integer code, String message) {
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
