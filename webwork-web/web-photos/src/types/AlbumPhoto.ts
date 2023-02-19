/**
 * 照片数据类型定义
 */
export declare class AlbumPhoto {
    id?: number;
    name?: string;
    albumId?: number;
    photoSize?: number;
    objectName?: string;
    description?: string;
    thumbObjectName?: string;
    createTime?: string;
    updateTime?: string;
    // 其它属性
    [key: string]: any;
}
