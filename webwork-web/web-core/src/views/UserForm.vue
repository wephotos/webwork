<template>
  <a-form
    ref="formRef"
    :colon="false"
    :model="user"
    :rules="rules"
    :label-col="{ span: 4 }"
    :wrapper-col="{ span: 18 }"
  >
    <a-row type="flex" :gutter="8" style="margin: 0px;">
      <a-col :span="4"></a-col>
      <a-col :span="6">
        <a-image
        height="170px"
        src="https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png"
      />
      </a-col>
      <a-col :span="12">
        <a-form-item :wrapper-col="{ flex:'auto' }" name="name">
          <a-input v-model:value="user.name" placeholder="姓名">
            <template #prefix
              ><UserOutlined style="color: rgba(0, 0, 0, 0.25)"
            /></template>
          </a-input>
        </a-form-item>
        <a-form-item :wrapper-col="{ flex:'auto' }" name="account">
          <a-input v-model:value="user.account" placeholder="账号">
            <template #prefix
              ><UserOutlined style="color: rgba(0, 0, 0, 0.25)"
            /></template>
          </a-input>
        </a-form-item>
        <a-form-item :wrapper-col="{ flex:'auto' }" name="password">
          <a-input v-model:value="user.password" type="password" placeholder="密码">
            <template #prefix
              ><LockOutlined style="color: rgba(0, 0, 0, 0.25)"
            /></template>
          </a-input>
        </a-form-item>
      </a-col>

    </a-row>

    <a-form-item label="手机">
      <a-input v-model:value="user.phone" placeholder="手机">
        <template #prefix
          ><MobileOutlined style="color: rgba(0, 0, 0, 0.25)"
        /></template>
      </a-input>
    </a-form-item>
    <a-form-item label="邮箱">
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
      <a-select
        mode="multiple"
        v-model:value="value"
        style="width: 100%"
        placeholder="部门"
        @change="handleChange"
      >
        <a-select-option v-for="i in 25" :key="(i + 9).toString(36) + i">
          {{ (i + 9).toString(36) + i }}
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item label="角色">
      <a-select
        mode="multiple"
        v-model:value="value"
        style="width: 100%"
        placeholder="角色"
        @change="handleChange"
      >
        <a-select-option v-for="i in 25" :key="(i + 9).toString(36) + i">
          {{ (i + 9).toString(36) + i }}
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item label="标签">
      <a-select
        mode="multiple"
        v-model:value="value"
        style="width: 100%"
        placeholder="标签"
        @change="handleChange"
      >
        <a-select-option v-for="i in 25" :key="(i + 9).toString(36) + i">
          {{ (i + 9).toString(36) + i }}
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item label="状态">
      <a-switch v-model:checked="user.status" />
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
  MailOutlined
} from '@ant-design/icons-vue'
import { User } from '@/types/User'
import Dialog from '@/components/Dialog.vue'
import { ValidateErrorEntity } from 'ant-design-vue/es/form/interface'

@Options({
  components: {
    UserOutlined,
    LockOutlined,
    MobileOutlined,
    MailOutlined
  },
  props: {
    id: {
      type: String,
      required: true
    },
    dialog: Object as PropType<Dialog>
  }
})
export default class UserForm extends Vue {
  // 当前弹框
  dialog!: Dialog
  // 用户ID，传入参数
  id!: string
  // 用户对象数据
  user = {}
  // 表单引用
  formRef = ref<AntType.Form>()
  // 验证规则
  rules = {
      name: [
        { required: true, message: '请输入姓名', trigger: 'blur' }

      ],
      account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 15, message: '密码为6-15位字符串', trigger: 'blur' }

        ]
    }

  // 挂载后执行
  mounted() {
    console.log('页面已挂载', this.id)
  }

  // 保存
  onSubmit() {
    const formUnref = unref(this.formRef)
    formUnref &&
      formUnref
        .validate()
        .then(() => {
          console.log('values', this.user, toRaw(this.user))
          this.dialog._ok()
        })
        .catch((error: ValidateErrorEntity<User>) => {
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
.ant-form{
  padding: 5px 0px;
}
</style>
