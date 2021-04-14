import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import components from './components'

/** 声明组件全局属性 */
declare module '@vue/runtime-core' {
    interface ComponentCustomProperties {
        $loading: (show?: boolean, cover?: boolean) => void;
        $toast: (options: Options.Toast | string) => void;
        $alert: (options: Options.Alert | string) => void;
        $dialog: (options: Options.Dialog | Component) => ComponentPublicInstance;
    }
}

createApp(App).use(router).use(Antd).use(components).mount('#app')
