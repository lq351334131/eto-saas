package etocrm.enums;

public enum AccountTypeEnum {


    ETO_BASIC("ETOSHOP","电商"),
    ETO_DATA("ETODATA","DATA"),
    ETO_CRM("ETOCRM","crm系统"),
    ETO_MINIAPP("ETOMINIAPP","小程序"),
    ETO_SERVICE("ETOSERVICE","公众号系统");

    private String code;
    private String message;

    AccountTypeEnum(String code, String message) {
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
