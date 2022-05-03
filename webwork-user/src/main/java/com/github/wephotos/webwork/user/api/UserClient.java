package com.github.wephotos.webwork.user.api;

import com.github.wephotos.webwork.user.api.entity.ro.UserRo;

/**
 * 用户客户端接口
 * @author TQ
 *
 */
public interface UserClient {

	/**
	 * 根据账号查询用户信息
	 * @param account 账号
	 * @return 用户信息
	 */
	UserRo selectByAccount(String account);
	
	/**
	 * 查询用户详情，包含部门单位信息
	 * @param userId 用户ID
	 * @return 用户详情数据
	 */
	UserRo findUserDetailsById(Integer userId);
}
