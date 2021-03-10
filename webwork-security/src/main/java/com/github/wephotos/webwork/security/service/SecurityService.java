package com.github.wephotos.webwork.security.service;

import com.github.wephotos.webwork.security.auth.SecurityAuth;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.entity.UserAuth;

/**
 * 认证服务
 * @author TQ
 *
 */
public class SecurityService {
	
	/**
	 * 认证接口
	 */
	private SecurityAuth securityAuth;
	
	/**
	 * 设置认证接口
	 * @param securityAuth
	 */
	public void setSecurityAuth(SecurityAuth securityAuth) {
		this.securityAuth = securityAuth;
	}
	
	/**
	 * 用户认证
	 * @param auth 认证信息
	 * @return 用户
	 */
	public User auth(UserAuth auth) {
		return securityAuth.auth(auth);
	}
}
