<template>
  <div class="about">
    <a-button @click="alert">Alert</a-button>
    <a-button @click="dialog">Dialog</a-button>
    <a-button @click="toast">Toast</a-button>
    <a-button @click="loading">Loading</a-button>
    <a-button @click="message">Message</a-button>
    <a-button @click="moment">Moment</a-button>
  </div>
</template>
<script lang="ts">
import { Vue } from 'vue-class-component'
import Iframe from '../components/Iframe.vue'
import { message } from 'ant-design-vue'
import PubUtils from '@/utils/PubUtils'

export default class About extends Vue {
  alert() {
    this.$alert({
      content: '弹出框',
      ok: () => {
        return true
      }
    })
  }

  dialog() {
    this.$dialog({
      width: '100%',
      height: '100%',
      title: '百度一下',
      content: {
        component: Iframe,
        props: { src: 'https://www.baidu.com' }
      }
    })
  }

  toast() {
    this.$toast('toast')
  }

  loading() {
    this.$loading(true, true)
    setTimeout(() => {
      console.log('隐藏')
      this.$loading(false, false)
    }, 2000)
  }

  message() {
    message.info('This is a normal message')
  }

  moment() {
    this.$toast(PubUtils.formatDate())
  }
}
</script>
