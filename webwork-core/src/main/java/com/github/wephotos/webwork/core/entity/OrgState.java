package com.github.wephotos.webwork.core.entity;

import lombok.Getter;

/**
 * @author chengzi
 * @date 2021-01-26 09:22
 */
@Getter
public enum OrgState {
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

    OrgState(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static OrgState resolve(int value) {
        for (OrgState state : OrgState.values()) {
            if (state.value == value) {
                return state;
            }
        }
        return null;
    }
}
