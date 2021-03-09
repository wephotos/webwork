import axiosUtils from '@/utils/AxiosUtils'
import { AxiosRequestConfig } from 'axios'

/** 基础请求 */
export default class BaseRequest {
    /** GET请求 */
    doGet<T = any>(url: string, config?: AxiosRequestConfig) {
        return axiosUtils.get<T>(url, config)
    }

    /** POST请求 */
    doPost<T = any>(url: string, data?: any, config?: AxiosRequestConfig) {
        return axiosUtils.post<T>(url, data, config)
    }
}
