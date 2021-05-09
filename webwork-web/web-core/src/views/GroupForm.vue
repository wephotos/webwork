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
    <a-form-item label="上级单位" name="parentId">
      <a-tree-select
        v-model:value="formData.parentId"
        :defaultValue="formData.parentName"
        :disabled="!!id"
        :tree-data="treeData"
        :load-data="onLoadData"
        @treeExpand="onSelectTreeExpand"
        tree-default-expand-all
        style="width: 100%"
        placeholder="请选择上级单位"
        :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
      />
    </a-form-item>
    <a-form-item v-if="type == 1" label="虚拟节点" name="virtual">
      <a-radio-group v-model:value="formData.virtual">
        <a-radio :value="true">是</a-radio>
        <a-radio :value="false">否</a-radio>
      </a-radio-group>
    </a-form-item>
    <a-form-item label="节点状态" name="enabled">
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
/**
 * 组织机构表单
 */
@Options({
  props: {
    id: {
      type: String
    },
    type: {
      type: Number
    },
    parentId: {
      type: String
    },
    parentName: {
        type: String
    },
    dialog: Object as PropType<Dialog>
  }
})
export default class GroupForm extends Vue {
  // 节点ID
  id!: string
  // 节点类型
  type!: number
  // 父级节点
  parentId!: string
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
    enabled: true, // 默认启用
    virtual: false, // 默认非虚拟节点
    parentId: this.parentId,
    parentName: this.parentName
  }

  // 表单引用
  formRef = ref<AntType.Form>()
  // 验证规则
  rules = {
    name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
    parentId: [{ required: true, message: '请选择上级单位', trigger: 'blur' }]
  }

  // 真实父ID
  parentId0 = this.parentId

  // 加载数据
  async mounted() {
    // 加载组织机构根节点
    const ret = await request.children()
    if (ret.code !== 0) {
      message.error(ret.msg)
      return false
    }
    this.treeData = this.toChildren(ret)

    // 加载当前节点
    if (this.id) {
      const ret = await request.find(this.id)
      if (ret.code !== 0) {
        message.error(ret.msg)
        return false
      }
      this.formData = {
        ...this.formData,
        ...ret.data
      }
      this.parentId0 = this.formData.parentId as string
      // 查询父节点
      const parent = (await request.find(this.parentId0)).data
      this.formData.parentId = parent.name as string
      this.formData.parentName = parent.name as string
    } else {
      const parent = (await request.find(this.parentId)).data
      this.formData.parentId = parent.name as string
      this.formData.parentName = parent.name as string
    }
  }

  /**
   * 加载节点数据
   */
  onLoadData(treeNode: {dataRef: TreeDataItem}) {
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

  onSelectTreeExpand(expandedKeys: string[]) {
      console.log(expandedKeys)
  }

  // 保存
  onSubmit() {
    const formUnref = unref(this.formRef)
    formUnref &&
      formUnref
        .validate()
        .then(async () => {
          const data = { ...toRaw(this.formData) }
          data.type = data.virtual ? 0 : this.type
          data.status = data.enabled ? 1 : 0
          delete data.virtual
          delete data.enabled
          // 处理父ID
          if (data.parentId === data.parentName) {
              data.parentId = this.parentId0
          }
          let ret
          if (this.id) {
            ret = await request.update(data)
          } else {
            ret = await request.add(data)
            data.id = ret.data
          }
          if (ret.code !== 0) {
              message.error(`错误:${ret.msg}`)
          } else {
              this.dialog._ok(data)
          }
        })
        .catch((error: ValidateErrorEntity<Group>) => {
          console.log('error', error)
        })
  }

  // 取消
  onCancel() {
    this.dialog._close()
  }

  // 转为树节点类型
  toChildren(ret: R<Group[]>) {
    return ret.data.map((value) => {
      return {
        key: value.id,
        type: value.type, // 节点类型 0虚拟节点 1组织 2部门
        value: value.id,
        title: value.name,
        code: value.code,
        isLeaf: value.type === 2,
        // disabled: value.type === 2
        selectable: value.type === 1
      } as TreeDataItem
    })
  }
}
</script>
