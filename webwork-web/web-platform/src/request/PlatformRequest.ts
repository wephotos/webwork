import { Resource } from '@/types/Resource'
import { User } from '@/types/User'
import BaseRequest from './BaseRequest'

/** 平台接口请求 */
export class PlatformRequest extends BaseRequest {
    /** 获取登录用户的应用数据 */
    listApps() {
        return super.get<Resource[]>('/platform/list-apps')
    }
    /** 获取当前用户信息 */
    getUserInfo() {
        return super.get<User>('/platform/get-user-info')
    }
    /** 更新用户信息 */
    updateUserInfo(po: User) {
        return super.post<boolean>('/platform/update-user-info', po)
    }
    /** 修改用户密码 */
    changePassword(po: { password: string, newPassword: string }) {
        return super.post<boolean>('/platform/change-password', po)
    }
}
const request = new PlatformRequest()
export default request
