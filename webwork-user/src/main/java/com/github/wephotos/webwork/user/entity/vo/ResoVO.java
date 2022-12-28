package com.github.wephotos.webwork.user.entity.vo;

import java.io.Serializable;

import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回资源数据定义
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class ResoVO implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 资源类型
     * @see {@link NodeTypeEnum}
     */
    private Integer type;
    /**
     * 权限code
     */
    private String permission;
    /**
     * 上下级别关系
     */
    private String code;
    /**
     * 上级菜单
     */
    private Integer parentId;
    /**
     * 父节点类型
     * @see NodeTypeEnum
     */
    private Integer parentType;
    /**
     * 资源状态
     * @see {@link EntityState}
     */
    private Integer status;
    /**
     * url
     */
    private String url;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否通用权限
     */
    private boolean common;
    /**
     * 备注
     */
    private String remark;
}
