import Page from '@/types/Page'
import Pageable from '@/types/Pageable'
import { Resource } from '@/types/Resource'
import { TreeNode } from '@/types/TreeNode'
import BaseRequest from './BaseRequest'

/** 资源请求 */
export class ResRequest extends BaseRequest {
    /** 查询 */
    find(id: string) {
        return super.get<Resource>(`/resource/get/${id}`)
    }

    /** 删除 */
    delete(id: string) {
        return super.get(`/resource/delete/${id}`)
    }

    /** 添加资源 */
    add(entity: Resource) {
        return super.post('/resource/add', entity)
    }

    /** 更新资源 */
    update(entity: Resource) {
        return super.post('/resource/update', entity)
    }

    /** 获取权限子节点 */
    listNodes(parentId = '') {
        return super.get<TreeNode[]>(`/resource/list-nodes?parentId=${parentId}`)
    }

    /** 分页查询 */
    pageList(pageable: Pageable) {
        return super.post<Page<Resource>>('/resource/page', pageable)
    }
}
const request = new ResRequest()
export default request
