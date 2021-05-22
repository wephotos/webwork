<template>
    <div>角色授权</div>
</template>
<script lang="ts">
import Dialog from '@/components/Dialog.vue'
import { PropType } from '@vue/runtime-core'
import { Options, Vue } from 'vue-class-component'
import { message } from 'ant-design-vue'

@Options({
  props: {
    roleId: {
      type: String
    },
    dialog: Object as PropType<Dialog>
  }
})
export default class RoleResoVue extends Vue {
    // 节点ID
  id!: string
  // 当前弹框
  dialog!: Dialog

  mounted() {
      message.info('打开')
  }
}
</script>
<style scoped>

</style>
