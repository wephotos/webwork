<template>
  <div class="toast" :style="{ top: top + 'px', left: left + 'px' }">
    {{ msg }}
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({
  props: {
    msg: String,
    seconds: Number
  }
})
export default class Toast extends Vue {
  msg!: string
  seconds = 2
  top = 0
  left = 0

  position() {
    const cw = document.documentElement.clientWidth
    const ch = document.documentElement.clientHeight

    const ew = this.$el.offsetWidth
    const eh = this.$el.offsetHeight

    this.top = (ch - eh) / 2
    this.left = (cw - ew) / 2
  }

  mounted() {
    document.body.appendChild(this.$el)
    this.$nextTick(() => {
      this.position()
    })
    window.onresize = () => this.position()
    setTimeout(() => {
      document.body.removeChild(this.$el)
    }, this.seconds * 1000)
  }
}
</script>
<style scoped>
.toast {
  display: inline-block;
  position: fixed;
  padding: 12px 25px;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 3000;
  color: #fff;
  text-align: center;
}
</style>
