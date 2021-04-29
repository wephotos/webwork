package com.github.wephotos.webwork.security.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.http.RestObject;
import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.entity.UserAuth;
import com.github.wephotos.webwork.security.service.SecurityService;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;

/**
 * API接口
 *
 * @author TQ
 */
@RestController
@RequestMapping("/security")
public class SecurityController {
	//日志
	private static final Logger log = LoggerFactory.getLogger(SecurityController.class);
    /**
     * 认证服务
     */
	@Lazy
    @Resource
    private SecurityService securityService;

    /**
     * 用户认证
     * {@link UserAuth}
     *
     * @param auth 认证信息
     * @return {@link User}
     */
    @PostMapping("/auth")
    public RestObject auth(UserAuth auth, HttpSession session) {
        User user = securityService.auth(auth);
        SessionUserStorage.put(user, session);
        log.info("登录接口调用");
        return RestObject.builder().data(user).build();
    }

}
