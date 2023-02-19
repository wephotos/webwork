/**
 * 相册数据类型定义
 */
export declare class Album {
    id?: number;
    name?: string;
    cover?: string;
    usageSpace?: number;
    photoCount?: number;
    maxPhotoCount?: number;
    createTime?: string;
    updateTime?: string;
    // 其它属性
    [key: string]: any;
}
