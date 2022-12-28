<template>
    <div
      class="app-view"
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
        <i class="close-dialog" @click="close(source)" />
        <i
          class="size-icon"
          v-if="max"
          :class="{ full: isFull }"
          @click="changeSize"
        />
      </div>
      <div class="content-box">
        <iframe class="content" :src="url" frameborder="0"></iframe>
      </div>
    </div>
  </template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

  @Options({
    name: 'AView',
    props: {
      title: {
        type: String,
        default: '应用'
      },
      // 应用地址
      url: { type: String, default: '/404' },
      // 事件源
      source: Object,
      // 页面锁定
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
        default: 800
      },
      height: {
        type: [Number, String],
        default: 650
      },
      top: {
        type: [Number, String],
        default: '50%'
      },
      left: {
        type: [Number, String],
        default: '50%'
      },
      close: Function
    }
  })
  export default class Dialog extends Vue {
    // 标题
    title!: string
    // 地址
    url!: string
    // 事件源
    source!: Record<string, any>;

    max!: boolean
    lock!: boolean

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

    // 组件挂载完执行
    mounted() {
      window.onresize = () => this.client()
    }
  }
  </script>
  <style lang="css" scoped>
  .app-view * {
    margin-bottom: 0;
    margin: 0;
  }
  .app-view {
    display: inline-block;
    position: fixed;
    background: #fff;
    z-index: 1000;
    padding-top: 30px;
  }
  .app-view div:not(.title) {
    background: inherit;
  }
  .app-view .mask {
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
    width: 100%;
    border: none;
    overflow: auto;
  }
  .content-box{
    width: 100%;
    height: 100%;
    padding-top: 2px;
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
