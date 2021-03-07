import { User } from "@/types/User";
import axiosUtils from "@/utils/AxiosUtils";

/** 用户请求 */
export default class UserRequest {

    /** 根据ID查询用户 */
    static get(id: string) {
        return axiosUtils.get<User>(`/user/get/${id}`);
    }
}