<template>
    <a-layout class="a-layout">
        <a-layout-sider class="a-layout-sider">
            <a-tree :tree-data="treeData" v-model:expandedKeys="expandedKeys">
                <template #title="{ key: treeKey, title }">
                <a-dropdown :trigger="['contextmenu']">
                    <span>{{ title }}</span>
                    <template #overlay>
                    <a-menu @click="({ key: menuKey }) => onContextMenuClick(treeKey, menuKey)">
                        <a-menu-item key="1">1st menu item</a-menu-item>
                        <a-menu-item key="2">2nd menu item</a-menu-item>
                        <a-menu-item key="3">3rd menu item</a-menu-item>
                    </a-menu>
                    </template>
                </a-dropdown>
                </template>
            </a-tree>
        </a-layout-sider>
        <a-layout-content class="a-layout-content">
          Content
        </a-layout-content>
    </a-layout>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
const treeDataConst = [
  {
    title: '宝宝集团',
    key: '0-0',
    children: [
      {
        title: '0-0-0',
        key: '0-0-0',
        children: [
          { title: '0-0-0-0', key: '0-0-0-0' },
          { title: '0-0-0-1', key: '0-0-0-1' },
          { title: '0-0-0-2', key: '0-0-0-2' }
        ]
      },
      {
        title: '0-0-1',
        key: '0-0-1',
        children: [
          { title: '0-0-1-0', key: '0-0-1-0' },
          { title: '0-0-1-1', key: '0-0-1-1' },
          { title: '0-0-1-2', key: '0-0-1-2' }
        ]
      }
    ]
  }
]

@Options({
    watch: {
        expandedKeys(value: string[], newValue: string[]) {
            this.watchExpandedKeys(value, newValue)
        }
    }
})
export default class Organization extends Vue {
    treeData = treeDataConst;
    // 展开的KEY
    expandedKeys: string[] = ['0-0-0', '0-0-1'];
    // 右键菜单
    onContextMenuClick(treeKey: string, menuKey: string) {
      console.log(`treeKey: ${treeKey}, menuKey: ${menuKey}`)
    }

    // 属性监听
    watchExpandedKeys(value: string[], oldValue: string[]) {
      console.log('expandedKeys', value, oldValue)
    }
}
</script>
<style scoped>
.a-layout{
    height: 100%;
}
.a-layout-sider{
    width: 250px;
    overflow: auto;
    background-color: #fff;
}
.a-layout-content{
    padding: '0 24px';
    overflow: auto;
}
</style>
