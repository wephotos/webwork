package com.github.wephotos.webwork.user.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.user.client.entity.po.UserResoQueryPO;
import com.github.wephotos.webwork.user.client.entity.po.UserRoleQueryPO;
import com.github.wephotos.webwork.user.client.entity.ro.ResoRO;
import com.github.wephotos.webwork.user.client.entity.ro.RoleRO;
import com.github.wephotos.webwork.user.service.client.UserClientDefault;

/**
 * 用户模块对外服务接口
 * @author TianQi
 *
 */
@RestController
@RequestMapping("/user-client")
public class UserClientController {

	@Resource
	private UserClientDefault userClient;
	
	/**
	 * 获取用户拥有的应用
	 * @param userId 应用ID
	 * @return 应用数据
	 */
	@GetMapping("/user-apps/{id}")
	public Result<List<ResoRO>> listAppByUserId(@PathVariable("id") Integer userId){
		return userClient.listAppByUserId(userId);
	}
	
	/**
	 * 获取用户拥有的角色
	 * @param po 查询参数
	 * @return 角色数据
	 */
	@PostMapping("/user-roles")
	public Result<List<RoleRO>> listRoleByUserId(@RequestBody UserRoleQueryPO po){
		return userClient.listRoleByUserId(po);
	}
	
	/**
	 * 获取用户的授权资源信息
	 * @param po 查询参数
	 * @return 授权资源
	 */
	@PostMapping("/user-resources")
	public Result<List<ResoRO>> listResourceForAppByUserId(@RequestBody UserResoQueryPO po){
		return userClient.listResourceForAppByUserId(po);
	}
}
