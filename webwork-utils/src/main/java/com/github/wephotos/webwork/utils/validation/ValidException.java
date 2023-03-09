package com.github.wephotos.webwork.utils.validation;

/**
 * 参数校验异常
 * 
 * @author TianQi
 *
 */
public class ValidException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ValidException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * 参数校验异常构造函数
	 * 
	 * @param errcode 错误码
	 * @param message 错误消息
	 */
	public ValidException(String errcode, String message) {
		super(message);
		this.errcode = errcode;
	}
	
	private String errcode;
	
	public String getErrcode() {
		return errcode;
	}
}
