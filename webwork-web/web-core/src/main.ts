import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'

/** 声明组件全局属性 */
declare module '@vue/runtime-core' {
    interface ComponentCustomProperties {
        // 显示LOADING图标
        $loading: (show?: boolean, cover?: boolean) => void;
    }
}

createApp(App).use(router).use(Antd).mount('#app')
