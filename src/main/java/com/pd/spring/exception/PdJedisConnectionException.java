package com.pd.spring.exception;

/**
 * @author peramdy
 */
public class PdJedisConnectionException extends PdRuntimeException {

    public PdJedisConnectionException() {
        super();
    }

    public PdJedisConnectionException(String message) {
        super(message);
    }

    public PdJedisConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdJedisConnectionException(Throwable cause) {
        super(cause);
    }

    public PdJedisConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
