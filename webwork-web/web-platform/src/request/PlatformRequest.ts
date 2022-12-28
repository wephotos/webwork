import { Resource } from '@/types/Resource'
import BaseRequest from './BaseRequest'

/** 平台接口请求 */
export class PlatformRequest extends BaseRequest {
    /** 获取登录用户的应用数据 */
    listApps() {
        return super.get<Resource[]>('/platform/list-apps')
    }
}
const request = new PlatformRequest()
export default request
