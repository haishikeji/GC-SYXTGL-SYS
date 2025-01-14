<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="处理方式">
              <a-input placeholder="请输入处理方式" v-model="queryParam.handlingMethod"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button type="primary" icon="download" @click="handleExportXls('样品过期表')">导出</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text,record">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" :preview="record.id" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEditTG(record)">审批通过</a>
          <a-divider type="vertical" />
          <a @click="handleEditJJ(record)">审批拒绝</a>
<!--          <a-dropdown>-->
<!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
<!--            <a-menu slot="overlay">-->
<!--              <a-menu-item>-->
<!--                <a @click="handleEditJJ(record)">审批拒绝</a>-->
<!--              </a-menu-item>-->
<!--            </a-menu>-->
<!--          </a-dropdown>-->
        </span>

      </a-table>
    </div>
    <itdm-sample-expire-modal1 ref="modalForm" @ok="modalFormOk"></itdm-sample-expire-modal1>
  </a-card>
</template>

<script>

import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
import ItdmSampleExpireModal1 from './modules/ItdmSampleExpireModal1'
import { sampleExpireSH } from '@/api/api.js'

export default {
  name: 'ItdmSampleExpireList1',
  mixins:[JeecgListMixin, mixinDevice],
  components: {
    ItdmSampleExpireModal1,
  },
  data () {
    return {
      description: '样品过期表管理页面',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key:'rowIndex',
          width:60,
          align:"center",
          customRender:function (t,r,index) {
            return parseInt(index)+1;
          }
        },
        {
          title:'条码列表',
          align:"center",
          dataIndex: 'barCodes'
        },
        {
          title:'样品列表',
          align:"center",
          dataIndex: 'samples'
        },
        {
          title:'处理方式',
          align:"center",
          dataIndex: 'handlingMethod'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align:"center",
          fixed:"right",
          width:200,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: "/inventory/itdmSampleExpire/shenheList",
        delete: "/inventory/itdmSampleExpire/delete",
        deleteBatch: "/inventory/itdmSampleExpire/deleteBatch",
        exportXlsUrl: "/inventory/itdmSampleExpire/exportXls",
        importExcelUrl: "inventory/itdmSampleExpire/importExcel",

      },
      dictOptions:{},
      superFieldList:[],
    }
  },
  created() {
    this.getSuperFieldList();
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    },
  },
  methods: {
    initDictConfig(){
    },
    getSuperFieldList(){
      let fieldList=[];
      fieldList.push({type:'string',value:'barCodes',text:'过期的条码列表',dictCode:''})
      fieldList.push({type:'string',value:'handlingMethod',text:'处理方式',dictCode:''})
      fieldList.push({type:'string',value:'auditStatus',text:'审核状态',dictCode:'audit_status'})
      fieldList.push({type:'string',value:'rejectReason',text:'拒绝原因',dictCode:''})
      this.superFieldList = fieldList
    },
    handleEditTG(record){
      console.log("record",record)
      record.auditStatus = 1
      sampleExpireSH(record).then((res) => {
        console.log(res)
        this.loadData();
      })
    },
    handleEditJJ(record){
      console.log("拒绝record",record)
      record.auditStatus = 2
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.title = "编辑"
      this.$refs.modalForm.disableSubmit = false

      // record.auditStatus = 2
      // sampleExpireSH(record).then((res) => {
      //   console.log(res)
      //   this.loadData();
      // })
    },
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>