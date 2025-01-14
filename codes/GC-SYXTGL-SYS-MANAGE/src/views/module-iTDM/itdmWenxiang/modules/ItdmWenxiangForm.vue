<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="设备id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deviceId">
              <j-dict-select-tag type="list" v-model="model.deviceId" dictCode="itdm_device,device_name,id" placeholder="请选择设备id" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deviceName">
              <a-input v-model="model.deviceName" placeholder="请输入设备名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="设备编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deviceNo">
              <a-input v-model="model.deviceNo" placeholder="请输入设备编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="温度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="temperature">
              <a-input-number v-model="model.temperature" placeholder="请输入温度" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="温度上限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="temperatureMax">
              <a-input-number v-model="model.temperatureMax" placeholder="请输入温度上限" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="温度下限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="temperatureMin">
              <a-input-number v-model="model.temperatureMin" placeholder="请输入温度下限" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="温度报警（0正常1报警）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="temperatureAlarm">
              <j-dict-select-tag type="list" v-model="model.temperatureAlarm" dictCode="data_status" placeholder="请选择温度报警（0正常1报警）" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="湿度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="humidity">
              <a-input-number v-model="model.humidity" placeholder="请输入湿度" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="湿度上限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="humidityMax">
              <a-input-number v-model="model.humidityMax" placeholder="请输入湿度上限" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="湿度下限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="humidityMin">
              <a-input-number v-model="model.humidityMin" placeholder="请输入湿度下限" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="湿度报警（0正常1报警）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="humidityAlarm">
              <j-dict-select-tag type="list" v-model="model.humidityAlarm" dictCode="data_status" placeholder="请选择湿度报警（0正常1报警）" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="A相电流" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentA">
              <a-input-number v-model="model.currentA" placeholder="请输入A相电流" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="A相电流上限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentAMax">
              <a-input-number v-model="model.currentAMax" placeholder="请输入A相电流上限" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="A相电流下限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentAMin">
              <a-input-number v-model="model.currentAMin" placeholder="请输入A相电流下限" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="A相电流报警（0正常1报警）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentAAlarm">
              <j-dict-select-tag type="list" v-model="model.currentAAlarm" dictCode="data_status" placeholder="请选择A相电流报警（0正常1报警）" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="B相电流" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentB">
              <a-input-number v-model="model.currentB" placeholder="请输入B相电流" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="B相电流上限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentBMax">
              <a-input-number v-model="model.currentBMax" placeholder="请输入B相电流上限" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="B相电流下限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentBMin">
              <a-input-number v-model="model.currentBMin" placeholder="请输入B相电流下限" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="B相电流报警（0正常1报警）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentBAlarm">
              <j-dict-select-tag type="list" v-model="model.currentBAlarm" dictCode="data_status" placeholder="请选择B相电流报警（0正常1报警）" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="C相电流" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentC">
              <a-input-number v-model="model.currentC" placeholder="请输入C相电流" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="C相电流上限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentCMax">
              <a-input-number v-model="model.currentCMax" placeholder="请输入C相电流上限" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="C相电流下限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentCMin">
              <a-input-number v-model="model.currentCMin" placeholder="请输入C相电流下限" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="C相电流报警（0正常1报警）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="currentCAlarm">
              <j-dict-select-tag type="list" v-model="model.currentCAlarm" dictCode="data_status" placeholder="请选择C相电流报警（0正常1报警）" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createTime">
              <j-date placeholder="请选择创建时间"  v-model="model.createTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
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
    name: 'ItdmWenxiangForm',
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
          add: "/itdmDataHistory/itdmWenxiang/add",
          edit: "/itdmDataHistory/itdmWenxiang/edit",
          queryById: "/itdmDataHistory/itdmWenxiang/queryById"
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