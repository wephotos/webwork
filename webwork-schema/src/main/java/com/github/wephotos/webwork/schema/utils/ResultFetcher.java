package com.github.wephotos.webwork.schema.utils;

import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;

/**
 * 获取返回结果中的数据
 * @author TianQi
 *
 */
public final class ResultFetcher {
	
	/**
	 * 获取结果对象中的数据
	 * @param <T> 数据类型
	 * @param result 结果对象
	 * @return 数据对象
	 */
	public static <T> T fetch(Result<T> result) {
		return fetch(result, "");
	}

	/**
	 * 获取结果对象中的数据
	 * @param <T> 数据类型
	 * @param result 结果对象
	 * @param message 出错情况下的异常错误信息
	 * @return 数据对象
	 */
	public static <T> T fetch(Result<T> result, String message) {
		if(result == null) {
			throw new WebworkRuntimeException(StateCode.UNKNOW_ERROR, "返回结果为空");
		}
		if(result.isSuccess()) {
			return result.getData();
		}
		throw new WebworkRuntimeException(new StateCode(result.getCode(), result.getMsg()), message);
	}
}
