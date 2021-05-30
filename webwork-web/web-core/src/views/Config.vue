<template>
  <div class="config-box">
  <a-button
    class="editable-add-btn"
    @click="handleRefresh"
    style="margin-bottom: 8px"
    >刷新配置</a-button
  >
  <a-table
    class="ant-table-striped"
    rowKey="id"
    :columns="columns"
    :data-source="configs"
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
          @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)"
        />
        <a-button
          type="primary"
          size="small"
          style="width: 90px; margin-right: 8px"
          @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
        >
          <template #icon><SearchOutlined /></template>
          Search
        </a-button>
        <a-button
          size="small"
          style="width: 90px"
          @click="handleReset(clearFilters)"
        >
          Reset
        </a-button>
      </div>
    </template>
    <template #filterIcon="filtered">
      <search-outlined :style="{ color: filtered ? '#108ee9' : undefined }" />
    </template>
    <template #name="{ text, column }">
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
    <template #value="{ text, record }">
      <div class="editable-cell">
        <div v-if="editableData[record.id]" class="editable-cell-input-wrapper">
          <a-input
            v-model:value="editableData[record.id].value"
            @pressEnter="save(record.id)"
          />
          <check-outlined
            class="editable-cell-icon-check"
            @click="save(record.id)"
          />
        </div>
        <div v-else class="editable-cell-text-wrapper">
          {{ text || ' ' }}
          <edit-outlined class="editable-cell-icon" @click="edit(record.id)" />
        </div>
      </div>
    </template>
  </a-table>
  </div>
</template>
<script lang="ts">
import { ref, unref } from 'vue'
import { Options, Vue } from 'vue-class-component'
import { message } from 'ant-design-vue'
import {
  SearchOutlined,
  CheckOutlined,
  EditOutlined
} from '@ant-design/icons-vue'
import {
  TableState,
  TableStateFilters
} from 'ant-design-vue/es/table/interface'
import { cloneDeep } from 'lodash'
import Pageable from '@/types/Pageable'
import BaseRequest from '@/request/BaseRequest'
import Page from '@/types/Page'
// 声明配置数据类型
type Config = { id?: string; name?: string; value?: string }
// 分页数据类型
type Pagination = TableState['pagination']
// 排序映射
const orderMap: { [key: string]: string } = {
  ascend: 'ASC',
  descend: 'DESC'
}
@Options({
  components: {
    SearchOutlined,
    CheckOutlined,
    EditOutlined
  }
})
export default class ConfigVue extends Vue {
  // 列定义
  columns = [
    {
      title: '序号',
      dataIndex: 'number',
      width: '10%',
      customRender: (info: { text: string; record: Config; index: number }) => {
        return (this.pageable.curr - 1) * this.pageable.size + info.index + 1
      }
    },
    {
      title: '环境',
      dataIndex: 'profile',
      width: '10%'
    },
    {
      title: '键',
      dataIndex: 'name',
      width: '40%',
      sorter: true,
      key: 'name',
      slots: {
        filterDropdown: 'filterDropdown',
        filterIcon: 'filterIcon',
        customRender: 'name'
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
    {
      title: '值',
      dataIndex: 'value',
      slots: { customRender: 'value' }
    }
  ]

  // 编辑数据
  editableData: Record<string, Config> = {}

  // 表格数据
  configs: Config[] = []

  // 搜索
  searchInput = ref<HTMLInputElement>()
  searchText = ''
  searchedColumn = ''

  // 分页参数定义
  pagination = {
    total: 200,
    current: 1,
    pageSize: 10
  }

  // 分页条件
  pageable: Pageable = {
    curr: this.pagination.current,
    size: this.pagination.pageSize,
    condition: {}
  }

  // 请求工具
  req = new BaseRequest()
  /**
   * 页面挂载后
   */
  async mounted() {
    // 加载列表
    this.pageQuery()
  }

  // 分页查询资源列表
  async pageQuery() {
    const result = await this.req.post<Page<Config>>('/config/page', this.pageable)
    if (result.code === 0) {
      this.configs = result.data.data
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
    this.pageable.sortField = sorter.columnKey || ''
    this.pageable.sortOrder = orderMap[sorter.order] || ''
    this.pageable.condition.name =
      filters.name instanceof Array ? filters.name[0] : ''
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

  get count() {
    return this.configs.length + 1
  }

  /** 编辑数据 */
  edit(id: string) {
    this.editableData[id] = cloneDeep(
      this.configs.filter((item) => id === item.id)[0]
    )
  }

  /** 保存数据 */
  async save(id: string) {
    const item = this.configs.filter((item) => id === item.id)[0]
    Object.assign(
      item,
      this.editableData[id]
    )
    delete this.editableData[id]
    const ret = await this.req.post('/config/update', item)
    if (ret.code === 0) {
      message.success('更新成功')
    } else {
      message.error(ret.msg)
    }
  }

  /** 刷新配置信息 */
  handleRefresh() {
    this.$loading(true, true)
    this.req.get('/config/refresh').then(res => {
      this.$loading(false, false)
      if (res.code === 0) {
        message.success('配置刷新成功')
      } else {
        message.error(`配置刷新失败:${res.msg}`)
      }
    })
  }
}
</script>
<style lang="scss" scoped>
.config-box{
    padding: 5px 10px;
}
.editable-cell {
  position: relative;
  .editable-cell-input-wrapper,
  .editable-cell-text-wrapper {
    padding-right: 24px;
  }

  .editable-cell-text-wrapper {
    padding: 5px 24px 5px 5px;
  }

  .editable-cell-icon,
  .editable-cell-icon-check {
    position: absolute;
    right: 0;
    width: 20px;
    cursor: pointer;
  }

  .editable-cell-icon {
    margin-top: 4px;
    display: none;
  }

  .editable-cell-icon-check {
    line-height: 28px;
  }

  .editable-cell-icon:hover,
  .editable-cell-icon-check:hover {
    color: #108ee9;
  }

  .editable-add-btn {
    margin-bottom: 8px;
  }
}
.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}
</style>
