package com.github.wephotos.webwork.schema.entity;

import lombok.Getter;

/**
 * 数据状态枚举
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
     * 编码
     */
    private final Integer code;

    EntityState(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public static EntityState resolve(int code) {
        for (EntityState state : EntityState.values()) {
            if (state.code == code) {
                return state;
            }
        }
        return null;
    }
    
    public boolean is(Integer code) {
    	return this.code.equals(code);
    }
}
