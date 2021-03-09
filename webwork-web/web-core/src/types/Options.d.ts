import { Component } from '@vue/runtime-core'

/**
 * 组件选项类型
 */
declare namespace Options {
    // Toast
    interface Toast {
        // 消息
        msg: string;
        // 显示时长
        seconds?: number;
    }
    // 提示框
    interface Alert {
        // 标题
        title?: string;
        // 内容
        content: string;
        // 遮罩
        lock?: boolean;
        // 确定回调
        ok?: () => void;
        // 关闭回调
        close?: () => void;
    }
    // 对话框
    interface Dialog {
        // 标题
        title?: string;
        // 内容
        content: {
            component: Component;
            props?: Record<string, any>;
            handle?: boolean;
        };
        // 遮罩
        lock?: boolean;
        max?: boolean;
        height?: number | string;
        width?: number | string;
        top?: number | string;
        left?: number | string;
        // 确定回调
        ok?: () => void;
        // 关闭回调
        close?: () => void;
    }
}
