package com.github.wephotos.webwork.user.service.client;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.user.client.UserClient;
import com.github.wephotos.webwork.user.client.entity.po.UserLoginPO;
import com.github.wephotos.webwork.user.client.entity.po.UserResoQueryPO;
import com.github.wephotos.webwork.user.client.entity.po.UserRoleQueryPO;
import com.github.wephotos.webwork.user.client.entity.ro.NodeRO;
import com.github.wephotos.webwork.user.client.entity.ro.ResoRO;
import com.github.wephotos.webwork.user.client.entity.ro.RoleRO;
import com.github.wephotos.webwork.user.client.entity.ro.UserRO;
import com.github.wephotos.webwork.user.entity.Role;
import com.github.wephotos.webwork.user.entity.User;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;
import com.github.wephotos.webwork.user.entity.vo.ResoVO;
import com.github.wephotos.webwork.user.entity.vo.UserVO;
import com.github.wephotos.webwork.user.service.OrganizationService;
import com.github.wephotos.webwork.user.service.ResourceService;
import com.github.wephotos.webwork.user.service.RoleService;
import com.github.wephotos.webwork.user.service.UserService;
import com.github.wephotos.webwork.utils.BeanUtils;
import com.github.wephotos.webwork.utils.StringUtils;

/**
 * 用户客户端接口默认实现
 * @author TQ
 *
 */
@Primary
@Component
public class UserClientDefault implements UserClient {
	
	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private ResourceService resourceService;
	
	@Resource
	private OrganizationService organizationService;

	@Override
	public Result<UserRO> login(UserLoginPO po) {
		Result<UserVO> result = userService.login(po);
		if(result.isSuccess()) {
			UserVO vo = result.getData();
			UserRO ro = BeanUtils.toBean(vo, UserRO.class);
			return Results.newSuccessfullyResult(ro);
		}else {
			return Results.newResult(StateCode.get(result.getCode()));
		}
	}

	@Override
	public Result<UserRO> selectByUsername(String username) {
		User user = this.userService.getByAccount(username);
		UserRO ro = BeanUtils.toBean(user, UserRO.class);
		return Results.newSuccessfullyResult(ro);
	}

	@Override
	public Result<UserRO> findUserDetailsById(Integer userId) {
		UserVO vo = userService.findUserDetailsById(userId);
		UserRO ro = BeanUtils.toBean(vo, UserRO.class);
		return Results.newSuccessfullyResult(ro);
	}

	@Override
	public Result<List<NodeRO>> listOrgaNodes(Integer parentId) {
		List<NodeVO> nodes = organizationService.children(parentId);
		List<NodeRO> data = BeanUtils.toBeanList(nodes, NodeRO.class);
		return Results.newSuccessfullyResult(data);
	}

	@Override
	public Result<List<UserRO>> listUserByDeptId(Integer deptId) {
		List<UserVO> users = userService.listUserByDeptId(deptId);
		List<UserRO> data = BeanUtils.toBeanList(users, UserRO.class);
		return Results.newSuccessfullyResult(data);
	}

	@Override
	public Result<List<RoleRO>> listRoleByUserId(UserRoleQueryPO po) {
		List<Role> roles = roleService.listRoleByUserId(po);
		List<RoleRO> data = BeanUtils.toBeanList(roles, RoleRO.class);
		return Results.newSuccessfullyResult(data);
	}
	
	@Override
	public Result<List<ResoRO>> listAppByUserId(Integer userId) {
		Objects.requireNonNull(userId, "用户ID不能为空");
		// 构建 用户应用筛选条件
		UserResoQueryPO po = new UserResoQueryPO();
		po.setUserId(userId);
		po.setResoType(NodeTypeEnum.APP.getCode());
		List<ResoVO> apps = resourceService.listResourceByUserId(po);
		List<ResoRO> data = BeanUtils.toBeanList(apps, ResoRO.class);
		return Results.newSuccessfullyResult(data);
	}
	
	@Override
	public Result<List<ResoRO>> listResourceForAppByUserId(UserResoQueryPO po) {
		// 参数非空校验
		Objects.requireNonNull(po.getUserId(), "用户ID[userId]不能为空");
		if(po.getAppId() == null && StringUtils.isBlank(po.getAppCode())) {
			throw new NullPointerException("应用ID[appId]与应用代码[appCode]不能同时为空");
		}
		
		List<ResoVO> data = resourceService.listResourceByUserId(po);
		List<ResoRO> roList = BeanUtils.toBeanList(data, ResoRO.class);
		return Results.newSuccessfullyResult(roList);
	}
	
}
