package com.github.wephotos.webwork.security.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 响应对象
 * @author TQ
 *
 */
@Getter
@Setter
@Builder
@ToString
public class RestObject {

	/**
	 * 错误代码
	 */
	private int code;
	/**
	 * 错误消息
	 */
	private String msg;
	/**
	 * 返回数据
	 */
	private Object data;
}
