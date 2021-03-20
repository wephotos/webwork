package com.github.wephotos.webwork.core.controller;

import com.github.wephotos.webwork.core.service.RoleResourceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 角色资源
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/role-resource")
public class RoleResourceController {
    @Resource
    private RoleResourceService roleResourceService;

}
