<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="设备">
              <j-dict-select-tag placeholder="请选择设备" v-model="queryParam.deviceId" dictCode="itdm_device,device_name,id"/>
            </a-form-item>
          </a-col>
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="设备类型">-->
<!--              <a-input placeholder="请输入设备类型" v-model="queryParam.deviceType"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
          <template v-if="toggleSearchStatus">
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item label="设备开始运行时间">
                <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择开始时间" class="query-group-cust" v-model="queryParam.timeBegin_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束时间" class="query-group-cust" v-model="queryParam.timeBegin_end"></j-date>
              </a-form-item>
            </a-col>
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item label="设备结束运行时间">
                <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择开始时间" class="query-group-cust" v-model="queryParam.timeEnd_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束时间" class="query-group-cust" v-model="queryParam.timeEnd_end"></j-date>
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
      <a-button type="primary" icon="download" @click="handleExportXls('设备运行时间表')">导出</a-button>
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
<!--      &lt;!&ndash; 高级查询区域 &ndash;&gt;-->
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
<!--      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">-->
<!--        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项-->
<!--        <a style="margin-left: 24px" @click="onClearSelected">清空</a>-->
<!--      </div>-->

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
<!--          <a-menu-item>-->
<!--                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
<!--                  <a>删除</a>-->
<!--                </a-popconfirm>-->
<!--              </a-menu-item>-->
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

    <itdm-device-run-modal ref="modalForm" @ok="modalFormOk"></itdm-device-run-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ItdmDeviceRunModal from './modules/ItdmDeviceRunModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'ItdmDeviceRunList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      ItdmDeviceRunModal
    },
    data () {
      return {
        /* 排序参数 */
        isorter: {
          column: 'timeEnd', // 排序字段
          order: 'desc' // 正序asc  or  倒叙desc
        },
        description: '设备运行时间表管理页面',
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
          // {
          //   title:'设备id',
          //   align:"center",
          //   dataIndex: 'deviceId_dictText'
          // },
          {
            title:'设备名称',
            align:"center",
            dataIndex: 'deviceName'
          },
          {
            title:'设备类型',
            align:"center",
            dataIndex: 'deviceType'
          },
          {
            title:'设备开始运行时间',
            align:"center",
            dataIndex: 'timeBegin'
          },
          {
            title:'设备结束运行时间',
            align:"center",
            dataIndex: 'timeEnd'
          },
          {
            title:'设备本次运行时间',
            align:"center",
            dataIndex: 'timeRun'
          },
          // {
          //   title:'天',
          //   align:"center",
          //   dataIndex: 'timeDay'
          // },
          // {
          //   title:'小时',
          //   align:"center",
          //   dataIndex: 'timeHour'
          // },
          // {
          //   title:'分钟',
          //   align:"center",
          //   dataIndex: 'timeMinute'
          // },
          // {
          //   title:'秒',
          //   align:"center",
          //   dataIndex: 'timeSecond'
          // },
          // {
          //   title:'创建日期',
          //   align:"center",
          //   dataIndex: 'createTime'
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
          list: "/itdmDeviceRun/itdmDeviceRun/list",
          delete: "/itdmDeviceRun/itdmDeviceRun/delete",
          deleteBatch: "/itdmDeviceRun/itdmDeviceRun/deleteBatch",
          exportXlsUrl: "/itdmDeviceRun/itdmDeviceRun/exportXls",
          importExcelUrl: "itdmDeviceRun/itdmDeviceRun/importExcel",

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
        fieldList.push({type:'int',value:'deviceId',text:'设备id',dictCode:"itdm_device,device_name,id"})
        fieldList.push({type:'string',value:'deviceName',text:'设备名称',dictCode:''})
        fieldList.push({type:'string',value:'deviceType',text:'设备类型',dictCode:''})
        fieldList.push({type:'datetime',value:'timeBegin',text:'设备开始运行时间'})
        fieldList.push({type:'datetime',value:'timeEnd',text:'设备结束运行时间'})
        fieldList.push({type:'string',value:'timeRun',text:'设备本次运行时间',dictCode:''})
        fieldList.push({type:'int',value:'timeDay',text:'天',dictCode:''})
        fieldList.push({type:'int',value:'timeHour',text:'小时',dictCode:''})
        fieldList.push({type:'int',value:'timeMinute',text:'分钟',dictCode:''})
        fieldList.push({type:'int',value:'timeSecond',text:'秒',dictCode:''})
        fieldList.push({type:'datetime',value:'createTime',text:'创建日期'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>