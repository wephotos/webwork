import Page from '@/types/Page'
import Pageable from '@/types/Pageable'
import { Role } from '@/types/Role'
import { TreeNode } from '@/types/TreeNode'
import BaseRequest from './BaseRequest'

/** 资源请求 */
export class RoleRequest extends BaseRequest {
    /** 查询 */
    find(id: string) {
        return super.get<Role>(`/role/get/${id}`)
    }

    /** 删除 */
    delete(id: string) {
        return super.get(`/role/delete/${id}`)
    }

    /** 添加资源 */
    add(entity: Role) {
        return super.post('/role/add', entity)
    }

    /** 更新资源 */
    update(entity: Role) {
        return super.post('/role/update', entity)
    }

    /** 获取权限子节点 */
    listNodes(parentId = '') {
        return super.get<TreeNode[]>(`/role/list-nodes?parentId=${parentId}`)
    }

    /** 分页查询 */
    pageList(pageable: Pageable) {
        return super.post<Page<Role>>('/role/page', pageable)
    }
}
const request = new RoleRequest()
export default request
