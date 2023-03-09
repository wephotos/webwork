package com.github.wephotos.webwork.user.client.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 修改密码参数
 * 
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class ChangePasswordPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 原密码
	 */
	private String password;
	
	/**
	 * 新密码
	 */
	private String newPassword;
}
