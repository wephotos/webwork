/** 分页数据类型定义 */
export default interface Page<T> {
    // 条数
    count: number;
    // 数据
    data: T[];
}
