package com.pd.spring.exception;

/**
 * @author pd 2018/3/8.
 */
public class PdRuntimeException extends PdException {

    public PdRuntimeException() {
    }

    public PdRuntimeException(String message) {
        super(message);
    }

    public PdRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdRuntimeException(Throwable cause) {
        super(cause);
    }

    public PdRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
