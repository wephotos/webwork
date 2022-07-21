package com.github.wephotos.webwork.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.entity.Result;
import com.github.wephotos.webwork.schema.entity.Results;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.user.api.UserClient;
import com.github.wephotos.webwork.user.api.entity.po.UserLoginPo;
import com.github.wephotos.webwork.user.api.entity.ro.UserRo;
import com.github.wephotos.webwork.user.utils.UserStateCode;
import com.github.wephotos.webwork.utils.BeanUtils;

@Service
public class UserLoginService {
	
	@Resource
	private UserClient userClient;

	public Result<SecurityUser> login(UserLoginPo po) {
		UserRo user = userClient.selectByAccount(po.getUsername());
        if (user == null) {
            return Results.newFailedResult(UserStateCode.USER_NOT_EXISTS);
        }
        Integer status = user.getStatus();
        // 账号被删除，返回不存在
        if (EntityState.DELETED.is(status)) {
            return Results.newFailedResult(UserStateCode.USER_NOT_EXISTS);
        }
        // 账号禁用
        if (EntityState.DISABLED.is(status)) {
            return Results.newFailedResult(UserStateCode.USER_DISABLED);
        }
        // TODO 密码加密处理
        String passwordDb = user.getPassword();
        String passwordParam = po.getPassword();
        if (passwordDb.equals(passwordParam)) {
            // 获取部门与组织信息
            UserRo userRo = userClient.findUserDetailsById(user.getId());
            SecurityUser securityUser = BeanUtils.toBean(userRo, SecurityUser.class);
            securityUser.setGroupId(userRo.getOrgId());
            securityUser.setGroupName(userRo.getOrgName());
            return Results.newResult(securityUser);
        } else {
        	return Results.newFailedResult(UserStateCode.USER_PASSWORD_ERROR);
        }
	}
}
