package com.github.wephotos.webwork.security.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户认证信息
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
public class UserAuth {

	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
}
