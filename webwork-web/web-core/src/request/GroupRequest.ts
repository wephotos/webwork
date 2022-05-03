import { Group } from '@/types/Group'
import { TreeNode } from '@/types/TreeNode'
import BaseRequest from './BaseRequest'

/** 组织机构接口请求 */
export class GroupRequest extends BaseRequest {
    /** 根据ID查询组织 */
    find(id: number) {
        return super.get<Group>(`/organization/get/${id}`)
    }

    /** 根据ID删除组织 */
    delete(id: number) {
        return super.get(`/organization/delete/${id}`)
    }

    /** 添加组织机构 */
    add(data: Group) {
        return super.post('/organization/add', data)
    }

    /** 更新组织机构 */
    update(data: Group) {
        return super.post('/organization/update', data)
    }

    /** 查询子级  */
    children(parentId?: number) {
        return super.get<Group[]>('/organization/children' + (parentId ? `?parentId=${parentId}` : ''))
    }
    /** 当前组织节点树 */
    deepTreeNodes() {
        return super.get<TreeNode[]>('/organization/deep-tree-nodes')
    }
}

const request = new GroupRequest()
export default request
