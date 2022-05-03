/**
 * 树节点类型定义
 */
export declare class TreeNode {
    id: number;
    name: string;
    code: string;
    type: number;
    parentId?: number;
    children: TreeNode[]
}