import { AlbumPhoto } from '@/types/AlbumPhoto'
import AxiosRequest from './AxiosRequest'

/** 照片接口请求 */
export class AlbumPhotoRequest extends AxiosRequest {
    /** 更新照片信息 */
    update(photo: AlbumPhoto) {
        return super.post<boolean>('/album-photo/update', photo)
    }

    /** 删除照片 */
    deletePhoto(photoId: number) {
        return super.get<boolean>(`/album-photo/delete/${photoId}`)
    }

    /** 获取照片列表 */
    listQuery(query: {albumId: number}) {
        return super.post<AlbumPhoto[]>('/album-photo/list-query', query)
    }
}
const request = new AlbumPhotoRequest()
export default request
