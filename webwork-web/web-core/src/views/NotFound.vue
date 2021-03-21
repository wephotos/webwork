<template>
  <a-result status="404" title="404" sub-title="对不起，你要访问的页面不存在">
    <template #extra>
      <a-button type="primary" @click="backHome">返回页面</a-button>
    </template>
  </a-result>
</template>
<script lang="ts">
import { Vue } from 'vue-class-component'

export default class NotFound extends Vue {
  backHome() {
    this.$router.replace('/')
  }
}
</script>
