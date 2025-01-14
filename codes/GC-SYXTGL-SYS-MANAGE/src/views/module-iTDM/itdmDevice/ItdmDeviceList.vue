<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="设备名称">
              <a-input placeholder="请输入设备名称" v-model="queryParam.deviceName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="设备类型">
              <a-input placeholder="请输入设备类型" v-model="queryParam.deviceType"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="设备状态">
                <j-dict-select-tag placeholder="请选择设备状态" v-model="queryParam.deviceStatus" dictCode="device_status"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="是否已维护">
                <j-dict-select-tag placeholder="请选择是否已维护" v-model="queryParam.isRepair" dictCode="is_repair"/>
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
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('设备表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-button type="primary" icon="download" @click="handleDownload()">模板下载</a-button>
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


        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
<!--                <a @click="handleDetail(record)">详情</a>-->
                <a-popconfirm :title="record.deviceStatus == 3 ? '确定取消设备故障吗': '确定设备出现故障吗'" @confirm="() => handleGuzhang(record)">
                  <a>{{record.deviceStatus == 3 ? '取消故障状态': '设为故障状态'}}</a>
                </a-popconfirm>
              </a-menu-item>

              <a-menu-item v-if="record.izDelete == 1">
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id) ">
                  <a>{{record.izDelete == 0 ? '不可删除': '删除'}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
<!--        <span slot="action2" slot-scope="text, item">-->
<!--          <a-popconfirm :title="item.deviceStatus == 3 ? '确定取消设备故障吗': '确定设备出现故障吗'" @confirm="() => handleGuzhang(item)">-->
<!--            <a>{{item.deviceStatus == 3 ? '非故障': '故障'}}</a>-->
<!--          </a-popconfirm>-->
<!--        </span>-->

      </a-table>
    </div>

    <itdm-device-modal ref="modalForm" @ok="modalFormOk"></itdm-device-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ItdmDeviceModal from './modules/ItdmDeviceModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import {checkGuzhang, getDz} from '@/api/api'
  import { getFileAccessHttpUrl,downloadFile } from '@/api/manage'

  export default {
    name: 'ItdmDeviceList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      ItdmDeviceModal
    },
    data () {
      return {
        /* 排序参数 */
        isorter: {
          column: 'id', // 排序字段
          order: 'asc' // 正序or倒叙
        },
        description: '设备表管理页面',
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
            title:'设备名称',
            align:"center",
            dataIndex: 'deviceName'
          },
          {
            title:'重要性标准',
            align:"center",
            dataIndex: 'zyxbz_dictText'
          },
          {
            title:'试验单价',
            align:"center",
            dataIndex: 'danjia'
          },
          {
            title:'设备编号',
            align:"center",
            dataIndex: 'deviceNo'
          },
          {
            title:'设备规格',
            align:"center",
            dataIndex: 'deviceSpecification'
          },
          {
            title:'设备型号',
            align:"center",
            dataIndex: 'deviceModel'
          },
          {
            title:'设备状态',
            align:"center",
            dataIndex: 'deviceStatus_dictText'
          },
          {
            title:'设备类型',
            align:"center",
            dataIndex: 'deviceType'
          },
          {
            title:'计量有效期',
            align:"center",
            dataIndex: 'deviceLife'
          },
          {
            title:'维护时间',
            align:"center",
            dataIndex: 'repairTime'
          },
          {
            title:'维护人',
            align:"center",
            dataIndex: 'repairer'
          },
          {
            title:'维护记录',
            align:"center",
            dataIndex: 'record'
          },
          {
            title:'本次运行开始时间',
            align:"center",
            dataIndex: 'runStartTime'
          },
          {
            title:'设备运行标识',
            align:"center",
            dataIndex: 'biaoshi_dictText'
          },
          // {
          //   title:'是否已维护',
          //   align:"center",
          //   dataIndex: 'isRepair_dictText'
          // },
          {
            title:'更新人',
            align:"center",
            dataIndex: 'updateBy'
          },
          {
            title:'更新日期',
            align:"center",
            dataIndex: 'updateTime'
          },
          // {
          //   title: '故障判断',
          //   dataIndex: 'action2',
          //   align:"center",
          //   fixed:"right",
          //   scopedSlots: { customRender: 'action2' }
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:70,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/itdmDevice/itdmDevice/list",
          delete: "/itdmDevice/itdmDevice/delete",
          deleteBatch: "/itdmDevice/itdmDevice/deleteBatch",
          exportXlsUrl: "/itdmDevice/itdmDevice/exportXls",
          importExcelUrl: "itdmDevice/itdmDevice/importExcel",

        },
        dictOptions:{},
        superFieldList:[],
        deviceForm:{},
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
        fieldList.push({type:'string',value:'updateBy',text:'更新人',dictCode:''})
        fieldList.push({type:'datetime',value:'updateTime',text:'更新日期'})
        fieldList.push({type:'string',value:'deviceName',text:'设备名称',dictCode:''})
        fieldList.push({type:'string',value:'deviceType',text:'设备类型',dictCode:''})
        fieldList.push({type:'string',value:'deviceNo',text:'设备编号',dictCode:''})
        fieldList.push({type:'int',value:'deviceStatus',text:'设备状态',dictCode:'device_status'})
        fieldList.push({type:'datetime',value:'repairTime',text:'维护时间'})
        fieldList.push({type:'string',value:'repairer',text:'维护人',dictCode:''})
        fieldList.push({type:'string',value:'record',text:'维护记录',dictCode:''})
        fieldList.push({type:'string',value:'deviceSpecification',text:'设备规格',dictCode:''})
        fieldList.push({type:'string',value:'deviceModel',text:'设备型号',dictCode:''})
        fieldList.push({type:'string',value:'deviceLife',text:'计量有效期',dictCode:''})
        fieldList.push({type:'datetime',value:'runStartTime',text:'本次运行开始时间'})
        fieldList.push({type:'int',value:'biaoshi',text:'设备运行标识',dictCode:'biaoshi'})
        fieldList.push({type:'int',value:'isRepair',text:'是否已维护',dictCode:'is_repair'})
        fieldList.push({type:'int',value:'equipmenttreeid',text:'设备树id',dictCode:''})
        fieldList.push({type:'string',value:'address',text:'安装地点',dictCode:''})
        fieldList.push({type:'int',value:'specid',text:'区域',dictCode:''})
        fieldList.push({type:'string',value:'remark',text:'备注',dictCode:''})
        this.superFieldList = fieldList
      },
      handleGuzhang(item){
        console.log("item",item,item.deviceStatus)
        const _this = this
        _this.deviceForm = JSON.parse(JSON.stringify(item))
        console.log(_this.deviceForm)
        if(item.deviceStatus == 3){
          _this.deviceForm.deviceStatus = 4
          // console.log(_this.deviceForm,item)
          checkGuzhang(_this.deviceForm).then((res) => {
          console.log(res)
          this.loadData();
        })
        }else{
          _this.deviceForm.deviceStatus = 3
          checkGuzhang(_this.deviceForm).then((res) => {
          console.log(res)
          this.loadData();
        })
        }
      },
      // 模板下载
      handleDownload() {
        getDz().then((res) => {
          console.log(res)
          let url = getFileAccessHttpUrl(res);
          downloadFile(url,'设备表.xls')
        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>