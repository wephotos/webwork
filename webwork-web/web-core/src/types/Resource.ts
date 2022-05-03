/** 资源数据类型 */
export declare class Resource {
    /** ID */
    id?: number;
    /** 名称 */
    name?: string;
    /** 类型 */
    type?: number;
    /** 权限代码 */
    permission?: string;
    /** 编码 */
    code?: string;
    /** 父ID */
    parentId?: number;
    /** 状态 */
    status?: number;
    /** 通用权限 */
    common?: boolean;
    /** 链接 */
    url?: string;
    /** 图标 */
    icon?: string;
    /** 排序 */
    sort?: number;
    /** 备注 */
    remark?: string;
    // 其它属性
    [key: string]: any;
}
