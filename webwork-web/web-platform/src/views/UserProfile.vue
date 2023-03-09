<template>
    <a-form ref="formRef" name="user-form" :colon="false" :rules="rules" :model="formData" :label-col="{ span: 4 }"
        :wrapper-col="{ span: 20 }" autocomplete="off" @finish="handleFinish">
        <a-form-item has-feedback name="avatar" :wrapper-col="{ offset: 4 }" :style="{marginBottom: '0px'}">
            <a-upload
                name="file"
                :data="uploadData"
                list-type="picture-card"
                class="avatar-uploader"
                :show-upload-list="false"
                action="/file/upload"
                :withCredentials="true"
                @change="handleUploadChange"
                :before-upload="beforeUpload"
                >
                <img v-if="formData.avatar" :src="avatarUrl" alt="头像" :style="{maxWidth: '100%'}" />
                <div v-else>
                    <plus-outlined v-if="!uploadLoading" />
                    <loading-outlined v-else />
                    <div class="ant-upload-text">上传头像</div>
                </div>
                </a-upload>
        </a-form-item>
        <a-form-item has-feedback label="姓名" name="name">
            <a-input v-model:value="formData.name" size="large" placeholder="姓名">
                <template #prefix><UserOutlined style="color: rgba(0, 0, 0, 0.25)" /></template>
            </a-input>
        </a-form-item>
        <a-form-item has-feedback label="手机" name="phone">
            <a-input v-model:value="formData.phone" size="large" placeholder="手机号码">
                <template #prefix><MobileOutlined style="color: rgba(0, 0, 0, 0.25)" /></template>
            </a-input>
        </a-form-item>
        <a-form-item has-feedback label="邮箱" name="email">
            <a-input v-model:value="formData.email" size="large" placeholder="电子邮箱">
                <template #prefix><mail-outlined style="color: rgba(0, 0, 0, 0.25)" /></template>
            </a-input>
        </a-form-item>
        <a-form-item has-feedback label="职务" name="post">
            <a-input v-model:value="formData.post" size="large" placeholder="职务信息">
                <template #prefix><crown-outlined style="color: rgba(0, 0, 0, 0.25)" /></template>
            </a-input>
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 20, offset: 4 }" :style="{ textAlign: 'right' }">
            <a-space>
                <a-button @click="handleCancel">取消</a-button>
                <a-button type="primary" html-type="submit">确定</a-button>
            </a-space>
        </a-form-item>
    </a-form>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import {
    UserOutlined,
    MailOutlined,
    MobileOutlined,
    CrownOutlined,
    PlusOutlined,
    LoadingOutlined
} from '@ant-design/icons-vue'
import { Rule } from 'ant-design-vue/es/form/interface'
import { ref, toRaw } from 'vue'
import request from '@/request/PlatformRequest'
import { message } from 'ant-design-vue'
import { User } from '@/types/User'
import { R } from '@/types/R'
import PubUtils from '@/utils/PubUtils'

interface FileItem {
  uid: string;
  name?: string;
  status?: string;
  response?: R<any>;
  url?: string;
  type?: string;
  size: number;
  originFileObj: any;
}

interface FileInfo {
  file: FileItem;
  fileList: FileItem[];
}

@Options({
    components: {
        UserOutlined,
        MailOutlined,
        MobileOutlined,
        CrownOutlined,
        PlusOutlined,
        LoadingOutlined
    },
    emits: ['close']
})
export default class Password extends Vue {
    // 表单对象
    formRef = ref<AntType.Form>()

    // 表单数据
    formData: User = {}
    // 头像上传标志
    uploadLoading = false

    // 校验规则
    rules: Record<string, Rule[]> = {
        name: [
            { required: true, message: '姓名不能为空', validateTrigger: 'blur' },
            { max: 15, message: '姓名不能超过15个字符' }
        ],
        post: [{ max: 15, message: '职位信息不能超出15个字符', validateTrigger: 'blur' }],
        phone: [
            { required: true, message: '手机号码不能为空', validateTrigger: 'blur' },
            { max: 11, message: '手机号不能超过11位数字', validateTrigger: 'blur' },
            { pattern: /^1[0-9]{10}$/, message: '手机号码格式错误', validateTrigger: 'blur' }
        ],
        email: [
            {
                pattern: /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/,
                message: '电子邮箱格式错误',
                validateTrigger: 'blur'
            },
            { max: 25, message: '电子邮箱不能超过25个字符', validateTrigger: 'blur' }
        ]
    }

    async mounted() {
        const res = await request.getUserInfo()
        if (res.code === 0) {
            this.formData = res.data
        } else {
            message.error(res.msg)
        }
    }

    // 用户头像地址
    get avatarUrl() {
        // 更换头像
        if (!this.formData.newAvatar) {
            return `/file/download/${this.formData.avatar}`
        } else {
            return `/file/download/${this.formData.newAvatar}`
        }
    }

    // 上传头像数据
    get uploadData() {
        return { objectName: this.formData.avatar || '' }
    }

    // 头像上传前回调
    beforeUpload(file: FileItem) {
        const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
        if (!isJpgOrPng) {
            message.error('头像只能上传JPG或PNG格式的照片')
        }
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isLt2M) {
            message.error('头像大小不能超过 2MB')
        }
        return isJpgOrPng && isLt2M
    }

    // 上传事件
    handleUploadChange = (info: FileInfo) => {
        if (info.file.status === 'uploading') {
        this.uploadLoading = true
        } else {
            this.uploadLoading = false
            if (info.file.status === 'done') {
                if (info.file.response?.code === 0) {
                    this.formData.avatar = info.file.response.data.objectName
                    this.formData.newAvatar = this.formData.avatar + '?random=' + Math.random()
                } else {
                    message.error(info.file.response?.msg as string)
                }
            } else if (info.file.status === 'error') {
                message.error('上传失败')
            }
        }
    }

    // 取消
    handleCancel() {
        this.formRef.value?.clearValidate()
        this.$emit('close')
    }

    // 确认
    handleFinish(values: unknown) {
        const data = toRaw(this.formData)
        request.updateUserInfo(data).then(res => {
            if (res.code === 0) {
                this.handleCancel()
            } else {
                message.error(res.msg)
            }
        })
    }
}
</script>
<style scoped>
.avatar-uploader > ::v-deep(.ant-upload) {
  width: 150px;
  height: 180px;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
