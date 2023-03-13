import { User } from '@/types/User'
import dayjs from 'dayjs'

/** 公共工具类 */
export default class PubUtils {
    /**
     * 获取地址栏参数
     * @param key 参数名
     * @returns 参数值
     */
    static getQueryUrl(key: string) {
        const reg = new RegExp('(^|&)' + key + '=([^&]*)(&|$)')
        const matchArray = window.location.search.substr(1).match(reg)
        return matchArray == null ? null : decodeURIComponent(matchArray[2])
    }

    /**
     * 数字补零
     * @param num 数字
     * @param len 长度
     * @returns 补零后字符串
     */
    static fill0(num: number, len: number) {
        return Array(len)
            .join('0')
            .concat(String(num))
            .substr(-len)
    }

    /**
     * 格式化日期
     * YYYY-MM-DD HH:mm:ss
     * @param date 日期
     * @param format 格式
     * @returns 格式化字符串
     */
    static formatDate(date: Date = new Date(), format = 'YYYY-MM-DD') {
        return dayjs(date).format(format)
    }

    /**
     * 将用户信息存入本地存储中
     * @param user 用户信息
     */
    static setLocalStorageUser(user: User) {
        localStorage.setItem("webworks-user", JSON.stringify(user))
    }

    /**
     * 从本地存储中获取当前用户信息
     * @returns 用户信息
     */
    static getLocalStorageUser(): User {
        const ustr = localStorage.getItem("webworks-user")
        return (ustr ? JSON.parse(ustr) : ustr) as User
    }
}
