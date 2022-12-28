<template>
  <div class="client">
    <a-divider orientation="left">URL</a-divider>
    <a-row>
      <a-col flex="200px">
        <a-select v-model:value="method" size="large" style="width: 100%">
          <a-select-option v-for="item in methods" :key="item" :value="item">{{
            item
          }}</a-select-option>
        </a-select>
      </a-col>
      <a-col flex="200px">
        <a-select
          v-model:value="contentType"
          size="large"
          style="width: 100%"
          @change="contentTypeChange"
        >
          <a-select-option
            v-for="item in contentTypes"
            :key="item"
            :value="item"
            >{{ item }}</a-select-option
          >
        </a-select>
      </a-col>
      <a-col flex="auto">
        <a-input-search
          v-model:value="url"
          placeholder="请输入请求地址"
          enter-button="请求"
          size="large"
          @search="handleRequest"
        />
      </a-col>
    </a-row>
    <a-divider orientation="left">参数</a-divider>
    <a-row>
      <a-col flex="auto">
        <div
          v-if="contentType == ContentTypes.MULTIPART"
          style="margin-bottom: 5px"
        >
          <a-input
            v-model:value="filename"
            placeholder="发到后台的文件参数名"
            style="width: 100px; margin-right: 5px"
          />
          <a-upload
            :name="filename"
            :action="url"
            :data="filedata"
            :disabled="url == '' || filename == ''"
            :withCredentials="true"
            :before-upload="beforeUpload"
            @change="handleFileChange"
          >
            <a-button>
              <upload-outlined></upload-outlined>
              上传文件
            </a-button>
          </a-upload>
        </div>
        <a-textarea
          v-model:value="body"
          placeholder="请输入请求内容,JSON字符串请使用双引号"
          :auto-size="{ minRows: 5, maxRows: 15 }"
        />
      </a-col>
    </a-row>
    <a-divider orientation="left">结果</a-divider>
    <a-row>
      <a-col flex="auto">
        <pre class="result-box">{{ result }}</pre>
        <a-alert
          v-show="message"
          :message="message"
          :type="alertType"
          style="margin-top: 5px"
        />
      </a-col>
    </a-row>
  </div>
</template>
<script lang="ts">
import Qs from 'qs'
import axios from 'axios'
import { Options, Vue } from 'vue-class-component'
import { UploadOutlined } from '@ant-design/icons-vue'
/** HTTP方法定义 */
enum HttpMethod {
  GET = 'GET',
  POST = 'POST',
  PUT = 'PUT',
  DELETE = 'DELETE',
  HEAD = 'HEAD',
  OPTIONS = 'OPTIONS',
  PATCH = 'PATCH'
}
/** 请求内容类型定义 */
enum HttpContentType {
  JSON = 'application/json;charset=UTF-8',
  URLENCODED = 'application/x-www-form-urlencoded;charset=UTF-8',
  MULTIPART = 'multipart/form-data;charset=UTF-8',
  XML = 'text/xml;charset=UTF-8'
}

/** 获取枚举值数组 */
function enumValues<T extends object>(enumeration: T): Array<T[keyof T]> {
  return Object.keys(enumeration).map((key) => enumeration[key as keyof T])
}
@Options({
  components: {
    UploadOutlined
  }
})
export default class Client extends Vue {
  // 请求地址
  url = ''
  // 请求体
  body = ''
  // 返回数据
  result = ''
  // 响应状态
  message = ''
  // 提示样式
  alertType = 'success'
  // 默认GET
  method = HttpMethod.POST
  contentType = HttpContentType.URLENCODED
  // 支持的HTTP方法
  methods = enumValues(HttpMethod)
  // 内容类型
  ContentTypes = HttpContentType
  contentTypes = enumValues(HttpContentType)
  // 文件域名
  filename = 'file'
  // 上传参数
  filedata = {}

  // 请求实例
  private instance = axios.create({
    baseURL: '/',
    timeout: 5000
  })

  // 发起请求
  handleRequest() {
    if (this.url === '') {
      return false
    }
    // 附件上传时禁用
    if (this.contentType === HttpContentType.MULTIPART) {
      return false
    }
    this.$loading()
    this.instance
      .request({
        url: this.url,
        method: this.method,
        headers: {
          'Content-Type': this.contentType
        },
        data: this.requestBody()
      })
      .then((res) => {
        this.result = JSON.stringify(res.data, null, 4)
        if (res.status !== 200) {
          this.alertType = 'error'
        }
        this.message = `status:${res.status}, statusText:${res.statusText}, contentType: ${res.headers['content-type']}`
      })
      .catch((reason) => {
        this.result = String(reason)
      })
      .finally(() => {
        this.$loading(false)
      })
  }

  // 内容类型发生改变时
  contentTypeChange(value: string) {
    if (value === HttpContentType.MULTIPART) {
      this.url = '/file/upload'
      this.body = 'owner=123456'
    } else {
      this.url = ''
      this.body = ''
    }
  }

  requestBody() {
    if (this.contentType === HttpContentType.URLENCODED) {
      const obj = this.parseJSON(this.body)
      if (obj instanceof Object) {
        return Qs.stringify(obj)
      } else {
        return this.body
      }
    } else {
      return this.body
    }
  }

  // 上传前操作
  beforeUpload() {
    const body = this.parseJSON(this.body)
    if (body instanceof Object) {
      this.filedata = body
    } else {
      this.filedata = Qs.parse(this.body)
    }
    return true
  }

  // 上传文件改变时的状态
  handleFileChange(event: Record<string, unknown>) {
    this.result = JSON.stringify(event.file, null, 4)
  }

  // 字符串转JSON
  parseJSON(text: string) {
    try {
      return JSON.parse(text)
    } catch (err) {
      return text
    }
  }
}
</script>
<style scoped>
.client {
  padding: 10px 20px;
}
.ant-col {
  padding: 1px 2px;
}
.result-box {
  overflow: auto;
  min-height: 200px;
  max-height: 400px;
  background-color: #fffbe6;
  border: 1px solid #ccc;
}
</style>
