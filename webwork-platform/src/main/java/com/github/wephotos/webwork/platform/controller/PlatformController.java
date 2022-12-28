package com.github.wephotos.webwork.platform.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.platform.service.PlatformService;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.user.client.entity.po.UserLoginPO;
import com.github.wephotos.webwork.user.client.entity.ro.ResoRO;

/**
 * 提供平台相关接口
 * @author TianQi
 *
 */
@RestController
@RequestMapping("/platform")
public class PlatformController {

	@Resource
	private PlatformService platformService;
	
	/**
	 * 用户登录接口 {@link UserLoginPO}
	 *
	 * @param loginPo 登录信息
	 * @return {@link SecurityUser}
	 */
	@PostMapping("/login")
	public Result<SecurityUser> login(UserLoginPO loginPo, HttpSession session) {
		Result<SecurityUser> result = platformService.login(loginPo);
		if(result.isSuccess()) {
			SecurityUtils.setSecurityUser(result.getData(), session);			
		}
		return result;
	}
	
	/**
	 * 获取用户的应用
	 * @param session 会话
	 * @return 应用集合
	 */
	@GetMapping("/list-apps")
	public Result<List<ResoRO>> listApps(HttpSession session){
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		return platformService.listApps(user.getId());
	}
	
}
