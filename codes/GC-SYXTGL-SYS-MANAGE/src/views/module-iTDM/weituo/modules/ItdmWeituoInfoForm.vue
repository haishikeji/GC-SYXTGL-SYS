<template>


  <a-card :bordered="false">
    <a-steps class="steps" :current="currentTab">
      <a-step title="填写基本信息" />
      <a-step title="填写样品信息" />
      <a-step title="填写试验信息" />
    </a-steps>
    <div class="content">
      <step1 v-if="currentTab === 0" ref="data001" @nextStep="nextStep"   :disabled="disabled"/>
      <step2 v-if="currentTab === 1" @prevStep="prevStep" ref="ypxx" @nextStep="nextStep"  :disabled="disabled" />
      <step3 v-if="currentTab === 2" @submitForm="submitForm" ref="syxx" @prevStep="prevStep" :disabled="disabled" />
    </div>
  </a-card>


</template>

<script>

import { httpAction, getAction, postAction  } from '@api/manage'
import { validateDuplicateValue } from '@/utils/util'
import Step1 from './step/Step1'
import Step2 from './step/Step2'
import Step3 from './step/Step3'

export default {
  name: 'ItdmWeituoInfoForm',
  components: {
    Step1,
    Step2,
    Step3
  },
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
      currentTab: 0,
      weituomodel: {},
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
      validatorRules: {},
      url: {
        add: '/weituo/itdmWeituoInfo/add',
        edit: '/weituo/itdmWeituoInfo/edit',
        queryById: '/weituo/itdmWeituoInfo/queryById'
      }
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
    baseInfos() {
      return this.$store.getters.baseInfos
    },
    yangpinInfos() {
      return this.$store.getters.yangpinInfos
    },
    shiyanInfos() {
      return this.$store.getters.shiyanInfos
    },
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {
    nextStep() {
      if (this.currentTab < 3) {
        this.currentTab += 1
      }

    },
    prevStep() {
      if (this.currentTab > 0) {
        this.currentTab -= 1
      }
    },
    add() {
      this.model = Object.assign({}, this.modelDefault)
      this.visible = true
    },
    edit(record) {
      const that = this
      const select = { id: record.id }
      getAction(this.url.queryById, select).then((res) => {
        if (res.success) {
          this.model.id = res.result.id
          this.$store.commit('SET_BASEINFO', res.result.data);
          this.$store.commit('SET_YANGPININFO', res.result.yangpins);
          this.$store.commit('SET_SHIYANINFO', res.result.pinShiYans);
          this.$refs.data001.init()

        } else {
          that.$message.warning(res.message)
        }
      })
      this.visible = true
    },
    // 提交
    submitForm() {
      const that = this
      // 触发表单验证
      this.weituomodel = this.baseInfos
      this.weituomodel.yangpins = this.yangpinInfos
      this.weituomodel.pinShiYans = this.shiyanInfos
      
      that.confirmLoading = true
      let httpurl = ''
      let method = ''
      if (!this.model.id) {
        httpurl += this.url.add
        method = 'post'
      } else {
        httpurl += this.url.edit
        method = 'put'
        this.weituomodel.id = this.model.id
      }
      // console.log(this.weituomodel)
      httpAction(httpurl, this.weituomodel, method).then((res) => {
        if (res.success) {
          that.$message.success(res.message)
          that.$emit('ok')
        } else {
          that.$message.warning(res.message)
        }
      }).finally(() => {
        that.confirmLoading = false
      })
    },

    handleCategoryChange(value, backObj) {
      this.model = Object.assign(this.model, backObj)
    }
  }
}
</script>