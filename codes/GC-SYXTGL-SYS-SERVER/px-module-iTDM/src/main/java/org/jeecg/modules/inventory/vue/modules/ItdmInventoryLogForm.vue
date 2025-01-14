<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="类型（入库、出库）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type">
              <a-input v-model="model.type" placeholder="请输入类型（入库、出库）"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="条码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="barCode">
              <a-input v-model="model.barCode" placeholder="请输入条码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="样品库存id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="inventoryId">
              <a-input v-model="model.inventoryId" placeholder="请输入样品库存id"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数量 用来同步" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num">
              <a-input v-model="model.num" placeholder="请输入数量 用来同步"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="使用人员（入库借出和出库归还）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="useBy">
              <a-input v-model="model.useBy" placeholder="请输入使用人员（入库借出和出库归还）"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="库管人员（当前登录或者自己填写）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="warehouseBy">
              <a-input v-model="model.warehouseBy" placeholder="请输入库管人员（当前登录或者自己填写）"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="使用时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="useTime">
              <j-date placeholder="请选择使用时间"  v-model="model.useTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-input v-model="model.remark" placeholder="请输入备注"  ></a-input>
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
    name: 'ItdmInventoryLogForm',
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
          add: "/inventory/itdmInventoryLog/add",
          edit: "/inventory/itdmInventoryLog/edit",
          queryById: "/inventory/itdmInventoryLog/queryById"
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