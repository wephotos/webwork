<template>
  <a-layout class="a-layout">
    <a-layout-sider class="a-layout-sider">
      <a-tree :tree-data="treeData" v-model:expandedKeys="expandedKeys">
        <template #title="{ key: treeKey, title }">
          <a-dropdown :trigger="['contextmenu']">
            <span>{{ title }}</span>
            <template #overlay>
              <a-menu
                @click="
                  ({ key: menuKey }) => onContextMenuClick(treeKey, menuKey)
                "
              >
                <a-menu-item key="1">新增单位</a-menu-item>
                <a-menu-item key="2">新增部门</a-menu-item>
                <a-menu-item key="3">更新单位</a-menu-item>
                <a-menu-item key="4">更新部门</a-menu-item>
                <a-menu-item key="5">删除单位</a-menu-item>
                <a-menu-item key="6">删除部门</a-menu-item>
                <a-menu-item key="7">新增人员</a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </template>
      </a-tree>
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
            <a href="javascript:void(0);" @click="onUserSort(record)">排序</a>
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
const treeDataConst = [
  {
    title: '诗和远方',
    key: '0-0',
    children: [
      {
        title: '安徽',
        key: '0-0-0',
        children: [
          { title: '黄山', key: '0-0-0-0' },
          { title: '九华山', key: '0-0-0-1' },
          { title: '天柱山', key: '0-0-0-2' }
        ]
      },
      {
        title: '江苏',
        key: '0-0-1',
        children: [
          { title: '太湖', key: '0-0-1-0' },
          { title: '洪泽湖', key: '0-0-1-1' },
          { title: '微山湖', key: '0-0-1-2' }
        ]
      }
    ]
  }
]
// 分页数据类型
type Pagination = TableState['pagination'];

@Options({
  components: {
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

  mounted() {
    // console.log('mounted', this.$refs.searchInput)
  }

  updated() {
    // console.log('updated', this.$refs.searchInput)
  }

  // 表格变动监听
  handleTableChange(pag: Pagination, filters: TableStateFilters, sorter: {field: string; order: string}) {
      console.log(pag, filters, sorter)
      this.pagination.current = pag?.current || 1
  }

  /** 列表搜索 */
  handleSearch(selectedKeys: string[], confirm: Function, dataIndex: string) {
    confirm()
    this.searchText = selectedKeys[0]
    this.searchedColumn = dataIndex
    console.log(this.searchText, this.searchedColumn)
  }

  /** 查询条件重置 */
  handleReset(clearFilters: Function) {
    clearFilters()
    this.searchText = ''
  }

  // 组织树数据源
  treeData = treeDataConst
  // 展开的KEY
  expandedKeys: string[] = ['0-0-0', '0-0-1']
  // 右键菜单
  onContextMenuClick(treeKey: string, menuKey: string) {
    console.log(`treeKey: ${treeKey}, menuKey: ${menuKey}`)
  }

  // 属性监听
  watchExpandedKeys(value: string[], oldValue: string[]) {
    console.log('expandedKeys', value, oldValue)
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
