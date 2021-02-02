package com.github.wephotos.webwork.controller;

import com.github.wephotos.webwork.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户角色
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/user-role")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
}
