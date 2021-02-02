package com.github.wephotos.webwork.entity;

import lombok.Getter;

/**
 * @author chengzi
 * @date 2021-01-26 09:22
 */
@Getter
public enum UserState {
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

    UserState(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static UserState resolve(int value) {
        for (UserState state : UserState.values()) {
            if (state.value == value) {
                return state;
            }
        }
        return null;
    }
}
