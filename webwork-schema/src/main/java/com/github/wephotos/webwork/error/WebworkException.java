package com.github.wephotos.webwork.error;

import lombok.Getter;

/**
 * @author chengzi
 * @date 2021-03-10 21:50
 */
@Getter
public class WebworkException extends RuntimeException {
    private final String msg;
    private final Integer code;

    public WebworkException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public WebworkException(String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
    }

    public WebworkException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = 500;
    }

    public WebworkException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public WebworkException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
