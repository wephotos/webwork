package com.github.wephotos.webwork.core.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
public class UserVo extends User {
    /**
     * 部门名称
     */
    private String depName;
    /**
     * 部门id
     */
    private String depId;
    /**
     * 组织名称
     */
    private String orgName;
}
