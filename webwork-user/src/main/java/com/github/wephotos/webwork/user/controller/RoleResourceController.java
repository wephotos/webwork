package com.github.wephotos.webwork.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.user.entity.po.RoleResourcesPO;
import com.github.wephotos.webwork.user.service.RoleResourceService;

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

    /**
     * 保存角色资源数据
     * @param data 角色资源数据
     * @return {@link Result}
     */
    @PostMapping("/save")
    public Result<Void> save(@RequestBody RoleResourcesPO data, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	data.setCreateUser(user.getId() + "_" + user.getName());
    	roleResourceService.save(data);
    	return Results.newSuccessfullyResult();
    }
    
}
