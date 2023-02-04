package com.github.wephotos.webwork.photos.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.photos.entity.UserProfile;
import com.github.wephotos.webwork.photos.entity.vo.UserProfileVO;
import com.github.wephotos.webwork.photos.service.UserProfileService;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.utils.BeanUtils;

/**
 * 用户描述信息接口
 * @author TianQi
 *
 */
@RestController
@RequestMapping("/user-profile")
public class UserProfileController {

	@Resource
	private UserProfileService userProfileService;
	
	/**
	 * 获取用户描述信息
	 * @param session 会话对象
	 * @return 用户描述信息
	 */
	@GetMapping("/get")
	public Result<UserProfileVO> getProfile(HttpSession session){
		SecurityUser user = SecurityUtils.getSecurityUser(session);
		UserProfile profile = userProfileService.getProfile(user.getId());
		UserProfileVO userProfileVO = BeanUtils.toObject(profile, UserProfileVO.class);
		return Results.newSuccessfullyResult(userProfileVO);
	}
}
