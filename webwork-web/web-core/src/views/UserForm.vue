<template>
  <a-form
    ref="formRef"
    :colon="false"
    :model="user"
    :rules="rules"
    :label-col="{ span: 4 }"
    :wrapper-col="{ span: 18 }"
  >
    <a-row type="flex" :gutter="8" style="margin: 0px">
      <a-col :span="4"></a-col>
      <a-col :span="6">
        <a-upload
          name="file"
          :data="avatarData"
          list-type="picture-card"
          class="avatar-uploader"
          :show-upload-list="false"
          action="/file/upload"
          :withCredentials="true"
          @change="handleUploadChange"
          :before-upload="beforeUpload"
        >
          <img :key="avatarKey" v-if="user.avatar" :src="avatarUrl" alt="头像" />
          <div v-else>
            <loading-outlined v-if="avatarLoading"></loading-outlined>
            <plus-outlined v-else></plus-outlined>
            <div class="ant-upload-text">Upload</div>
          </div>
        </a-upload>
      </a-col>
      <a-col :span="12">
        <a-form-item :wrapper-col="{ flex: 'auto' }" name="name">
          <a-input v-model:value="user.name" placeholder="姓名">
            <template #prefix
              ><UserOutlined style="color: rgba(0, 0, 0, 0.25)"
            /></template>
          </a-input>
        </a-form-item>
        <a-form-item :wrapper-col="{ flex: 'auto' }" name="account">
          <a-input v-model:value="user.account" placeholder="账号">
            <template #prefix
              ><UserOutlined style="color: rgba(0, 0, 0, 0.25)"
            /></template>
          </a-input>
        </a-form-item>
        <a-form-item :wrapper-col="{ flex: 'auto' }" name="password">
          <a-input
            v-model:value="user.password"
            type="password"
            placeholder="密码"
          >
            <template #prefix
              ><LockOutlined style="color: rgba(0, 0, 0, 0.25)"
            /></template>
          </a-input>
        </a-form-item>
      </a-col>
    </a-row>

    <a-form-item label="手机" name="phone">
      <a-input v-model:value="user.phone" placeholder="手机">
        <template #prefix
          ><MobileOutlined style="color: rgba(0, 0, 0, 0.25)"
        /></template>
      </a-input>
    </a-form-item>
    <a-form-item label="邮箱" name="email">
      <a-input v-model:value="user.email" placeholder="邮箱">
        <template #prefix
          ><MailOutlined style="color: rgba(0, 0, 0, 0.25)"
        /></template>
      </a-input>
    </a-form-item>
    <a-form-item label="职务">
      <a-input v-model:value="user.post" placeholder="职务">
        <template #prefix
          ><UserOutlined style="color: rgba(0, 0, 0, 0.25)"
        /></template>
      </a-input>
    </a-form-item>
    <a-form-item label="部门">
      <a-tree-select
        v-if="treeData.length"
        :tree-data="treeData"
        :load-data="onLoadData"
        v-model:value="user.deptId"
        tree-default-expand-all
        style="width: 100%"
        placeholder="请选择用户部门"
        :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
      />
    </a-form-item>
    <a-form-item label="状态">
      <a-switch :checked="user.status == 1" @click="handleUserStatus" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">保存</a-button>
      <a-button style="margin-left: 10px" @click="onCancel">取消</a-button>
    </a-form-item>
  </a-form>
</template>
<script lang="ts">
import { PropType, ref, toRaw, unref } from 'vue'
import { Options, Vue } from 'vue-class-component'
import {
  UserOutlined,
  LockOutlined,
  MobileOutlined,
  MailOutlined,
  PlusOutlined,
  LoadingOutlined
} from '@ant-design/icons-vue'
import { User } from '@/types/User'
import Dialog from '@/components/Dialog.vue'
import { ValidateErrorEntity } from 'ant-design-vue/es/form/interface'
import request from '@/request/UserRequest'
import GroupRequest from '@/request/GroupRequest'
import { message } from 'ant-design-vue'
import { TreeDataItem } from 'ant-design-vue/es/tree/Tree'
import { Group } from '@/types/Group'
import { R } from '@/types/R'

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
      type: Number
    },
    deptId: {
      type: Number
    },
    dialog: Object as PropType<Dialog>
  }
})
export default class UserForm extends Vue {
  // 当前弹框
  dialog!: Dialog
  // 用户ID，传入参数
  id!: number
  // 部门ID
  deptId!: number
  // 用户对象数据
  user: User = {
    status: 1,
    deptId: this.deptId
  }

  avatarKey = Math.random()
  // 头像上传标志
  avatarLoading = false
  // 组织树数据源
  treeData: TreeDataItem[] = []
  // 表单引用
  formRef = ref<AntType.Form>()
  // 验证规则
  rules = {
    name: [{ required: true, message: '请输入姓名', trigger: 'blur' }, { max: 20, message: '最多输入20个字符' }],
    account: [{ required: true, message: '请输入账号', trigger: 'blur' }, { max: 20, message: '最多输入20个字符' }],
    post: [{ max: 20, message: '最多输入20个字符' }],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 15, message: '密码为6-15位字符串', trigger: 'blur' }
    ],
    phone: [
      { pattern: /^1[0-9]{10}$/, message: '手机号码错误', trigger: 'blur' }
    ],
    email: [
      {
        pattern: /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/,
        message: '邮箱地址错误',
        trigger: 'blur'
      },
      { max: 50, message: '最多输入50个字符' }
    ]
  }

  // 用户头像地址
  get avatarUrl() {
    return `/file/download/thumb/${this.user.avatar}`
  }

  // 上传头像数据
  get avatarData() {
    return { objectName: this.user.avatar || '' }
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
      this.avatarLoading = true
      return false
    }
    this.avatarLoading = false
    if (info.file.status === 'done') {
      if (info.file.response?.code === 0) {
        this.user.avatar = info.file.response.data.objectName
        this.avatarKey = Math.random()
      } else {
        message.error(info.file.response?.msg as string)
      }
    }
    if (info.file.status === 'error') {
      message.error('上传失败')
    }
  }

  // 挂载后执行
  async mounted() {
    // 加载组织树数据
    const ret = await GroupRequest.children()
    if (ret.code === 0) {
      this.treeData = this.toChildren(ret)
    } else {
      message.error(ret.msg)
    }
    // 加载用户信息
    this.id &&
      request.find(this.id).then((ret) => {
        if (ret.code === 0) {
          this.user = ret.data
        } else {
          message.error(ret.msg)
        }
      })
  }

  /**
   * 加载节点数据
   */
  onLoadData(treeNode: { dataRef: TreeDataItem }) {
    return new Promise((resolve: (value?: unknown) => void) => {
      if (treeNode.dataRef.children) {
        resolve()
        return false
      }
      GroupRequest.children(treeNode.dataRef.key as number).then((ret) => {
        treeNode.dataRef.children = this.toChildren(ret)
        this.treeData = [...this.treeData]
        resolve()
        this.user.deptId = this.user.deptId || 0
      })
    })
  }

  // 保存
  onSubmit() {
    const formUnref = unref(this.formRef)
    formUnref &&
      formUnref
        .validate()
        .then(async () => {
          let ret
          if (!this.user.id) {
            ret = await request.add(toRaw(this.user))
          } else {
            ret = await request.update(toRaw(this.user))
          }
          if (ret.code === 0) {
            this.dialog._ok()
          } else {
            message.error(ret.msg)
          }
        })
        .catch((error: ValidateErrorEntity<User>) => {
          console.log('error', error)
        })
  }

  // 取消
  onCancel() {
    this.dialog._close()
  }

  // 用户状态切换
  handleUserStatus() {
    this.user.status = this.user.status === 1 ? 0 : 1
  }

  // 转为树节点类型
  toChildren(ret: R<Group[]>) {
    return ret.data.map((value) => {
      return {
        key: value.id,
        type: value.type, // 节点类型 0虚拟节点 1组织 2部门
        value: value.id,
        title: value.name,
        code: value.code,
        isLeaf: value.type === 2,
        // disabled: value.type === 2
        selectable: value.type === 2
      } as TreeDataItem
    })
  }
}
</script>

<style scoped>
.ant-form {
  padding: 5px 0px;
}
.avatar-uploader > ::v-deep(.ant-upload) {
  width: 130px;
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
