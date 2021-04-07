/** 分页条件类型定义 */
export default class Pageable {
    // 当前页码
    curr: number = 1;
    // 每页条数
    size: number = 10;
    // 用户ID
    userId?: string;
    // 排序字段
    sortField?: string;
    // ASC DESC
    sortOrder?: string;
    // 其它条件
    condition?: any;
}