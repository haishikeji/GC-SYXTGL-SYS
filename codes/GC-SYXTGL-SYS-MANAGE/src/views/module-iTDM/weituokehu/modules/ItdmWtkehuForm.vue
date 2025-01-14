<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="委托单位名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituiClient">
              <!-- <a-input v-model="model.weituiClient" placeholder="请输入委托单位名称"  ></a-input> -->
              <a-auto-complete
                :disabled="formDisabled"
                v-model="model.weituiClient"
                placeholder="请输入委托单位名称"
                option-label-prop="label"
                @change="handleSelect"
                @search="querySearch"
              >
              <template slot="dataSource">
                <a-select-option v-for="(item, index) in weituoClients" :key="index" :value="item.id" :label="item.weituiClient">
                  {{item.weituiClient}}
                </a-select-option>
              </template>


              </a-auto-complete>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托单位地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituiAddress">
              <a-input v-model="model.weituiAddress" placeholder="请输入委托单位地址"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托联系人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituiLxr">
              <a-input v-model="model.weituiLxr" placeholder="请输入委托联系人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituiPhone">
              <a-input v-model="model.weituiPhone" placeholder="请输入委托电话"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="委托邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituiEmail">
              <a-input v-model="model.weituiEmail" placeholder="请输入委托邮箱"  ></a-input>
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
    name: 'ItdmWtkehuForm',
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
        restaurants: [], // 获取的所有客户信息
        weituoClients: [], // 搜索用的客户下拉列表
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
           weituiClient: [
              { required: true, message: '请输入委托单位名称!'},
           ],
           weituiAddress: [
              { required: true, message: '请输入委托单位地址!'},
           ],
           weituiLxr: [
              { required: true, message: '请输入委托联系人!'},
           ],
           weituiPhone: [
              { required: true, message: '请输入委托电话!'},
           ],
        },
        url: {
          add: "/ItdmWtkehu/itdmWtkehu/add",
          edit: "/ItdmWtkehu/itdmWtkehu/edit",
          queryById: "/ItdmWtkehu/itdmWtkehu/queryById"
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
      this.initweituo()
    },
    methods: {
      initweituo() {
        getAction('/ItdmWtkehu/itdmWtkehu/xialalist', {}).then((res) => {
          this.restaurants = res
          this.weituoClients = res
        })
      },
      // 带输入建议的输入框
      querySearch(queryString) {
        var restaurants = this.restaurants
        var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
        console.log(queryString, results)

        this.weituoClients = results
      },
      createFilter(queryString) {
        return (restaurant) => {
          console.log(restaurant)
          return (restaurant.weituiClient.toLowerCase().indexOf(queryString.toLowerCase()) !== -1)
        }
      },
      // 选择委托单位
      handleSelect(item) {
        console.log(item)

        var v = this.restaurants.find(i => i.id == item)
        // var v = this.restaurants.find(i => i.weituiClient === item)
        if(v){
          console.log(v)
          this.$set(this.model, "weituiClient", v.weituiClient)
          this.$set(this.model, "weituiAddress", v.weituiAddress)
          this.$refs.form.validate()
          console.log(this.model)
        }
      },
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