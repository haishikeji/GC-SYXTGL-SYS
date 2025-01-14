<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
           <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="委托单位名称">
              <j-search-select-tag placeholder="请选择委托单位名称" :dictOptions="weituoClientList" @change="handlewcChange"/>
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="委托单位地址">
              <a-input placeholder="请输入委托单位地址" v-model="queryParam.weituoAddress"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <!-- <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a> -->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('委托信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
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
          <a @click="handleEdit(record)">审核</a>

             <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail2(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <itdm-weituo-info-modal2 ref="modalForm" @ok="modalFormOk"></itdm-weituo-info-modal2>
    <itdm-weituo-info-modal ref="modalForm1"></itdm-weituo-info-modal>
  </a-card>
</template>

<script>

import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import ItdmWeituoInfoModal2 from './modules/ItdmWeituoInfoModal2'
import { ajaxGetDictItems, loadCategoryData } from '@/api/api'
import { filterMultiDictText } from '@comp/dict/JDictSelectUtil'
 import { httpAction, getAction } from '@/api/manage'
import ItdmWeituoInfoModal from '../weituo/modules/ItdmWeituoInfoModal.vue'
export default {
  name: 'ItdmWeituoInfoList',
  mixins:[JeecgListMixin, mixinDevice],
  components: {
    ItdmWeituoInfoModal2,
    ItdmWeituoInfoModal
  },
  data () {
    return {
      weituoClientList:[],
      description: '委托信息管理页面',
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
          title:'委托单位名称',
          align:"center",
          dataIndex: 'weituoClient'
        },
        
        // {
        //   title:'委托单位地址',
        //   align:"center",
        //   dataIndex: 'weituoAddress'
        // },
        {
          title:'委托联系人',
          align:"center",
          dataIndex: 'weituoLxr'
        },
        // {
        //   title:'委托电话',
        //   align:"center",
        //   dataIndex: 'weituoPhone'
        // },
            {
            title:'委托编号',
            align:"center",
            dataIndex: 'weituoNo'
          },
        // {
        //   title:'委托邮箱',
        //   align:"center",
        //   dataIndex: 'weituoEmail'
        // },
        // {
        //   title:'报告用章',
        //   align:"center",
        //   dataIndex: 'bgyz',
        //   customRender: (text) => (text ? filterMultiDictText(this.dictOptions['bgyz'], text) : '')
        // },
        // {
        //   title:'报告形式',
        //   align:"center",
        //   dataIndex: 'bgxs',
        //   customRender: (text) => (text ? filterMultiDictText(this.dictOptions['bgxs'], text) : '')
        // },
        // {
        //   title:'特殊要求',
        //   align:"center",
        //   dataIndex: 'teshuyaoqiu'
        // },
           {
          title:'审核时间',
          align:"center",
          dataIndex: 'shenheTime'
        },
           {
          title:'审核拒绝原因',
          align:"center",
          dataIndex: 'shenheMsg'
        },
        {
          title:'审核状态',
          align:"center",
          dataIndex: 'shenheStatus_dictText'
        },
        {
          title:'试验条件文件地址',
          align:"center",
          dataIndex: 'tiaojianFile',
          scopedSlots: {customRender: 'fileSlot'}
        },
        {
          title: '操作',
          dataIndex: 'action',
          align:"center",
          fixed:"right",
          width:147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: "/weituo/itdmWeituoShenhe/list",
        
        delete: "/weituo/itdmWeituoInfo/delete",
        deleteBatch: "/weituo/itdmWeituoInfo/deleteBatch",
        exportXlsUrl: "/weituo/itdmWeituoInfo/exportXls",
        importExcelUrl: "weituo/itdmWeituoInfo/importExcel",
           edit: "/weituo/itdmWeituoInfo/saveOrUpdateWeituo"

      },
      dictOptions:{},
      superFieldList:[],
    }
  },
  created() {
    this.init();
    this.getSuperFieldList();
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    },
  },
  
  methods: {
          init(){

        getAction('/weituo/itdmWeituoInfo/distinctClientList', {}).then((res) => {
          if (res.success) {
            console.log(res.result)
            this.weituoClientList = res.result.map(i=>{
              return { value: i, text: i}
            })
            console.log(this.weituoClientList)
          }
        })

      },
      
          handlewcChange(data){

      var weituoClient={weituoClient:data}
      getAction('/weituo/itdmWeituoInfo/weituoNoList', weituoClient).then((res) => {
        if (res.success) {
          console.log("xxxxxxxxxxxxxxxxxxx")
          console.log(res.result)
          this.weituoNoList = res.result.map(i=>{
            return { value: i.id, text: i.weituoNo}
          })
          console.log(this.weituoNoList)
        }
      })




      console.log(data)
      },

    initDictConfig(){
      ajaxGetDictItems('weituo.bgyz').then((res) => {
        if (res.success) {
          console.log(res.result)
          this.$set(this.dictOptions, 'bgyz', res.result)
        }
      })
      ajaxGetDictItems('weituo.bgxs').then((res) => {
        if (res.success) {
          console.log(res.result)
          this.$set(this.dictOptions, 'bgxs', res.result)
        }
      })
    },
    getSuperFieldList(){
      let fieldList=[];
      fieldList.push({type:'string',value:'weituoClient',text:'委托单位名称',dictCode:''})
      fieldList.push({type:'string',value:'weituoAddress',text:'委托单位地址',dictCode:''})
      fieldList.push({type:'string',value:'weituoLxr',text:'委托联系人',dictCode:''})
      fieldList.push({type:'string',value:'weituoPhone',text:'委托电话',dictCode:''})
      fieldList.push({type:'string',value:'weituoEmail',text:'委托邮箱',dictCode:''})
      fieldList.push({type:'string',value:'shenheTime',text:'审核时间',dictCode:''})
      fieldList.push({type:'string',value:'shenheMsg',text:'审核拒绝原因',dictCode:''})
      fieldList.push({type:'string',value:'shenheStatus_dictText',text:'审核状态',dictCode:''})
      fieldList.push({type:'string',value:'bgyz',text:'报告用章'})
      fieldList.push({type:'string',value:'bgxs',text:'报告形式'})
      fieldList.push({type:'string',value:'teshuyaoqiu',text:'特殊要求',dictCode:''})
      fieldList.push({type:'string',value:'tiaojianFile',text:'试验条件文件地址',dictCode:''})
      this.superFieldList = fieldList
    },
    // 详情
    handleDetail2:function(record){
      this.$refs.modalForm1.edit(record);
      this.$refs.modalForm1.title="详情";
      this.$refs.modalForm1.disableSubmit = true;
    },
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>