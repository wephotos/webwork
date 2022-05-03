import Page from '@/types/Page'
import Pageable from '@/types/Pageable'
import { User } from '@/types/User'
import BaseRequest from './BaseRequest'

/** 用户请求 */
export class UserRequest extends BaseRequest {
    /** 根据ID查询用户 */
    find(id: number) {
        return super.get<User>(`/user/get/${id}`)
    }

    /** 根据ID删除用户 */
    delete(id: number) {
        return super.get(`/user/delete/${id}`)
    }

    /** 添加用户 */
    add(user: User) {
        return super.post('/user/add', user)
    }

    /** 更新用户 */
    update(user: User) {
        return super.post('/user/update', user)
    }
    /**
     * 用户置顶
     * @param userId 用户ID
     * @param deptId 部门ID
     * @returns R<boolean>
     */
    top(userId: number, deptId: number) {
        return super.get(`/user/top?userId=${userId}&deptId=${deptId}`)
    }

    /** 分页查询 */
    pageList(pageable: Pageable) {
        return super.post<Page<User>>('/user/page', pageable)
    }
}
const request = new UserRequest()
export default request
