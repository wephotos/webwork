<template>
  <teleport to="body">
    <div>
      <a-spin v-show="show" size="large" class="loading" />
      <div v-show="show && cover" class="cover"></div>
    </div>
  </teleport>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({
  props: {
    show: {
      type: Boolean,
      default: true
    },
    cover: {
      type: Boolean,
      default: false
    }
  }
})
export default class Loading extends Vue {}
</script>
<style scoped>
.loading {
  z-index: 9999;
  top: 50%;
  left: 50%;
  margin-top: -30px;
  margin-left: -30px;
  position: fixed;
}
.cover {
  z-index: 8888;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
  opacity: 0.6;
  position: fixed;
  background: #f8f8ff !important;
}
</style>
