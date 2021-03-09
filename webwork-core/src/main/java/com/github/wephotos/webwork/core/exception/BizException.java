package com.github.wephotos.webwork.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义业务异常
 *
 * @author chengzi
 * @date 2021-01-27 08:52
 */
@Getter
@Setter
public class BizException extends RuntimeException {
    private String msg;
    private Integer code;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
    }

    public BizException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = 500;
    }

    public BizException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BizException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
