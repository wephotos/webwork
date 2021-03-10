package com.github.wephotos.webwork.error;

/**
 * @author chengzi
 * @date 2021-03-10 22:09
 */
public class WebworkRuntimeException extends RuntimeException {
    public WebworkRuntimeException() {
        super();
    }

    public WebworkRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebworkRuntimeException(Throwable cause) {
        super(cause);
    }
}
