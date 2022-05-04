package com.github.wephotos.webwork.user.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.entity.Results;
import com.github.wephotos.webwork.user.api.entity.po.RoleResourcesPo;
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
    public Result<Void> save(@RequestBody RoleResourcesPo data) {
    	roleResourceService.save(data);
    	return Results.newSuccessfullyResult();
    }
    
}