package com.github.wephotos.webwork.schema.utils;

import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.exception.StateCode;

/**
 * 返回结构工具类
 * @author TQ
 *
 */
public final class Results {

	public static <T> Result<T> newSuccessfullyResult(){
		return newSuccessfullyResult(null);
	}
	
	public static <T> Result<T> newSuccessfullyResult(T data){
		return newResult(data, StateCode.SUCCESS, null);
	}
	
	public static <T> Result<T> newResult(StateCode code){
		return newResult(code, null);
	}
	
	public static <T> Result<T> newResult(StateCode code, String message){
		return newResult(null, code, message);
	}
	
	public static <T> Result<T> newResult(T data, StateCode code, String message){
		return new Result<T>(data, code, message);
	}
}
