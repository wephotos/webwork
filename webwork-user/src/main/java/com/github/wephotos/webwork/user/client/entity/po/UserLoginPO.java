package com.github.wephotos.webwork.user.client.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString(exclude = {"password"})
public class UserLoginPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 验证码
	 */
	private String verifyCode;
}
