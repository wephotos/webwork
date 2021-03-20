package com.github.wephotos.webwork.error;

import com.github.wephotos.webwork.http.RestObject;
import lombok.Getter;

/**
 * @author chengzi
 */

@Getter
public enum Errors {
    /**
     * USER相关
     */
    USER_NAME_EXIST(100101, "登录名重复"),
    USER_PHONE_EXIST(100102, "手机号重复"),
    USER_MAIL_EXIST(100103, "邮箱重复"),

    /**
     * ROLE相关
     */
    ROLE_NAME_EXIST(100201, "角色名称重复"),
    ROLE_CODE_EXIST(100202, "角色code重复"),
    /**
     * RESOURCE相关
     */
    RESOURCE_NAME_EXIST(100301, "资源名称重复"),
    RESOURCE_CODE_EXIST(100301, "资源code重复"),
    /**
     * 登录相关
     */
    USERNAME_NOT_FOUND(1004001, "用户不存在"),
    USER_DISABLED(1004002, "用户被禁用"),
    USER_DELETED(1004003, "用户被删除"),
    USER_PASSWORD_ERROR(1004004, "密码错误");

    private final Integer code;
    private final String message;

    Errors(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestObject overrideMsg(String msg) {
        return RestObject.builder().code(getCode()).msg(msg).build();
    }
}
