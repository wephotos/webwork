/**
 * 用户信息数据类型
 */
export declare class User {
    /**
     * 姓名
     */
    name?: string;
    /**
     * 职位
     */
    post?: string;
    /**
     * 头像
     */
    avatar?: string;
    /**
     * 账号
     */
    account?: string;
    /**
     * 邮箱
     */
    email?: string;
    /**
     * 账号
     */
    phone?: string;
    /**
     * 其它属性
     */
    [key: string]: any;
}