package com.github.wephotos.webwork.schema.entity;

import com.github.wephotos.webwork.schema.exception.StateCode;

/**
 * 返回结构工具类
 * @author TQ
 *
 */
public final class Results {

	public static <T> Result<T> newResult(T data){
		return newResult(data, StateCode.SUCCESS);
	}
	
	public static <T> Result<T> newSuccessfullyResult(){
		return newResult(null, StateCode.SUCCESS);
	}
	
	public static <T> Result<T> newFailedResult(StateCode code){
		return newResult(null, code);
	}
	
	public static <T> Result<T> newResult(T data, StateCode code){
		return new Result<T>(data, code);
	}
}
