<template>
  <div class="alert-container" :style="{ top: top + 'px', left: left + 'px' }">
    <div v-if="lock" class="mask"></div>
    <div class="title" @mousedown="mousedown($event)">
      <span>{{ title }}</span>
      <i class="close-alert" @click="_close" />
    </div>
    <div class="content">{{ content }}</div>
    <div class="btn">
      <!-- <p class="submit" v-if="ok" @click="_ok">确定</p>
      <p class="cancel" @click="_close">关闭</p> -->
      <a-space>
        <a-button type="primary" v-if="ok" @click="_ok">确定</a-button>
        <a-button @click="_close">关闭</a-button>
      </a-space>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({
  props: {
    title: {
      type: String,
      default: '提示'
    },
    content: String,
    lock: {
      type: Boolean,
      default: true
    },
    ok: Function,
    close: Function
  }
})
export default class Alert extends Vue {
  content!: string
  lock!: boolean
  ok!: Function
  close!: Function

  top = 0
  left = 0

  width = 0
  height = 0

  clientWidth = 0
  clientHeight = 0
  moveX = 0
  moveY = 0

  _ok() {
    if (!this.ok || this.ok()) {
      this.$el.remove()
    }
  }

  _close() {
    if (!this.close || this.close()) {
      this.$el.remove()
    }
  }

  mousedown(event: MouseEvent) {
    this.moveX = event.pageX
    this.moveY = event.pageY
    document.onselectstart = function () {
      return false
    }
    document.onmousemove = (event: MouseEvent) => {
      const x = this.left + event.pageX - this.moveX
      const y = this.top + event.pageY - this.moveY
      if (x > 0 && x + this.width < this.clientWidth) {
        this.left = x
      }
      if (y > 0 && y + this.height < this.clientHeight) {
        this.top = y
      }
      this.moveX = event.pageX
      this.moveY = event.pageY
    }
    document.onmouseup = (event: MouseEvent) => {
      document.onmousemove = document.onmouseup = document.onselectstart = null
    }
  }

  position() {
    this.clientWidth = document.documentElement.clientWidth
    this.clientHeight = document.documentElement.clientHeight
    this.width = this.$el.offsetWidth
    this.height = this.$el.offsetHeight
    this.left = (this.clientWidth - this.width) / 2
    this.top = (this.clientHeight - this.height) / 2
  }

  mounted() {
    document.body.appendChild(this.$el)
    this.$nextTick(() => {
      this.position()
    })
    window.onresize = () => this.position()
  }
}
</script>
<style lang="css" scoped>
.alert-container * {
  margin-bottom: 0;
  margin: 0;
}
.alert-container {
  display: inline-block;
  position: fixed;
  min-width: 200px;
  box-shadow: 0px 0px 5px 2px #999999;
  max-width: 600px;
  background: #fff;
  z-index: 2000;
  padding-top: 30px;
}
.alert-container div:not(.title) {
  background: inherit;
}
.alert-container .mask {
  width: 100%;
  height: 100%;
  background: #333 !important;
  opacity: 0.6;
  position: fixed;
  left: 0px;
  top: 0px;
  z-index: -1;
}
.title {
  text-align: left;
  padding: 0 10px;
  color: #fff;
  cursor: move;
  height: 30px;
  line-height: 30px;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  background-image: none;
  background: #3080f8;
}
.title span {
  color: #fff;
  font-weight: 700;
  text-shadow: -1px -1px 0 rgba(0, 0, 0, 0.7);
}
.content {
  padding: 20px;
  height: 100%;
  color: #000;
}
.btn {
  text-align: right;
  padding: 10px;
}
.close-alert {
  cursor: pointer;
  float: right;
  position: relative;
  width: 20px;
  height: 20px;
  background: url(../assets/images/icon2.png) no-repeat;
  background-position: 0 -72px;
  top: 5px;
}
</style>
