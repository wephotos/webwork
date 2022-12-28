package com.github.wephotos.webwork.platform.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.utils.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.user.client.UserClient;
import com.github.wephotos.webwork.user.client.entity.po.UserLoginPO;
import com.github.wephotos.webwork.user.client.entity.ro.ResoRO;
import com.github.wephotos.webwork.user.client.entity.ro.UserRO;
import com.github.wephotos.webwork.utils.BeanUtils;

/**
 * 平台服务
 * @author TianQi
 *
 */
@Service
public class PlatformService {

	@Resource
	private UserClient userClient;
	
	public Result<SecurityUser> login(UserLoginPO po) {
		Result<UserRO> result = userClient.login(po);
		if(result.isSuccess()) {
			UserRO vo = result.getData();
			SecurityUser user = BeanUtils.toBean(vo, SecurityUser.class);
			user.setGroupId(vo.getOrgId());
			user.setGroupName(vo.getOrgName());
			return Results.newSuccessfullyResult(user);
		}else {
			return Results.newResult(StateCode.get(result.getCode()));
		}
	}
	
	/**
	 * 获取用户拥有的APP集合
	 * @param userId 用户ID
	 * @return APP信息集合
	 */
	public Result<List<ResoRO>> listApps(Integer userId){
		return userClient.listAppByUserId(userId);
	}

}
