<template >
  <a-tree
    :load-data="onLoadData"
    :tree-data="treeData"
    v-model:expandedKeys="expandedKeys"
    draggable
    @select="onSelect"
    style="height: 100%"
    @contextmenu.prevent
  >
    <template #title="node">
      <a-dropdown :trigger="['contextmenu']">
        <span>{{ node.title }}</span>
        <template #overlay>
          <a-menu
            @click="({ key: menuKey }) => onContextMenuClick(node, menuKey)"
            @contextmenu.prevent
          >
            <!-- 单位菜单 -->
            <template v-if="node.type == 1">
              <a-menu-item :key="contextMenuKeys.ADD_DEPT"
                >新增部门</a-menu-item
              >
              <a-menu-item :key="contextMenuKeys.ADD_GROUP"
                >新增单位</a-menu-item
              >
              <a-menu-item :key="contextMenuKeys.UPDATE_GROUP"
                >更新单位</a-menu-item
              >
              <a-menu-item :key="contextMenuKeys.DELETE_GROUP">
                删除单位
              </a-menu-item>
            </template>
            <!-- 部门菜单 -->
            <template v-if="node.type == 2">
              <a-menu-item :key="contextMenuKeys.ADD_USER"
                >新增人员</a-menu-item
              >
              <a-menu-item :key="contextMenuKeys.UPDATE_DEPT"
                >更新部门</a-menu-item
              >
              <a-menu-item :key="contextMenuKeys.DELETE_DEPT"
                >删除部门</a-menu-item
              >
            </template>
          </a-menu>
        </template>
      </a-dropdown>
    </template>
  </a-tree>
</template>
<script lang="ts">
import { TreeDataItem, SelectEvent } from 'ant-design-vue/es/tree/Tree'
import { Options, Vue } from 'vue-class-component'
import { message, Modal } from 'ant-design-vue'
import request from '@/request/GroupRequest'
import { R } from '@/types/R'
import { Group } from '@/types/Group'
import UserForm from './UserForm.vue'
import GroupForm from './GroupForm.vue'
// 右键菜单键
enum ContextMenuKeys {
  // 新增人员
  ADD_USER,
  // 添加部门
  ADD_DEPT,
  // 添加单位
  ADD_GROUP,
  // 更新部门
  UPDATE_DEPT,
  // 更新单位
  UPDATE_GROUP,
  // 删除部门
  DELETE_DEPT,
  // 删除单位
  DELETE_GROUP
}
/**
 * 组织机构管理
 */
@Options({
  emits: ['select']
})
export default class GroupVue extends Vue {
  // 组织树数据源
  treeData: TreeDataItem[] = []
  // 展开的KEY
  expandedKeys: string[] = []
  // 右键菜单
  contextMenuKeys = ContextMenuKeys
  /**
   * 页面挂载后
   */
  async mounted() {
    // 加载组织机构根节点
    const ret = await request.children()
    if (ret.code !== 0) {
      message.error(ret.msg)
      return false
    }
    this.treeData = this.toChildren(ret)
  }

  /**
   * 加载节点数据
   */
  onLoadData(treeNode: TreeDataItem & { dataRef: TreeDataItem }) {
    return new Promise((resolve: (value?: unknown) => void) => {
      if (treeNode.dataRef.children) {
        resolve()
        return false
      }
      request.children(treeNode.dataRef.key as string).then((ret) => {
        treeNode.dataRef.children = this.toChildren(ret)
        this.treeData = [...this.treeData]
        resolve()
      })
    })
  }

  // 节点点击事件
  onSelect(selectedKeys: string[], info: SelectEvent) {
    this.$emit('select', selectedKeys, info)
  }

  // 右键菜单
  onContextMenuClick(node: TreeDataItem & {}, menuKey: ContextMenuKeys) {
    let title
    let props: {
      id?: string;
      type?: number;
      parentId?: string;
      parentName?: string;
    }
    if (ContextMenuKeys.ADD_DEPT === menuKey) {
      title = '新增部门'
      props = {
        type: 2,
        parentId: node.key as string,
        parentName: node.title
      }
    } else if (ContextMenuKeys.ADD_GROUP === menuKey) {
      title = '新增单位'
      props = {
        type: 1,
        parentId: node.key as string,
        parentName: node.title
      }
    } else if (ContextMenuKeys.UPDATE_DEPT === menuKey) {
      title = '更新部门'
      props = {
        type: 2,
        id: node.key as string
      }
    } else if (ContextMenuKeys.UPDATE_GROUP === menuKey) {
      title = '更新单位'
      props = {
        type: 1,
        id: node.key as string
      }
    } else if (
      ContextMenuKeys.DELETE_DEPT === menuKey ||
      ContextMenuKeys.DELETE_GROUP === menuKey
    ) {
      Modal.confirm({
        title: `您确定要删除<${node.title}>吗?`,
        okType: 'danger',
        onOk: () => {
          request
            .delete(node.key as string)
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
    } else if (ContextMenuKeys.ADD_USER === menuKey) {
      this.$dialog({
        title: '新增用户',
        width: 550,
        height: 750,
        content: {
          handle: true,
          component: UserForm,
          props: { deptId: node.key, deptName: name }
        }
      })
      return false
    } else {
      console.log(`unknow menukey ${menuKey}`)
      return false
    }
    this.$dialog({
      title: title,
      width: 550,
      height: 400,
      content: {
        handle: true,
        props: props,
        component: GroupForm
      },
      ok: (args: unknown[]) => {
        const data = args[0] as Group
        if (node.key === data.id) {
          // 更新
          node.dataRef.title = data.name
        } else if (node.key === data.parentId) {
          // 当前节点下新增
          node.dataRef.children.push(this.dataToTreeDataItem(data))
        } else {
          // 非当前节点下新增
          this.loop(
            this.treeData,
            data.parentId as string,
            (item, index, arr) => {
              item.children && item.children.push(this.dataToTreeDataItem(data))
            }
          )
        }
        return true
      }
    })
  }

  // 属性监听
  watchExpandedKeys(value: string[], oldValue: string[]) {
    console.log('expandedKeys', value, oldValue)
  }

  // 转为树节点类型
  toChildren(ret: R<Group[]>) {
    return ret.data.map((value) => {
      return this.dataToTreeDataItem(value)
    })
  }

  // 数据转树节点
  dataToTreeDataItem(data: Group) {
    return {
      key: data.id,
      type: data.type, // 节点类型 0虚拟节点 1组织 2部门
      title: data.name,
      code: data.code,
      isLeaf: data.type === 2
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
}
</script>
