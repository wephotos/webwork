<template>
    <div class="content">
      <h1 style="text-align: center;">{{ document.title }}</h1>
      <div style="text-align: right;">
        <a-space>
          <a-button v-if="document.canEdit" type="primary" @click="edit">编辑</a-button>
          <a-popconfirm
            title="您确定要删除文档吗?"
            ok-text="是"
            cancel-text="否"
            @confirm="onDelete"
          >
            <a-button v-if="document.canDelete" type="danger">删除</a-button>
          </a-popconfirm>
          <a-button @click="back">返回</a-button>
        </a-space>
      </div>
      <a-divider />
      <div style="width: 100%;" v-html="document.html"></div>
    </div>
</template>
<script lang="ts">
  import { Options, Vue } from 'vue-class-component'
  import { message } from 'ant-design-vue'
  import { Document } from '@/types/Document'
  import request from '@/request/DocumentRequest'
  import { marked } from 'marked'
  import highlight from 'highlight.js'
  import 'highlight.js/styles/default.css' // Code CSS

  @Options({
    components: {
    }
  })
  export default class DocView extends Vue {
    // 文档ID
    docId = 0
    document: Document = {}
    // 初始化
    async mounted() {
      if (!this.$route.query.docId) {
        return false
      }
      this.docId = Number(this.$route.query.docId as string)
      const ret = await request.getDoc(this.docId)
      if (ret.code !== 0) {
        message.error(ret.msg)
      } else {
        this.document = ret.data
        // 处理markdown
        if (this.document.contentType === 1) {
          this.document.content && this.renderMarkdown(this.document.content)
        } else {
          this.document.html = this.document.content
        }
      }
    }

    // 渲染Markdown
    renderMarkdown(markdown: string) {
      if (!markdown) {
        return false
      }
      this.document.html = marked.parse(markdown, {
        renderer: new marked.Renderer(),
        highlight: function(code, lang) {
          const language = highlight.getLanguage(lang) ? lang : 'plaintext'
          return highlight.highlight(code, { language }).value
        },
        langPrefix: 'hljs language-', // highlight.js css expects a top-level 'hljs' class.
        pedantic: false,
        gfm: true,
        breaks: false,
        sanitize: false,
        smartypants: false,
        xhtml: false
      })
    }

    // 返回上一步
    back() {
        this.$router.back()
    }

    // 编辑文档
    edit() {
      this.$router.replace({ path: '/doc-edit', query: { docId: this.docId } })
    }

    // 删除文档
    async onDelete() {
      const ret = await request.deleteDoc(this.docId)
      if (ret.code === 0) {
        this.$router.back()
      } else {
        message.error(ret.msg)
      }
    }
  }
  </script>
  <style lang="scss" scoped>
  .content {
    padding: 20px 50px;
  }
  </style>
