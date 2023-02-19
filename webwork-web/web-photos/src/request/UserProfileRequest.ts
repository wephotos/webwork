import { UserProfile } from '@/types/UserProfile'
import AxiosRequest from './AxiosRequest'

/** 用户信息请求 */
export class UserProfileRequest extends AxiosRequest {
    /** 获取用户信息 */
    getUserProfile() {
        return super.get<UserProfile>('/user-profile/get')
    }
}
const request = new UserProfileRequest()
export default request
