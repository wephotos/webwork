<template>
  <a-layout class="a-layout">
    <a-layout-sider class="a-layout-sider" @contextmenu.prevent>
      <Group />
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
        <template #customRender="{ text, column }">
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
    </a-layout-content>
  </a-layout>
</template>
<script lang="ts">
import { User } from '@/types/User'
import { Options, Vue } from 'vue-class-component'
import { SearchOutlined } from '@ant-design/icons-vue'
import { TableState, TableStateFilters } from 'ant-design-vue/es/table/interface'
import { ref, unref } from 'vue'
import userRequest from '@/request/UserRequest'
import Pageable from '@/types/Pageable'
import UserForm from './UserForm.vue'
import Group from './Group.vue'
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
  },
  watch: {
    expandedKeys(value: string[], newValue: string[]) {
      this.watchExpandedKeys(value, newValue)
    }
  }
})
export default class VueUser extends Vue {
  // 用户列定义
  columns = [
    {
      title: '序号',
      dataIndex: 'number',
      width: 100
    },
    {
      title: '姓名',
      dataIndex: 'name',
      slots: {
        filterDropdown: 'filterDropdown',
        filterIcon: 'filterIcon',
        customRender: 'customRender'
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
    { title: '更新时间', dataIndex: 'updateTime', width: 200, sorter: true },
    {
      title: '操作',
      dataIndex: 'operation',
      width: 200,
      slots: { customRender: 'operation' }
    }
  ]

  // 表格数据
  users: User[] = [
    {
      id: 'tianqi',
      name: '田奇',
      account: 'tianq',
      phone: '18709827703',
      email: 'tq_email@qq.com',
      updateTime: '2021-04-03 17:23:36'
    }
  ]

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
    size: this.pagination.pageSize
  }

  mounted() {
    this.pageQueryUser()
  }

  // 分页查询用户
  async pageQueryUser() {
    const result = await userRequest.pageList(this.pageable)
    if (result.code === 0) {
      this.users = result.data.data
      this.pagination.total = result.data.count
      // 处理序号
      for (let i = 0; i < this.users.length; i++) {
        this.users[i].number = (this.pageable.curr - 1) * this.pageable.size + i + 1
      }
    } else {
      this.$toast(result.msg)
    }
  }

  // 表格变动监听
  handleTableChange(pag: Pagination, filters: TableStateFilters, sorter: {field: string; order: string}) {
      this.pagination.current = pag?.current || 1
      this.pageable.sortField = sorter.field || ''
      this.pageable.sortOrder = orderMap[sorter.order] || ''
      this.pageable.condition = { name: (filters.name instanceof Array ? filters.name[0] : '') }
      console.log(this.pageable)
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

  // 编辑人员
  onUserEdit(user: User) {
    this.$dialog({
      title: '用户信息',
      width: 550,
      height: 750,
      content: {
        handle: true,
        component: UserForm,
        props: { id: user.id }
      }
    })
  }

  // 人员置顶
  onUserTop(user: User) {
    console.log(user.sort)
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
