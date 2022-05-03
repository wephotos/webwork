/** 角色数据类型 */
export declare class Role {
    /** ID */
    id?: number;
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
    parentId?: number;
    /** 创建时间 */
    createTime?: string;
    /** 其它属性 */
    [key: string]: any;
}
