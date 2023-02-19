<template>
  <div
    class="dialog-container"
    :style="{
      top: pxTop + 'px',
      left: pxLeft + 'px',
      width: pxWidth + 'px',
      height: pxHeight + 'px'
    }"
  >
    <div v-if="lock" class="mask"></div>
    <div class="title" @mousedown="mousedown($event)">
      <span>{{ title }}</span>
      <i class="close-dialog" @click="_close" />
      <i
        class="size-icon"
        v-if="max"
        :class="{ full: isFull }"
        @click="changeSize"
      />
    </div>
    <div :id="id" class="content"></div>
    <div class="btn" style="display: none">
      <p class="submit" v-if="ok" @click="_ok">确定</p>
      <p class="cancel" v-if="close" @click="_close">关闭</p>
    </div>
  </div>
</template>

<script lang="ts">
import { App, Component, createApp, PropType } from 'vue'
import { Options, Vue } from 'vue-class-component'
import Antd from 'ant-design-vue'
import $plugin from './index'
@Options({
  name: '$dialog',
  props: {
    title: {
      type: String,
      default: '标题'
    },
    content: {
      component: Object as Component,
      props: Object as PropType<Record<string, unknown>>,
      handle: {
        type: Boolean,
        default: false
      }
    },
    lock: {
      type: Boolean,
      default: true
    },
    max: {
      type: Boolean,
      default: true
    },
    width: {
      type: [Number, String],
      default: 200
    },
    height: {
      type: [Number, String],
      default: 150
    },
    top: {
      type: [Number, String],
      default: '50%'
    },
    left: {
      type: [Number, String],
      default: '50%'
    },
    ok: Function,
    close: Function
  }
})
export default class Dialog extends Vue {
  id = 'content-' + Math.round(Math.random() * 100000)
  title!: string;
  content!: {
    component: Component;
    props?: Record<string, unknown>;
    handle: boolean;
  }

  lock!: boolean
  max!: boolean
  ok!: Function
  close!: Function

  top: number | string = '50%'
  left: number | string = '50%'
  pxTop = 0
  pxLeft = 0

  width: number | string = 200
  height: number | string = 150
  pxWidth = 0
  pxHeight = 0

  clientWidth = 0
  clientHeight = 0
  moveX = 0
  moveY = 0
  isFull = false

  // 弹框存活状态
  isActive = true

  mousedown(event: MouseEvent) {
    this.moveX = event.pageX
    this.moveY = event.pageY
    document.onselectstart = function () {
      return false
    }
    document.onmousemove = (event: MouseEvent) => {
      const x = this.pxLeft + event.pageX - this.moveX
      const y = this.pxTop + event.pageY - this.moveY
      if (x > 0 && x + this.pxWidth < this.clientWidth) {
        this.pxLeft = x
      }
      if (y > 0 && y + this.pxHeight < this.clientHeight) {
        this.pxTop = y
      }
      this.moveX = event.pageX
      this.moveY = event.pageY
    }
    document.onmouseup = (/* event: MouseEvent */) => {
      document.onmousemove = document.onmouseup = document.onselectstart = null
    }
  }

  changeSize() {
    if (this.isFull) {
      this.position()
    } else {
      this.pxWidth = this.clientWidth
      this.pxHeight = this.clientHeight
      this.initTop()
      this.initLeft()
    }
    this.isFull = !this.isFull
  }

  // 确认回调
  _ok(...args: unknown[]) {
    if (!this.ok || this.ok(args)) {
      this._destroy()
    }
  }

  // 关闭回调
  _close(...args: unknown[]) {
    if (!this.close || this.close(args)) {
      this._destroy()
    }
  }

  // 销毁
  _destroy() {
    this.$el.remove()
    this.isActive = false
  }

  initTop() {
    if (typeof this.top === 'number') {
      this.pxTop = this.top
    } else {
      if (this.top.endsWith('%')) {
        this.pxTop =
          (parseFloat(this.top.replace('%', '')) / 100) *
          (this.clientHeight - this.pxHeight)
      } else {
        this.pxTop = parseInt(this.top)
      }
    }
    if (this.pxTop + this.pxHeight > this.clientHeight) {
      this.pxTop = this.clientHeight - this.pxHeight
    }
  }

  initLeft() {
    if (typeof this.left === 'number') {
      this.pxLeft = this.left
    } else {
      if (this.left.endsWith('%')) {
        this.pxLeft =
          (parseFloat(this.left.replace('%', '')) / 100) *
          (this.clientWidth - this.pxWidth)
      } else {
        this.pxLeft = parseInt(this.left)
      }
    }
    if (this.pxLeft + this.pxWidth > this.clientWidth) {
      this.pxLeft = this.clientWidth - this.pxWidth
    }
  }

  initWidth() {
    if (typeof this.width === 'number') {
      this.pxWidth = this.width
    } else {
      if (this.width.endsWith('%')) {
        this.pxWidth =
          (parseFloat(this.width.replace('%', '')) / 100) * this.clientWidth
      } else {
        this.pxWidth = parseInt(this.width)
      }
    }
    if (this.pxWidth > this.clientWidth) {
      this.pxWidth = this.clientWidth
    }
  }

  initHeight() {
    if (typeof this.height === 'number') {
      this.pxHeight = this.height
    } else {
      if (this.height.endsWith('%')) {
        this.pxHeight =
          (parseFloat(this.height.replace('%', '')) / 100) * this.clientHeight
      } else {
        this.pxHeight = parseInt(this.height)
      }
    }
    if (this.pxHeight > this.clientHeight) {
      this.pxHeight = this.clientHeight
    }
  }

  position() {
    this.initWidth()
    this.initHeight()
    this.initTop()
    this.initLeft()
  }

  client() {
    this.clientWidth = document.documentElement.clientWidth
    this.clientHeight = document.documentElement.clientHeight
  }

  created() {
    this.client()
    this.position()
  }

  // 内容组件
  contentApp!: App
  // 组件挂载完执行
  mounted() {
    document.body.appendChild(this.$el)
    // 是否传递弹框对象引用
    if (this.content.handle) {
      Object.assign(this.content.props || {}, { dialog: this })
    }
    this.contentApp = createApp(this.content.component, this.content.props)
    this.contentApp
      .use($plugin)
      .use(Antd)
      .mount('#' + this.id)
    window.onresize = () => this.client()
  }

  /**
   * 使用新的组件替换弹框内容
   */
  replace(
    component: Component,
    props: Record<string, unknown>,
    handle?: boolean
  ) {
    if (!this.isActive) {
      this.$toast('this dialog has been destroyed')
      return
    }
    // 是否传递弹框对象引用
    if (handle) {
      Object.assign(props, { dialog: this })
    }
    this.contentApp.unmount()
    this.contentApp = createApp(component, props)
    this.contentApp
      .use($plugin)
      .use(Antd)
      .mount('#' + this.id)
  }
}
</script>
<style lang="css" scoped>
.dialog-container * {
  margin-bottom: 0;
  margin: 0;
}
.dialog-container {
  display: inline-block;
  position: fixed;
  background: #fff;
  z-index: 1000;
  padding-top: 30px;
}
.dialog-container div:not(.title) {
  background: inherit;
}
.dialog-container .mask {
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
  background: #001529;
}
.title span {
  color: #fff;
  font-weight: 700;
  text-shadow: -1px -1px 0 rgba(0, 0, 0, 0.7);
}
.content {
  height: 100%;
  overflow: auto;
}
.btn {
  text-align: right;
  padding: 10px;
}
.btn p {
  display: inline-block;
  height: 30px;
  padding: 0 10px;
  border: none;
  line-height: 30px;
  cursor: pointer;
  color: #fff;
  border-radius: 5px;
}
.btn p.submit {
  background: #3161a9;
  margin-right: 10px;
}
.btn p.cancel {
  background: #3161a9;
}
.close-dialog {
  cursor: pointer;
  float: right;
  position: relative;
  width: 20px;
  height: 20px;
  background: url(../assets/images/icon2.png) no-repeat;
  background-position: 0 -72px;
  top: 5px;
}
.close-dialog:hover {
  background-position: 0 -92px;
}
.size-icon {
  cursor: pointer;
  float: right;
  position: relative;
  width: 20px;
  height: 20px;
  background: url(../assets/images/icon2.png) no-repeat;
  background-position: 0 -152px;
  top: 5px;
  margin-right: 5px;
}
.size-icon.full {
  background-position: 0 -112px;
}
.size-icon.full:hover {
  background-position: 0 -132px;
}
.size-icon:hover {
  background-position: 0 -172px;
}
</style>
