/**
 * 组件参数类型定义
 */
declare namespace Options {
    import { Component } from '@vue/runtime-core'
    // 吐司
    export type Toast = {
        // 消息
        msg: string;
        // 显示时长
        seconds?: number;
    }
    // 提示框
    export type Alert = {
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
    export type Dialog = {
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
