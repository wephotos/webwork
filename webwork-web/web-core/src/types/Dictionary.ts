/** 数据字典数据类型 */
export declare class Dictionary {
    /** ID */
    id?: number;
    /** 名称 */
    name?: string;
    /** 字典值 */
    value?: string;
    /** 编码 */
    code?: string;
    /** 父ID */
    parentId?: number;
    /** 状态 */
    status?: number;
    /** 排序 */
    sort?: number;
    /** 创建时间 */
    createTime?: string;

    /** 其它属性 */
    [key: string]: any;
}
