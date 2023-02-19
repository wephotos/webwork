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
                @click="(e) => onContextMenuClick(node)"
              >
                <a-menu-item key="create_dict">添加子级</a-menu-item>
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
              v-if="dataList.length && record.parentId"
              title="您确定要删除吗?"
              @confirm="onDictDel(record)"
            >
              <a href="javascript:void(0);">删除</a>
            </a-popconfirm>
            <a href="javascript:void(0);" @click="onDictEdit(record)">编辑</a>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
  <!-- 添加编辑Modal -->
  <a-modal v-model:visible="modalVisible" title="数据字典" @ok="handleOk">
    <a-form
      ref="formRef"
      :colon="false"
      :model="formData"
      :rules="rules"
      :label-col="{ span: 4 }"
      :wrapper-col="{ span: 18 }"
      style="margin-top: 15px"
    >
      <a-form-item label="父节点">
        <template v-if="formData.parentName">
          {{ formData.parentName }}
        </template>
        <template v-else> 无 </template>
      </a-form-item>
      <a-form-item label="字典名" name="name">
        <a-input v-model:value="formData.name" />
      </a-form-item>
      <a-form-item label="字典值" name="value">
        <a-input v-model:value="formData.value" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">
import { ref, toRaw, unref } from 'vue'
import { Options, Vue } from 'vue-class-component'
import { message } from 'ant-design-vue'
import { TreeDataItem, SelectEvent } from 'ant-design-vue/es/tree/Tree'
import { SearchOutlined } from '@ant-design/icons-vue'
import {
  TableState,
  TableStateFilters
} from 'ant-design-vue/es/table/interface'
import { TreeNode } from '@/types/TreeNode'
import request from '@/request/DictRequest'
import Pageable from '@/types/Pageable'
import { Dictionary } from '@/types/Dictionary'
import { R } from '@/types/R'
import { ValidateErrorEntity } from 'ant-design-vue/es/form/interface'

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
export default class DictionaryVue extends Vue {
  // 权限树数据源
  treeData: TreeDataItem[] = []
  // 字典Modal显示属性
  modalVisible = false
  // 表单数据
  formData: Dictionary = {}

  // 表单引用
  formRef = ref<AntType.Form>()
  // 验证规则
  rules = {
    name: [
      { required: true, message: '请输入字典名称', trigger: 'blur' },
      { max: 50, message: '字典名称最多50个字符' }
    ],
    value: [{ max: 50, message: '字典值最多50个字符' }]
  }

  // 表格列定义
  columns = [
    {
      title: '序号',
      dataIndex: 'number',
      width: 100,
      customRender: (info: {
        text: string;
        record: Dictionary;
        index: number;
      }) => {
        return (this.pageable.curr - 1) * this.pageable.size + info.index + 1
      }
    },
    {
      title: '字典ID',
      dataIndex: 'id',
      width: 100
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
    { title: '字典值', dataIndex: 'value' },
    { title: '父节点', dataIndex: 'parentName', width: 200 },
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
      width: 100,
      slots: { customRender: 'operation' }
    }
  ]

  // 当前操作树节点
  currentTreeNode!: TreeDataItem;

  // 表格数据
  dataList: Dictionary[] = []

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

  // 右键菜单 新建字典值
  onContextMenuClick(node: TreeDataItem & {}) {
    this.modalVisible = true
    this.formData = {
      name: '',
      value: '',
      parentId: node.key as number,
      parentName: node.title as string
    }
    this.currentTreeNode = node
  }

  // 节点点击事件
  onSelect(selectedKeys: string[], info: SelectEvent) {
    // console.log(selectedKeys, info)
    this.pageable.condition.parentId = info.node.dataRef.key
    this.pageQuery()
  }

  /** 转换树节点数据 */
  toTreeDataItem(nodes: TreeNode[]) {
    return nodes.map((node) => {
      return {
        key: node.id,
        title: node.name,
        code: node.code
      } as TreeDataItem
    })
  }

  // 转换单个树节点
  toTreeDataItemOne(record: Dictionary) {
    return {
      key: record.id,
      title: record.name,
      code: record.code
    } as TreeDataItem
  }

  // 查询树节点
  loop(
    data: TreeDataItem[],
    key: number,
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

  /** 字典模态框确定回调 */
  async handleOk() {
    const formUnref = unref(this.formRef)
    formUnref &&
      formUnref
        .validate()
        .then(async () => {
          const data = { ...toRaw(this.formData) }
          let ret: R<unknown>
          if (!this.formData.id) {
            ret = await request.add(data)
          } else {
            ret = await request.update(data)
          }
          if (ret.code === 0) {
            this.modalVisible = false
            this.loop(
              this.treeData,
              this.formData.id || this.formData.parentId || 0,
              (node) => {
                if (!this.currentTreeNode) {
                    return false
                }
                if (this.formData.id) {
                  node.title = data.name
                  node.value = data.value
                } else if (this.currentTreeNode.dataRef.children) {
                  this.currentTreeNode.dataRef.children.push(
                    this.toTreeDataItemOne({
                      id: ret.data as number,
                      ...data
                    })
                  )
                } else {
                  this.onLoadData(this.currentTreeNode)
                }
              }
            )
          } else {
            message.error(ret.msg)
          }
        })
        .catch((error: ValidateErrorEntity<Dictionary>) => {
          console.log('error', error)
        })
  }

  /** 删除权限 */
  async onDictDel(record: Dictionary) {
    const ret = await request.delete(record.id as number)
    if (ret.code === 0) {
      this.pageQuery()
      this.loop(this.treeData, record.id as number, (item, index, arr) => {
          arr.splice(index, 1)
      })
    } else {
      message.error(`删除失败:${ret.msg}`)
    }
  }

  // 编辑字典
  onDictEdit(record: Dictionary) {
    this.modalVisible = true
    this.formData = record
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
