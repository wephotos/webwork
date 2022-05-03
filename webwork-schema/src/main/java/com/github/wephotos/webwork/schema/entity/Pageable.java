package com.github.wephotos.webwork.schema.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用于分页的参数
 *
 * @author TQ
 */
@Getter
@Setter
@ToString
public class Pageable<T> {
    /**
     * 当前页码
     */
    private int curr = 1;
    /**
     * 每页条数
     */
    private int size = 15;
    /**
     * 查询条件
     */
    private T condition;
    /**
     * 排序字段
     */
    private String sortField;
    /**
     * ASC DESC
     */
    private String sortOrder;
    
    /**
     * 获取当前起始下标
     * @return 分页开始下标
     */
    public int getStart() {
    	return (this.curr - 1) * this.size;
    }
}
