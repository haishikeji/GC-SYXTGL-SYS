<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="委托单位名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag placeholder="请选择委托单位名称" :dictOptions="weituoClientList"
                                   @change="handlewcChange" />
            </a-form-model-item>
          </a-col>

          <a-col :span="24">
            <a-form-model-item label="委托信息" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weituoId">


              <j-search-select-tag ref="weituoNo" v-model="model.weituoId" :dictOptions="weituoDictOptions"
                                   placeholder="请选择" @change="handleChange" />

            </a-form-model-item>
          </a-col>

          <a-col :span="24">
            <a-form-model-item label="委托样品" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sampleIds">
              <j-multi-select-tag ref="yangpin" v-model="model.sampleIds" placeholder="请选择"
                                  :options="yangppinDictOptions" @change="handleYPChange" />

            </a-form-model-item>

          </a-col>

          <a-col :span="24">
            <a-form-model-item label="委托样品检测项目" :labelCol="labelCol" :wrapperCol="wrapperCol"
                               prop="testItemsIds">

              <j-multi-select-tag ref="jiance" v-model="model.testItemsIds" placeholder="请选择"
                                  :options="yangppinLsDictOptions" />
            </a-form-model-item>
          </a-col>

          <a-col :span="24">
            <a-form-model-item label="文件类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="baogaoType">

              <j-search-select-tag v-model="model.baogaoType" dict="itdm_moban,name,id" />

            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="环境温度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="wendu">
              <a-input v-model="model.wendu" placeholder="请输入温度"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="环境湿度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shidu">
              <a-input v-model="model.shidu" placeholder="请输入湿度"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="环境大气压" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="daqiya">
              <a-input v-model="model.daqiya" placeholder="请输入大气压"></a-input>
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
      weituoClientList: [],

      yangppinLsDictOptions: [],
      weituoDictOptions: [],
      yangppinDictOptions: [],
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
        weituoId: [
          { required: true, message: '请选择委托信息 !' }
        ],
        sampleIds: [
          { required: true, message: '请选择委托样品' }
        ],
        testItemsIds: [
          { required: true, message: '请选择委托样品检测项目!' }
        ],
        baogaoType: [
          { required: true, message: '请输入文件类型!' }
        ]
      },
      url: {
        add: '/baogao/itdmBaogao/add',
        edit: '/baogao/itdmBaogao/edit',
        queryById: '/baogao/itdmBaogao/queryById'
      }
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    }
  },
  created() {
    this.init()
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {

    handlewcChange(data) {
      this.$refs.weituoNo.handleChange('')
      this.$refs.yangpin.onChange([])
      this.$refs.jiance.onChange([])


      var weituoClient = { weituoClient: data }
      getAction('/weituo/itdmWeituoInfo/weituoNoList', weituoClient).then((res) => {
        if (res.success) {
          this.weituoDictOptions = res.result.map(i => {
            return { value: i.id, text: i.weituoNo }

          })
        }
      })

    },

    init() {

      getAction('/weituo/itdmWeituoInfo/distinctClientList', {}).then((res) => {
        if (res.success) {
          console.log(res.result)
          this.weituoClientList = res.result.map(i => {
            return { value: i, text: i }
          })
          console.log(this.weituoClientList)
        }
      })

    },

    yangpmList(weituoId) {
      console.log(weituoId)
      if (weituoId !== '') {
        const select = { weituoId: weituoId }
        getAction('/baogao/itdmBaogao/yangpin/list', select).then((res) => {
          if (res.success) {
            this.yangppinDictOptions = res.result.map(i => {
              const map = {}
              map.label = i.sampleName
              map.value = i.id
              return map
            })

          } else {
            this.$message.warning(res.message)
          }
        })
      }else{
        this.yangppinDictOptions=[]
      }

    },

    queryShiyanList(yangpinIds) {
      console.log(yangpinIds)
      if (yangpinIds !== '') {
        const select = { 'yangpinIds': yangpinIds.toString() }
        getAction('/baogao/itdmBaogao/shiyan/list', select).then((res) => {
          if (res.success) {
            this.yangppinLsDictOptions = res.result.map(i => {
              const map = {}
              map.label = i.testItems
              map.value = i.id
              return map
            })

          } else {
            this.$message.warning(res.message)
          }
        })
      }else{
        this.yangppinLsDictOptions=[]
      }

    },

    handleYPChange(value) {

      this.$refs.jiance.onChange([])
      console.log(value)
      this.queryShiyanList(value)
    },
    handleChange(value) {
      this.$refs.yangpin.onChange([])
      this.$refs.jiance.onChange([])
      console.log(value)
      this.yangpmList(value)

      // this.value = value
      // fetch(value, data => (this.data = data))
    },
    add() {
      this.edit(this.modelDefault)
    },
    edit(record) {
      this.model = Object.assign({}, record)
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
          this.model.sampleIds=this.model.sampleIds.split(",")
          this.model.testItemsIds=this.model.testItemsIds.split(",")

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