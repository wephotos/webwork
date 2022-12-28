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
    <a-form-item label="名称" name="name">
      <a-input v-model:value="formData.name" />
    </a-form-item>
    <a-form-item label="备注" name="remark">
      <a-input v-model:value="formData.remark" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">提交</a-button>
      <a-button style="margin-left: 10px" @click="onCancel">取消</a-button>
    </a-form-item>
  </a-form>
</template>
<script lang="ts">
import Dialog from '@/components/Dialog.vue'
import { Role } from '@/types/Role'
import { PropType, ref, toRaw, unref } from '@vue/runtime-core'
import { Options, Vue } from 'vue-class-component'
import { message } from 'ant-design-vue'
import request from '@/request/RoleRequest'
import { ValidateErrorEntity } from 'ant-design-vue/es/form/interface'

import {
  UserOutlined,
  LockOutlined,
  MobileOutlined,
  MailOutlined,
  PlusOutlined,
  LoadingOutlined
} from '@ant-design/icons-vue'
import { TreeNodeType } from '@/types/TreeNodeType'

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
    parentId: {
      type: Number
    },
    parentName: {
      type: String
    },
    dialog: Object as PropType<Dialog>
  }
})
export default class RoleForm extends Vue {
  // 节点ID
  id!: number
  // 父级节点
  parentId!: number
  parentName!: string
  // 当前弹框
  dialog!: Dialog
  // 表单数据
  formData: Role = {
    id: this.id,
    parentId: this.parentId,
    parentType: TreeNodeType.APP
  }

  // 表单引用
  formRef = ref<AntType.Form>()
  // 验证规则
  rules = {
    name: [
      { required: true, message: '请输入角色名称', trigger: 'blur' },
      { max: 10, message: '角色名称最多10个字符' }
    ],
    remark: [
      { max: 50, message: '备注最多50个字符' }
    ]
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
        .catch((error: ValidateErrorEntity<Role>) => {
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
</style>
