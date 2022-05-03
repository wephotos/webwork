<template>
  <a-layout class="a-layout">
    <a-layout-sider class="a-layout-sider" @contextmenu.prevent>
      <a-tree
        :load-data="onLoadData"
        :tree-data="treeData"
        draggable
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
                <!-- 单位菜单 -->
                <template v-if="node.type == 0">
                  <a-menu-item :key="contextMenuKeys.ADD_APP"
                    >新建应用</a-menu-item
                  >
                </template>
                <!-- 应用菜单 -->
                <template v-if="node.type == 1">
                  <a-menu-item :key="contextMenuKeys.ADD_MOD"
                    >新建模块</a-menu-item
                  >
                  <a-menu-item :key="contextMenuKeys.ADD_FUN"
                    >新建功能</a-menu-item
                  >
                  <a-menu-item :key="contextMenuKeys.UPDATE_APP"
                    >更新应用</a-menu-item
                  >
                  <a-menu-item :key="contextMenuKeys.DELETE_APP"
                    >删除应用</a-menu-item
                  >
                </template>
                <!-- 模块菜单 -->
                <template v-if="node.type == 2">
                  <a-menu-item :key="contextMenuKeys.ADD_FUN"
                    >新建功能</a-menu-item
                  >
                  <a-menu-item :key="contextMenuKeys.UPDATE_MOD"
                    >更新模块</a-menu-item
                  >
                  <a-menu-item :key="contextMenuKeys.DELETE_MOD"
                    >删除模块</a-menu-item
                  >
                  <a-menu-item :key="contextMenuKeys.ADD_MOD"
                    >新建子模块</a-menu-item
                  >
                </template>
                <!-- 功能菜单 -->
                <template v-if="node.type == 3">
                  <a-menu-item :key="contextMenuKeys.UPDATE_FUN"
                    >更新功能</a-menu-item
                  >
                  <a-menu-item :key="contextMenuKeys.DELETE_FUN"
                    >删除功能</a-menu-item
                  >
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
        :data-source="resources"
        :rowClassName="
          (record, index) => (index % 2 === 1 ? 'table-striped' : null)
        "
        :pagination="pagination"
        @change="handleTableChange"
      >
        <!-- 操作列模板 -->
        <template #typeRender="{ record }">
          <template v-if="record.type == 1"> 应用 </template>
          <template v-else-if="record.type == 2"> 模块 </template>
          <template v-else> 功能 </template>
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
      </a-table>
    </a-layout-content>
  </a-layout>
</template>
<script lang="ts">
import { ref, unref } from 'vue'
import { Options, Vue } from 'vue-class-component'
import { message, Modal } from 'ant-design-vue'
import { TreeDataItem, SelectEvent } from 'ant-design-vue/es/tree/Tree'
import { SearchOutlined } from '@ant-design/icons-vue'
import {
  TableState,
  TableStateFilters
} from 'ant-design-vue/es/table/interface'
import { TreeNode } from '@/types/TreeNode'
import request from '@/request/ResRequest'
import Pageable from '@/types/Pageable'
import ResourceForm from './ResourceForm.vue'
import { Resource } from '@/types/Resource'
// 右键菜单键
enum ContextMenuKeys {
  // 新增应用
  ADD_APP,
  // 新增模块
  ADD_MOD,
  // 新增功能
  ADD_FUN,
  // 更新应用
  UPDATE_APP,
  // 更新模块
  UPDATE_MOD,
  // 更新功能
  UPDATE_FUN,
  // 删除应用
  DELETE_APP,
  // 删除单位
  DELETE_MOD,
  // 删除功能
  DELETE_FUN
}
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
export default class ResourceVue extends Vue {
  // 权限树数据源
  treeData: TreeDataItem[] = []
  // 右键菜单KEY定义
  contextMenuKeys = ContextMenuKeys

  // 权限列定义
  columns = [
    {
      title: '序号',
      dataIndex: 'number',
      width: 100,
      customRender: (info: {
        text: string;
        record: Resource;
        index: number;
      }) => {
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
    { title: '类别', dataIndex: 'type', width: 200, slots: { customRender: 'typeRender' } },
    { title: '编码', dataIndex: 'permission', width: 200 },
    { title: '地址', dataIndex: 'url' },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      width: 200,
      sorter: true,
      key: 'create_time'
    }
  ]

  // 表格数据
  resources: Resource[] = []

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
      request.listNodes(treeNode.dataRef.key as number).then((ret) => {
        treeNode.dataRef.children = this.toTreeDataItem(ret.data)
        this.treeData = [...this.treeData]
        resolve()
      })
    })
  }

  // 节点点击事件
  onSelect(selectedKeys: string[], info: SelectEvent) {
    console.log(selectedKeys, info)
    this.pageable.condition.code = info.node.dataRef.code
    this.pageQuery()
  }

  // 右键菜单
  onContextMenuClick(node: TreeDataItem & {}, menuKey: ContextMenuKeys) {
    if (ContextMenuKeys.DELETE_APP === menuKey ||
        ContextMenuKeys.DELETE_MOD === menuKey ||
        ContextMenuKeys.DELETE_FUN === menuKey) {
        Modal.confirm({
        title: `您确定要删除<${node.title}>吗?`,
        okType: 'danger',
        onOk: () => {
          request
            .delete(node.key as number)
            .then((res) => {
              if (res.code === 0) {
                message.success('删除成功')
                this.loop(
                  this.treeData,
                  node.key as string,
                  (item, index, arr) => {
                    arr.splice(index, 1)
                  }
                )
              } else {
                message.error('删除失败:' + res.msg)
              }
            })
            .catch((err) => {
              message.error(err.message)
            })
        }
      })
      return false
    }
    // 标题
    const titleMap = new Map([
      [ContextMenuKeys.ADD_APP, '新建应用'],
      [ContextMenuKeys.ADD_MOD, '新建模块'],
      [ContextMenuKeys.ADD_FUN, '新建功能'],
      [ContextMenuKeys.UPDATE_APP, '更新应用'],
      [ContextMenuKeys.UPDATE_MOD, '更新模块']
    ])
    // 参数
    const props: {
      id?: string;
      type?: number;
      parentId?: string;
      parentName?: string;
    } = {
      id: node.key as string,
      parentId: node.key as string,
      parentName: node.title as string
    }
    switch (menuKey) {
      case ContextMenuKeys.ADD_APP:
        props.type = 1
        delete props.id
        break
      case ContextMenuKeys.ADD_MOD:
        props.type = 2
        delete props.id
        break
      case ContextMenuKeys.ADD_FUN:
        props.type = 3
        delete props.id
        break
      case ContextMenuKeys.UPDATE_APP:
      case ContextMenuKeys.UPDATE_MOD:
      case ContextMenuKeys.UPDATE_FUN:
        delete props.parentId
        delete props.parentName
        break
      default:
        console.log(menuKey)
        break
    }
    this.$dialog({
      title: titleMap.get(menuKey),
      width: 550,
      height: 650,
      content: {
        handle: true,
        props: props,
        component: ResourceForm
      },
      ok: (args: unknown[]) => {
        const data = args[0] as Resource
        // 更新
        if (node.key === data.id) {
          node.dataRef.title = data.name
        } else if (node.key === data.parentId) {
          if (node.dataRef.children) {
              node.dataRef.children.push(this.toTreeDataItemOne(data))
          } else {
              this.onLoadData(node)
          }
        } else {
          console.log('添加成功', data)
        }
        return true
      }
    })
  }

  /** 转换树节点数据 */
  toTreeDataItem(nodes: TreeNode[]) {
    return nodes.map((node) => {
      return {
        key: node.id,
        type: node.type, // 节点类型 0单位 1应用 2模块 3功能
        title: node.name,
        code: node.code,
        isLeaf: node.type === 3
      } as TreeDataItem
    })
  }

  toTreeDataItemOne(record: Resource) {
    return {
      key: record.id,
      type: record.type, // 节点类型 0单位 1应用 2模块 3功能
      title: record.name,
      code: record.code,
      isLeaf: record.type === 3
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
      this.resources = result.data.data
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
  async onResourceDel(record: Resource) {
    const ret = await request.delete(record.id as number)
    if (ret.code === 0) {
      this.pageQuery()
    } else {
      message.error(`删除失败:${ret.msg}`)
    }
  }

  // 编辑权限
  onResourceEdit(resource: Resource) {
    this.$dialog({
      title: '权限编辑',
      width: 550,
      height: 650,
      max: false,
      content: {
        handle: true,
        component: ResourceForm,
        props: { id: resource.id }
      },
      ok: () => {
        this.pageQuery()
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
