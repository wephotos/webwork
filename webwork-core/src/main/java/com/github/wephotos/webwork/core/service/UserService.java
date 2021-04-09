package com.github.wephotos.webwork.core.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.entity.User;
import com.github.wephotos.webwork.core.entity.UserOrg;
import com.github.wephotos.webwork.core.entity.UserRole;
import com.github.wephotos.webwork.core.entity.dto.UserDto;
import com.github.wephotos.webwork.core.mapper.OrganizationMapper;
import com.github.wephotos.webwork.core.mapper.UserMapper;
import com.github.wephotos.webwork.core.mapper.UserOrgMapper;
import com.github.wephotos.webwork.core.mapper.UserRoleMapper;
import com.github.wephotos.webwork.core.utils.WebWorkUtil;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private OrganizationMapper organizationMapper;
    @Resource
    private UserOrgMapper userOrgMapper;


    public User get(String id) {
        return userMapper.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean create(UserDto user) {
        user.setId(WebWorkUtil.uuid());
        user.setStatus(EntityState.ENABLED.getValue());
        user.setCreateTime(new Date());
        // 新增用户
        userMapper.insert(user);
        // 新增用户角色关联
        saveUserRole(user);
        // 新增用户部门
        saveUserDept(user);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(UserDto user) {
        user.setPassword(null);
        // 修改用户
        userMapper.updateById(user);
        // 删除用户与角色关联
        userRoleMapper.delete(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, user.getId()));
        // 新增用户角色关联
        saveUserRole(user);
        // 删除用户与部门关联
        userOrgMapper.delete(new QueryWrapper<UserOrg>().lambda().eq(UserOrg::getUserId, user.getId()));
        // 新增用户部门关联
        saveUserDept(user);
        return true;
    }

    public int disable(String id) {
        User user = new User();
        user.setStatus(EntityState.DISABLED.getValue());
        user.setId(id);
        return userMapper.updateById(user);
    }

    public int enable(String id) {
        User user = new User();
        user.setStatus(EntityState.ENABLED.getValue());
        user.setId(id);
        return userMapper.updateById(user);
    }

    public com.github.wephotos.webwork.http.Page<User> page(Pageable<User> pageable) {
        User condition = pageable.getCondition();
        Page<User> page = new Page<>(pageable.getCurr(), pageable.getSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 查询条件
        if (condition != null) {
            wrapper.like(StringUtils.isNotEmpty(condition.getAccount()), "account", condition.getAccount());
            wrapper.like(StringUtils.isNotEmpty(condition.getName()), "name", condition.getName());
            wrapper.like(StringUtils.isNotEmpty(condition.getEmail()), "email", condition.getEmail());
            wrapper.like(StringUtils.isNotEmpty(condition.getPhone()), "phone", condition.getPhone());
            wrapper.eq(condition.getStatus() != null, "status", condition.getStatus());
        }
        // 传入排序字段
        if (StringUtils.isNotBlank(pageable.getSortField())) {
            String order = pageable.getSortOrder();
            wrapper.orderBy(true, "asc".equals(order), pageable.getSortField());
        }
        IPage<User> res = userMapper.page(page, wrapper);
        com.github.wephotos.webwork.http.Page<User> result = new com.github.wephotos.webwork.http.Page<>();
        result.setCount(res.getTotal());
        result.setData(res.getRecords());
        return result;
    }

    public boolean checkExistsAccount(String account) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId).eq(User::getAccount, account.trim());
        return userMapper.selectCount(wrapper) == 0;
    }

    public boolean checkExistsPhone(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getAccount, User::getPhone).eq(User::getPhone, user.getPhone().trim());
        User result = userMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), user.getId());
    }

    public boolean checkExistsEmail(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getAccount, User::getEmail).eq(User::getEmail, user.getEmail().trim());
        User result = userMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), user.getId());
    }


    public boolean resetUserPwd(User user) {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setPassword(user.getPassword());
        return SqlHelper.retBool(userMapper.updateById(newUser));
    }

    public void saveUserRole(UserDto userDto) {
        String userId = userDto.getId();
        List<String> roles = userDto.getRoles();
        roles.stream().map(roleId -> {
            UserRole ur = new UserRole();
            ur.setId(WebWorkUtil.uuid());
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            return ur;
        }).forEach(userRoleMapper::insert);
    }

    private void saveUserDept(UserDto user) {
        String depId = user.getDepId();
        if (StringUtils.isNotBlank(depId)) {
            // 根据部门id查找单位
            Organization org = getOrg(depId);
            UserOrg userOrg = new UserOrg();
            userOrg.setId(WebWorkUtil.uuid());
            userOrg.setUserId(user.getId());
            userOrg.setDeptId(depId);
            userOrg.setOrgId(org.getId());
            userOrgMapper.insert(userOrg);
        }
    }

    private Organization getOrg(String orgId) {
        Organization org = organizationMapper.selectById(orgId);
        if (org != null && org.getType() != 1) {
            Organization parent = getOrg(org.getParentId());
            // 找到单位
            if (parent.getStatus() == 1) {
                return parent;
            }
        }
        return org;
    }

    public User getByAccount(String account) {
        return userMapper.getByAccount(account);
    }
}
