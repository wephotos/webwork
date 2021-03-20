package com.github.wephotos.webwork.core.service;

import com.github.wephotos.webwork.core.entity.dto.UserOrgDto;
import com.github.wephotos.webwork.error.Errors;
import com.github.wephotos.webwork.error.WebworkException;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.security.auth.SecurityAuth;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.entity.UserAuth;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
            throw new WebworkException(Errors.USERNAME_NOT_FOUND.getMessage(), Errors.USERNAME_NOT_FOUND.getCode());
        }
        Integer status = user.getStatus();
        if (status.intValue() == EntityState.DISABLED.getValue().intValue()) {
            throw new WebworkException(Errors.USER_DISABLED.getMessage(), Errors.USER_DISABLED.getCode());
        }
        if (status.intValue() == EntityState.DELETED.getValue().intValue()) {
            throw new WebworkException(Errors.USER_DELETED.getMessage(), Errors.USER_DELETED.getCode());
        }
        // TODO 密码加密处理
        String password_ = user.getPassword();
        String password = auth.getPassword();
        if (password_.equals(password)) {
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
            throw new WebworkException(Errors.USER_PASSWORD_ERROR.getMessage(), Errors.USER_PASSWORD_ERROR.getCode());
        }
    }
}