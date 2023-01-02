
import { Document } from '@/types/Document'
import Page from '@/types/Page'
import Pageable from '@/types/Pageable'
import BaseRequest from './BaseRequest'

/** 文档接口请求 */
export class DocumentRequest extends BaseRequest {
    /** 保存文档 */
    save(doc: Document) {
        return super.post<Document>('/document/save', doc)
    }

    /** 获取文档详情 */
    getDoc(docId: number) {
        return super.get<Document>(`/document/get/${docId}`)
    }

    /** 删除文档 */
    deleteDoc(docId: number) {
        return super.get<boolean>(`/document/delete/${docId}`)
    }

    /** 分页查询文档 */
    pageQuery(pageable: Pageable) {
        return super.post<Page<Document>>('/document/page', pageable)
    }
}
const request = new DocumentRequest()
export default request
