<template>
  <a-layout class="a-layout">
    <a-layout-sider class="a-layout-sider" @contextmenu.prevent>
      <Group @select="onSelectGroup" />
    </a-layout-sider>
    <a-layout-content class="a-layout-content">
      <a-table
        class="ant-table-striped"
        rowKey="id"
        :columns="columns"
        :data-source="users"
        :rowClassName="
          (record, index) => (index % 2 === 1 ? 'table-striped' : null)
        "
        :pagination="pagination"
        @change="handleTableChange"
      >
        <!-- 操作列模板 -->
        <template #operation="{ record }">
          <a-space>
            <a-popconfirm
              v-if="users.length"
              title="您确定要删除吗?"
              @confirm="onUserDel(record)"
            >
              <a href="javascript:void(0);">删除</a>
            </a-popconfirm>
            <a href="javascript:void(0);" @click="onUserTop(record)">置顶</a>
            <a href="javascript:void(0);" @click="onUserEdit(record)">编辑</a>
          </a-space>
        </template>
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
          <search-outlined
            :style="{ color: filtered ? '#108ee9' : undefined }"
          />
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
        <!-- 用户状态 -->
        <template #status="{record}">
          <a-switch :checked="record.status == 1" @click="onUserChange(record)" />
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
</template>
<script lang="ts">
import { ref, unref } from 'vue'
import { message } from 'ant-design-vue'
import { Options, Vue } from 'vue-class-component'
import { SearchOutlined } from '@ant-design/icons-vue'
import { SelectEvent } from 'ant-design-vue/es/tree/Tree'
import { TableState, TableStateFilters } from 'ant-design-vue/es/table/interface'
import { User } from '@/types/User'
import Group from './Group.vue'
import UserForm from './UserForm.vue'
import Pageable from '@/types/Pageable'
import request from '@/request/UserRequest'
// 分页数据类型
type Pagination = TableState['pagination'];
// 排序映射
const orderMap: {[key: string]: string} = {
  ascend: 'ASC',
  descend: 'DESC'
}
@Options({
  components: {
    Group,
    SearchOutlined
  }
})
export default class VueUser extends Vue {
  // 用户列定义
  columns = [
    {
      title: '序号',
      dataIndex: 'number',
      width: 100,
      customRender: (info: {
        text: string;
        record: User;
        index: number;
      }) => {
        return (this.pageable.curr - 1) * this.pageable.size + info.index + 1
      }
    },
    {
      title: '姓名',
      dataIndex: 'name',
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
    { title: '账号', dataIndex: 'account' },
    { title: '手机', dataIndex: 'phone', width: 200 },
    { title: '邮箱', dataIndex: 'email', width: 200 },
    { title: '更新时间', dataIndex: 'updateTime', width: 200, sorter: true, key: 'update_time' },
    {
        title: '状态',
        width: 100,
        dataIndex: 'status',
        slots: { customRender: 'status' }
    },
    {
      title: '操作',
      dataIndex: 'operation',
      width: 200,
      slots: { customRender: 'operation' }
    }
  ]

  // 表格数据
  users: User[] = []

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

  mounted() {
    this.pageQueryUser()
  }

  // 分页查询用户
  async pageQueryUser() {
    const result = await request.pageList(this.pageable)
    if (result.code === 0) {
      this.users = result.data.data
      this.pagination.total = result.data.count
    } else {
      message.error(result.msg)
    }
  }

  // 表格变动监听
  handleTableChange(pag: Pagination, filters: TableStateFilters, sorter: {field: string; order: string; columnKey: string}) {
      this.pagination.current = pag?.current || 1
      this.pageable.sortField = sorter.columnKey || ''
      this.pageable.sortOrder = orderMap[sorter.order] || ''
      this.pageable.condition.name = (filters.name instanceof Array ? filters.name[0] : '')
      this.pageQueryUser()
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

  /** 删除人员 */
  async onUserDel(user: User) {
    const ret = await request.delete(user.id as string)
    if (ret.code === 0) {
      this.pageQueryUser()
    } else {
      message.error(`删除失败:${ret.msg}`)
    }
  }

  // 编辑人员
  onUserEdit(user: User) {
    this.$dialog({
      title: '用户信息',
      width: 550,
      height: 650,
      max: false,
      content: {
        handle: true,
        component: UserForm,
        props: { id: user.id }
      },
      ok: () => {
        this.pageQueryUser()
        return true
      }
    })
  }

  // 人员置顶
  async onUserTop(user: User) {
    const ret = await request.top(user.id as string)
    if (ret.code === 0) {
      this.pageQueryUser()
    } else {
      message.error(`置顶失败:${ret.msg}`)
    }
  }

  userStatus(user: User) {
    return user.status === 1
  }

  /** 改变用户状态 */
  async onUserChange(user: User) {
    user.status = user.status === 1 ? 0 : 1
    const ret = await request.update(user)
    if (ret.code !== 0) {
      message.error(ret.msg)
      user.status = user.status === 1 ? 0 : 1
    }
  }

  // 节点选择
  onSelectGroup(selectedKeys: string[], info: SelectEvent) {
    console.log(selectedKeys, info)
    this.pageable.condition.orgcode = info.node.dataRef.code
    this.pageQueryUser()
  }
}
</script>
<style scoped>
.a-layout {
  height: 100%;
}
.a-layout-sider {
  width: 250px;
  overflow: auto;
  background-color: #fff;
}
.a-layout-content {
  padding: 5px 2px;
  overflow: auto;
}
.highlight {
  background-color: rgb(255, 192, 105);
  padding: 0px;
}
</style>
