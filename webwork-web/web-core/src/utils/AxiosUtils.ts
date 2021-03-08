import { ResponseEntity } from '@/types/ResponseEntity'
import axios, { AxiosRequestConfig } from 'axios'

/** AxiosUtils */
class AxiosUtils {
    /** axios instance */
    private instance = axios.create({
        baseURL: process.env.BASE_URL,
        timeout: 5000
    });

    /** 初始化 */
    constructor() {
        this.instance.interceptors.request.use(config => {
            return config
        }, error => {
            return Promise.reject(error)
        })

        this.instance.interceptors.response.use(response => {
            return response
        }, error => {
            return Promise.reject(error)
        })
    }

    /** GET请求 */
    get<T = any>(url: string, config?: AxiosRequestConfig) {
        return new Promise<ResponseEntity<T>>((resolve, reject) => {
            this.instance.get(url, config).then((res) => {
                resolve(res.data)
            }).catch((reason) => {
                reject(reason)
            })
        })
    }

    /** POST请求 */
    post<T = any>(url: string, data?: any, config?: AxiosRequestConfig) {
        return new Promise<ResponseEntity<T>>((resolve, reject) => {
            this.instance.post(url, data, config).then((res) => {
                resolve(res.data)
            }).catch((reason) => {
                reject(reason)
            })
        })
    }
}
/** 导出AxiosUtils实例 */
const axiosUtils = new AxiosUtils()
export default axiosUtils
