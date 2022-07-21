package com.github.wephotos.webwork.schema.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * 系统状态码定义
 * 0   - 100：保留状态码
 * 100 - 200: 客户端错误
 * 200 - 300: 服务端错误
 * 1000 - ∞ : 业务模块自定义错误
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
	 * 缺失参数
	 */
	public static final StateCode PARAMETER_MISSING = new StateCode(100, "缺失参数");
	
	/**
	 * 非法参数
	 */
	public static final StateCode PARAMETER_ILLEGAL = new StateCode(101, "非法参数");
	
	/**
	 * 服务器未知错误
	 */
	public static final StateCode SERVER_ERROR = new StateCode(200, "服务器内部错误");
	
	
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
