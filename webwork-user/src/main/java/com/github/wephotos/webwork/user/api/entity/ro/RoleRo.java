package com.github.wephotos.webwork.user.api.entity.ro;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回的角色数据类型定义
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
public class RoleRo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
    
	/**
	 * 所属应用
	 */
	private String app;
	
}
