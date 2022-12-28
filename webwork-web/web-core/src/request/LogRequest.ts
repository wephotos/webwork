import { Log } from '@/types/Log'
import Page from '@/types/Page'
import Pageable from '@/types/Pageable'
import BaseRequest from './BaseRequest'

/** 日志请求 */
export class LogRequest extends BaseRequest {
    /** 分页查询 */
    pageList(pageable: Pageable) {
        return super.post<Page<Log>>('/webwork-log/page-query', pageable)
    }
}
const request = new LogRequest()
export default request
