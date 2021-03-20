package com.github.wephotos.webwork.core.service;

import com.github.wephotos.webwork.core.entity.dto.UserOrgDto;
import com.github.wephotos.webwork.core.mapper.UserOrgMapper;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class UserOrgService {
    @Resource
    private UserOrgMapper userOrgMapper;

    public UserOrgDto getByUserId(@NotNull String userId) {
        return userOrgMapper.getByUserId(userId);
    }
}
