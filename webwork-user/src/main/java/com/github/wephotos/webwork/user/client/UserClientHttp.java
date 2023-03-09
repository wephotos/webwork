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
 * 用户权限模块 HTTP 客户端
 * @author TianQi
 *
 */
public class UserClientHttp implements UserClient {
	
	/**
	 * 服务地址
	 */
	private String baseUrl;
	
	public String getBaseUrl() {
		return baseUrl;
	}
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public Result<UserRO> login(UserLoginPO po) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result<UserRO> selectByUsername(String username) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Result<Boolean> changePassword(ChangePasswordPO po) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Result<Boolean> updateUserInfo(UpdateUserInfoPO po) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result<UserRO> findUserDetailsById(Integer userId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result<List<NodeRO>> listOrgaNodes(Integer parentId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result<List<UserRO>> listUserByDeptId(Integer deptId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result<List<RoleRO>> listRoleByUserId(UserRoleQueryPO po) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result<List<ResoRO>> listAppByUserId(Integer userId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result<List<ResoRO>> listResourceForAppByUserId(UserResoQueryPO po) {
		throw new UnsupportedOperationException();
	}
	
}
