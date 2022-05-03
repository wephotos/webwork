package com.github.wephotos.webwork.user.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 角色
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role")
public class Role {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 上级(单位或应用)
     */
    private Integer parentId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     *备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
}
