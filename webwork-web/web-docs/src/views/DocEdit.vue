<template>
    <div class="content">
      <a-form
        name="DocForm"
        layout="vertical"
        :model="document"
        autocomplete="off"
        @finish="save"
      >
        <div style="text-align: right;margin-bottom: 25px;">
          <a-space>
            <a-button type="primary" html-type="submit">保存</a-button>
            <a-button @click="back">返回</a-button>
          </a-space>
        </div>
        <a-divider />
        <a-form-item name="open">
          <a-checkbox v-model:checked="document.open">公开文档</a-checkbox>
        </a-form-item>
        <a-form-item name="title" :rules="[{ required: true, message: '请输入文档标题' }]">
          <a-input v-model:value="document.title" placeholder="请输入文档标题" :maxlength="50" />
        </a-form-item>
        <a-form-item name="content" :rules="[{ required: true, message: '请输入文档内容' }]">
          <span style="font-size: 12px; color: gray;">请输入Markdown文本</span>
          <a-textarea v-model:value="document.content" placeholder="请输入文档内容 Markdown"
            :rows="20" :maxlength="5000" :showCount="true" />
        </a-form-item>
        <a-form-item>
          <a-button @click="onPreview">文档预览</a-button>
        </a-form-item>
    </a-form>
    <a-modal
      v-model:visible="preview"
      title="文档预览"
      width="100%"
      :footer="null"
      wrap-class-name="full-modal"
    >
    <div v-html="previewMarkdown"></div>
    </a-modal>
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
  export default class DocEdit extends Vue {
    // 文档ID
    docId = 0
    document: Document = {
      contentType: 1 // Markdown
    }

    // 文档预览
    preview = false
    previewMarkdown = ''
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
      }
    }

    // 返回上一步
    back() {
        this.$router.back()
    }

    // 保存文档
    async save(values: any) {
      // console.log(this.document, values)
      const ret = await request.save(this.document)
      if (ret.code === 0) {
        this.document.id = ret.data.id
        this.document.versionNo = ret.data.versionNo
        message.success('保存成功')
      } else {
        message.error(ret.msg)
      }
    }

    // 文档预览
    onPreview() {
      if (!this.document.content) {
        message.warn('请输入内容后预览')
        return false
      }
      this.preview = true
      this.previewMarkdown = marked.parse(this.document.content as string, {
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
  }
  </script>
  <style lang="scss" >
  .content {
    padding: 20px 50px;
  }
  .full-modal .ant-modal {
    max-width: 100%;
    top: 0;
    padding-bottom: 0;
    margin: 0;
  }
  .full-modal .ant-modal-content {
    display: flex;
    flex-direction: column;
    height: calc(100vh);
  }
  .full-modal .ant-modal-body {
    flex: 1;
    overflow: auto;
  }
</style>
