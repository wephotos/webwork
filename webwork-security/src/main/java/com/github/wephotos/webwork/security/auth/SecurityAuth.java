package com.github.wephotos.webwork.security.auth;

import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.entity.UserAuth;

/**
 * 安全认证接口
 * @author TQ
 *
 */
public interface SecurityAuth {

	/**
	 * 用户认证
	 * @param user 用户信息
	 * @return 认证是否成功
	 */
	User auth(UserAuth auth);

}
