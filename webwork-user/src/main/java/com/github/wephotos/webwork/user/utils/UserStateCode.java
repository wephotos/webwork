package com.github.wephotos.webwork.user.utils;

import com.github.wephotos.webwork.schema.exception.StateCode;

/**
 * 用户模块错误码定义
 * @author TQ
 *
 */
public final class UserStateCode {
    /**
     * USER相关
     */
    public static final StateCode USER_NAME_EXIST = new StateCode(100101, "登录名重复");
    public static final StateCode USER_PHONE_EXIST = new StateCode(100102, "手机号重复");
    public static final StateCode USER_MAIL_EXIST = new StateCode(100103, "邮箱重复");

    /**
     * ROLE相关
     */
    public static final StateCode ROLE_NAME_EXIST = new StateCode(100201, "角色名重复");
    public static final StateCode ROLE_CODE_EXIST = new StateCode(100202, "角色编码重复");
    /**
     * RESOURCE相关
     */
    public static final StateCode RESOURCE_NAME_EXIST = new StateCode(100301, "资源名重复");
    public static final StateCode RESOURCE_CODE_EXIST = new StateCode(100302, "资源编码重复");
    
    /**
     * 登录相关
     */
    public static final StateCode USER_DISABLED = new StateCode(101301, "用户被禁用");
    public static final StateCode USER_NOT_EXISTS = new StateCode(101302, "用户不存在");
    public static final StateCode USER_PASSWORD_ERROR = new StateCode(101303, "密码错误");
}
