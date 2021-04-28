package com.github.wephotos.webwork.core.controller;


import com.github.wephotos.webwork.core.entity.User;
import com.github.wephotos.webwork.core.entity.UserVo;
import com.github.wephotos.webwork.core.entity.dto.UserDto;
import com.github.wephotos.webwork.core.service.UserService;
import com.github.wephotos.webwork.core.utils.ValidationUtil;
import com.github.wephotos.webwork.error.Errors;
import com.github.wephotos.webwork.http.Page;
import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.http.RestObject;
import com.github.wephotos.webwork.logging.LoggerFactory;
import org.slf4j.Logger;
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
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
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
        ValidationUtil.isTrue(Errors.USER_NAME_EXIST, userService.checkExistsAccount(user.getAccount()));
        ValidationUtil.isTrue(Errors.USER_PHONE_EXIST, userService.checkExistsPhone(user));
        ValidationUtil.isTrue(Errors.USER_MAIL_EXIST, userService.checkExistsEmail(user));
        boolean bool = userService.create(user);
        return RestObject.builder().data(bool).build();
    }

    @PostMapping("/update")
    public RestObject update(@RequestBody UserDto user) {
        ValidationUtil.isTrue(Errors.USER_PHONE_EXIST, userService.checkExistsPhone(user));
        ValidationUtil.isTrue(Errors.USER_MAIL_EXIST, userService.checkExistsEmail(user));
        boolean update = userService.update(user);
        return RestObject.builder().data(update).build();
    }

    @PostMapping("/page")
    public Page<UserVo> page(@RequestBody Pageable<UserVo> pageable) {
        return userService.page(pageable);
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

    @PostMapping("/password")
    public RestObject password(@RequestBody User user) {
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
        log.debug("查询用户");
        return RestObject.builder().data(user).build();
    }

    /**
     * 置顶用户
     *
     * @param id 用户id
     * @return RestObject
     */
    @PostMapping("/top/{id}")
    public RestObject top(@PathVariable String id) {
        userService.top(id);
        return RestObject.builder().data(id).build();
    }

    /**
     * 校验用户名
     */
    @GetMapping("/check-exists-account")
    public RestObject checkExistsAccount(String account) {
        boolean bool = userService.checkExistsAccount(account);
        return RestObject.builder().data(bool).build();
    }

    /**
     * 校验手机号码
     */
    @GetMapping("/check-exists-phone")
    public RestObject checkExistsPhone(User user) {
        boolean bool = userService.checkExistsPhone(user);
        return RestObject.builder().data(bool).build();
    }

    /**
     * 校验email邮箱
     */
    @GetMapping("/check-exists-email")
    public RestObject checkExistsEmail(User user) {
        boolean bool = userService.checkExistsEmail(user);
        return RestObject.builder().data(bool).build();
    }
}
