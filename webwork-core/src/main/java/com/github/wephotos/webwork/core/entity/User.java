package com.github.wephotos.webwork.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 登录名
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 中文名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 状态 启用：1，0：禁用，2：删除
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 置顶时间
     */
    private Date topTime;
}
