<template>
  <a-layout>
    <a-layout-header>
      <a-space>
      <a-button size="default" @click="handleBack">返回</a-button>
      <a-upload
        name="file"
        action="/album-photo/upload"
        accept="image/*"
        :multiple="true"
        :data="{albumId: albumId}"
        :withCredentials="true"
        :show-upload-list="false"
        :before-upload="beforeUpload"
        @change="handleUpload"
      >
        <a-button>
          <upload-outlined></upload-outlined>
          上传照片
        </a-button>
      </a-upload>
    </a-space>
    </a-layout-header>
    <a-layout-content>
      <a-empty v-if="photos.length === 0" :description="null" />
      <a-card hoverable v-for="item, index in photos" :key="item.id">
        <template #cover>
          <a-image :alt="item.name" :preview="{ visible: false }" :src="photoSrc(item)"
            @click="onView(index)" />
        </template>
        <template #actions>
          <file-image-outlined key="cover" title="设为封面" @click="onCover(item)" />
          <edit-outlined key="edit" title="编辑" @click="onEdit(item)" />
          <a-popconfirm title="您确定要删除这张照片吗？" ok-text="是" cancel-text="否" @confirm="onDelete(item)">
            <delete-outlined key="delete" title="删除照片" />
          </a-popconfirm>
        </template>
      </a-card>
      <!-- 暂不支持，可能需要升级版本 -->
      <div style="display: none;">
        <a-image-preview-group :preview="preview">
          <a-image v-for="photo in photos" :key="photo.id" :src="photoSrc(photo)" />
        </a-image-preview-group>
      </div>
      <!-- 照片信息弹框 -->
      <a-modal v-model:visible="photoInfoDialog.visible" :title="photoInfoDialog.title"
        :confirm-loading="photoInfoDialog.confirmLoading" @ok="onSavePhoto">
        <a-form ref="formRef" :model="formData" :rules="rules" :colon="false"
         :label-col="{ span: 3 }" :wrapper-col="{ span: 21 }">
          <a-form-item label="名称" name="name">
            <a-input v-model:value="formData.name" placeholder="照片名称" size="large" show-count :maxlength="10" />
          </a-form-item>
          <a-form-item label="描述" name="description">
            <a-textarea v-model:value="formData.description" placeholder="照片描述" :rows="4" />
          </a-form-item>
          <a-form-item label="地址" name="address">
            <a-input :value="photoSrc(formData)" :bordered="false" :disabled="true" placeholder="照片对象地址" />
          </a-form-item>
        </a-form>
      </a-modal>
    </a-layout-content>
  </a-layout>
</template>
<script lang="ts">
import {
  EditOutlined,
  DeleteOutlined,
  UploadOutlined,
  FileImageOutlined
} from '@ant-design/icons-vue'
import { AlbumPhoto } from '@/types/AlbumPhoto'
import { Options, Vue } from 'vue-class-component'
import request from '@/request/PhotoRequest'
import albumReq from '@/request/AlbumRequest'
import { message } from 'ant-design-vue'
import { ref, unref, toRaw } from 'vue'
import { ValidateErrorEntity } from 'ant-design-vue/lib/form/interface'
import { R } from '@/types/R'

// 上传文件类型
interface FileItem {
  uid: string;
  name?: string;
  status?: string;
  response?: R<any>;
  url?: string;
  type?: string;
  size: number;
  originFileObj: any;
}
// 上传文件信息
interface FileInfo {
  file: FileItem;
  fileList: FileItem[];
}

@Options({
  components: {
    EditOutlined,
    DeleteOutlined,
    UploadOutlined,
    FileImageOutlined
  }
})
export default class Album extends Vue {
  // 表单引用
  formRef = ref<AntType.Form>()
  // 照片
  photos: AlbumPhoto[] = []
  // 照片预览
  preview = {
    visible: false,
    current: 1,
    onVisibleChange: (visible: boolean) => {
      this.preview.visible = visible
    }
  }

  // 照片弹框
  photoInfoDialog = {
    title: '照片信息',
    visible: false,
    confirmLoading: false
  }

  // 照片信息
  formData: AlbumPhoto = {}

  rules = {
    name: [{ required: true, message: '照片名称不能为空' }, { max: 10, message: '照片名称最多10个字符' }],
    description: [{ max: 100, message: '照片描述最多100个字符' }]
  }

  // 相册ID
  albumId = 0

  // 加载图片
  mounted() {
    this.albumId = Number(this.$route.query.albumId)
    this.albumId && request.listQuery({ albumId: this.albumId }).then(res => {
      if (res.code === 0) {
        this.photos = res.data
      } else {
        message.error(res.msg)
      }
    })
  }

  /**
   * 预览图片
   * @param index 图片下标
   */
  onView(index: number) {
    this.preview.visible = true
    this.preview.current = index
  }

  /**
   * 编辑照片
   * @param photo 照片对象
   */
  onEdit(photo: AlbumPhoto) {
    this.formData = { ...photo }
    this.photoInfoDialog.visible = true
  }

  /**
   * 将照片设为相册封面
   * @param photo 照片
   */
  onCover(photo: AlbumPhoto) {
    const cover = { cover: photo.thumbObjectName, albumId: this.albumId }
    albumReq.setCover(cover).then(res => {
      if (res.code === 0) {
        message.success(`照片<${photo.name}>已设为相册封面`)
      } else {
        message.error(res.msg)
      }
    })
  }

  /**
   * 删除照片
   * @param photo 照片对象
   */
  onDelete(photo: AlbumPhoto) {
    request.deletePhoto(photo.id as number).then(res => {
      if (res.code !== 0) {
        message.error(res.msg)
      } else {
        message.success('删除成功')
        this.photos = this.photos.filter(item => item.id !== photo.id)
      }
    })
  }

  /**
   * 保存照片信息
   */
  onSavePhoto() {
    const formUnref = unref(this.formRef)
    formUnref && formUnref.validate().then(async () => {
        this.photos.forEach(item => {
          if (item.id === this.formData.id) {
            request.update(toRaw(this.formData)).then(res => {
              if (res.code !== 0) {
                message.error(res.msg)
              } else {
                item.name = this.formData.name
                item.description = this.formData.description
                this.photoInfoDialog.visible = false
              }
            })
          }
        })
      }).catch((error: ValidateErrorEntity<unknown>) => {
        console.log('error', error)
      })
  }

  /**
   * 返回主页
   */
  handleBack() {
    this.$router.back()
  }

  /**
   * 返回照片可访问地址
   * @param photo 照片对象
   */
  photoSrc(photo: AlbumPhoto) {
    if (photo.thumbObjectName?.startsWith('http')) {
      return photo.thumbObjectName
    } else {
      return '/file/download/' + photo.thumbObjectName
    }
  }

  /**
   * 上传前验证
   */
  beforeUpload(file: FileItem) {
    const isLt5M = file.size / 1024 / 1024 < 5
    if (!isLt5M) {
      message.error('上传照片不能大于 5MB!')
    }
    return isLt5M && file.type?.startsWith('image/')
  }

  /**
   * 上传照片
   */
  handleUpload(info: FileInfo) {
    if (info.file.status === 'uploading') {
      return false
    }
    if (info.file.status === 'done') {
      if (info.file.response?.code === 0) {
        this.photos.push(info.file.response.data)
      } else {
        message.error(info.file.response?.msg as string)
      }
    }
    if (info.file.status === 'error') {
      message.error('上传失败')
    }
  }
}
</script>
<style lang="scss" scoped>
.ant-layout {
  background-color: #fff;
}
.ant-layout-header {
  padding: 0px;
  margin: 0px 50px;
  background-color: #fff;
  border-bottom: 1px solid silver;
}
.ant-layout-content {
  padding: 25px 50px;
  background-color: #fff;
}

.ant-card {
  margin: 0px 30px 30px 0px;
  width: 250px;
  display: inline-block;
}

.ant-card img {
  height: 280px;
  max-width: 100%;
}
::v-deep(.ant-card-body) {
  display: none;
}
::v-deep(.ant-image-img) {
  height: 280px;
  max-width: 100%;
}
</style>
