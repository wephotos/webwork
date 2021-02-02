package com.github.wephotos.webwork.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wephotos.webwork.entity.UserRole;
import com.github.wephotos.webwork.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    public void save(List<UserRole> userRoleList) {

    }

    private int delete(String userId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserRole::getUserId, userId);
        return userRoleMapper.delete(wrapper);
    }
}
