package com.github.wephotos.webwork.platform.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.platform.service.PlatformService;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.user.client.entity.po.ChangePasswordPO;
import com.github.wephotos.webwork.user.client.entity.po.UpdateUserInfoPO;
import com.github.wephotos.webwork.user.client.entity.po.UserLoginPO;
import com.github.wephotos.webwork.user.client.entity.ro.ResoRO;
import com.github.wephotos.webwork.user.client.entity.ro.UserRO;

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
	 * 用户登录
	 *
	 * @param po 登录信息
	 * @return 用户信息
	 */
	@PostMapping("/login")
	public Result<SecurityUser> login(UserLoginPO po, HttpSession session) {
		Result<SecurityUser> result = platformService.login(po);
		if(result.isSuccess()) {
			SecurityUtils.setSecurityUser(result.getData(), session);			
		}
		return result;
	}
	
	/**
	 * 退出系统登录
	 * @param session 当前会话
	 * @param request HTTP request
	 * @param response HTTP response
	 * @throws IOException I/O exception
	 */
	@GetMapping("/logout")
	public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		session.invalidate();
		response.sendRedirect(request.getContextPath());
	}
	
	/**
	 * 获取用户个人信息
	 * 
	 * @param session 会话对象
	 * @return 用户信息
	 */
	@GetMapping("/get-user-info")
	public Result<UserRO> getUserInfo(HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		return platformService.getUserInfo(user.getAccount());
	}
	
	/**
	 * 更新用户个人信息
	 * 
	 * @param po 更新信息
	 * @param session 会话对象
	 * @return 是否更新成功
	 */
	@PostMapping("/update-user-info")
	public Result<Boolean> updateUserInfo(@RequestBody UpdateUserInfoPO po, HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		po.setId(user.getId());
		return platformService.updateUserInfo(po);
	}
	
	/**
	 * 修改用户密码
	 * @param po 修改密码参数
	 * @param session HTTP会话
	 * @return 是否修改成功
	 */
	@PostMapping("/change-password")
	public Result<Boolean> login(@RequestBody ChangePasswordPO po, HttpSession session) {
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		po.setUserId(user.getId());
		return platformService.changePassword(po);
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
