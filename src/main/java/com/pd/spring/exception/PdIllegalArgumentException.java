package com.pd.spring.exception;

/**
 * @author pd 2018/3/8.
 */
public class PdIllegalArgumentException extends PdRuntimeException {

    public PdIllegalArgumentException() {
    }

    public PdIllegalArgumentException(String s) {
        super(s);
    }

    public PdIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdIllegalArgumentException(Throwable cause) {
        super(cause);
    }
}
