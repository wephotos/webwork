package com.github.wephotos.webwork.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.user.service.UserOrgService;

import javax.annotation.Resource;

/**
 * 用户组织
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/userOrg")
public class UserOrgController {
    @Resource
    private UserOrgService userOrgService;
}
