package com.github.wephotos.webwork.schema.exception;

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
	
    private final StateCode stateCode;

    public WebworkException(String message) {
    	super(message);
    	this.stateCode = StateCode.UNKNOW_ERROR;
    }
    
    public WebworkException(Throwable cause) {
    	super(cause);
    	this.stateCode = StateCode.UNKNOW_ERROR;
    }

    public WebworkException(String message, Throwable cause) {
        super(message, cause);
        this.stateCode = StateCode.UNKNOW_ERROR;
    }

    public WebworkException(StateCode stateCode, String message) {
        super(message);
        this.stateCode = stateCode;
    }

    public WebworkException(StateCode stateCode, String message, Throwable cause) {
        super(message, cause);
        this.stateCode = stateCode;
    }
}
