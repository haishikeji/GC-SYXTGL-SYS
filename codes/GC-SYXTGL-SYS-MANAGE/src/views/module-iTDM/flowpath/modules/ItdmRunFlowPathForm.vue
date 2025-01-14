<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>

          
          <a-col :span="24">
            <a-form-model-item label="委托编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoNo">
              <j-search-select-tag  placeholder="请选择委托编号"  v-model="model.weituoNo"   :dictOptions="weituoNoList" />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="24"> -->
<!--            <a-form-model-item label="流程" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="flowPathId">-->
<!--              <j-search-select-tag placeholder="请选择流程id" v-model="model.flowPathId" :dictOptions="flowPathOptions"/>-->
<!--            </a-form-model-item>-->
          <!-- </a-col> -->
          <a-col :span="24">
            <a-form-model-item label="责任人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="userName">
              <a-select
              v-model="model.userName"
              placeholder="请选择责任人"
              show-search
              allowClear
              option-label-prop="label">
                  <a-select-option
                  v-for="item in zrrOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                  <span style="float: left">{{ item.label }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
                  </a-select-option>
              </a-select>
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
  import { queryFlowPathSelectList, getFirNextZrr } from '@api/api'

  export default {
    name: 'ItdmRunFlowPathForm',
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
        weituoNoList: [],
        flowPathOptions:[],

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
          weituoNo: [
            { required: true, message: '请选择委托编号!'},
          ],
          userName: [
            { required: true, message: '请选择责任人!'},
          ],
        },
        url: {
          add: "/flowpath/itdmRunFlowPath/add",
          edit: "/flowpath/itdmRunFlowPath/edit",
          queryById: "/flowpath/itdmRunFlowPath/queryById"
        },
        // 责任人
        zrrOptions: [],
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
      this.initFlowPathDictData()

    },
    created(){
      this.initFlowPathDictData()
      this.getZrrList()
    },
    methods: {

      // 获取责任人列表
      getZrrList(){
        getFirNextZrr().then((res) =>{
            this.zrrOptions = res.map(res => {
                return {
                    value: res.userName,  //传的值
                    label: res.realName,  //展示
                }
            })
        })
      },

      initFlowPathDictData() {
        queryFlowPathSelectList().then(response => {
          this.flowPathOptions = response.result
        })

        console.log("xxxxxxxxxxxxxxxxxxx")

        getAction('/weituo/itdmWeituoInfo/shenheList').then((res) => {
          if (res.success) {
            console.log(res.result)
            this.weituoNoList = res.result.map(i=>{
              return { value: i.weituoNo, text: i.weituoNo}
            })
            console.log(this.weituoNoList)
          }
        })
      },
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        console.log(this.model)
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
            var data = this.model

            data.flowPathId='1681829245243805698'
            console.log(data)

            httpAction(httpurl,data,method).then((res)=>{
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