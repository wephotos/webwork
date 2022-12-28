import Page from '@/types/Page'
import Pageable from '@/types/Pageable'
import { Resource } from '@/types/Resource'
import { TreeNode } from '@/types/TreeNode'
import { TreeNodeQuery } from '@/types/TreeNodeQuery'
import BaseRequest from './BaseRequest'

/** 资源请求 */
export class ResRequest extends BaseRequest {
    /** 查询 */
    find(id: number) {
        return super.get<Resource>(`/resource/get/${id}`)
    }

    /** 删除 */
    delete(id: number) {
        return super.get(`/resource/delete/${id}`)
    }

    /** 添加资源 */
    add(entity: Resource) {
        return super.post<Resource>('/resource/add', entity)
    }

    /** 更新资源 */
    update(entity: Resource) {
        return super.post('/resource/update', entity)
    }
    
    /** 分页查询 */
    pageList(pageable: Pageable) {
        return super.post<Page<Resource>>('/resource/page', pageable)
    }

    /** 获取权限子节点 */
    listNodes(params: TreeNodeQuery) {
        return super.post<TreeNode[]>('/resource/list-nodes', params)
    }

    /** 获取全部树节点，包含自己 */
    deepListNodes(params: TreeNodeQuery) {
        return super.post<TreeNode[]>('/resource/load-all-nodes', params)
    }

    /** 获取角色下全部资源 */
    listByRoleId(roleId: number) {
        return super.get<Resource[]>(`/resource/list-by-role?roleId=${roleId}`)
    }
}
const request = new ResRequest()
export default request
