<template>
  <iframe :src="url" frameborder="0"></iframe>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({
  props: {
    url: String
  }
})
export default class Iframe extends Vue {}
</script>
<style lang="css" scoped>
iframe {
  width: 100%;
  height: 100%;
  border: none;
}
</style>
