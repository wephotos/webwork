package com.github.wephotos.webwork.schema.entity;

import lombok.Getter;

/**
 * @author xc
 * @date 2021-03-20 11:34
 */
@Getter
public enum EntityState {
	/**
	 * 删除
	 */
	DELETED("删除", -1),
    /**
     * 启用
     */
    ENABLED("启用", 1),
    /**
     * 禁用
     */
    DISABLED("禁用", 0);


    /**
     * 状态名
     */
    private final String name;
    /**
     * 状态 启用：1，0：禁用，2：删除
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
