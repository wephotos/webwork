/**
 * 树节点类型定义
 */
export declare class TreeNode {
    id: string;
    rawId: number; // 原始的数据ID
    name: string;
    code: string;
    type: number;
    sort?: number;
    parentId?: string;
    children: TreeNode[]
}
