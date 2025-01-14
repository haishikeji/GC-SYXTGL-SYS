<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="委托单位名称">
              <a-select
                v-model="queryParam.weituoClient"
                label-in-value
                show-search
                :options="weituoClientOptions"
                @change="getWeituoClientOptions"
              ></a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="委托编号">
              <a-select
                v-model="queryParam.weituoId"
                key-in-value
                show-search
                :options="weituoNoOptions"
              ></a-select>
            </a-form-item>
          </a-col>
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="工单id">-->
<!--              <a-input placeholder="请输入工单id" v-model="queryParam.woId"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="实际设备id">-->
<!--              <j-search-select-tag placeholder="请选择实际设备id" v-model="queryParam.shijiShebeiId" dict="itdm_device,device_name,id"/>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
          <template v-if="toggleSearchStatus">
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="实际试验类型">-->
<!--                <a-input placeholder="请输入实际试验类型" v-model="queryParam.shijiShiiyanType"></a-input>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item label="任务日期">
                <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.workDate_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.workDate_end"></j-date>
              </a-form-item>
            </a-col>
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item label="实际任务日期">
                <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.shijiWorkDate_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.shijiWorkDate_end"></j-date>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
      <a-button type="primary" icon="download" @click="handleExportXls('工单detail')">导出</a-button>
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
      <!-- 高级查询区域 -->
<!--      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
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

<!--        <span slot="action" slot-scope="text, record">-->
<!--          <a @click="handleEdit(record)">编辑</a>-->

<!--          <a-divider type="vertical" />-->
<!--          <a-dropdown>-->
<!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
<!--            <a-menu slot="overlay">-->
<!--              <a-menu-item>-->
<!--                <a @click="handleDetail(record)">详情</a>-->
<!--              </a-menu-item>-->
<!--              <a-menu-item>-->
<!--                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
<!--                  <a>删除</a>-->
<!--                </a-popconfirm>-->
<!--              </a-menu-item>-->
<!--            </a-menu>-->
<!--          </a-dropdown>-->
<!--        </span>-->

      </a-table>
    </div>

    <itdm-gongdan-detail-modal ref="modalForm" @ok="modalFormOk"></itdm-gongdan-detail-modal>
  </a-card>
</template>

<script>

import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import ItdmGongdanDetailModal from './modules/ItdmGongdanDetailModal'
import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
import { getWeituoClientList,getWeituoInfoListByClient } from '@/api/api'


export default {
  name: 'ItdmGongdanDetailList',
  mixins:[JeecgListMixin, mixinDevice],
  components: {
    ItdmGongdanDetailModal,
  },

  data () {
    return {
      weituoClientQuery: {
        weituoClient: '',
      },
      weituoClientOptions : [],
      weituoNoQuery: {
        weituoNo: '',
      },
      weituoIdQuery: {
        weituoId: '',
      },
      weituoNoOptions:[],
      isorter: { // 排序参数
        column: 'woId,workDate', // 排序字段
        order: 'asc,asc' // 正序asc  or  倒叙desc
      },
      description: '工单detail管理页面',
      // 表头
      columns: [
        // {
        //   title: '#',
        //   dataIndex: '',
        //   key:'rowIndex',
        //   width:60,
        //   align:"center",
        //   customRender:function (t,r,index) {
        //     return parseInt(index)+1;
        //   }
        // },
        {
          title:'工单号',
          align:"center",
          dataIndex: 'woId'
        },
        {
          title:'委托单位',
          align:"center",
          dataIndex: 'weituoClient'
        },
        {
          title:'委托编号',
          align:"center",
          dataIndex: 'weituoNo'
        },
        {
          title:'检测项目',
          align:"center",
          dataIndex: 'testItemsId_dictText'
        },
        // {
        //   title:'报告编号',
        //   align:"center",
        //   dataIndex: 'jihuaShiyanId_dictText'
        // },
        {
          title:'委托样品',
          align:"center",
          dataIndex: 'weituoYangpinId_dictText'
        },
        {
          title:'规格型号',
          align:"center",
          dataIndex: 'sampleModelSpecification'
        },
        {
          title:'计划设备',
          align:"center",
          dataIndex: 'shebeiId_dictText'
        },
        // 有时间改改_dictText
        {
          title:'实际设备',
          align:"center",
          dataIndex: 'shijiShebeiId_dictText'
        },
        {
          title:'试验类型',
          align:"center",
          dataIndex: 'shiiyanType_dictText'
        },
        {
          title:'试验员',
          align:"center",
          dataIndex: 'worker'
        },
        {
          title:'委托样品数量',
          align:"center",
          dataIndex: 'yangpinCount'
        },
        {
          title:'任务日期',
          align:"center",
          dataIndex: 'workDate',
          customRender:function (text) {
            return !text?"":(text.length>10?text.substr(0,10):text)
          }
        },
        {
          title:'试验开始时间',
          align:"center",
          dataIndex: 'startTime'
        },
        {
          title:'试验结束时间',
          align:"center",
          dataIndex: 'endTime'
        },
        {
          title:'传感器',
          align:"center",
          dataIndex: 'chuanganqi_dictText'
          // dataIndex: 'chuanganqi'
        },
        // {
        //   title:'实际设备',
        //   align:"center",
        //   dataIndex: 'shijiShebeiId_dictText'
        // },
        // {
        //   title:'实际试验类型',
        //   align:"center",
        //   dataIndex: 'shijiShiiyanType_dictText'
        // },
        // {
        //   title:'实际任务日期',
        //   align:"center",
        //   dataIndex: 'shijiWorkDate',
        //   customRender:function (text) {
        //     return !text?"":(text.length>10?text.substr(0,10):text)
        //   }
        // },
        // {
        //   title:'实际试验开始时间',
        //   align:"center",
        //   dataIndex: 'shijiStartTime'
        // },
        // {
        //   title:'实际试验结束时间',
        //   align:"center",
        //   dataIndex: 'shijiEndTime'
        // },
        // {
        //   title:'工单id',
        //   align:"center",
        //   dataIndex: 'woId'
        // },
        // {
        //   title:'创建时间',
        //   align:"center",
        //   dataIndex: 'createTime'
        // },
        // {
        //   title:'备注',F
        //   align:"center",
        //   dataIndex: 'remark'
        // },
        // {
        //   title: '操作',
        //   dataIndex: 'action',
        //   align:"center",
        //   fixed:"right",
        //   width:147,
        //   scopedSlots: { customRender: 'action' }
        // }
      ],
      url: {
        list: "/itdmGongdanDetail/itdmGongdanDetail/list",
        delete: "/itdmGongdanDetail/itdmGongdanDetail/delete",
        deleteBatch: "/itdmGongdanDetail/itdmGongdanDetail/deleteBatch",
        exportXlsUrl: "/itdmGongdanDetail/itdmGongdanDetail/exportXls",
        importExcelUrl: "itdmGongdanDetail/itdmGongdanDetail/importExcel",

      },
      dictOptions:{},
      superFieldList:[],
    }
  },
  created() {
    this.getSuperFieldList();
    this.getClientList();
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    },
  },
  methods: {
    // 获得委托单位下拉列表，此方法要在created里，初始化时调用
    getClientList(){
      getWeituoClientList().then((res) =>{
        console.log(res)
        this.weituoClientOptions = res.result.map( res => {
          return{
            value: res,
            label: res
          }
        })
      })
    },
    // 监听触发方法
    getWeituoClientOptions(value){
      console.log(value);
      this.weituoClientQuery.weituoClient = value.key;
      console.log('this.weituoClientQuery',this.weituoClientQuery);
      console.log('this.weituoClientQuery.weituoClient',this.weituoClientQuery.weituoClient);
      getWeituoInfoListByClient(this.weituoClientQuery).then((res) =>{
        console.log("this.weituoNoOptions",this.weituoNoOptions),
        this.weituoNoOptions = res.result.map( res => {
          return{
            value: res.id,  //传的值
            label: res.weituoNo,  //展示
          }
        })
      })
    },
    initDictConfig(){
    },
    getSuperFieldList(){
      let fieldList=[];
      fieldList.push({type:'int',value:'shijiShiiyanType_dictText',text:'实际试验类型',dictCode:'shiyan_type'})
      fieldList.push({type:'string',value:'woId',text:'工单id',dictCode:''})
      fieldList.push({type:'sel_search',value:'shijiShebeiId',text:'实际设备id',dictTable:"itdm_device", dictText:'device_name', dictCode:'id'})
      fieldList.push({type:'date',value:'workDate',text:'任务日期'})
      fieldList.push({type:'string',value:'startTime',text:'试验开始时间',dictCode:''})
      fieldList.push({type:'string',value:'endTime',text:'试验结束时间',dictCode:''})
      fieldList.push({type:'date',value:'shijiWorkDate',text:'实际任务日期'})
      fieldList.push({type:'string',value:'shijiStartTime',text:'实际试验开始时间',dictCode:''})
      fieldList.push({type:'string',value:'shijiEndTime',text:'实际试验结束时间',dictCode:''})
      fieldList.push({type:'string',value:'remark',text:'备注',dictCode:''})
      fieldList.push({type:'string',value:'chuanganqi',text:'传感器',dictCode:''})
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>