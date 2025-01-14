<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="区域名称（1温箱区域2力学区域3霉菌区域）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="areaName">
              <j-dict-select-tag type="list" v-model="model.areaName" dictCode="area_name" placeholder="请选择区域名称（1温箱区域2力学区域3霉菌区域）" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="温度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="temperature">
              <a-input-number v-model="model.temperature" placeholder="请输入温度" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="湿度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="humidity">
              <a-input-number v-model="model.humidity" placeholder="请输入湿度" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="气压" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="airPrassure">
              <a-input-number v-model="model.airPrassure" placeholder="请输入气压" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="采集时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createTime">
              <j-date placeholder="请选择采集时间"  v-model="model.createTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
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
    name: 'ItdmEnvironmentQuyuForm',
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
          add: "/itdmDataHistory/itdmEnvironmentQuyu/add",
          edit: "/itdmDataHistory/itdmEnvironmentQuyu/edit",
          queryById: "/itdmDataHistory/itdmEnvironmentQuyu/queryById"
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