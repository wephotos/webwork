
import { Album } from '@/types/Album'
import AxiosRequest from './AxiosRequest'

/** 相册接口请求 */
export class AlbumRequest extends AxiosRequest {
    /** 保存相册 */
    save(album: Album) {
        return super.post<Document>('/album/save', album)
    }

    /** 删除相册 */
    deleteAlbum(albumId: number) {
        return super.get<boolean>(`/album/delete/${albumId}`)
    }

    /** 获取用户相册列表 */
    listQuery() {
        return super.post<Album[]>('/album/list-query')
    }

    /**
     * 设置相册封面
     * @param params 入参
     */
    setCover(params: { cover?:string, albumId?: number }) {
        return super.post<boolean>('/album/cover', params)
    }
}
const request = new AlbumRequest()
export default request
