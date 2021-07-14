package etocrm.enums;

/**
 * @Author: dkx
 * @Date: 10:39 2020/9/1
 * @Desc: 业务状态
 */
public enum BusinessEnum {

    DATA_SOURCE_EMAIL_ERROR(4001,"邮箱格式不正确"),
    DATA_SOURCE_PHONE_ERROR(4001,"手机号格式不正确"),

    USING(1, "启用"),
    NOTUSE(0, "未启用"),

    MENU(0, "菜单"),
    NO_MENU(1, "按钮"),


    //========机构认证======
    AUTH(0,"认证"),
    APPROVAL(1,"审核"),




//
    APPNAME(0,"公众号"),
    MINAPP(1,"小程序"),

    HEADOFFICE(0,"总公司"),
    BRANCHOFFIC(1,"分公司"),

    COMPANY_AUTO(1,"系统默认"),
    COMPANY_MANUAL(2,"自定义"),

//    BACKGROUNDADD(0,"来源 0后台添加 "),
//    BRANDSELFHELP(1,"来源 1 品牌自助"),

//    NOTINFORCE(0,"订单状态 0未生效"),
//    INVALID(1,"订单状态 1已作废"),
//    INFORCE(2,"订单状态2已生效"),

//    FREEADMISSION(0,"订单类型 0免费"),
//    PAY(1,"订单类型 1付费"),

//    UNPAID(0,"支付状态0未支付"),
//    PAID(1,"支付状态1已支付"),
//    PARTIALPAYMENT(2,"支付状态2部分支付"),

//    NOTINVOICED(0,"开票状态 0未开票 "),
//    INVOICED(1,"开票状态 1已开票 "),
//    NOINVOICE(2,"开票状态 2不开发票"),

//    SEVENDAYS(7,"试用套餐 7天"),
//    ONEMONTH(31,"套餐计划 1个月"),
//    AQUARTER(93,"套餐计划 一个季度"),
//    AYEAR(365,"套餐计划 一年"),

    ;

    private Integer code;
    private String message;

    BusinessEnum(Integer code, String message) {
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
