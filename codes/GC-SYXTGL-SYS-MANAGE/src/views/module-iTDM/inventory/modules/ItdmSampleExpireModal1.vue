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
    <itdm-sample-expire-form1  ref="realForm" @ok="submitCallback" :disabled="disableSubmit" :editdisabled="editdisabled"></itdm-sample-expire-form1>
  </j-modal>
</template>

<script>

import ItdmSampleExpireForm1 from './ItdmSampleExpireForm1'
export default {
  name: 'ItdmSampleExpireModal1',
  components: {
    ItdmSampleExpireForm1,
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