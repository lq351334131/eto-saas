package etocrm.exception;

import org.etocrm.database.enums.ResponseEnum;

public class UamException extends BussinessException {
    public UamException(ResponseEnum responseEnum, Throwable cause) {
        super(responseEnum, cause);
    }

    public UamException(int code, String msg) {
        super(code, msg);
    }
}
