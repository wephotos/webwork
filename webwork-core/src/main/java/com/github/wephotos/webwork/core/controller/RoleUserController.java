package com.github.wephotos.webwork.core.controller;

import com.github.wephotos.webwork.core.entity.RoleUser;
import com.github.wephotos.webwork.core.service.RoleUserService;
import com.github.wephotos.webwork.http.RestObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

/**
 * 角色用户
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/role-user")
public class RoleUserController {
	
    @Resource
    private RoleUserService roleUserService;
    
    /**
     * 添加角色成员
     * @param record 角色成员
     * @return {@link RestObject} 包含数据为新增记录ID
     */
    @PostMapping("/add")
    public RestObject add(@RequestBody RoleUser record) {
    	String id = roleUserService.add(record);
    	return RestObject.builder().data(id).build();
    }
    
    /**
     * 根据主键删除
     * @param id 记录主键
     * @return {@link RestObject} 成功返回true
     */
    @GetMapping("/delete/{id}")
    public RestObject delete(@PathVariable("id") String id) {
    	boolean ret = roleUserService.deleteById(id);
    	return  RestObject.builder().data(ret).build();
    }
    
    /**
     * 查询角色下成员
     * @param roleId 角色ID
     * @return {@link RestObject} {@link RoleUser}
     */
    @GetMapping("/list-by-role")
    public RestObject listByRoleId(String roleId) {
    	List<RoleUser> data = roleUserService.listByRoleId(roleId);
    	return  RestObject.builder().data(data).build();
    }
}
