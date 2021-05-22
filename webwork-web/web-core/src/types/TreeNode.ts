/**
 * 树节点类型定义
 */
export declare class TreeNode {
    id: string;
    name: string;
    code: string;
    type: number;
    parentId?: string;
    children: TreeNode[]
}