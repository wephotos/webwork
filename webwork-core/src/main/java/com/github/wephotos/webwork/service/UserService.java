package com.github.wephotos.webwork.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.entity.User;
import com.github.wephotos.webwork.entity.UserRole;
import com.github.wephotos.webwork.entity.UserState;
import com.github.wephotos.webwork.entity.dto.UserDto;
import com.github.wephotos.webwork.mapper.UserMapper;
import com.github.wephotos.webwork.mapper.UserRoleMapper;
import com.github.wephotos.webwork.utils.WebWorkUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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


    public User get(String id) {
        return userMapper.selectById(id);
    }

    public boolean create(UserDto user) {
        user.setId(WebWorkUtil.uuid());
        user.setStatus(UserState.ENABLED.getValue());
        user.setCreateTime(new Date());
        // 新增用户
        userMapper.insert(user);
        // 新增用户角色关联
        saveUserRole(user);
        return true;
    }


    public boolean update(UserDto user) {
        user.setPassword(null);
        // 修改用户
        userMapper.updateById(user);
        // 删除用户与角色关联
        userRoleMapper.delete(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, user.getId()));
        // 新增用户角色关联
        saveUserRole(user);
        return true;
    }

    public int disable(String id) {
        User user = new User();
        user.setStatus(UserState.DISABLED.getValue());
        user.setId(id);
        return userMapper.updateById(user);
    }

    public int enable(String id) {
        User user = new User();
        user.setStatus(UserState.ENABLED.getValue());
        user.setId(id);
        return userMapper.updateById(user);
    }


    public boolean checkLoginNameUnique(String loginName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId).eq(User::getLoginName, loginName);
        return userMapper.selectCount(wrapper) == 0;
    }

    public boolean checkPhoneUnique(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getLoginName, User::getPhone).eq(User::getPhone, user.getPhone());
        User result = userMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), user.getId());
    }

    public boolean checkEmailUnique(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getLoginName, User::getEmail).eq(User::getEmail, user.getEmail().trim());
        User result = userMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), user.getId());
    }


    public boolean resetUserPwd(User user) {
        // TODO 处理密码
        //user.setPassword("");
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
}
