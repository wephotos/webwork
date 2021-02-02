package com.github.wephotos.webwork.util;

import lombok.Getter;

/**
 * @author chengzi
 */

@Getter
public enum Errors {
    /**
     * USER相关
     */
    USER_NAME_EXIST(100101, "用户名称重复"),
    USER_PHONE_EXIST(100102, "手机号重复"),
    USER_MAIL_EXIST(100103, "邮箱重复"),

    /**
     * ROLE相关
     */
    ROLE_NAME_EXIST(100201, "角色名称重复"),
    ROLE_CODE_EXIST(100202, "角色Code重复"),
    /**
     * RESOURCE相关
     */
    RESOURCE_NAME_EXIST(100301, "资源名称重复"),
    RESOURCE_CODE_EXIST(100301, "资源Code重复");

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
