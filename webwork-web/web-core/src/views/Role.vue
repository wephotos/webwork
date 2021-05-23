<template>
  <a-layout class="a-layout">
    <a-layout-sider class="a-layout-sider" @contextmenu.prevent>
      <a-tree
        :load-data="onLoadData"
        :tree-data="treeData"
        @select="onSelect"
        style="height: 100%"
        @contextmenu.prevent
      >
        <template #title="node">
          <a-dropdown :trigger="['contextmenu']">
            <span @contextmenu.prevent>{{ node.title }}</span>
            <template #overlay>
              <a-menu
                @click="({ key: menuKey }) => onContextMenuClick(node, menuKey)"
              >
                <!-- 应用菜单 -->
                <template v-if="node.type == 1">
                  <a-menu-item :key="create_role">新建角色</a-menu-item>
                </template>
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
        :data-source="roles"
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
        <!-- 操作列模板 -->
        <template #operation="{ record }">
          <a-space>
            <a-popconfirm
              v-if="roles.length"
              title="您确定要删除吗?"
              @confirm="onRoleDel(record)"
            >
              <a href="javascript:void(0);">删除</a>
            </a-popconfirm>
            <a href="javascript:void(0);" @click="onRoleEdit(record)">编辑</a>
            <a href="javascript:void(0);" @click="onRoleUser(record)">人员</a>
            <a href="javascript:void(0);" @click="onRoleReso(record)"
              >权限</a
            >
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
</template>
<script lang="ts">
import { ref, unref } from 'vue'
import { Options, Vue } from 'vue-class-component'
import { message } from 'ant-design-vue'
import { TreeDataItem, SelectEvent } from 'ant-design-vue/es/tree/Tree'
import { SearchOutlined } from '@ant-design/icons-vue'
import {
  TableState,
  TableStateFilters
} from 'ant-design-vue/es/table/interface'
import { TreeNode } from '@/types/TreeNode'
import request from '@/request/RoleRequest'
import Pageable from '@/types/Pageable'
import { Role } from '@/types/Role'
import RoleForm from './RoleForm.vue'
import RoleUserVue from './RoleUser.vue'
import RoleResoVue from './RoleReso.vue'

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
export default class RoleVue extends Vue {
  // 权限树数据源
  treeData: TreeDataItem[] = []

  // 权限列定义
  columns = [
    {
      title: '序号',
      dataIndex: 'number',
      width: 100,
      customRender: (info: { text: string; record: Role; index: number }) => {
        return (this.pageable.curr - 1) * this.pageable.size + info.index + 1
      }
    },
    {
      title: '名称',
      dataIndex: 'name',
      width: 220,
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
    { title: '所属应用', dataIndex: 'app', width: 200 },
    { title: '备注', dataIndex: 'remark' },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      width: 200,
      sorter: true,
      key: 'create_time'
    },
    {
      title: '操作',
      dataIndex: 'operation',
      width: 200,
      slots: { customRender: 'operation' }
    }
  ]

  // 表格数据
  roles: Role[] = []

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

  /**
   * 页面挂载后
   */
  async mounted() {
    // 加载组织机构根节点
    const ret = await request.listNodes()
    if (ret.code !== 0) {
      message.error(ret.msg)
      return false
    }
    this.treeData = this.toTreeDataItem(ret.data)
    // 加载列表
    this.pageQuery()
  }

  /**
   * 加载节点数据
   */
  onLoadData(treeNode: TreeDataItem) {
    return new Promise((resolve: (value?: unknown) => void) => {
      if (treeNode.dataRef.children) {
        resolve()
        return false
      }
      request.listNodes(treeNode.dataRef.key as string).then((ret) => {
        treeNode.dataRef.children = this.toTreeDataItem(ret.data)
        this.treeData = [...this.treeData]
        resolve()
      })
    })
  }

  // 右键菜单
  onContextMenuClick(node: TreeDataItem & {}) {
      this.$dialog({
      title: '新建角色',
      width: 550,
      height: 300,
      content: {
        handle: true,
        props: {
            parentId: node.key,
            parentName: node.title
        },
        component: RoleForm
      },
      ok: () => {
        this.pageQuery()
        return true
      }
    })
  }

  // 节点点击事件
  onSelect(selectedKeys: string[], info: SelectEvent) {
    console.log(selectedKeys, info)
    this.pageable.condition.parentId = info.node.dataRef.key
    this.pageQuery()
  }

  /** 转换树节点数据 */
  toTreeDataItem(nodes: TreeNode[]) {
    return nodes.map((node) => {
      return {
        key: node.id,
        type: node.type, // 节点类型 0单位 1应用 2角色
        title: node.name,
        code: node.code,
        isLeaf: node.type === 1
      } as TreeDataItem
    })
  }

  toTreeDataItemOne(record: Role) {
    return {
      key: record.id,
      type: record.type, // 节点类型 0单位 1应用 2角色
      title: record.name,
      code: record.code,
      isLeaf: record.type === 1
    } as TreeDataItem
  }

  // 查询树节点
  loop(
    data: TreeDataItem[],
    key: string,
    callback: (item: TreeDataItem, index: number, arr: TreeDataItem[]) => void
  ) {
    data.forEach((item, index, arr) => {
      if (item.key === key) {
        return callback(item, index, arr)
      }
      if (item.children) {
        return this.loop(item.children, key, callback)
      }
    })
  }

  // 分页查询资源列表
  async pageQuery() {
    const result = await request.pageList(this.pageable)
    if (result.code === 0) {
      this.roles = result.data.data
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

  /** 删除权限 */
  async onRoleDel(record: Role) {
    const ret = await request.delete(record.id as string)
    if (ret.code === 0) {
      this.pageQuery()
    } else {
      message.error(`删除失败:${ret.msg}`)
    }
  }

  // 编辑角色
  onRoleEdit(record: Role) {
    this.$dialog({
      title: '编辑角色',
      width: 550,
      height: 300,
      max: false,
      content: {
        handle: true,
        component: RoleForm,
        props: { id: record.id }
      },
      ok: () => {
        this.pageQuery()
        return true
      }
    })
  }

  /** 角色人员 */
  onRoleUser(record: Role) {
    this.$dialog({
      title: '角色人员',
      width: 550,
      height: 600,
      max: false,
      content: {
        handle: true,
        component: RoleUserVue,
        props: { roleId: record.id }
      },
      ok: () => {
        return true
      }
    })
  }

  /** 角色授权 */
  onRoleReso(record: Role) {
    this.$dialog({
      title: '角色权限',
      width: 400,
      height: 600,
      max: false,
      content: {
        handle: true,
        component: RoleResoVue,
        props: { roleId: record.id, parentId: record.parentId }
      },
      ok: () => {
        return true
      }
    })
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
