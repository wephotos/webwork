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

    /** 加载当前用户的单位树数据 */
    loadGroupNodes() {
        return super.get<TreeNode[]>('/organization/load-nodes')
    }

    /** 查询组织机构子节点  */
    children(parentId?: number) {
        return super.get<TreeNode[]>('/organization/children' + (parentId ? `?parentId=${parentId}` : ''))
    }

    /** 拖动排序 */
    dropSort(params: {parent: any; sort: number; targetSort: number}) {
        return super.post<boolean>('/organization/drop-sort', params)
    }
}

const request = new GroupRequest()
export default request
