package com.github.wephotos.webwork.user.service.api;

import javax.annotation.Resource;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.github.wephotos.webwork.user.api.UserClient;
import com.github.wephotos.webwork.user.api.entity.ro.UserRo;
import com.github.wephotos.webwork.user.entity.User;
import com.github.wephotos.webwork.user.service.UserService;
import com.github.wephotos.webwork.utils.BeanUtils;

/**
 * 用户类对外接口本地实现
 * @author TQ
 *
 */
@Primary
@Component
public class UserClientDefault implements UserClient {
	
	@Resource
	private UserService userService;

	@Override
	public UserRo selectByAccount(String account) {
		User user = this.userService.getByAccount(account);
		return BeanUtils.toBean(user, UserRo.class);
	}

	@Override
	public UserRo findUserDetailsById(Integer userId) {
		return userService.findUserDetailsById(userId);
	}

}
