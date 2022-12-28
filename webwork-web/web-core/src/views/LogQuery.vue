<template>
    <a-form layout="inline" :model="formState" :colon="false" class="search-form">
      <a-form-item name="level" label="级别">
        <a-select
          ref="select"
          v-model:value="formState.level"
          style="width: 120px"
        >
          <a-select-option value="">ALL</a-select-option>
          <a-select-option value="ERROR">ERROR</a-select-option>
          <a-select-option value="WARN">WARN</a-select-option>
          <a-select-option value="INFO">INFO</a-select-option>
          <a-select-option value="DEBUG">DEBUG</a-select-option>
          <a-select-option value="TRACE">TRACE</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item name="range-time" label="时间">
        <a-range-picker
          v-model:value="formState['range-time']"
          show-time
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </a-form-item>
      <a-form-item name="traceId" label="TraceId">
        <a-input v-model:value="formState.traceId" />
      </a-form-item>
      <a-form-item name="content" label="内容">
        <a-input v-model:value="formState.content" />
      </a-form-item>
      <a-form-item >
        <a-button type="primary" @click="highSearch()"> 搜 索 </a-button>
      </a-form-item>
    </a-form>
    <a-table
        class="ant-table-striped"
        rowKey="id"
        :columns="columns"
        :data-source="dataList"
        :rowClassName="
          (record, index) => (index % 2 === 1 ? 'table-striped' : null)
        "
        :pagination="pagination"
        @change="handleTableChange"
      >
        <!-- 自定义筛选菜单 -->
        <template
          #filterDropdown="{
            setSelectedKeys,
            selectedKeys,
            confirm,
            clearFilters,
            column
          }"
        >
          <div style="padding: 8px">
            <a-input
              ref="searchInput"
              :placeholder="`搜索 ${column.title}`"
              :value="selectedKeys[0]"
              style="width: 188px; margin-bottom: 8px; display: block"
              @change="
                (e) => setSelectedKeys(e.target.value ? [e.target.value] : [])
              "
              @pressEnter="
                handleSearch(selectedKeys, confirm, column.dataIndex)
              "
            />
            <a-button
              type="primary"
              size="small"
              style="width: 90px; margin-right: 8px"
              @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
            >
              <template #icon><SearchOutlined /></template>
              查询
            </a-button>
            <a-button
              size="small"
              style="width: 90px"
              @click="handleReset(clearFilters)"
            >
              重置
            </a-button>
          </div>
        </template>
        <template #filterIcon="filtered">
          <search-outlined
            :style="{ color: filtered ? '#108ee9' : undefined }"
          />
        </template>
        <template #content="{ text, column }">
          <span v-if="searchText && searchedColumn === column.dataIndex">
            <template
              v-for="(fragment, i) in text
                .toString()
                .split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i'))"
            >
              <mark
                v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                class="highlight"
                :key="i"
              >
                {{ fragment }}
              </mark>
              <template v-else>{{ fragment }}</template>
            </template>
          </span>
          <template v-else>
            {{ text }}
          </template>
        </template>
      </a-table>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import Pageable from '@/types/Pageable'
import { Log } from '@/types/Log'
import { ref, unref } from 'vue'
import { message } from 'ant-design-vue'
import { SearchOutlined } from '@ant-design/icons-vue'
import {
  TableState,
  TableStateFilters
} from 'ant-design-vue/es/table/interface'
import request from '@/request/LogRequest'

// 分页数据类型
type Pagination = TableState['pagination']
// 排序映射
const orderMap: { [key: string]: string } = {
  ascend: 'ASC',
  descend: 'DESC'
}
@Options({
  components: {
    SearchOutlined
  }
})
export default class LogQueryVue extends Vue {
  // 搜索表单
  formState: {
    level: string;
    content: string;
    traceId: string;
    'range-time': [string, string];
  } = {
    level: '',
    content: '',
    traceId: '',
    'range-time': ['', '']
  }

  // 日志列定义
  columns = [
    {
      title: '序号',
      dataIndex: 'number',
      width: 70,
      customRender: (info: {
        text: string;
        record: Log;
        index: number;
      }) => {
        return (this.pageable.curr - 1) * this.pageable.size + info.index + 1
      }
    },
    {
      title: '时间',
      dataIndex: 'createTime',
      width: 180,
      sorter: true,
      key: 'create_time'
    },
    { title: '级别', dataIndex: 'level', width: 100 },
    { title: 'TraceId', dataIndex: 'traceId', width: 280 },
    { title: '操作用户', dataIndex: 'username', width: 100 },
    {
      title: '日志内容',
      dataIndex: 'content',
      ellipsis: true,
      slots: {
        // filterDropdown: 'filterDropdown',
        // filterIcon: 'filterIcon'
        // customRender: 'content'
      },
      onFilterDropdownVisibleChange: (visible: boolean) => {
        if (visible) {
          setTimeout(() => {
            const searchInput = unref(this.searchInput)
            searchInput && searchInput.focus()
          }, 0)
        }
      }
    },
    { title: '堆栈信息', dataIndex: 'stackTrace', ellipsis: true }
  ]

  // 搜索
  searchInput = ref<HTMLInputElement>()

  searchText = ''

  searchedColumn = ''

  // 日志列表数据
  dataList: Log[] = []

  // 分页参数定义
  pagination = {
    total: 0,
    current: 1,
    pageSize: 15
  }

  // 分页条件
  pageable: Pageable = {
    curr: this.pagination.current,
    size: this.pagination.pageSize,
    condition: {}
  }

  /**
   * 页面挂载后
   */
   async mounted() {
    this.pageQuery()
  }

  // 分页查询资源列表
  async pageQuery() {
    const result = await request.pageList(this.pageable)
    if (result.code === 0) {
      this.dataList = result.data.data
      this.pagination.total = result.data.count
    } else {
      message.error(result.msg)
    }
  }

  // 表格变动监听
  handleTableChange(
    pag: Pagination,
    filters: TableStateFilters,
    sorter: { field: string; order: string; columnKey: string }
  ) {
    this.pagination.current = pag?.current || 1
    this.pageable.curr = this.pagination.current
    this.pageable.sortField = sorter.columnKey || ''
    this.pageable.sortOrder = orderMap[sorter.order] || ''
    // this.pageable.condition.content = filters.content instanceof Array ? filters.content[0] : ''
    this.pageQuery()
  }

  /** 列表搜索 */
  handleSearch(selectedKeys: string[], confirm: Function, dataIndex: string) {
    confirm()
    this.searchText = selectedKeys[0] || ''
    this.searchedColumn = dataIndex
  }

  /** 查询条件重置 */
  handleReset(clearFilters: Function) {
    clearFilters()
    this.searchText = ''
  }

  /** 表单搜索 */
  highSearch() {
    this.pageable.condition.level = this.formState.level
    this.pageable.condition.content = this.formState.content
    this.pageable.condition.traceId = this.formState.traceId
    const btime = this.formState['range-time'][0]
    const etime = this.formState['range-time'][1]
    btime && (this.pageable.condition.btime = btime)
    etime && (this.pageable.condition.etime = etime)

    this.pageable.curr = 1
    this.pagination.current = 1
    console.log(this.pageable)
    this.pageQuery()
  }
}
</script>
<style scoped>
.search-form {
  padding: 24px 5px;
  background: rgba(255, 255, 255, 0.04);
}
.highlight {
  background-color: rgb(255, 192, 105);
  padding: 0px;
}
::v-deep(.ant-table-tbody > tr > td) {
    padding: 10px 15px;
    overflow-wrap: break-word;
}
</style>
