import { User } from '@/types/User'
import BaseRequest from './BaseRequest'

/** 用户请求 */
export default class UserRequest extends BaseRequest {
    /** 根据ID查询用户 */
    get(id: string) {
        return this.doGet<User>(`/user/get/${id}`)
    }

    /** 根据ID删除用户 */
    delete(id: string) {
        return super.doGet(`/user/delete/${id}`)
    }

    /** 添加用户 */
    add(user: User) {
        return this.doPost('/user/add', user)
    }

    /** 更新用户 */
    update(user: User) {
        return this.doPost('/user/update', user)
    }
}
