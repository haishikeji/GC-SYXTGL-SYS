<template>
  <a-modal
    title="流程图123"
    :visible="visible"
    :confirm-loading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
  >
    <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
      <a-form @submit="handleSubmit" :form="form">
        <a-form-item
          label="标题2"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
          <a-input
            v-decorator="[
            'name',
            {rules: [{ required: true, message: '请输入标题' }]}
          ]"
            name="name"
            placeholder="给目标起个名字" />
        </a-form-item>

        <a-form-item
          :wrapperCol="{ span: 24 }"
          style="text-align: center"
        >
          <a-button htmlType="submit" type="primary">提交</a-button>
          <a-button style="margin-left: 8px">保存</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </a-modal>

</template>

<script>
  import { getModelList } from '@/api/activiti/activiti'
  export default {
    name: 'BaseForm1',
    data () {
      return {
        description: '表单页用于向用户收集或验证信息，基础表单常见于数据项较少的表单场景。',
        value: 1,

        // form
        form: this.$form.createForm(this),
        ModalText: 'Content of the modal',
        visible: false,
        confirmLoading: false,
        url: {
          commit: 'xiaoetong/saveForm'
        }
      }
    },
    methods: {
      handleOk(e) {
        this.ModalText = 'The modal will be closed after two seconds';
        this.confirmLoading = true;
        setTimeout(() => {
          this.visible = false;
          this.confirmLoading = false;
        }, 2000);
      },
      handleCancel(e) {
        console.log('Clicked cancel button');
        this.visible = false;
      },
      // handler
      handleSubmit (e) {
        e.preventDefault()
        this.form.validateFields((err, values) => {
          if (!err) {
            // eslint-disable-next-line no-console
            //console.log('Received values of form: ', values)
             this.loading = true;
            let p = {};
            p.name = values.name;
            p.description = values.description;
            p.type = values.type;
            p.start = values.buildTime[0];
            p.end = values.buildTime[1];
            getModelList(this.url.commit, p).then(res => {
              //console.log('查询数据', res);
              this.loading = false;
            })
          }
        })
      }
    }
  }
</script>
<style lang="less" scoped>
/deep/.ant-modal {
  width: 40% !important;
}
</style>