package com.github.wephotos.webwork.security.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.wephotos.webwork.security.auth.SecurityAuth;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.entity.UserAuth;

/**
 * 认证服务
 * @author TQ
 *
 */
@Service("securityService")
public class SecurityService {
	
	/**
	 * 认证接口
	 */
	@Resource
	private SecurityAuth securityAuth;
	
	/**
	 * 用户认证
	 * @param auth 认证信息
	 * @return 用户
	 */
	public User auth(UserAuth auth) {
		return securityAuth.auth(auth);
	}
}
