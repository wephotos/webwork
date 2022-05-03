/** 用户数据类型 */
export declare class User {
    /** ID */
    id?: number;
    /** 姓名 */
    name?: string;
    // 头像
    avatar?: string;
    /** 账号 */
    account?: string;
    /** 密码 */
    password?: string;
    /** 排序 */
    sort?: number;
    /** 邮箱 */
    email?: string;
    /** 手机 */
    phone?: string;
    /** 状态 */
    status?: number;
    /** 创建时间 */
    createTime?: string;
    /** 更新时间 */
    updateTime?: string;
    /** 部门ID */
    deptId?: number;
    /** 部门名称 */
    deptName?: string;
    /** 单位ID */
    orgId?: number;
    /** 单位名称 */
    orgName?: string;
    // 其它属性
    [key: string]: any;
}
