package com.github.wephotos.webwork.core.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 角色资源关联表
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user_org")
public class UserOrg {
    /**
     * 主键
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 组织id
     */
    private String orgId;
    /**
     * 部门id
     */
    private String deptId;
    
    /**
     * 部门内排序
     */
    private Integer userSort;
    /**
     * 是否主部门
     */
    private Boolean mainDept;

    /**
     * 置顶时间
     */
    private Date topTime;
}
