package com.github.wephotos.webwork.error;

import lombok.Getter;

/**
 * @author chengzi
 * @date 2021-03-10 21:50
 */
@Getter
public class WebworkException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private final Integer code;

    public WebworkException(String message) {
    	super(message);
    	this.code = 500;
    }
    
    public WebworkException(Throwable cause) {
    	super(cause);
    	this.code = 500;
    }

    public WebworkException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }

    public WebworkException(int code, String message) {
        super(message);
        this.code = code;
    }

    public WebworkException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
