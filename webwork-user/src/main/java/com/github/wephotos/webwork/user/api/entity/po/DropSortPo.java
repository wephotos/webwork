package com.github.wephotos.webwork.user.api.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 拖动排序参数
 * @author xc
 * @date 2021-04-14 21:32
 */

@Getter
@Setter
@ToString
public class DropSortPo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 父节点
	 */
	private String parent;
	/**
	 * 当前序号
	 */
    private Integer sort;
    /**
     * 目录序号
     */
    private Integer targetSort;
}
