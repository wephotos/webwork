import Page from '@/types/Page'
import Pageable from '@/types/Pageable'
import { Dictionary } from '@/types/Dictionary'
import { TreeNode } from '@/types/TreeNode'
import BaseRequest from './BaseRequest'

/** 数据字典接口请求类 */
export class DictRequest extends BaseRequest {
    /** 查询 */
    find(id: number) {
        return super.get<Dictionary>(`/dictionary/get/${id}`)
    }

    /** 删除 */
    delete(id: number) {
        return super.get(`/dictionary/delete/${id}`)
    }

    /** 添加资源 */
    add(entity: Dictionary) {
        return super.post('/dictionary/add', entity)
    }

    /** 更新资源 */
    update(entity: Dictionary) {
        return super.post('/dictionary/update', entity)
    }

    /** 获取权限子节点 */
    listNodes(parentId?: number) {
        return super.get<TreeNode[]>('/dictionary/list-nodes' + (parentId ? `?parentId=${parentId}` : ''))
    }

    /** 分页查询 */
    pageList(pageable: Pageable) {
        return super.post<Page<Dictionary>>('/dictionary/page', pageable)
    }
}
const request = new DictRequest()
export default request
