package com.github.wephotos.webwork.security.controller;

import com.github.wephotos.webwork.http.RestObject;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.entity.UserAuth;
import com.github.wephotos.webwork.security.service.SecurityService;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * API接口
 *
 * @author TQ
 */
@RestController
@RequestMapping("/security")
@ConditionalOnBean(value = SecurityService.class)
public class SecurityController {

    /**
     * 认证服务
     */
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
        return RestObject.builder().data(user).build();
    }

}
