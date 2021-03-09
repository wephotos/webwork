package com.github.wephotos.webwork.core.controller;


import com.github.wephotos.webwork.core.entity.User;
import com.github.wephotos.webwork.core.entity.dto.UserDto;
import com.github.wephotos.webwork.core.service.UserService;
import com.github.wephotos.webwork.core.utils.Errors;
import com.github.wephotos.webwork.core.utils.RestObject;
import com.github.wephotos.webwork.core.utils.ValidationUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户管理
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 新增
     *
     * @param user user
     * @return RestObject
     */
    @PostMapping("/save")
    public RestObject save(@RequestBody UserDto user) {
        ValidationUtil.isTrue(Errors.USER_NAME_EXIST, userService.checkLoginNameUnique(user.getLoginName()));
        ValidationUtil.isTrue(Errors.USER_PHONE_EXIST, userService.checkPhoneUnique(user));
        ValidationUtil.isTrue(Errors.USER_MAIL_EXIST, userService.checkEmailUnique(user));
        boolean bool = userService.create(user);
        return RestObject.builder().data(bool).build();
    }

    @PostMapping("/update")
    public RestObject update(@RequestBody UserDto user) {
        ValidationUtil.isTrue(Errors.USER_PHONE_EXIST, userService.checkPhoneUnique(user));
        ValidationUtil.isTrue(Errors.USER_MAIL_EXIST, userService.checkEmailUnique(user));
        boolean update = userService.update(user);
        return RestObject.builder().data(update).build();
    }

    /**
     * 禁用用户
     *
     * @param id 用户编号
     * @return RestObject
     */
    @PostMapping("/disable/{id}")
    public RestObject disable(@PathVariable String id) {
        int result = userService.disable(id);
        return RestObject.builder().data(result).build();
    }

    /**
     * 启用用户
     *
     * @param id 用户编号
     * @return RestObject
     */
    @PostMapping("/enable/{id}")
    public RestObject enable(@PathVariable String id) {
        int result = userService.enable(id);
        return RestObject.builder().data(result).build();
    }

    @PostMapping("/resetPwd")
    public RestObject resetPwd(@RequestBody User user) {
        if (userService.resetUserPwd(user)) {
            // 重新登录,清除缓存...
        }
        return RestObject.builder().build();
    }

    /**
     * 根据用户编号获取用户
     *
     * @param id 用户编号
     * @return RestObject
     */
    @GetMapping("/get/{id}")
    public RestObject get(@PathVariable String id) {
        User user = userService.get(id);
        return RestObject.builder().data(user).build();
    }

    /**
     * 校验用户名
     */
    @GetMapping("/checkLoginNameUnique")
    public RestObject checkLoginNameUnique(String loginName) {
        boolean bool = userService.checkLoginNameUnique(loginName);
        return RestObject.builder().data(bool).build();
    }

    /**
     * 校验手机号码
     */
    @GetMapping("/checkPhoneUnique")
    public RestObject checkPhoneUnique(User user) {
        boolean bool = userService.checkPhoneUnique(user);
        return RestObject.builder().data(bool).build();
    }

    /**
     * 校验email邮箱
     */
    @GetMapping("/checkEmailUnique")
    public RestObject checkEmailUnique(User user) {
        boolean bool = userService.checkEmailUnique(user);
        return RestObject.builder().data(bool).build();
    }
}
