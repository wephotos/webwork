<template>
  <a-layout style="height: 100%">
    <a-layout-header>
      <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
        v-model:selectedKeys="current"
      >
        <a-menu-item key="user">组织人员</a-menu-item>
        <a-menu-item key="menu">功能模块</a-menu-item>
        <a-menu-item key="role">角色权限</a-menu-item>
        <a-menu-item key="dict">数据字典</a-menu-item>
        <a-menu-item key="conf">系统设置</a-menu-item>
      </a-menu>
    </a-layout-header>
    <a-layout>
      <a-layout-sider width="200" style="background: #fff">Sider</a-layout-sider>
      <a-layout-content>Content</a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script lang="ts">
import { Vue } from 'vue-class-component'

export default class Home extends Vue {
  current: string[] = ['user']
}
</script>
<style lang="scss">
</style>
