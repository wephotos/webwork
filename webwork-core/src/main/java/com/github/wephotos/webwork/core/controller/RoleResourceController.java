package com.github.wephotos.webwork.core.controller;

import com.github.wephotos.webwork.core.entity.dto.RoleResoDto;
import com.github.wephotos.webwork.core.service.RoleResourceService;
import com.github.wephotos.webwork.http.RestObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 保存角色资源数据
     * @param data 角色资源数据
     * @return {@link RestObject}
     */
    @PostMapping("/save")
    public RestObject save(@RequestBody RoleResoDto data) {
    	roleResourceService.save(data);
    	return RestObject.builder().build();
    }
    
}
