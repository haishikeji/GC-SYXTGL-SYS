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
    <itdm-device-form ref="realForm" @ok="submitCallback" :editdisabled="editdisabled"  :disabled="disableSubmit"></itdm-device-form>
  </j-modal>
</template>

<script>

  import ItdmDeviceForm from './ItdmDeviceForm'
  export default {
    name: 'ItdmDeviceModal',
    components: {
      ItdmDeviceForm
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
        this.editdisabled=false
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.add();
        })
      },
      edit (record) {
        console.log(777,record.izDelete)
        if (record.izDelete == 1) {
          this.editdisabled=false
        } else {
          this.editdisabled=true
        }
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