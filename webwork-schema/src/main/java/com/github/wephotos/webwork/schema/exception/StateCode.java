package com.github.wephotos.webwork.schema.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * 状态码
 * @author TQ
 *
 */
@Getter
public final class StateCode {
	
	/**
	 * 状态码映射表
	 */
	private static final Map<Integer, StateCode> lookup = new HashMap<>();
	
	/**
	 * 成功，code = 0
	 */
	public static final StateCode SUCCESS = new StateCode(0, "成功");
	/**
	 * 内部错误，code = 500
	 */
	public static final StateCode FAILED = new StateCode(500, "服务器内部错误");
	
	/**
	 * 参数缺失或非法
	 */
	public static final StateCode MISSING_ILLEGAL_PARAMETER = new StateCode(400, "错误的参数");
	
	/**
	 * 错误码
	 */
    private final Integer code;
    /**
     * 错误消息
     */
    private final String message;

    /**
     * 错误码构建函数
     * @param code 错误码
     * @param message 错误消息
     */
    public StateCode(int code, String message) {
        this.code = code;
        this.message = message;
        if(lookup.containsKey(code)) {
        	throw new IllegalArgumentException("重复的错误码定义");
        }
        lookup.put(code, this);
    }
    
}
