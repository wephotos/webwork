<template>
  <div class="content">
    <a-list size="large" item-layout="horizontal" :data-source="docs">
      <template #header>
        <a-space>
          <a-input-search
            v-model:value="pageable.condition.keyword"
            placeholder="关键词搜索"
            style="width: 300px;"
            :maxlength="20"
            enter-button
            allow-clear
            @search="onSearch"
            />
          <a-button type="primary" @click="newDocument">新建文档</a-button>
        </a-space>
      </template>
      <template #renderItem="{item}">
        <a-list-item @click="onItemClick(item)">
          <a-skeleton :title="false" :loading="!!item.loading" active>
            <a-list-item-meta>
              <template #title>
                <span v-if="keyword">
                  <template
                    v-for="(fragment, i) in item.title
                      .toString()
                      .split(new RegExp(`(?<=${keyword})|(?=${keyword})`, 'i'))"
                  >
                    <mark
                      v-if="fragment.toLowerCase() === keyword.toLowerCase()"
                      class="highlight"
                      :key="i"
                    >
                      {{ fragment }}
                    </mark>
                    <template v-else>{{ fragment }}</template>
                  </template>
                </span>
                <template v-else>
                  {{ item.title }}
                </template>
              </template>
              <template #description>
                <span v-if="keyword">
                  <template
                    v-for="(fragment, i) in item.subtitle
                      .toString()
                      .split(new RegExp(`(?<=${keyword})|(?=${keyword})`, 'i'))"
                  >
                    <mark
                      v-if="fragment.toLowerCase() === keyword.toLowerCase()"
                      class="highlight"
                      :key="i"
                    >
                      {{ fragment }}
                    </mark>
                    <template v-else>{{ fragment }}</template>
                  </template>
                </span>
                <template v-else>
                  {{ item.subtitle }}
                </template>
              </template>
            </a-list-item-meta>
          </a-skeleton>
          <template v-if="!item.loading" #extra>
            <div style="font-size: 12px; color: #333;">
              {{ item.createTime }}
            </div>
            </template>
        </a-list-item>
      </template>
      <template #loadMore>
        <div
          v-if="!loading"
          :style="{ textAlign: 'center', marginTop: '12px', height: '32px', lineHeight: '32px' }">
        <a-button @click="onLoadMore">加载更多...</a-button>
      </div>
    </template>
    </a-list>
  </div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { message } from 'ant-design-vue'
import { Document } from '@/types/Document'
import request from '@/request/DocumentRequest'
import Pageable from '@/types/Pageable'

@Options({
  watch: {
    'pageable.condition.keyword': function(newValue, oldValue) {
      this.keyword = newValue
    }
  }
})
export default class DocList extends Vue {
  // 文档集合
  docs: Document[] = []
  skeleton: Document[] = []
  // 加载标识
  loading = true
  // 搜索关键字
  keyword = ''
  // 分页参数
  pageable: Pageable = {
    curr: 1,
    size: 15,
    condition: {
      keyword: ''
    }
  }

  // 初始化
  async mounted() {
    this.onSearch()
  }

  // 加载列表
  async pageQuery(renderList: (docs: Document[]) => void) {
    const ret = await request.pageQuery(this.pageable)
    if (ret.code === 0) {
      renderList(ret.data.data)
    } else {
      message.error(ret.msg)
    }
    // 加载数目等于页大小可继续加载
    this.loading = ret.data.data.length < this.pageable.size
  }

  // 搜索
  onSearch() {
    this.loading = true
    this.docs = [...new Array(this.pageable.size)].map(() => ({ loading: true, id: 0, title: '', subtitle: '' }))
    this.pageQuery(docs => {
      this.docs = docs
      this.skeleton = docs
    })
  }

  onLoadMore() {
    this.loading = true
    this.pageable.curr += 1
    this.docs = this.skeleton.concat(
      [...new Array(this.pageable.size)].map(() => ({ loading: true, id: 0, title: '', subtitle: '' }))
      )
    this.pageQuery(docs => {
      this.docs = this.skeleton.concat(docs)
      this.skeleton = this.docs
    })
  }

  // 列表点击事件
  onItemClick(doc: Document) {
    this.$router.push({ path: '/doc-view', query: { docId: doc.id } })
  }

  // 新建文档
  newDocument() {
    this.$router.push({ path: '/doc-edit', query: {} })
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 20px 50px;
}
::v-deep(.ant-list-header) {
  text-align: right;
}
::v-deep(.ant-list-item-meta-content) {
  cursor: pointer;
}
.highlight {
  background-color: rgb(255, 192, 105);
  padding: 0px;
}
</style>
