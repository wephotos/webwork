<template>
  <a-form
    ref="formRef"
    :colon="false"
    :model="formData"
    :rules="rules"
    :label-col="{ span: 4 }"
    :wrapper-col="{ span: 18 }"
    style="margin-top: 15px"
  >
    <a-form-item label="图标" name="icon" class="icon-item">
      <a-upload
        name="file"
        :data="iconData"
        list-type="picture-card"
        class="icon-uploader"
        :show-upload-list="false"
        action="/file/upload"
        :withCredentials="true"
        @change="handleUploadChange"
        :before-upload="beforeUpload"
      >
        <img :key="iconKey" v-if="formData.icon" :src="iconUrl" alt="图标" />
        <div v-else>
          <loading-outlined v-if="iconLoading"></loading-outlined>
          <plus-outlined v-else></plus-outlined>
          <div class="ant-upload-text">上传</div>
        </div>
      </a-upload>
    </a-form-item>
    <a-form-item label="名称" name="name">
      <a-input v-model:value="formData.name" />
    </a-form-item>
    <a-form-item label="编码" name="permission">
      <a-input v-model:value="formData.permission" />
    </a-form-item>
    <a-form-item label="地址" name="url">
      <a-input v-model:value="formData.url" />
    </a-form-item>
    <a-form-item label="备注" name="remark">
      <a-input v-model:value="formData.remark" />
    </a-form-item>
    <a-form-item label="通用权限" name="common">
      <a-switch v-model:checked="formData.common" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">提交</a-button>
      <a-button style="margin-left: 10px" @click="onCancel">取消</a-button>
    </a-form-item>
  </a-form>
</template>
<script lang="ts">
import Dialog from '@/components/Dialog.vue'
import { Resource } from '@/types/Resource'
import { PropType, ref, toRaw, unref } from '@vue/runtime-core'
import { Options, Vue } from 'vue-class-component'
import { message } from 'ant-design-vue'
import request from '@/request/ResRequest'
import { ValidateErrorEntity } from 'ant-design-vue/es/form/interface'
import { R } from '@/types/R'
import {
  UserOutlined,
  LockOutlined,
  MobileOutlined,
  MailOutlined,
  PlusOutlined,
  LoadingOutlined
} from '@ant-design/icons-vue'
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
    LockOutlined,
    MobileOutlined,
    MailOutlined,
    PlusOutlined,
    LoadingOutlined
  },
  props: {
    id: {
      type: String
    },
    type: {
      type: Number
    },
    parentId: {
      type: String
    },
    parentName: {
      type: String
    },
    dialog: Object as PropType<Dialog>
  }
})
export default class ResourceForm extends Vue {
  // 节点ID
  id!: string
  // 节点类型
  type!: number
  // 父级节点
  parentId!: string
  parentName!: string
  // 当前弹框
  dialog!: Dialog
  // 表单数据
  formData: Resource = {
    id: this.id,
    type: this.type,
    parentId: this.parentId
  }

  // 表单引用
  formRef = ref<AntType.Form>()
  // 验证规则
  rules = {
    name: [{ required: true, message: '请输入名称', trigger: 'blur' }, { max: 10, message: '名称最多10个字符' }],
    permission: [{ required: true, message: '请输入编码', trigger: 'blur' }, { max: 50, message: '编码最多20个字符' }],
    url: [{ max: 255, message: '地址最多255个字符' }],
    remark: [{ max: 50, message: '备注信息最多50个字符' }]
  }

  // 图标相关
  iconKey = Math.random()
  // 头像上传标志
  iconLoading = false
  // 用户头像地址
  get iconUrl() {
    return `/file/download/thumb/${this.formData.icon}`
  }

  // 上传头像数据
  get iconData() {
    return { objectName: this.formData.icon || '' }
  }

  // 头像上传前回调
  beforeUpload(file: FileItem) {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
    if (!isJpgOrPng) {
      message.error('You can only upload JPG file!')
    }
    const isLt2M = file.size / 1024 / 1024 < 2
    if (!isLt2M) {
      message.error('Image must smaller than 2MB!')
    }
    return isJpgOrPng && isLt2M
  }

  // 上传事件
  handleUploadChange = (info: FileInfo) => {
    if (info.file.status === 'uploading') {
      this.iconLoading = true
      return false
    }
    this.iconLoading = false
    if (info.file.status === 'done') {
      if (info.file.response?.code === 0) {
        this.formData.icon = info.file.response.data.objectName
        this.iconKey = Math.random()
      } else {
        message.error(info.file.response?.msg as string)
      }
    }
    if (info.file.status === 'error') {
      message.error('上传失败')
    }
  }

  async mounted() {
    // 加载当前节点
    if (this.id) {
      const ret = await request.find(this.id)
      if (ret.code === 0) {
        this.formData = {
          ...this.formData,
          ...ret.data
        }
      } else {
        message.error(ret.msg)
      }
    }
  }

  // 保存
  onSubmit() {
    const formUnref = unref(this.formRef)
    formUnref &&
      formUnref
        .validate()
        .then(async () => {
          const data = { ...toRaw(this.formData) }
          let ret
          if (!this.id) {
            ret = await request.add(data)
            data.id = ret.data
          } else {
            ret = await request.update(data)
          }
          if (ret.code !== 0) {
            message.error(`错误:${ret.msg}`)
          } else {
            this.dialog._ok(data)
          }
        })
        .catch((error: ValidateErrorEntity<Resource>) => {
          console.log('error', error)
        })
  }

  // 取消
  onCancel() {
    this.dialog._close()
  }
}
</script>
<style scoped>
.icon-item ::v-deep(.ant-form-item-label){
    line-height: 104px;
}
.icon-item ::v-deep(.ant-form-item-control){
    line-height: inherit;
}
::v-deep(.ant-upload-select-picture-card){
    margin-bottom: 0px;
}
</style>
