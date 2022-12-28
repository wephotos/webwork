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
  </div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import request from '@/request/PlatformRequest'
import { Resource } from '@/types/Resource'
import { message } from 'ant-design-vue'
import AView from '@/views/AView.vue'
/**
 * 打开的应用
 */
type OpenApp = Resource & {
  visible: boolean; // 是否显示
  timestamp: number; // 打开时间
}

@Options({
  components: {
    AView
  }
})
export default class Home extends Vue {
  // 最多可打开的应用数量
  static readonly OPEN_APP_MAX = 10;

  // 用户的应用
  apps: Resource[] = [
    { id: 1, name: '一二三', url: '/404' },
    { id: 2, name: '四五六', url: '/404' }
  ];

  // 当前打开的应用
  openApps: OpenApp[] = new Array<OpenApp>(0);

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
    console.log(this.openApps)
  }
}
</script>
<style lang="scss" scoped>
#home {
  width: 100%;
  height: 100%;
  padding: 15px;
  background-color: azure;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  align-content: flex-start;
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
</style>
