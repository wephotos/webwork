<template>
  <a-layout>
    <a-layout-content>
      <a-card v-for="item in albums" :key="item.id" hoverable :title="item.name">
        <template #cover>
          <img v-if="item.cover" :title="item.name" :src="coverSrc(item.cover)" @click="onEnter(item)" />
          <img v-else :title="item.name" src="../assets/images/cover.png" @click="onEnter(item)" />
        </template>
        <template #actions>
          <edit-outlined key="edit" @click="onEdit(item)" />
          <a-popconfirm title="您确定要删除这个相册吗？" ok-text="是" cancel-text="否" @confirm="onDelete(item)">
            <delete-outlined key="delete" />
          </a-popconfirm>
        </template>
      </a-card>
      <!-- 新建相册 -->
      <div class="ant-card ant-card-bordered ant-card-hoverable" style="padding-top: 58px;vertical-align: top;">
        <img title="新建相册" src="../assets/images/plus.png" @click="onCreateAlbum" />
      </div>
    </a-layout-content>
  </a-layout>
  <!-- 创建相册弹框 -->
  <a-modal
      v-model:visible="albumVisible"
      :title="albumModalTitle"
      :confirm-loading="albumConfirm"
      @ok="onSaveAlbum"
    >
    <a-input v-model:value="formData.name" placeholder="请输入相册名称"
       size="large" show-count :maxlength="10" />
  </a-modal>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { message } from 'ant-design-vue'
import {
  EditOutlined,
  DeleteOutlined,
  PlusOutlined
} from '@ant-design/icons-vue'
import { Album } from '@/types/Album'
import request from '@/request/AlbumRequest'
@Options({
  components: {
    EditOutlined,
    DeleteOutlined,
    PlusOutlined
  }
})
export default class Home extends Vue {
  // 相册列表
  albums: Album[] = []

  // 创建相册相关属性
  albumVisible = false
  albumConfirm = false
  albumModalTitle = '新建相册'
  formData: Album = {}

  // 加载事件
  mounted() {
    this.loadAlbums()
  }

  // 加载相册
  async loadAlbums() {
    const res = await request.listQuery()
    if (res.code === 0) {
      this.albums = res.data
    } else {
      message.error(res.msg)
    }
  }

  /**
   * 转换封面图片地址
   * @param cover 封面图片
   */
  coverSrc(cover: string) {
    return cover.startsWith('http') ? cover : '/file/download/' + cover
  }

  /**
   * 进入相册
   * @param album 相册数据
   */
  onEnter(album: Album) {
    this.$router.push({
      path: '/album',
      query: { albumId: album.id }
    })
  }

  /**
   * 编辑相册
   * @param album 相册数据
   */
  onEdit(album: Album) {
    this.formData = album
    this.albumVisible = true
    this.albumModalTitle = '编辑相册'
  }

  /**
   * 删除相册
   * @param album 相册数据
   */
  onDelete(album: Album) {
    album.id && request.deleteAlbum(album.id).then(res => {
      if (res.code === 0) {
        this.albums = this.albums.filter(item => item.id !== album.id)
      } else {
        message.error(res.msg)
      }
    })
  }

  /**
   * 点击新建相册
   */
  onCreateAlbum() {
    this.albumVisible = true
    this.albumModalTitle = '新建相册'
    this.formData = {}
  }

  /**
   * 保存相册
   */
  async onSaveAlbum() {
    const name = this.formData.name
    if (!name || /^\s+/.test(name)) {
      message.warning('请输入相册名称')
      return false
    }
    this.albumConfirm = true
    const res = await request.save(this.formData)
    if (res.code === 0) {
      this.albumVisible = false
      message.success('相册创建成功')
    } else {
      this.albumConfirm = false
      message.error('相册创建失败: ' + res.msg)
    }
  }
}
</script>
<style lang="scss" scoped>
.ant-layout-content {
  padding: 25px 50px;
  background-color: #fff;
}

.ant-card {
  width: 200px;
  height: 300px;
  margin-right: 50px;
  margin-bottom: 20px;
  display: inline-block;
}

::v-deep(.ant-card-body) {
  display: none;
}

.ant-card img {
  height: 200px;
  max-width: 100%;
}
</style>
