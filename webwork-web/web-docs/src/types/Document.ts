/**
 * 文档类型定义
 */
export declare class Document {
    id?: number;
    open?: boolean;
    state?: number;
    title?: string;
    subtitle?: string;
    content?: string;
    contentType?: number;
    versionNo?: number;
    createUser?: string;
    updateUser?: string;
    createTime?: string;
    updateTime?: string;
    // 其它属性
    [key: string]: any;
}