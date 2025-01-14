<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    @cancel="onCancel"
    :confirmLoading="confirmLoading"
    :footer="null"
    switchFullscreen
  >
    <a-upload-dragger
      name="file"
      :multiple="true"
      :action="uploadAction"
      :headers="headers"
      :data="{biz:bizPath}"
      :before-upload="beforeUpload"
      @change="handleChange"
    >
      <p class="ant-upload-drag-icon">
        <a-icon type="inbox" />
      </p>
      <p class="ant-upload-text">
        将文件拖到此处，或点击上传
      </p>
      <p class="ant-upload-hint">
        支持文件格式：zip、bar、bpmn、bpmn20.xml
      </p>
    </a-upload-dragger>
  </a-modal>
</template>

<script>
  import { getFileAccessHttpUrl, deleteAction } from '@/api/manage'
  import { getToken } from '@/utils/auth'
  import { ACCESS_TOKEN, TENANT_ID } from '@/store/mutation-types'
  import Vue from 'vue'

  export default {
    name: 'ActivitiFileModel',
    components: {},
    data() {
      return {
        modelerLoading: true,
        title: '上传文件',
        visible: false,
        confirmLoading: false,
        uploadAction: window._CONFIG['domianURL'] + '/act/reprocdef/deploy',
        bizPath: 'act'
      }
    },
    computed: {

    },
    created() {
      const token = this.$store.getters.token
      this.headers = { 'X-Access-Token': token, 'tenant-id': Vue.ls.get(TENANT_ID) }
    },
    methods: {
      onCancel() {
        this.visible = false
      },
      openModel() {
        this.visible = true
      },
     handleChange(info) {
       console.log('info', info)
        const status = info.file.status
        if (status !== 'uploading') {
          console.log(info.file, info.fileList)
        }
        if (status === 'done') {
          this.$message.success(`${info.file.name} 文件上传成功。`)
        } else if (status === 'error') {
          this.$message.error(`${info.file.name} 文件上传失败。`)
        }
      },
      beforeUpload(file) {
        // 文件格式判断
          let fileType = file.name.toLowerCase()
          const isJpgOrPng = fileType.endsWith('zip') || fileType.endsWith('bar') || fileType.endsWith('bpmn') || fileType.endsWith('bpmn20.xml')
          if (!isJpgOrPng) {
            this.$message.error('只支持zip、bar、bpmn、bpmn20.xml格式的文件!')
          }
          // const isLt2M = file.size / 1024 / 1024 < 2;
          // if (!isLt2M) {
          //   this.$message.error('Image must smaller than 2MB!');
          // }
          // return isJpgOrPng && isLt2M;
          return isJpgOrPng
        }
    }
}

</script>

<style>

</style>
