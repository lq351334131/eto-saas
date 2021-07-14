package etocrm.enums;


public enum OtherSystemUrlTypeEnum {

    //ACCOUNT_ADD_REDIRECT(6,"账号新增同步地址"),
    LOGOUT_URL(2, "退出登录"),
    SYSTEM_URL(3, "跳转地址"),

    /**
     * 账号数据同步地址
     */
    ACCOUNT_DATA_REDIRECT(1, "账号数据同步地址"),
    /**
     * 登出回调地址
     */
    LOGOUT_REDIRECT(2, "登出回调地址"),
    /**
     * 登录跳转地址
     */
    LOGIN_REDIRECT(3, "登录跳转地址"),
    /**
     * 机构新增同步地址
     */
    ORG_ADD_REDIRECT(4, "机构新增同步地址"),
    /**
     * 品牌新增通知地址
     */
    BRAND_NOTICE_REDIRECT(5, "品牌新增通知地址"),
    /**
     * 账号新增同步地址
     */
    ACCOUNT_ADD_REDIRECT(6, "账号新增同步地址"),
    /**
     * 账号更新通知地址
     */
    ACCOUNT_UPDATE_NOTICE_REDIRECT(7, "账号更新通知地址"),
    /**
     * 账号身份类型新增同步地址
     */
    IDENTITY_ADD_REDIRECT(8, "账号身份类型新增同步地址"),
    /**
     * 账号身份类型更新通知地址
     */
    IDENTITY_UPDATE_REDIRECT(9, "账号身份类型更新通知地址"),
    /**
     * 分公司新增同步地址
     */
    SUBSIDIARY_ADD_REDIRECT(10, "分公司新增同步地址"),
    /**
     * 分公司更新通知地址
     */
    SUBSIDIARY_UPDATE_NOTICE_REDIRECT(11, "分公司更新通知地址"),
    /**
     * 开户通知地址
     */
    ACCOUNT_OPEN_NOTICE_REDIRECT(12, "开户通知地址"),


    BRAND_NOTICE_REDIRECT_UPDATE(13, "品牌更新通知地址");


    private Integer code;
    private String message;

    OtherSystemUrlTypeEnum(Integer code, String message) {
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
