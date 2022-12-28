package com.github.wephotos.webwork.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;
import com.github.wephotos.webwork.user.entity.RoleUser;
import com.github.wephotos.webwork.user.service.RoleUserService;

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
     * @return {@link Result} 包含数据为新增记录ID
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody RoleUser record, HttpSession session) {
    	SecurityUser user = SecurityUtils.getSecurityUser(session);
    	record.setCreateUser(user.getName());
    	Integer id = roleUserService.add(record);
    	return Results.newSuccessfullyResult(id);
    }
    
    /**
     * 根据主键删除
     * @param id 记录主键
     * @return {@link Result} 成功返回true
     */
    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") String id) {
    	boolean ret = roleUserService.deleteById(id);
    	return  Results.newSuccessfullyResult(ret);
    }
    
    /**
     * 查询角色下成员
     * @param roleId 角色ID
     * @return {@link Result} {@link RoleUser}
     */
    @GetMapping("/list-by-role")
    public Result<List<RoleUser>> listByRoleId(String roleId) {
    	List<RoleUser> data = roleUserService.listByRoleId(roleId);
    	return  Results.newSuccessfullyResult(data);
    }
}
