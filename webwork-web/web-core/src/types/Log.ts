/** 日志数据类型定义 */
export declare class Log {
    /** ID */
    id: number;
    /** 名称 */
    name: string;
    /** 追踪ID */
    traceId: string;
    /** 用户名 */
    username?: string;
    /** 请求路径 */
    url?: string;
    /** 日志级别 */
    level: string;
    /** 客户端信息 */
    clientInfo?: string;
    /** 客户端主机 */
    clientHost?: string;
    /** 日志内容 */
    content?:string;
    /** 错误日志的堆栈信息 */
    stackTrace?:string;
    /** 日志时间 */
    createTime?:string;

    /** 其它属性 */
    [key: string]: any;
}