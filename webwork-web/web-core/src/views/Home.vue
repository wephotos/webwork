<template>
  <a-layout style="height: 100%">
    <a-layout-header>
      <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
        v-model:selectedKeys="current"
        @click="handleClick"
      >
        <a-menu-item key="user">组织人员</a-menu-item>
        <a-menu-item key="resource">应用管理</a-menu-item>
        <a-menu-item key="role">角色管理</a-menu-item>
        <a-menu-item key="dictionary">数据字典</a-menu-item>
        <a-menu-item key="config">配置中心</a-menu-item>
      </a-menu>
    </a-layout-header>
    <a-layout-content>
      <router-view />
    </a-layout-content>
  </a-layout>
</template>
<script lang="ts">
import { Vue } from 'vue-class-component'

export default class Home extends Vue {
  // 当前选中
  current: string[] = ['user']
  // 菜单点击
  handleClick(e: {key: string; [key: string]: unknown}) {
    this.$router.push(e.key)
  }

  mounted() {
    const name = this.$route.name?.toString().toLowerCase()
    if (name) {
      this.current = [name]
    }
  }
}
</script>
<style lang="scss">
</style>
