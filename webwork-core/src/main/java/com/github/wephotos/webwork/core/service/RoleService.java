package com.github.wephotos.webwork.core.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.core.entity.Role;
import com.github.wephotos.webwork.core.entity.RoleResource;
import com.github.wephotos.webwork.core.entity.dto.RoleDto;
import com.github.wephotos.webwork.core.mapper.RoleMapper;
import com.github.wephotos.webwork.core.mapper.RoleResourceMapper;
import com.github.wephotos.webwork.http.EntityState;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

import static com.github.wephotos.webwork.core.utils.WebWorkUtil.uuid;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean save(RoleDto role) {
        role.setId(uuid());
        role.setStatus(1);
        // 保存角色
        roleMapper.insert(role);
        // 保存角色资源
        return saveRoleResource(role);
    }


    public boolean update(RoleDto role) {
        // 更新角色
        roleMapper.updateById(role);
        // 删除角色与资源关联
        roleResourceMapper.delete(new QueryWrapper<RoleResource>().lambda().eq(RoleResource::getRoleId, role.getId()));
        // 保存角色资源
        return saveRoleResource(role);
    }

    public boolean disable(String id) {
        Role role = new Role();
        role.setStatus(EntityState.DISABLED.getValue());
        role.setId(id);
        return SqlHelper.retBool(roleMapper.updateById(role));
    }

    public boolean enable(String id) {
        Role role = new Role();
        role.setStatus(EntityState.ENABLED.getValue());
        role.setId(id);
        return SqlHelper.retBool(roleMapper.updateById(role));
    }

    public boolean checkExistsName(Role role) {
        String roleId = role.getId();
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Role::getId).eq(Role::getName, role.getName());
        Role result = roleMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), roleId);
    }

    public boolean checkExistsCode(Role role) {
        String roleId = role.getId();
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Role::getId).eq(Role::getCode, role.getCode());
        Role result = roleMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), roleId);
    }

    public Role get(String id) {
        return roleMapper.selectById(id);
    }

    /**
     * 保存角色资源
     *
     * @param role role
     */
    private boolean saveRoleResource(RoleDto role) {
        if (ArrayUtils.isNotEmpty(role.getMenuIds())) {
            Arrays.stream(role.getMenuIds()).map(resourceId -> {
                RoleResource rs = new RoleResource();
                rs.setId(uuid());
                rs.setResourceId(resourceId);
                rs.setRoleId(role.getId());
                return rs;
            }).forEach(roleResourceMapper::insert);
        }
        return true;
    }
}
