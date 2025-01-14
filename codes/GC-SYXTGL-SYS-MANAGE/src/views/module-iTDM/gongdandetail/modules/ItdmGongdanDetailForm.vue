<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="实际设备" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shijiShebeiId">

              <j-search-select-tag
                placeholder="请做出你的选择"
                v-model="model.shijiShebeiId"
                dict="itdm_device,device_name,id"
                :async="true">
              </j-search-select-tag>

            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="传感器" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="chuanganqi">
              <j-multi-select-tag type="list_multi" v-model="model.chuanganqi" dictCode="itdm_chuanganqi,xuliehao,id" placeholder="请选择传感器" />
            </a-form-model-item>
          </a-col>



          <a-col :span="24">
            <a-form-model-item label="试验类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shijiShiiyanType">
              <j-multi-select-tag type="list_multi" v-model="model.shijiShiiyanType" dictCode="shiiyan_type" placeholder="请选择实际试验类型" />
            </a-form-model-item>
          </a-col>





          <a-col :span="24">
            <a-form-model-item label="实际任务日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shijiWorkDate">
              <j-date placeholder="请选择实际任务日期" v-model="model.shijiWorkDate" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="实际试验开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol"
                               prop="shijiStartTime">
              <a-time-picker placeholder="请选择实际试验开始时间" v-model="model.shijiStartTime" :valueFormat="'HH:mm'"
                      :format="'HH:mm'" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="实际试验结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol"
                               prop="shijiEndTime">
              <a-time-picker placeholder="请选择实际试验结束时间" v-model="model.shijiEndTime" :valueFormat="'HH:mm'"
                      :format="'HH:mm'" style="width: 100%" />
            </a-form-model-item>
          </a-col>
<!--          <a-col :span="24">-->
<!--            <a-form-model-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">-->
<!--              <j-dict-select-tag type="list" v-model="model.status" dictCode="test_plan_status"-->
<!--                                 placeholder="请选择状态" />-->
<!--            </a-form-model-item>-->
<!--          </a-col>-->
          <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-input v-model="model.remark" placeholder="请输入备注"></a-input>
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
import moment from 'moment';

export default {
  name: 'ItdmGongdanDetailForm',
  components: {},
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false
    }
  },
  data() {
    return {
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      confirmLoading: false,
      validatorRules: {
        woId: [
          { required: true, message: '请输入工单id!' }
        ],
        shijiWorkDate: [
          { required: true, message: '请输入任务日期!' }
        ],
        startTime: [
          { required: true, message: '请输入试验开始时间!' }
        ],
        endTime: [
          { required: true, message: '请输入试验结束时间!' }
        ]
      },
      url: {
        add: '/gongdandetail/itdmGongdanDetail/add',
        edit: '/gongdandetail/itdmGongdanDetail/edit',
        queryById: '/gongdandetail/itdmGongdanDetail/queryById'
      }
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    }
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {
    add() {
      this.edit(this.modelDefault)
    },
    edit(record) {
      this.model = Object.assign({}, record)
      console.log(record)
      if(record.updateTime){
        // 第二次弹窗
        var select={id:record.id}
        getAction('/itdmGongdanDetail/itdmGongdanDetail/queryById', select).then((res) => {
          if (res.success) {
            this.model = res.result
          }
        })
      } else {
        // 第一次确认 没有数据  默认把计划数据带过来
        var select1={id:record.woId}
        getAction('/itdmGongdanMaster/itdmGongdanMaster/queryById', select1).then((res) => {
          if (res.success) {
            // 接口数据获取的
            this.model.shijiShebeiId=res.result.shebeiId
            this.model.chuanganqi=res.result.chuanganqi
            this.model.shijiShiiyanType=res.result.shiiyanType
            
            // 表格当前行数据直接获取
            // this.model.shijiShebeiId=record.jihuaDeviceId
            // this.model.chuanganqi=record.chuanganqi
            this.model.shijiWorkDate=record.workDate
            this.model.shijiStartTime=record.startTime
            this.model.shijiEndTime=record.endTime
          }
        })
      }
      this.visible = true
    },
    submitForm() {
      const that = this
      // 触发表单验证
      this.$refs.form.validate(valid => {
        if (valid) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }

          httpAction(httpurl, this.model, method).then((res) => {
            if (res.success) {
              that.$message.success(res.message)
              that.$emit('ok')
            } else {
              that.$message.warning(res.message)
            }
          }).finally(() => {
            that.confirmLoading = false
          })
        }

      })
    }
  }
}
</script>