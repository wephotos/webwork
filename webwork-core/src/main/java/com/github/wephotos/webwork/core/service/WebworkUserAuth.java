package com.github.wephotos.webwork.core.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.wephotos.webwork.core.entity.dto.UserOrgDto;
import com.github.wephotos.webwork.error.Errors;
import com.github.wephotos.webwork.error.WebworkRuntimeException;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.security.auth.SecurityAuth;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.entity.UserAuth;

/**
 * @author xc
 * @date 2021-03-18 21:19
 */
@Component
public class WebworkUserAuth implements SecurityAuth {
    @Resource
    private UserService userService;
    @Resource
    private UserOrgService userOrgService;

    @Override
    public User auth(UserAuth auth) {
        com.github.wephotos.webwork.core.entity.User user = userService.getByAccount(auth.getUsername());
        if (user == null) {
            throw new WebworkRuntimeException(Errors.USERNAME_PASSWORD_ERROR.getCode(), Errors.USERNAME_PASSWORD_ERROR.getMessage());
        }
        Integer status = user.getStatus();
        // 账号禁用
        if (status.intValue() == EntityState.DISABLED.getValue().intValue()) {
            throw new WebworkRuntimeException(Errors.USER_DISABLED.getCode(), Errors.USER_DISABLED.getMessage());
        }
        // 账号删除
        if (status.intValue() == EntityState.DELETED.getValue().intValue()) {
            throw new WebworkRuntimeException(Errors.USER_DELETED.getCode(), Errors.USER_DELETED.getMessage());
        }
        // TODO 密码加密处理
        String passwordDb = user.getPassword();
        String passwordParam = auth.getPassword();
        if (passwordDb.equals(passwordParam)) {
            // 获取部门与组织信息
            UserOrgDto userOrgDto = userOrgService.getByUserId(user.getId());
            User result = new User();
            result.setId(user.getId());
            result.setName(user.getName());
            result.setDeptId(userOrgDto.getDeptId());
            result.setDeptName(userOrgDto.getDepName());
            result.setGroupId(userOrgDto.getOrgId());
            result.setGroupName(userOrgDto.getGroupName());
            return result;
        } else {
            throw new WebworkRuntimeException(Errors.USERNAME_PASSWORD_ERROR.getCode(), Errors.USERNAME_PASSWORD_ERROR.getMessage());
        }
    }
}
