<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="委托编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoNo">
              <a-input v-model="model.weituoNo" placeholder="请输入委托编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoId">
              <a-input v-model="model.weituoId" placeholder="请输入委托id"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托单位名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoClient">
              <j-multi-select-tag type="list_multi" v-model="model.weituoClient" dictCode="" placeholder="请选择委托单位名称" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托样品列表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sampleNames">
              <a-input v-model="model.sampleNames" placeholder="请输入委托样品列表"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托样品id列表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sampleIds">
              <a-input v-model="model.sampleIds" placeholder="请输入委托样品id列表"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托联系人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoLxr">
              <a-input v-model="model.weituoLxr" placeholder="请输入委托联系人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托样品检测Id列表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="testItemsIds">
              <j-multi-select-tag type="list_multi" v-model="model.testItemsIds" dictCode="" placeholder="请选择委托样品检测Id列表" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托样品检测列表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="testItemsNames">
              <a-input v-model="model.testItemsNames" placeholder="请输入委托样品检测列表"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报告类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="baogaoType">
              <a-input v-model="model.baogaoType" placeholder="请输入报告类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报告文件地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="filePath">
              <a-input v-model="model.filePath" placeholder="请输入报告文件地址"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'ItdmBaogaoForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
           weituoId: [
              { required: true, message: '请输入委托id!'},
           ],
           sampleIds: [
              { required: true, message: '请输入委托样品id列表!'},
           ],
           testItemsIds: [
              { required: true, message: '请输入委托样品检测Id列表!'},
           ],
           baogaoType: [
              { required: true, message: '请输入报告类型!'},
           ],
        },
        url: {
          add: "/baogao/itdmBaogao/add",
          edit: "/baogao/itdmBaogao/edit",
          queryById: "/baogao/itdmBaogao/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>