<template>
  <a-config-provider :locale="locale">
    <router-view />
  </a-config-provider>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component'
import zhCN from 'ant-design-vue/es/locale/zh_CN'

/** 全局国际化(中文)配置 */
export default class App extends Vue {
  locale = zhCN
}
</script>
<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100%;
}
</style>
