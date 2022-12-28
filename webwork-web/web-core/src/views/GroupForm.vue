<template>
  <a-form
    ref="formRef"
    :model="formData"
    :rules="rules"
    :label-col="{ span: 4 }"
    :wrapper-col="{ span: 18 }"
    style="margin-top: 15px"
  >
    <a-form-item label="节点名称" name="name">
      <a-input v-model:value="formData.name" />
    </a-form-item>
    <a-form-item
      v-if="!isTop"
      label="上级单位"
      name="parentId"
    >
      <a-tree-select
        :disabled="!!id"
        :tree-data="treeData"
        :load-data="onLoadData"
        v-model:value="formData.parentName"
        @change="onSelectChange"
        @treeExpand="onSelectTreeExpand"
        tree-default-expand-all
        style="width: 100%"
        placeholder="请选择上级单位"
        :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
      />
    </a-form-item>
    <a-form-item v-if="!isTop && type == 1" label="虚拟节点" name="virtual">
      <a-radio-group v-model:value="formData.virtual">
        <a-radio :value="true">是</a-radio>
        <a-radio :value="false">否</a-radio>
      </a-radio-group>
    </a-form-item>
    <a-form-item v-if="!isTop" label="节点状态" name="enabled">
      <a-switch v-model:checked="formData.enabled" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">提交</a-button>
      <a-button style="margin-left: 10px" @click="onCancel">取消</a-button>
    </a-form-item>
  </a-form>
</template>
<script lang="ts">
import Dialog from '@/components/Dialog.vue'
import request from '@/request/GroupRequest'
import { Group } from '@/types/Group'
import { R } from '@/types/R'
import { PropType, ref, toRaw, unref } from '@vue/runtime-core'
import { ValidateErrorEntity } from 'ant-design-vue/es/form/interface'
import { message } from 'ant-design-vue'
import { Options, Vue } from 'vue-class-component'
import { TreeDataItem } from 'ant-design-vue/es/tree/Tree'
import { TreeNode } from '@/types/TreeNode'
import { TreeNodeType } from '@/types/TreeNodeType'
/**
 * 组织机构表单
 */
@Options({
  props: {
    id: {
      type: Number
    },
    type: {
      type: Number
    },
    parentId: {
      type: Number
    },
    parentType: {
      type: Number
    },
    parentName: {
      type: String
    },
    dialog: Object as PropType<Dialog>
  }
})
export default class GroupForm extends Vue {
  // 节点ID
  id!: number
  // 节点类型
  type!: number
  // 父级节点
  parentId!: number
  parentType!: number
  parentName!: string
  // 当前弹框
  dialog!: Dialog
  // 组织树数据源
  treeData: TreeDataItem[] = []
  // 表单数据
  formData: Group & {
    enabled?: boolean;
    virtual?: boolean;
    parentName?: string;
  } = {
    id: this.id,
    type: this.type,
    enabled: true, // 默认启用
    virtual: false, // 默认非虚拟节点
    parentId: this.parentId,
    parentType: this.parentType,
    parentName: this.parentName
  }

  // 表单引用
  formRef = ref<AntType.Form>()
  // 验证规则
  rules = {
    name: [
      { required: true, message: '请输入名称', trigger: 'blur' },
      { max: 50, message: '名称最多50个字符' }
    ],
    parentId: [{ required: true, message: '请选择上级单位', trigger: 'blur', type: 'number' }]
  }

  // 是否顶级单位
  get isTop() {
    return !(this.parentId || this.formData.parentId)
  }

  // 加载数据
  async mounted() {
    // 加载组织机构根节点
    const ret = await request.loadGroupNodes()
    if (ret.code !== 0) {
      message.error(ret.msg)
      return false
    }
    this.treeData = this.toAntTreeNodes(ret.data)

    // 加载当前节点
    if (this.id) {
      const ret = await request.find(this.id)
      if (ret.code !== 0) {
        message.error(ret.msg)
        return false
      }
      this.formData = {
        ...this.formData,
        ...ret.data,
        parentName: ret.data.parentType + '_' + ret.data.parentId
      }
    }
  }

  /**
   * 加载节点数据
   */
  onLoadData(treeNode: { dataRef: TreeDataItem }) {
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

  onSelectTreeExpand(expandedKeys: string[]) {
    console.log(expandedKeys)
  }

  // 切换上级单位
  onSelectChange(value: any, label: any, extra: any) {
    // console.log(value, label, extra)
    this.formData.parentId = extra.triggerNode.dataRef.rawId
    this.formData.parentType = extra.triggerNode.dataRef.type
  }

  // 保存
  onSubmit() {
    const formUnref = unref(this.formRef)
    formUnref &&
      formUnref
        .validate()
        .then(async () => {
          // 节点类型
          if (this.formData.virtual) {
            this.formData.type = 0
          }
          // 节点状态
          this.formData.status = this.formData.enabled ? 1 : 0

          let { enabled, virtual, parentName, ...data } = toRaw(this.formData)

          let ret
          if (!this.id) {
            ret = await request.add(data)
            data = (await request.find(ret.data)).data
          } else {
            ret = await request.update(data)
          }
          if (ret.code === 0) {
            this.dialog._ok(data)
          } else {
            message.error(`错误:${ret.msg}`)
          }
        }).catch((error: ValidateErrorEntity<Group>) => {
          console.log('error', error)
        })
  }

  // 取消
  onCancel() {
    this.dialog._close()
  }

  // 转为树节点类型
  toChildren(ret: R<TreeNode[]>) {
    return ret.data.map((node) => {
      return {
        key: node.id,
        rawId: node.rawId,
        type: node.type,
        value: node.id,
        title: node.name,
        code: node.code,
        isLeaf: node.type === TreeNodeType.DEPT,
        // disabled: value.type === TreeNodeType.DEPT
        selectable: node.type === TreeNodeType.GROUP
      } as TreeDataItem
    })
  }

  // 转换为 Ant树节点
  toAntTreeNodes(nodes: TreeNode[]): TreeDataItem[] {
    return nodes.map((node) => {
      return {
        key: node.id,
        rawId: node.rawId,
        type: node.type, // 节点类型 0虚拟节点 1单位 2部门
        value: node.id,
        title: node.name,
        code: node.code,
        isLeaf: node.type === TreeNodeType.DEPT,
        // disabled: node.type === TreeNodeType.DEPT
        selectable: node.type === TreeNodeType.GROUP,
        children: this.toAntTreeNodes(node.children)
      } as TreeDataItem
    })
  }
}
</script>
