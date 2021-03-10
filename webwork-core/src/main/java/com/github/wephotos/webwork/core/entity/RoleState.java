package com.github.wephotos.webwork.core.entity;

import lombok.Getter;

/**
 * @author chengzi
 * @date 2021-01-26 09:22
 */
@Getter
public enum RoleState {
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
     * 状态值
     */
    private final Integer value;

    RoleState(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static RoleState resolve(int value) {
        for (RoleState state : RoleState.values()) {
            if (state.value == value) {
                return state;
            }
        }
        return null;
    }
}
