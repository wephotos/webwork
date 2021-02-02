package com.github.wephotos.webwork.entity.sys;

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
    private String id;
    private String loginName;
    private String password;
    private Integer sort;
    private String name;
    private String email;
    private String phone;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
