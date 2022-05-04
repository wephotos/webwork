<template>
  <a-row class="main">
    <a-col :span="12" class="border-right">
      <a-tree
        :tree-data="treeData"
        :load-data="onLoadData"
        @select="onSelect"
        style="height: 100%"
        @contextmenu.prevent
      >
      </a-tree>
    </a-col>
    <a-col :span="12" class="content">
      <a-tag
        v-for="item in users"
        :key="item.id"
        closable
        color="green"
        @close="handleDelete(item)"
      >
        <template #icon>
          <UserOutlined v-if="item.userType == 3" />
          <TeamOutlined v-else />
        </template>
        {{ item.userName }}
      </a-tag>
    </a-col>
  </a-row>
</template>
<script lang="ts">
import Dialog from '@/components/Dialog.vue'
import { PropType } from '@vue/runtime-core'
import { Options, Vue } from 'vue-class-component'
import { message } from 'ant-design-vue'
import { UserOutlined, TeamOutlined } from '@ant-design/icons-vue'
import { SelectEvent, TreeDataItem } from 'ant-design-vue/es/tree/Tree'
import { TreeNode } from '@/types/TreeNode'
import BaseRequest from '@/request/BaseRequest'
// 角色用户类型定义
export type RoleUser = {
  id?: number;
  roleId?: number;
  userId?: number;
  userName?: string;
  userType?: number;
}
@Options({
  components: {
    UserOutlined,
    TeamOutlined
  },
  props: {
    roleId: {
      type: Number
    },
    dialog: Object as PropType<Dialog>
  }
})
export default class RoleUserVue extends Vue {
  // 角色ID
  roleId!: number
  // 当前弹框
  dialog!: Dialog
  // 组织树数据源
  treeData: TreeDataItem[] = []
  // 请求实例
  httpReq = new BaseRequest()
  // 角色用户
  users: RoleUser[] = []
  // 挂载钩子函数
  async mounted() {
    // 加载组织机构根节点
    const ret = await this.requestNodes()
    if (ret.code === 0) {
      this.treeData = this.toTreeDataItems(ret.data)
    } else {
      message.error(ret.msg)
    }
    // 加载角色用户
    const retRole = await this.requestRoleUsers(this.roleId)
    if (retRole.code === 0) {
      this.users = retRole.data
    } else {
      message.error(retRole.msg)
    }
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
      this.requestNodes(treeNode.dataRef.key).then((ret) => {
        treeNode.dataRef.children = this.toTreeDataItems(ret.data)
        this.treeData = [...this.treeData]
        resolve()
      })
    })
  }

  // 节点点击事件
  async onSelect(selectedKeys: string[], info: SelectEvent) {
    const dataRef = info.node.dataRef
    const exists = this.users.some(item => item.userId === dataRef.key)
    if (exists) {
      return false
    }
    const data: RoleUser = {
      roleId: this.roleId,
      userId: dataRef.key,
      userName: dataRef.title,
      userType: dataRef.type
    }
    const ret = await this.httpReq.post('/role-user/add', data)
    if (ret.code === 0) {
      data.id = ret.data
      this.users.push(data)
    } else {
      message.error(ret.msg)
    }
  }

  // 转为树节点类型
  toTreeDataItems(nodes: TreeNode[]) {
    return nodes.map((node) => {
      return {
        key: node.id,
        type: node.type, // 节点类型 0虚拟节点 1组织 2部门 3用户
        title: node.name,
        code: node.code,
        isLeaf: node.type === 3
      } as TreeDataItem
    })
  }

  // 删除角色用户
  async handleDelete(record: RoleUser) {
    const ret = await this.httpReq.get(`/role-user/delete/${record.id}`)
    if (ret.code === 0) {
      this.users = this.users.filter((item) => item.id !== record.id)
    } else {
      message.error(ret.msg)
    }
  }

  // 获取角色成员
  requestRoleUsers(roleId: number) {
    return this.httpReq.get<RoleUser[]>(
      `/role-user/list-by-role?roleId=${roleId}`
    )
  }

  // 获取人员树节点
  requestNodes(parentId?: number) {
    return this.httpReq.get<TreeNode[]>(
      '/user/list-tree-nodes' + (parentId ? `?parentId=${parentId}` : '')
    )
  }
}
</script>
<style scoped>
.main {
  height: 100%;
}
.content {
  padding: 7px;
}
.border-right {
  border-right: 1px solid #ccc;
}
</style>
