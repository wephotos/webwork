package com.github.wephotos.webwork.error;

import lombok.Getter;

/**
 * @author chengzi
 * @date 2021-03-10 22:09
 */
@Getter
public class WebworkRuntimeException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Integer code;

    public WebworkRuntimeException(String message) {
        super(message);
        this.code = 500;
    }
    
    public WebworkRuntimeException(Throwable cause) {
    	super(cause);
    	this.code = 500;
    }

    public WebworkRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }

    public WebworkRuntimeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public WebworkRuntimeException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
