<template >
  <a-tree
    :load-data="onLoadData"
    :tree-data="treeData"
    v-model:expandedKeys="expandedKeys"
    draggable
    @select="onSelect"
    style="height: 100%"
    @contextmenu.prevent
    @drop="onDrop"
  >
    <template #title="node">
      <a-dropdown :trigger="['contextmenu']">
        <span @contextmenu.prevent>{{ node.title }}</span>
        <template #overlay>
          <a-menu
            @click="onContextMenuClick(node, $event)"
          >
            <!-- 单位菜单 -->
            <template v-if="node.type == 0 || node.type == 1">
              <a-menu-item :key="contextMenuKeys.ADD_DEPT"
                >新增部门</a-menu-item
              >
              <a-menu-item :key="contextMenuKeys.ADD_GROUP"
                >新增单位</a-menu-item
              >
              <a-menu-item :key="contextMenuKeys.UPDATE_GROUP"
                >更新单位</a-menu-item
              >
              <!-- 根节点不可删除 -->
              <a-menu-item v-if="node.parentId != null" :key="contextMenuKeys.DELETE_GROUP">
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
import { TreeNode } from '@/types/TreeNode'
import UserForm from './UserForm.vue'
import GroupForm from './GroupForm.vue'
import { TreeNodeType } from '@/types/TreeNodeType'
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
  // 右键菜单枚举
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
  onLoadData(treeNode: TreeDataItem) {
    return new Promise((resolve: (value?: unknown) => void) => {
      if (treeNode.dataRef.children) {
        resolve()
        return false
      }
      request.children(treeNode.dataRef.rawId as number).then((ret) => {
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
  onContextMenuClick(node: TreeDataItem, e: { key: ContextMenuKeys; [key: string]: any }) {
    const menuKey = e.key
    let title
    let formData: {
      id?: number; // rawId
      type: number; // enum NodeType
      parentId?: number; // 父节点ID
      parentType?: number; // 父节点类型
      parentName?: string; // 父节点名称
    }
    if (ContextMenuKeys.ADD_DEPT === menuKey) {
      title = '新增部门'
      formData = {
        type: TreeNodeType.DEPT,
        parentId: node.rawId,
        parentType: node.type,
        parentName: node.title
      }
    } else if (ContextMenuKeys.ADD_GROUP === menuKey) {
      title = '新增单位'
      formData = {
        type: TreeNodeType.GROUP,
        parentId: node.rawId,
        parentType: node.type,
        parentName: node.title
      }
    } else if (ContextMenuKeys.UPDATE_DEPT === menuKey) {
      title = '更新部门'
      formData = {
        id: node.rawId,
        type: TreeNodeType.DEPT
      }
    } else if (ContextMenuKeys.UPDATE_GROUP === menuKey) {
      title = '更新单位'
      formData = {
        id: node.rawId,
        type: TreeNodeType.GROUP
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
            .delete(node.rawId)
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
        height: 650,
        max: false,
        content: {
          handle: true,
          component: UserForm,
          props: { deptId: node.rawId, deptName: node.title }
        },
        ok: () => {
            this.$emit('select', [node.key], {
                node: {
                    dataRef: {
                        code: node.code
                    }
                }
            })
            return true
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
        props: formData,
        component: GroupForm
      },
      ok: (args: unknown[]) => {
        const data = args[0] as Group
        const key = `${data.type}_${data.id}`
        const parentKey = `${formData.parentType}_${formData.parentId}`
        const treeNode = {
                            id: key,
                            rawId: data.id,
                            type: data.type,
                            name: data.name,
                            code: data.code
                          } as TreeNode
        if (node.key === key) {
          // 更新节点名称
          node.dataRef.title = data.name
        } else if (node.key === parentKey) {
          // 当前节点下新增
          if (node.dataRef.children) {
            node.dataRef.children.push(this.dataToTreeDataItem(treeNode))
          } else {
            this.onLoadData(node)
          }
        } else {
          // 非当前节点下新增
          this.loop(
            this.treeData,
            parentKey,
            (item, index, arr) => {
              item.children && item.children.push(this.dataToTreeDataItem(treeNode))
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
  toChildren(ret: R<TreeNode[]>) {
    return ret.data.map((value) => {
      return this.dataToTreeDataItem(value)
    })
  }

  // 数据转树节点
  dataToTreeDataItem(data: TreeNode) {
    return {
      key: data.id,
      type: data.type,
      title: data.name,
      code: data.code,
      rawId: data.rawId,
      sort: data.sort,
      parentId: data.parentId,
      isLeaf: data.type === TreeNodeType.DEPT
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

  // 拖拽排序
  onDrop(info: { node: TreeDataItem; dragNode: TreeDataItem; dropToGap: boolean; dropPosition: number; [key: string]: any }) {
    // console.log(info)
    // 忽略拖入节点内的操作
    if (!info.dropToGap) {
      return false
    }
    // 只能同一层级内拖拽
    const dragPos = info.dragNode.pos as string
    if (info.node.pos.substring(0, info.node.pos.length - 1) !== dragPos.substring(0, dragPos.length - 1)) {
      return false
    }
    const dropKey = info.node.dataRef.key as string
    const dragKey = info.dragNode.dataRef.key as string
    const dropPos = info.node.pos.split('-')
    const dropPosition = info.dropPosition - Number(dropPos[dropPos.length - 1])

    // Find dragObject
    let dragIndex = 0
    let dragObj: TreeDataItem = {}
    this.loop(this.treeData, dragKey, (item: TreeDataItem, index: number, arr: TreeDataItem[]) => {
      arr.splice(index, 1)
      dragObj = item
      dragIndex = index
    })

    let dropIndex = 0
    let nodes: TreeDataItem[] = []
    this.loop(this.treeData, dropKey, (_item: TreeDataItem, index: number, arr: TreeDataItem[]) => {
      nodes = arr
      dropIndex = index
    })
    // debugger
    if (dropPosition === -1) {
      nodes.splice(dropIndex, 0, dragObj)
    } else {
      nodes.splice(dropIndex + 1, 0, dragObj)
    }

    // 重新查找拖拽节点, 向上拖拽取下节点，向下拖拽取上节点
    let targetNode: TreeDataItem = {}
    this.loop(this.treeData, dragKey, (item: TreeDataItem, index: number, arr: TreeDataItem[]) => {
      if (index < dragIndex) {
        targetNode = arr[index + 1]
      } else if (index > dragIndex) {
        targetNode = arr[index - 1]
      }
    })
    if (!targetNode.key) {
      return false
    }
    // 获取父节点ID
    let parentId
    this.loopParentNode(dragKey, {}, this.treeData, (pNode: TreeDataItem) => {
      parentId = pNode.rawId
    })
    const dropSort = {
      parent: parentId,
      sort: dragObj.sort,
      targetSort: targetNode.sort
    }
    request.dropSort(dropSort).then(ret => {
      console.log(ret.code, ret.msg, ret.data)
    })
    return false
  }

  // 查询父节点
  loopParentNode(key: string, pNode: TreeDataItem, children: TreeDataItem[], callback: (pNode: TreeDataItem) => void) {
    children.forEach((item, index, arr) => {
      if (key === item.key) {
        return callback(pNode)
      }
      if (item.children) {
        this.loopParentNode(key, item, item.children, callback)
      }
    })
  }
}
</script>
