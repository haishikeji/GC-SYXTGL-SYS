<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel"
    cancelText="关闭">
    <itdm-wenxiang-history-szh-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></itdm-wenxiang-history-szh-form>
  </j-modal>
</template>

<script>

  import ItdmWenxiangHistorySzhForm from './ItdmWenxiangHistorySzhForm'
  export default {
    name: 'ItdmWenxiangHistorySzhModal',
    components: {
      ItdmWenxiangHistorySzhForm
    },
    data () {
      return {
        title:'',
        width:800,
        visible: false,
        disableSubmit: false
      }
    },
    methods: {
      add () {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.add();
        })
      },
      edit (record) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.edit(record);
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        this.$refs.realForm.submitForm();
      },
      submitCallback(){
        this.$emit('ok');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>