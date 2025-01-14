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
            <a-form-model-item label="委托单位名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoClient">
              <a-input v-model="model.weituoClient" placeholder="请输入委托单位名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托单位地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoAddress">
              <a-input v-model="model.weituoAddress" placeholder="请输入委托单位地址"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托联系人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoLxr">
              <a-input v-model="model.weituoLxr" placeholder="请输入委托联系人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoPhone">
              <a-input v-model="model.weituoPhone" placeholder="请输入委托电话"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoEmail">
              <a-input v-model="model.weituoEmail" placeholder="请输入委托邮箱"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报告用章" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bgyz">
              <j-category-select v-model="model.bgyz" pcode="" placeholder="请选择报告用章"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报告形式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bgxs">
              <j-category-select v-model="model.bgxs" pcode="" placeholder="请选择报告形式"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="特殊要求" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="teshuyaoqiu">
              <a-input v-model="model.teshuyaoqiu" placeholder="请输入特殊要求"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审核状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shenheStatus">
              <a-input v-model="model.shenheStatus" placeholder="请输入审核状态"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审核错误原因" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shenheMsg">
              <a-input v-model="model.shenheMsg" placeholder="请输入审核错误原因"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审核时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shenheTime">
              <a-input v-model="model.shenheTime" placeholder="请输入审核时间"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="试验条件文件地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tiaojianFile">
              <j-upload v-model="model.tiaojianFile"   ></j-upload>
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
    name: 'ItdmWeituoInfoForm',
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
        },
        url: {
          add: "/weituo/itdmWeituoInfo/add",
          edit: "/weituo/itdmWeituoInfo/edit",
          queryById: "/weituo/itdmWeituoInfo/queryById"
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
      handleCategoryChange(value,backObj){
         this.model = Object.assign(this.model, backObj);
      }
    }
  }
</script>