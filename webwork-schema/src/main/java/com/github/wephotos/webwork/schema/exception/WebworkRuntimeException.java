package com.github.wephotos.webwork.schema.exception;

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

	private final StateCode stateCode;

    public WebworkRuntimeException(String message) {
        super(message);
        this.stateCode = StateCode.UNKNOW_ERROR;
    }
    
    public WebworkRuntimeException(Throwable cause) {
    	super(cause);
    	this.stateCode = StateCode.UNKNOW_ERROR;
    }

    public WebworkRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.stateCode = StateCode.UNKNOW_ERROR;
    }

    public WebworkRuntimeException(StateCode stateCode, String message) {
        super(message);
        this.stateCode = stateCode;
    }

    public WebworkRuntimeException(StateCode stateCode, String message, Throwable cause) {
        super(message, cause);
        this.stateCode = stateCode;
    }
}
