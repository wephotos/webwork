package com.github.wephotos.webwork.user.client;

import java.util.List;

import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.user.client.entity.po.UserResoQueryPO;
import com.github.wephotos.webwork.user.client.entity.po.UserRoleQueryPO;
import com.github.wephotos.webwork.user.client.entity.po.ChangePasswordPO;
import com.github.wephotos.webwork.user.client.entity.po.UpdateUserInfoPO;
import com.github.wephotos.webwork.user.client.entity.po.UserLoginPO;
import com.github.wephotos.webwork.user.client.entity.ro.NodeRO;
import com.github.wephotos.webwork.user.client.entity.ro.ResoRO;
import com.github.wephotos.webwork.user.client.entity.ro.RoleRO;
import com.github.wephotos.webwork.user.client.entity.ro.UserRO;


/**
 * 用户客户端接口
 * @author TQ
 *
 */
public interface UserClient {
	
	/**
	 * 用户登录
	 * @param po 登录信息
	 * @return 是否登录成功
	 */
	Result<UserRO> login(UserLoginPO po);

	/**
	 * 根据账号查询用户信息
	 * @param username 账号
	 * @return 用户信息
	 */
	Result<UserRO> selectByUsername(String username);
	
	/**
	 * 修改用户密码
	 * @param po 修改密码参数
	 * @return 是否修改成功
	 */
	Result<Boolean> changePassword(ChangePasswordPO po);
	
	/**
	 * 更新用户信息
	 * 
	 * @param po 用户信息参数
	 * @return 是否更新成功
	 */
	Result<Boolean> updateUserInfo(UpdateUserInfoPO po);
	
	/**
	 * 查询用户详情，包含部门单位信息
	 * @param userId 用户ID
	 * @return 用户详情数据
	 */
	Result<UserRO> findUserDetailsById(Integer userId);
	
	/**
	 * 加载组织架构的子节点
	 * @param parentId 父节点
	 * @return 子节点集合
	 */
	Result<List<NodeRO>> listOrgaNodes(Integer parentId);
	
	/**
	 * 获取部门下的用户
	 * @param deptId 部门ID
	 * @return 用户集合
	 */
	Result<List<UserRO>> listUserByDeptId(Integer deptId);
	
	/**
	 * 获取用户角色数据
	 * @param userId 用户ID
	 * @return 角色集合
	 */
	Result<List<RoleRO>> listRoleByUserId(UserRoleQueryPO po);
	
	/**
	 * 获取用户拥有的应用信息
	 * @param userId 用户ID
	 * @return 应用集合
	 */
	Result<List<ResoRO>> listAppByUserId(Integer userId);
	
	/**
	 * 获取用户在某个应用下的资源信息
	 * @param 查询参数
	 * @return 资源集合
	 */
	Result<List<ResoRO>> listResourceForAppByUserId(UserResoQueryPO po);
}
