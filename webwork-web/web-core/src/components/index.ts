import { App, Component, ComponentPublicInstance, createApp } from 'vue'
import Antd from 'ant-design-vue'
import Toast from '@/components/Toast.vue'
import Alert from '@/components/Alert.vue'
import Dialog from '@/components/Dialog.vue'
import Loading from '@/components/Loading.vue'
import components from '@/components'
import { Options } from '@/types/Options'

/** 插件安装 */
export default function install(app: App, ...$options: any[]) {
  // loading
  const vNodeLogo = document.createElement('div')
  app.config.globalProperties.$loading = (show = true, cover = false) => {
    createApp(Loading, {
      show: show,
      cover: cover
    }).use(Antd).mount(vNodeLogo)
  }
  // 提示
  app.config.globalProperties.$toast = (
    options: Options.Toast | string
  ) => {
    const props = { msg: '' }
    if (typeof options === 'string') {
      props.msg = options
    } else {
      Object.assign(props, options)
    }
    const toast = createApp(Toast, props).use(Antd)
    toast.mount(document.createElement('div'))
  }
  // 提示框
  app.config.globalProperties.$alert = (
    options: Options.Alert | string
  ) => {
    const props = {
      content: ''
    }
    if (typeof options === 'string') {
      props.content = options
    } else {
      Object.assign(props, options)
    }

    const alert = createApp(Alert, props).use(Antd)
    alert.mount(document.createElement('div'))
  }
  // 对话框
  app.config.globalProperties.$dialog = (
    options: Options.Dialog | Component
  ): ComponentPublicInstance => {
    const props = {}
    if ((options as Options.Dialog).content) {
      Object.assign(props, options)
    } else {
      Object.assign(props, { content: { component: options } })
    }
    const dialog = createApp(Dialog, props).use(components).use(Antd)
    return dialog.mount(document.createElement('div'))
  }
}
