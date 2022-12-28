<template>
  <a-row justify="center" align="middle" style="height: 100%">
    <a-col :span="6" class="login-box">
      <h1>
        <a-tag color="#1890ff">
          <template #icon>
            <GithubOutlined />
          </template>
          WEBWORKS
        </a-tag>
      </h1>
      <a-form
        layout="horizontal"
        :model="formData"
        @finish="handleFinish"
        @finishFailed="handleFinishFailed"
        style="display: inline-block"
      >
        <a-form-item>
          <a-input v-model:value="formData.username" placeholder="用户名">
            <template #prefix
              ><UserOutlined style="color: rgba(0, 0, 0, 0.25)"
            /></template>
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-input
            v-model:value="formData.password"
            type="password"
            placeholder="密码"
          >
            <template #prefix
              ><LockOutlined style="color: rgba(0, 0, 0, 0.25)"
            /></template>
          </a-input>
        </a-form-item>
        <a-form-item :wrapperCol="{ span: 24 }">
          <a-button
            block
            type="primary"
            html-type="submit"
            :disabled="formData.username === '' || formData.password === ''"
          >
            登 录
          </a-button>
        </a-form-item>
      </a-form>
    </a-col>
  </a-row>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import {
  UserOutlined,
  LockOutlined,
  GithubOutlined
} from '@ant-design/icons-vue'
import { ValidateErrorEntity } from 'ant-design-vue/es/form/interface'
import axiosUtils from '@/utils/AxiosUtils'
import { R } from '@/types/R'
interface UserAuth {
  username: string;
  password: string;
}
interface LoginUser {
  id: number;
  name: string;
  deptId: number;
  deptName: string;
  groupId: number;
  groupName: string;
}
@Options({
  components: {
    UserOutlined,
    LockOutlined,
    GithubOutlined
  }
})
export default class Login extends Vue {
  // 表单数据
  formData: UserAuth = {
    username: 'admin',
    password: '123456'
  }

  // 认证通过登录
  async handleFinish(values: UserAuth) {
    const params = `username=${this.formData.username}&password=${this.formData.password}`
    const result = await axiosUtils.post<R<LoginUser>>(
      '/platform/login',
      params,
      {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }
    )
    if (result.code === 0) {
      this.$toast('登录成功')
      this.$router.push('/user')
    } else {
      this.$toast(result.msg)
    }
  }

  handleFinishFailed(errors: ValidateErrorEntity<UserAuth>) {
    console.log(errors)
  }
}
</script>
<style scoped>
.login-box {
  height: 300px;
  padding-top: 20px;
  text-align: center;
  background: rgba(0, 0, 0, 0.3);
}
</style>
