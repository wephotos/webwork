package com.github.wephotos.webwork.core.controller;

import com.github.wephotos.webwork.core.service.UserRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户角色
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/user-role")
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;
}
