/**
 * 树节点类型定义
 */
export declare class TreeNode {
    // 节点ID
    id: string;
    // 原始ID
    rawId: number;
    // 节点名称
    name: string;
    // 节点代码
    code: string;
    // 节点类型
    type: number;
    // 父节点ID
    parentId?: string;
    // 子节点集合
    children: TreeNode[]
}
