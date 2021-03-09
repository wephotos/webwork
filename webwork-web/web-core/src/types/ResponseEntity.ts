/**
 * 接口响应数据定义
 */
export declare class ResponseEntity<T> {
    /** 错误代码 */
    code: number;
    /** 错误消息 */
    msg: string;
    /** 返回数据 */
    data: T;
}
