<template>
  <div id="home">
    <!-- 渲染应用 -->
    <a-badge v-for="app in apps" :key="app.id" count="0" @click="appClick(app)">
      <a-avatar shape="square" size="large" :src="app.icon" />
      <span class="app-text">{{app.name}}</span>
    </a-badge>
    <!-- 应用弹框 -->
    <div v-for="app in openApps" :key="app.id">
      <AView v-show="app.visible"
      :source="app"
      :title="app.name"
      :url="app.url"
      :close="appClose" />
    </div>
    <!-- 用户菜单 -->
    <a-affix :offset-top="0" :style="{position: 'fixed', top: '10px', right: '25px'}">
      <a-avatar v-if="user.avatar" :src="avatarUrl" />
      <a-avatar v-else style="background-color: #87d068">
        <template #icon>
          <UserOutlined />
        </template>
      </a-avatar>
      <a-dropdown>
        <a class="ant-dropdown-link" @click.prevent>
          您好，{{ user.name }}
          <DownOutlined />
        </a>
        <template #overlay>
          <a-menu @click="handleMenuClick">
            <a-menu-item key="info">
              <user-outlined />个人信息
            </a-menu-item>
            <a-menu-item key="pass">
              <lock-outlined />修改密码
            </a-menu-item>
            <a-menu-item key="exit">
              <poweroff-outlined />退出系统
            </a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
    </a-affix>
  </div>
  <!-- 用户信息弹框 -->
  <a-modal v-model:visible="visibleUsr" title="用户信息" :footer="null">
    <UserProfile @close="onCloseUserModal" />
  </a-modal>
  <!-- 修改密码弹框 -->
  <a-modal v-model:visible="visiblePwd" title="修改密码" :footer="null">
    <Password @close="onClosePwdModal" />
  </a-modal>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import request from '@/request/PlatformRequest'
import { Resource } from '@/types/Resource'
import { message } from 'ant-design-vue'
import AView from '@/views/AView.vue'
import Password from '@/views/Password.vue'
import UserProfile from '@/views/UserProfile.vue'

import {
  DownOutlined,
  UserOutlined,
  LockOutlined,
  PoweroffOutlined
} from '@ant-design/icons-vue'
import { User } from '@/types/User'
import PubUtils from '@/utils/PubUtils'
/**
 * 打开的应用
 */
type OpenApp = Resource & {
  visible: boolean; // 是否显示
  timestamp: number; // 打开时间
}

@Options({
  components: {
    AView,
    Password,
    UserProfile,
    DownOutlined,
    UserOutlined,
    LockOutlined,
    PoweroffOutlined
  }
})
export default class Home extends Vue {
  // 最多可打开的应用数量
  static readonly OPEN_APP_MAX = 10;

  // 用户框显示
  visibleUsr = false
  // 密码框显示
  visiblePwd = false

  // 用户信息
  user: User = {}

  random = Math.random()

  // 用户的应用
  apps: Resource[] = [
    { id: 1, name: '一二三', url: '/404' },
    { id: 2, name: '四五六', url: '/404' }
  ];

  // 当前打开的应用
  openApps: OpenApp[] = new Array<OpenApp>(0);

  get avatarUrl() {
    return '/file/download/' + this.user.avatar + '?random=' + this.random
  }

  // 初始化
  async mounted() {
    const ret = await request.listApps()
    if (ret.code === 0) {
      // 处理应用图标地址
      ret.data.forEach((app) => {
        app.icon = `/file/download/${app.icon}`
      })
      this.apps = ret.data
    } else {
      message.error(ret.msg)
    }
    // 当前用户信息
    this.user = PubUtils.getLocalStorageUser()
  }

  // APP点击
  appClick(app: Resource) {
    // 判断当前应用是否活跃中
    let openApp = this.openApps.find((item) => item.id === app.id)
    if (openApp) {
      openApp.visible = true
      openApp.timestamp = new Date().getTime()
      return false
    }
    // 打开已经达到上限
    // 淘汰最早打开应用
    if (this.openApps.length === Home.OPEN_APP_MAX) {
      this.openApps.sort((a, b) => b.timestamp - a.timestamp)
      this.openApps.pop()
    }
    // 打开新应用
    openApp = (app as OpenApp)
    openApp.visible = true
    openApp.timestamp = new Date().getTime()
    this.openApps.push(openApp)
  }

  // 关闭应用(隐藏)
  appClose(openApp: OpenApp) {
    openApp.visible = false
  }

  // 用户菜单点击事件
  handleMenuClick(e: Event & {[key: string]: any}) {
    if (e.key === 'info') {
      this.visibleUsr = true
    } else if (e.key === 'pass') {
      this.visiblePwd = true
    } else if (e.key === 'exit') {
      window.location.href = '/platform/logout'
    } else {
      console.log('未知的用户菜单KEY:' + e.key)
    }
  }

  // 关闭修改密码弹窗
  onClosePwdModal() {
    this.visiblePwd = false
  }

  // 关闭用户信息弹窗
  onCloseUserModal() {
    this.visibleUsr = false
    this.random = Math.random()
  }
}
</script>
<style lang="scss" scoped>
#home {
  width: 100%;
  height: 100%;
  padding: 15px;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  align-content: flex-start;
  background-color: #f1fafa;
}
.ant-badge {
  margin: 20px;
  cursor: pointer;
}
.app-text {
  display: block;
  font-size: 12px;
  margin-top: 5px;
  font-family: Arial,sans-serif;
}
::v-deep(.ant-avatar-lg) {
  width: 48px;
  height: 48px;
  line-height: 48px;
}
::v-deep(.ant-dropdown-link) {
  vertical-align: middle;
}
</style>
