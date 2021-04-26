<template>
  <a-tree
    :load-data="onLoadData"
    :tree-data="treeData"
    v-model:expandedKeys="expandedKeys"
    @select="onSelect"
  >
    <template #title="{ key: treeKey, title, type }">
      <a-dropdown :trigger="['contextmenu']">
        <span>{{ title }}</span>
        <template #overlay>
          <a-menu
            @click="({ key: menuKey }) => onContextMenuClick(treeKey, menuKey)"
          >
            <!-- 单位菜单 -->
            <template v-if="type == 1">
                <a-menu-item key="add-dept">新增部门</a-menu-item>
                <a-menu-item key="add-group">新增单位</a-menu-item>
                <a-menu-item key="update-group">更新单位</a-menu-item>
                <a-menu-item key="delete-group">删除单位</a-menu-item>
            </template>
            <!-- 部门菜单 -->
            <template v-if="type == 2">
                <a-menu-item key="add-user">新增人员</a-menu-item>
                <a-menu-item key="update-dept">更新部门</a-menu-item>
                <a-menu-item key="delete-dept">删除部门</a-menu-item>
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
import request from '@/request/GroupRequest'
import { R } from '@/types/R'
import { Group } from '@/types/Group'

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

  /**
   * 页面挂载后
   */
  async mounted() {
    // 加载组织机构根节点
    const ret = await request.children()
    if (ret.code !== 0) {
      this.$toast(ret.msg)
      return false
    }
    this.treeData = this.toChildren(ret)
  }

  /**
   * 加载节点数据
   */
  onLoadData(treeNode: any) {
    return new Promise((resolve: (value?: unknown) => void) => {
      if (treeNode.dataRef.children) {
        resolve()
        return false
      }
      request.children(treeNode.dataRef.key).then((ret) => {
        treeNode.dataRef.children = this.toChildren(ret)
        // treeData.value = [...treeData.value];
        this.treeData = [...this.treeData]
        resolve()
      })
    })
  }

  // 节点点击事件
  onSelect(selectedKeys: string[], info: SelectEvent) {
    this.$emit('select', selectedKeys, info)
    console.log('selected', selectedKeys, info)
  }

  // 右键菜单
  onContextMenuClick(treeKey: string, menuKey: string) {
    console.log(`treeKey: ${treeKey}, menuKey: ${menuKey}`)
  }

  // 属性监听
  watchExpandedKeys(value: string[], oldValue: string[]) {
    console.log('expandedKeys', value, oldValue)
  }

  // 转为树节点类型
  toChildren(ret: R<Group[]>) {
    return ret.data.map((value) => {
      return {
        key: value.id,
        type: value.type, // 节点类型 0虚拟节点 1组织 2部门
        title: value.name
      } as TreeDataItem
    })
  }
}
</script>
