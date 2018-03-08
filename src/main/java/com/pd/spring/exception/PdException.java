package com.pd.spring.exception;

/**
 * @author pd on 2018/3/8.
 */
public class PdException extends Exception {

    public PdException() {
    }

    public PdException(String message) {
        super(message);
    }

    public PdException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdException(Throwable cause) {
        super(cause);
    }

    public PdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
