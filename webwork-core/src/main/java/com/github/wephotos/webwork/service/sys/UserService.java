package com.github.wephotos.webwork.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.entity.sys.User;
import com.github.wephotos.webwork.entity.sys.UserState;
import com.github.wephotos.webwork.mapper.sys.UserMapper;
import com.github.wephotos.webwork.util.WebWorkUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private UserMapper userMapper;

    public User get(String id) {
        return userMapper.selectById(id);
    }

    public String create(User user) {
        user.setId(WebWorkUtil.uuid());
        user.setStatus(UserState.ENABLED.getValue());
        user.setCreateTime(new Date());
        userMapper.insert(user);
        return user.getId();
    }


    public boolean update(User user) {
        // 更新用户不更新用户密码
        user.setPassword(null);
        return userMapper.updateById(user) > 0;
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

    public User query(String loginName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getLoginName, loginName);
        return userMapper.selectOne(wrapper);
    }


    public boolean checkLoginNameUnique(String loginName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id").lambda().eq(User::getLoginName, loginName.trim());
        return SqlHelper.retBool(userMapper.selectCount(wrapper));
    }

    public boolean checkPhoneUnique(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "login_name", "phone").lambda().eq(User::getPhone, user.getPhone().trim());
        User result = userMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), user.getId());
    }

    public boolean checkEmailUnique(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "login_name", "email").lambda().eq(User::getEmail, user.getEmail().trim());
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

    public void saveUserRole(User user) {

    }
}
