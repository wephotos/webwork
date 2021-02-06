package com.github.wephotos.webwork.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 资源菜单
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_resource")
public class Resource {
    private String id;
    private String name;
    private Integer type;
    private String permission;
    private String code;
    private String parentId;
    private Integer status;
    private String url;
    private String icon;
    private Integer sort;
    private String remark;
}
