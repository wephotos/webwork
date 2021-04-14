import Page from '@/types/Page'
import Pageable from '@/types/Pageable'
import { User } from '@/types/User'
import BaseRequest from './BaseRequest'

/** 用户请求 */
export class UserRequest extends BaseRequest {
    /** 根据ID查询用户 */
    find(id: string) {
        return super.get<User>(`/user/get/${id}`)
    }

    /** 根据ID删除用户 */
    delete(id: string) {
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

    /** 分页查询 */
    pageList(pageable: Pageable) {
        return super.get<Page<User>>('/users.json')
    }
}
const userRequest = new UserRequest()
export default userRequest
