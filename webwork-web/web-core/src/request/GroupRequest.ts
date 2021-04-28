import { Group } from '@/types/Group'
import BaseRequest from './BaseRequest'

/** 组织机构接口请求 */
export class GroupRequest extends BaseRequest {
    /** 根据ID查询组织 */
    find(id: string) {
        return super.get<Group>(`/organization/get/${id}`)
    }

    /** 根据ID删除组织 */
    delete(id: string) {
        return super.get(`/organization/delete/${id}`)
    }

    /** 添加组织机构 */
    add(data: Group) {
        return super.post('/organization/save', data)
    }

    /** 更新组织机构 */
    update(data: Group) {
        return super.post('/organization/update', data)
    }

    /** 查询子级  */
    children(parentId = '') {
        return super.get<Group[]>(`/organization/children?parentId=${parentId}`)
    }

}

const request = new GroupRequest()
export default request