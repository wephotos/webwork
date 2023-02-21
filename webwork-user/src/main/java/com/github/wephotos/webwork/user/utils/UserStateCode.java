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
    public static final StateCode USER_ACCOUNT_EXIST = new StateCode(100101, "用户账号已存在");
    public static final StateCode USER_PHONE_EXIST = new StateCode(100102, "用户手机号已存在");
    public static final StateCode USER_MAIL_EXIST = new StateCode(100103, "用户邮箱已存在");
    public static final StateCode USER_PROPS_NOT_NULL = new StateCode(100104, "用户账号、姓名、密码不能为空");
    
    /**
     * ROLE相关
     */
    public static final StateCode ROLE_NAME_EXIST = new StateCode(100201, "角色名称已存在");
    public static final StateCode ROLE_CODE_EXIST = new StateCode(100202, "角色编码已存在");
    /**
     * RESOURCE相关
     */
    public static final StateCode RESOURCE_NAME_EXIST = new StateCode(100301, "资源名称已存在");
    public static final StateCode RESOURCE_CODE_EXIST = new StateCode(100302, "资源编码已存在");
    
    /**
     * 登录相关
     */
    public static final StateCode USER_DISABLED = new StateCode(101301, "用户被禁用");
    public static final StateCode USER_NOT_EXISTS = new StateCode(101302, "用户不存在");
    public static final StateCode USER_PASSWORD_ERROR = new StateCode(101303, "密码错误");
}
