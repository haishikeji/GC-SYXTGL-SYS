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
    <itdm-alarm-total-form ref="realForm" @ok="submitCallback" :editdisabled="editdisabled" :disabled="disableSubmit"></itdm-alarm-total-form>
  </j-modal>
</template>

<script>

  import ItdmAlarmTotalForm from './ItdmAlarmTotalForm'
  export default {
    name: 'ItdmAlarmTotalModal',
    components: {
      ItdmAlarmTotalForm
    },
    data () {
      return {
        title:'',
        width:800,
        visible: false,
        disableSubmit: false,
        editdisabled:false,
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
        this.editdisabled=true
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