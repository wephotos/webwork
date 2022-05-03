<template>
    <a-tree
        checkable
        :checkStrictly="true"
        default-expand-all
        v-if="treeData.length"
        :tree-data="treeData"
        :load-data="onLoadData"
        v-model:checkedKeys="checkedKeys"
        @select="onSelect"
        @contextmenu.prevent
      >
    </a-tree>
    <div class="footer">
        <a-space>
          <a-button @click="onCancel">取消</a-button>
          <a-button @click="onOk" type="primary">确认</a-button>
        </a-space>
    </div>
</template>
<script lang="ts">
import Dialog from '@/components/Dialog.vue'
import { PropType } from '@vue/runtime-core'
import { Options, Vue } from 'vue-class-component'
import { message } from 'ant-design-vue'
import { SelectEvent, TreeDataItem } from 'ant-design-vue/es/tree/Tree'
import request from '@/request/ResRequest'
import { TreeNode } from '@/types/TreeNode'
import BaseRequest from '@/request/BaseRequest'
// 角色资源数据定义
export type RoleReso = {
  roleId: number;
  resourceId: number;
}
// 树选中对象
export type CheckStrictly = {
  checked: number[];
  halfChecked: number[];
}
@Options({
  props: {
    roleId: {
      type: Number
    },
    parentId: {
      type: Number
    },
    dialog: Object as PropType<Dialog>
  }
})
export default class RoleResoVue extends Vue {
  // 节点ID
  roleId!: number
  // 角色上级
  parentId!: number
  // 当前弹框
  dialog!: Dialog
  // 权限树数据源
  treeData: TreeDataItem[] = []
  // 选中节点
  checkedKeys: CheckStrictly = { checked: [], halfChecked: [] }
  // HTTP请求
  httpReq = new BaseRequest()
  // 加载应用树
  async mounted() {
    // 加载已勾选资源
    const resret = await request.listByRoleId(this.roleId)
    if (resret.code === 0) {
      this.checkedKeys.checked = resret.data.map(item => item.id as number)
    } else {
      message.error(resret.msg)
    }
    // 加载组织机构根节点
    const ret = await request.deepListNodes(this.parentId)
    if (ret.code !== 0) {
      message.error(ret.msg)
      return false
    }
    this.treeData = this.toTreeDataItem(ret.data)
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
  }

  // 取消
  onCancel() {
    this.dialog._close()
  }

  // 确认
  onOk() {
    const ck = this.checkedKeys
    const resources = ck.checked.concat(ck.halfChecked)
    this.httpReq.post('/role-resource/save', {
      roleId: this.roleId,
      resources: resources
    }).then(ret => {
      if (ret.code === 0) {
        message.success('授权成功')
        this.dialog._ok()
      } else {
        message.error(ret.msg)
      }
    }).catch(reason => {
      message.error(reason)
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
}
</script>
<style scoped>
.footer{
  bottom: 5px;
  width: 100%;
  position: absolute;
  padding: 10px 16px;
  text-align: right;
  background: transparent;
  border-top: 1px solid #f0f0f0;
  border-radius: 0 0 2px 2px;
}
</style>
