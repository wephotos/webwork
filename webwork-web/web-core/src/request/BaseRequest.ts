import axiosUtils from '@/utils/AxiosUtils'
import { AxiosRequestConfig } from 'axios'

/** 基础请求 */
export default class BaseRequest {
    /** GET请求 */
    get<T = any>(url: string, config?: AxiosRequestConfig) {
        return axiosUtils.get<T>(url, config)
    }

    /** POST请求 */
    post<T = any>(url: string, data?: any, config?: AxiosRequestConfig) {
        return axiosUtils.post<T>(url, data, config)
    }
}
