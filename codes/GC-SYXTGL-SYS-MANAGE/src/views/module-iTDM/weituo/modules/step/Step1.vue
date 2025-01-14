<template>


  <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
    <a-row>
      <a-col :span="24">
        <a-form-model-item label="委托单位名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoClient">
          <a-auto-complete
            :disabled="formDisabled"
            v-model="model.weituoClient"
            placeholder="请输入委托单位名称"
            option-label-prop="label"
            @change="handleSelect"
            @search="querySearch"
          >
          <template slot="dataSource">
            <a-select-option v-for="(item, index) in weituoClients" :key="index" :value="item.id" :label="item.weituiClient">
              <el-row>
                <el-col :span="12">{{item.weituiClient}}</el-col>
                <el-col :span="12" style="text-align: right;">{{ item.weituiLxr }}</el-col>
              </el-row>
            </a-select-option>
          </template>


          </a-auto-complete>

        </a-form-model-item>
      </a-col>
      <a-col :span="24">
        <a-form-model-item label="委托单位地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoAddress">
          <a-input v-model="model.weituoAddress" placeholder="请输入委托单位地址" :disabled="formDisabled"></a-input>
        </a-form-model-item>
      </a-col>
      <a-col :span="24">
        <a-form-model-item label="委托联系人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoLxr">
          <a-input v-model="model.weituoLxr" placeholder="请输入委托联系人" :disabled="formDisabled"></a-input>
        </a-form-model-item>
      </a-col>
      <a-col :span="24">
        <a-form-model-item label="委托电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoPhone">
          <a-input v-model="model.weituoPhone" placeholder="请输入委托电话" :disabled="formDisabled"></a-input>
        </a-form-model-item>
      </a-col>
      <a-col :span="24">
        <a-form-model-item label="委托邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoEmail">
          <a-input v-model="model.weituoEmail" placeholder="请输入委托邮箱" :disabled="formDisabled"></a-input>
        </a-form-model-item>
      </a-col>
      <a-col :span="24">
        <a-form-model-item label="报告用章" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bgyz">
          <j-multi-select-tag v-model="model.bgyz" placeholder="请选择报告用章" :disabled="formDisabled"
                              dictCode="weituo.bgyz" />
        </a-form-model-item>
      </a-col>
      <a-col :span="24">
        <a-form-model-item label="报告形式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bgxs">


          <j-dict-select-tag v-model="model.bgxs" placeholder="请选择报告形式" :disabled="formDisabled"
                             dictCode="weituo.bgxs" />

        </a-form-model-item>
      </a-col>
      <a-col :span="24">
        <a-form-model-item label="特殊要求" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="teshuyaoqiu">
          <a-textarea v-model="model.teshuyaoqiu" placeholder="请输入特殊要求" :disabled="formDisabled"></a-textarea>
        </a-form-model-item>
      </a-col>

      <a-col :span="24">
        <a-form-model-item label="试验条件文件地址" :labelCol="labelCol" :wrapperCol="wrapperCol"
                           prop="tiaojianFile">
          <j-upload v-model="model.tiaojianFile" :disabled="formDisabled"></j-upload>
        </a-form-model-item>
      </a-col>
    </a-row>
    <a-form-item class="buttonAll">
      <div class="all">
        <a-button type="primary" @click="nextStep" class="next">下一步</a-button>
      </div>
    </a-form-item>
  </a-form-model>


</template>

<script>

import { httpAction, getAction } from '@api/manage'
import { validateDuplicateValue } from '@/utils/util'

export default {
  name: 'step1',
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false
    }
  },
  components: {},
  data() {
    return {
      restaurants: [], // 获取的所有客户信息
      weituoClients: [], // 搜索用的客户下拉列表
      // model: {},
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
        weituoClient: [
          { required: true, message: '请输入委托单位!' }
        ],
        weituoAddress: [
          { required: true, message: '请输入委托单位!' }
        ],
        weituoLxr: [
          { required: true, message: '请输入委托联系人!' }
        ],
        weituoPhone: [
          { required: true, message: '请输入委托电话!' }
        ],

        bgyz: [
          { required: true, message: '请输入报告用章!' }
        ],
        bgxs: [
          { required: true, message: '请输入报告形式!' }
        ],
        teshuyaoqiu: [
          { required: true, message: '请输入特殊要求!' }
        ]
      }

    }
  },
  created() {

    this.init()
    this.initweituo()

  },
  computed: {
    formDisabled() {
      return this.disabled
    },
    model() {
      return this.$store.getters.baseInfos
    }
  },

  methods: {

    initweituo() {
      getAction('/ItdmWtkehu/itdmWtkehu/olist', {}).then((res) => {
        if (res.success) {
          this.restaurants = res.result
          this.weituoClients = res.result
        }
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
        return (restaurant.weituiClient.toLowerCase().indexOf(queryString.toLowerCase()) !== -1) || (restaurant.weituiLxr.toLowerCase().indexOf(queryString.toLowerCase()) !== -1)
      }
    },
    // 选择委托单位
    handleSelect(item) {
      console.log(item)

      var v = this.restaurants.find(i => i.id === item)
      // var v = this.restaurants.find(i => i.weituiClient === item)
      if(v){
        this.$set(this.model, "weituoClient", v.weituiClient)
        this.$set(this.model, "weituoAddress", v.weituiAddress)
        this.$set(this.model, "weituoEmail", v.weituiEmail)
        this.$set(this.model, "weituoLxr", v.weituiLxr)
        this.$set(this.model, "weituoPhone", v.weituiPhone)
        this.$refs.form.validate()
      }
    },

    init() {
      // const data = JSON.parse(sessionStorage.getItem('data'))

      // if (data != null) {
      //   this.model = data

      // }
    },
    handleAsyncChange(value) {
      console.log(value)
    },
    nextStep() {
      const data = {
        weituoClient: this.model.weituoClient,
        weituoAddress: this.model.weituoAddress,
        weituoLxr: this.model.weituoLxr,
        weituoPhone: this.model.weituoPhone,
        weituoEmail: this.model.weituoEmail,
        bgyz: this.model.bgyz,
        bgxs: this.model.bgxs,
        teshuyaoqiu: this.model.teshuyaoqiu,
        tiaojianFile: this.model.tiaojianFile
      }

      // sessionStorage.setItem('data', JSON.stringify(data))
      this.$store.commit('SET_BASEINFO', data);

      this.$refs.form.validate(valid => {

        if(valid){
          this.$emit('nextStep')

        }
      })
    },
  }

}
</script>

<style scoped>
.next {
  width: 35%;
  margin-left: 20px;
  margin-right: 20px;
  margin-top: 20px;
}

.buttonAll {
  width: 100%;
  align-items: center;
  justify-content: center;
}

.all {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>