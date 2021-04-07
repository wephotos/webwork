<template>
  <a-form :model="user" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }">
    <a-form-item label="姓名">
      <a-input v-model:value="user.name" placeholder="姓名">
          <template #prefix><UserOutlined style="color: rgba(0, 0, 0, 0.25)"/></template>
      </a-input>
    </a-form-item>
    <a-form-item label="账号">
      <a-input v-model:value="user.account" placeholder="账号">
          <template #prefix><UserOutlined style="color: rgba(0, 0, 0, 0.25)" /></template>
      </a-input>
    </a-form-item>
    <a-form-item label="密码">
      <a-input v-model:value="user.password" type="password" placeholder="密码">
        <template #prefix
          ><LockOutlined style="color: rgba(0, 0, 0, 0.25)"
        /></template>
      </a-input>
    </a-form-item>
    <a-form-item label="手机">
      <a-input v-model:value="user.phone" placeholder="手机">
          <template #prefix><MobileOutlined style="color: rgba(0, 0, 0, 0.25)"/></template>
      </a-input>
    </a-form-item>
    <a-form-item label="邮箱">
      <a-input v-model:value="user.email" placeholder="邮箱">
          <template #prefix><MailOutlined style="color: rgba(0, 0, 0, 0.25)"/></template>
      </a-input>
    </a-form-item>
    <a-form-item label="状态">
      <a-switch v-model:checked="user.status" />
    </a-form-item>
    <a-form-item label="头像">
      <a-image
        :width="150"
        src="https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png"
        />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">保存</a-button>
      <a-button style="margin-left: 10px" @click="onCancel">取消</a-button>
    </a-form-item>

  </a-form>
</template>
<script lang="ts">
import { PropType } from 'vue'
import { Options, Vue } from 'vue-class-component'
import { UserOutlined, LockOutlined, MobileOutlined, MailOutlined } from '@ant-design/icons-vue'
import Dialog from '@/components/Dialog.vue'
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
  dialog!: Dialog;
  // 用户ID，传入参数
  id!: string;
  // 用户对象数据
  user = {}

  // 挂载后执行
  mounted() {
      console.log('页面已挂载', this.id)
  }

  // 保存
  onSubmit() {
    this.dialog._ok()
  }

  // 取消
  onCancel() {
    this.dialog._close()
  }
}
</script>

<style scoped>
</style>
