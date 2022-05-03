package com.github.wephotos.webwork.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.user.api.entity.po.UserLoginPo;
import com.github.wephotos.webwork.user.service.UserLoginService;

@RestController
@RequestMapping("/user-login")
public class UserLoginController {

	//日志
		private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);
		
	    /**
	     * 登录服务
	     */
	    @Resource
	    private UserLoginService userLoginService;

	    /**
	     * 用户登录接口
	     * {@link UserLoginPo}
	     *
	     * @param auth 认证信息
	     * @return {@link SecurityUser}
	     */
	    @PostMapping("/login")
	    public Result<SecurityUser> login(UserLoginPo po, HttpSession session) {
	        Result<SecurityUser> result = userLoginService.login(po);
	        SecurityUtils.setSecurityUser(result.getData(), session);
	        log.info("登录接口调用");
	        return result;
	    }
}
