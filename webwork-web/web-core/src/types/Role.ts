/** 角色数据类型 */
export declare class Role {
    /** ID */
    id?: string;
    /** 名称 */
    name?: string;
    /** 编码 */
    code?: string;
    /** 状态 */
    status?: number;
    /** 排序 */
    sort?: number;
    /** 备注 */
    remark?: string;
    /** 父级ID */
    parent_id?: string;
    /** 创建时间 */
    create_time?: string;
    /** 其它属性 */
    [key: string]: any;
}
