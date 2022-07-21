package com.github.wephotos.webwork.user.api;

import java.util.List;

import com.github.wephotos.webwork.user.api.entity.ro.ResourceRo;
import com.github.wephotos.webwork.user.api.entity.ro.RoleRo;
import com.github.wephotos.webwork.user.api.entity.ro.NodeRo;
import com.github.wephotos.webwork.user.api.entity.ro.UserRo;

/**
 * 用户客户端接口
 * @author TQ
 *
 */
public interface UserClient {

	/**
	 * 根据账号查询用户信息
	 * @param account 账号
	 * @return 用户信息
	 */
	UserRo selectByAccount(String account);
	
	/**
	 * 查询用户详情，包含部门单位信息
	 * @param userId 用户ID
	 * @return 用户详情数据
	 */
	UserRo findUserDetailsById(Integer userId);
	
	/**
	 * 用于加载组织架构树节点
	 * @param parentId 父节点
	 * @return 子节点集合
	 */
	List<NodeRo> listNodeByParentId(Integer parentId);
	
	/**
	 * 获取部门下的用户
	 * @param deptId 部门ID
	 * @return 用户集合
	 */
	List<UserRo> listUserByDeptId(Integer deptId);
	
	/**
	 * 获取用户所有角色
	 * @param userId 用户ID
	 * @return 角色集合
	 */
	List<RoleRo> listRoleByUserId(Integer userId);
	
	/**
	 * 获取用户所有权限数据
	 * @param userId 用户ID
	 * @return 权限数据集合
	 */
	List<ResourceRo> listResourceByUserId(Integer userId);
}
