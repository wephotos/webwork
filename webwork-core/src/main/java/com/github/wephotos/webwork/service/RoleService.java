package com.github.wephotos.webwork.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.entity.Role;
import com.github.wephotos.webwork.entity.RoleState;
import com.github.wephotos.webwork.mapper.RoleMapper;
import com.github.wephotos.webwork.utils.WebWorkUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    public boolean create(Role role) {
        role.setId(WebWorkUtil.uuid());
        role.setStatus(1);
        return SqlHelper.retBool(roleMapper.insert(role));
    }

    public boolean update(Role role) {
        return SqlHelper.retBool(roleMapper.updateById(role));
    }

    public boolean disable(String id) {
        Role role = new Role();
        role.setStatus(RoleState.DISABLED.getValue());
        role.setId(id);
        return SqlHelper.retBool(roleMapper.updateById(role));
    }

    public boolean enable(String id) {
        Role role = new Role();
        role.setStatus(RoleState.ENABLED.getValue());
        role.setId(id);
        return SqlHelper.retBool(roleMapper.updateById(role));
    }

    public boolean checkRoleNameUnique(Role role) {
        String roleId = role.getId();
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Role::getId).eq(Role::getName, role.getName());
        Role result = roleMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), roleId);
    }

    public boolean checkRoleCodeUnique(Role role) {
        String roleId = role.getId();
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Role::getId).eq(Role::getCode, role.getCode());
        Role result = roleMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), roleId);
    }

    public Role get(String id) {
        return roleMapper.selectById(id);
    }

}
