package com.github.wephotos.webwork.http;

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
     * 起始下标
     */
    private int start = 0;
    /**
     * 每页条数
     */
    private int size = 15;

    /**
     * 用户ID
     */
    private String userId;
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
}
