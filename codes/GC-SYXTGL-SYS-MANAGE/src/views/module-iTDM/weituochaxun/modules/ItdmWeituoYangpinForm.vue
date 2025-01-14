<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="委托单id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoId">
              <a-input v-model="model.weituoId" placeholder="请输入委托单id"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="样品名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sampleName">
              <a-input v-model="model.sampleName" placeholder="请输入样品名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="样品规格/型号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sampleModelSpecification">
              <a-input v-model="model.sampleModelSpecification" placeholder="请输入样品规格/型号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="样品数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sampleQuantities">
              <a-input v-model="model.sampleQuantities" placeholder="请输入样品数量"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="样品编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sampleCode">
              <a-input v-model="model.sampleCode" placeholder="请输入样品编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="生产厂家" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sampleManufacturer">
              <a-input v-model="model.sampleManufacturer" placeholder="请输入生产厂家"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sysOrgCode">
              <a-input v-model="model.sysOrgCode" placeholder="请输入所属部门"  ></a-input>
            </a-form-model-item>
          </a-col>

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
              <!-- <a-input v-model="model.bgyz" placeholder="请输入报告用章"  ></a-input> -->
              <j-multi-select-tag v-model="model.bgyz" placeholder="请选择报告用章" dictCode="weituo.bgyz" />
            </a-form-model-item>
          </a-col>

          <a-col :span="24">
            <a-form-model-item label="报告形式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bgxs">
              <!-- <a-input v-model="model.bgxs" placeholder="请输入报告形式"  ></a-input> -->
              <j-dict-select-tag v-model="model.bgxs" placeholder="请选择报告形式" dictCode="weituo.bgxs" />
            </a-form-model-item>
          </a-col>

          <a-col :span="24">
            <a-form-model-item label="特殊要求" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="teshuyaoqiu">
              <a-input v-model="model.teshuyaoqiu" placeholder="请输入特殊要求"  ></a-input>
            </a-form-model-item>
          </a-col>

          <a-col :span="24">
            <a-form-model-item label="审核状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shenheStatus">
              <!-- <a-input v-model="model.shenheStatus" placeholder="请输入审核状态"  ></a-input> -->
              <j-dict-select-tag v-model="model.shenheStatus" placeholder="请输入审核状态" dictCode="shenhe_status" />
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

          <!-- <a-col :span="24">
            <a-form-model-item label="试验条件文件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tiaojianFile">
              <a-input v-model="model.tiaojianFile" placeholder="请输入试验条件文件"  ></a-input>
              <a-button :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(model.tiaojianFile)">
                下载
              </a-button>
            </a-form-model-item>
          </a-col> -->

          <!-- <a-col :span="24">
            <a-form-model-item label="样品id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sampleCode">
              <a-input v-model="model.sampleCode" placeholder="请输入样品id"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="24">
            <a-form-model-item label="检测项目" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="testItems">
              <a-input v-model="model.testItems" placeholder="请输入检测项目"  ></a-input>
            </a-form-model-item>
          </a-col>

          <a-col :span="24">
            <a-form-model-item label="检测依据" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="standardRequirement">
              <a-input v-model="model.standardRequirement" placeholder="请输入检测依据"  ></a-input>
            </a-form-model-item>
          </a-col>

          <a-col :span="24">
            <a-form-model-item label="判定依据" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="judgementBasis">
              <a-input v-model="model.judgementBasis" placeholder="请输入判定依据"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="判定依据文件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="yijufile">
              <a-input v-model="model.yijufile" placeholder="请输入判定依据文件"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="judgementBasis">
              <a-input v-model="model.judgementBasis" placeholder="请输入beizhu"  ></a-input>
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
  name: 'ItdmWeituoYangpinForm',
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
        sampleName: [
          { required: true, message: '请输入样品名称!'},
        ],
        sampleModelSpecification: [
          { required: true, message: '请输入样品规格/型号!'},
        ],
        sampleQuantities: [
          { required: true, message: '请输入样品数量!'},
        ],
        sampleManufacturer: [
          { required: true, message: '请输入生产厂家!'},
        ],
      },
      url: {
        add: "/itdmWeituoChaXun/itdmWeituoYangpin/add",
        edit: "/itdmWeituoChaXun/itdmWeituoYangpin/edit",
        queryById: "/itdmWeituoChaXun/itdmWeituoYangpin/queryById"
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