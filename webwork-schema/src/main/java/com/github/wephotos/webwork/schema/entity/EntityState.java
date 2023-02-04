package com.github.wephotos.webwork.schema.entity;

import lombok.Getter;

/**
 * 定义数据状态枚举
 * @author TianQi
 *
 */
@Getter
public enum EntityState {
	/**
	 * 删除
	 */
	DELETED("删除", -1),
    /**
     * 正常
     */
    NORMAL("启用", 1),
    /**
     * 禁用
     */
    DISABLED("禁用", 0);


    /**
     * 名称
     */
    private final String name;
    /**
     * 状态
     */
    private final Integer value;

    EntityState(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static EntityState resolve(int value) {
        for (EntityState state : EntityState.values()) {
            if (state.value == value) {
                return state;
            }
        }
        return null;
    }
    
    public boolean is(Integer value) {
    	if(value == null) {
    		return false;
    	}
    	return this.value.equals(value);
    }
}
