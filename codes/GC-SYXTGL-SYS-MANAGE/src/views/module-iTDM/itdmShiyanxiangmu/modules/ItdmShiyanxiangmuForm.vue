<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="是否新客户" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="newkehu">
              <a-input v-model="model.newkehu" placeholder="请输入是否新客户"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="客户行业" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="kehuHangye">
              <a-input v-model="model.kehuHangye" placeholder="请输入客户行业"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="kehuMingcheng">
              <a-input v-model="model.kehuMingcheng" placeholder="请输入客户名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="输入类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shuruLeixing">
              <a-input v-model="model.shuruLeixing" placeholder="请输入输入类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="试验信息" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shiyanXinxi">
              <a-input v-model="model.shiyanXinxi" placeholder="请输入试验信息"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="方案设计" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fanganSheji">
              <j-dict-select-tag type="radio" v-model="model.fanganSheji" dictCode="fangan_sheji" placeholder="请选择方案设计" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="试验评审" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shiyanPingshen">
              <j-dict-select-tag type="radio" v-model="model.shiyanPingshen" dictCode="shiyan_pingshen" placeholder="请选择试验评审" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="用车安排" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="yongcheAnpai">
              <j-dict-select-tag type="radio" v-model="model.yongcheAnpai" dictCode="yongche_anpai" placeholder="请选择用车安排" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="工装设计" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="gongzhuangSheji">
              <j-dict-select-tag type="radio" v-model="model.gongzhuangSheji" dictCode="gongzhuang_sheji" placeholder="请选择工装设计" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="用餐安排" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="yongcanAnpai">
              <j-dict-select-tag type="radio" v-model="model.yongcanAnpai" dictCode="yongcan_anpai" placeholder="请选择用餐安排" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="住宿安排" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="zhusuAnpai">
              <j-dict-select-tag type="radio" v-model="model.zhusuAnpai" dictCode="zhusu_anpai" placeholder="请选择住宿安排" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报告确认" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="baogaoQueren">
              <j-dict-select-tag type="radio" v-model="model.baogaoQueren" dictCode="baogao_queren" placeholder="请选择报告确认" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="客户姓名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="kehuName">
              <a-input v-model="model.kehuName" placeholder="请输入客户姓名"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="客户电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="kehuPhone">
              <a-input v-model="model.kehuPhone" placeholder="请输入客户电话"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="合同编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="hetongBianhao">
              <a-input v-model="model.hetongBianhao" placeholder="请输入合同编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="试验要求" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shiyanYaoqiu">
              <a-input v-model="model.shiyanYaoqiu" placeholder="请输入试验要求"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报告体系" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="baogaoTixi">
              <j-dict-select-tag type="radio" v-model="model.baogaoTixi" dictCode="baogao_tixi" placeholder="请选择报告体系" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="资质要求" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="zizhiYaoqiu">
              <j-dict-select-tag type="radio" v-model="model.zizhiYaoqiu" dictCode="zizhi_yaoqiu" placeholder="请选择资质要求" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="预计报价" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="yujiBaojia">
              <a-input v-model="model.yujiBaojia" placeholder="请输入预计报价"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="评审结论" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pingshenJielun">
              <a-input v-model="model.pingshenJielun" placeholder="请输入评审结论"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="主审人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="zhushenRen">
              <a-input v-model="model.zhushenRen" placeholder="请输入主审人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="主审时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="zhushenShijian">
              <a-input v-model="model.zhushenShijian" placeholder="请输入主审时间"  ></a-input>
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
    name: 'ItdmShiyanxiangmuForm',
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
            fanganSheji:"1",
            shiyanPingshen:"1",
            yongcheAnpai:"1",
            gongzhuangSheji:"1",
            yongcanAnpai:"1",
            zhusuAnpai:"1",
            baogaoQueren:"1",
            baogaoTixi:"0",
            zizhiYaoqiu:"0",
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
          add: "/itdmShiYanXiangMu/itdmShiyanxiangmu/add",
          edit: "/itdmShiYanXiangMu/itdmShiyanxiangmu/edit",
          queryById: "/itdmShiYanXiangMu/itdmShiyanxiangmu/queryById"
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