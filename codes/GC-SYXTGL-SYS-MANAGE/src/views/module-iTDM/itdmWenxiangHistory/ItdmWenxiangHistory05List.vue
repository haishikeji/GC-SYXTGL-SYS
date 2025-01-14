<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="创建时间">
              <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择开始时间" class="query-group-cust" v-model="queryParam.createTime_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束时间" class="query-group-cust" v-model="queryParam.createTime_end"></j-date>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <!--              <a @click="handleToggleSearch" style="margin-left: 8px">-->
              <!--                {{ toggleSearchStatus ? '收起' : '展开' }}-->
              <!--                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
              <!--              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!--      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
      <a-button type="primary" icon="download" @click="dc('1立方温箱历史数据')">导出</a-button>
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
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <!-- 通过判断alarm的值，动态渲染对应数据这一列单元格的样式 -->
        <div slot="temperature" slot-scope="text, record">
          <span v-if="record.temperatureAlarm ==1" class="y">{{text}}</span>
          <span v-else>{{text}}</span>
        </div>
        <div slot="humidity" slot-scope="text, record">
          <span :class="record.humidityAlarm == 1?'y':''">{{text}}</span>
        </div>
        <div slot="currentA" slot-scope="text, record">
          <span :class="record.currentAAlarm == 1?'y':''">{{text}}</span>
        </div>
        <div slot="currentB" slot-scope="text, record">
          <span :class="record.currentBAlarm == 1?'y':''">{{text}}</span>
        </div>
        <div slot="currentC" slot-scope="text, record">
          <span :class="record.currentCAlarm == 1?'y':''">{{text}}</span>
        </div>

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
                <a @click="handleDetail(record)">详情</a>
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

    <itdm-wenxiang-history05-modal ref="modalForm" @ok="modalFormOk"></itdm-wenxiang-history05-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ItdmWenxiangHistory05Modal from './modules/ItdmWenxiangHistory05Modal'
  import { downFile } from '@/api/manage'

  export default {
    name: 'ItdmWenxiangHistory05List',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      ItdmWenxiangHistory05Modal
    },
    data () {
      return {
        description: '0.5立方温箱历史数据管理页面',
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
            title:'设备编号',
            align:"center",
            dataIndex: 'deviceNo'
          },
          {
            title:'温度',
            align:"center",
            dataIndex: 'temperature',
            scopedSlots: { customRender: 'temperature' }
          },
          {
            title:'温度Max',
            align:"center",
            dataIndex: 'temperatureMax'
          },
          {
            title:'温度Min',
            align:"center",
            dataIndex: 'temperatureMin'
          },
          // {
          //   title:'温度报警（0正常1报警）',
          //   align:"center",
          //   dataIndex: 'temperatureAlarm_dictText'
          // },
          {
            title:'湿度',
            align:"center",
            dataIndex: 'humidity',
            scopedSlots: { customRender: 'humidity' }
          },
          {
            title:'湿度Max',
            align:"center",
            dataIndex: 'humidityMax'
          },
          {
            title:'湿度Min',
            align:"center",
            dataIndex: 'humidityMin'
          },
          // {
          //   title:'湿度报警（0正常1报警）',
          //   align:"center",
          //   dataIndex: 'humidityAlarm_dictText'
          // },
          {
            title:'A相电流',
            align:"center",
            dataIndex: 'currentA',
            scopedSlots: { customRender: 'currentA' }
          },
          {
            title:'A相电流Max',
            align:"center",
            dataIndex: 'currentAMax'
          },
          {
            title:'A相电流Min',
            align:"center",
            dataIndex: 'currentAMin'
          },
          // {
          //   title:'A相电流报警（0正常1报警）',
          //   align:"center",
          //   dataIndex: 'currentAAlarm_dictText'
          // },
          {
            title:'B相电流',
            align:"center",
            dataIndex: 'currentB',
            scopedSlots: { customRender: 'currentB' }
          },
          {
            title:'B相电流Max',
            align:"center",
            dataIndex: 'currentBMax'
          },
          {
            title:'B相电流Min',
            align:"center",
            dataIndex: 'currentBMin'
          },
          // {
          //   title:'B相电流报警（0正常1报警）',
          //   align:"center",
          //   dataIndex: 'currentBAlarm_dictText'
          // },
          {
            title:'C相电流',
            align:"center",
            dataIndex: 'currentC',
            scopedSlots: { customRender: 'currentC' }
          },
          {
            title:'C相电流Max',
            align:"center",
            dataIndex: 'currentCMax'
          },
          {
            title:'C相电流Min',
            align:"center",
            dataIndex: 'currentCMin'
          },
          // {
          //   title:'C相电流报警（0正常1报警）',
          //   align:"center",
          //   dataIndex: 'currentCAlarm_dictText'
          // },
          {
            title:'创建时间',
            align:"center",
            dataIndex: 'createTime'
          },
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
          list: "/itdmWenxiangHistory/itdmWenxiangHistory05/list",
          delete: "/itdmWenxiangHistory/itdmWenxiangHistory05/delete",
          deleteBatch: "/itdmWenxiangHistory/itdmWenxiangHistory05/deleteBatch",
          exportXlsUrl: "/itdmWenxiangHistory/itdmWenxiangHistory05/exportXls",
          importExcelUrl: "itdmWenxiangHistory/itdmWenxiangHistory05/importExcel",

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
      // 判断时间段是否在3个月内，在的话导出，超出的话提示
      dc(fileName){
        let param = this.getQueryParams();
        console.log(11111,param)
        //判断时间段是否在3个月内
        let flag = this.completeDate(param.createTime_begin,param.createTime_end,3)

        if(flag){
          this.handleExportXls(fileName)
        }else {
          this.$message.error("时间跨度不得超过3个月!")
        }
      },

      //导出xls
      handleExportXls(fileName){
        if(!fileName || typeof fileName != "string"){
          fileName = "导出文件"
        }
        let param = this.getQueryParams();
        if(this.selectedRowKeys && this.selectedRowKeys.length>0){
          param['selections'] = this.selectedRowKeys.join(",")
        }
        console.log("导出参数",param)
        downFile(this.url.exportXlsUrl,param).then((data)=>{
          if (!data) {
            this.$message.warning("文件下载失败")
            return
          }
          if (typeof window.navigator.msSaveBlob !== 'undefined') {
            window.navigator.msSaveBlob(new Blob([data],{type: 'application/vnd.ms-excel'}), fileName+'.xls')
          }else{
            let url = window.URL.createObjectURL(new Blob([data],{type: 'application/vnd.ms-excel'}))
            let link = document.createElement('a')
            link.style.display = 'none'
            link.href = url
            link.setAttribute('download', fileName+'.xls')
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link); //下载完成移除元素
            window.URL.revokeObjectURL(url); //释放掉blob对象
          }
        })
      },

      //判断时间段是否在3个月内，是的话返回true，否则返回false
      completeDate (time1, time2, m) {

        var date1 = new Date(time1);
        var date2 = new Date(time2);

        // 计算时间差
        var diffMonths = (date2.getFullYear() - date1.getFullYear()) * 12 + date2.getMonth() - date1.getMonth();

        // 判断时间差是否小于等于m个月
        return diffMonths <= m;

      //   const year1 = time1.getFullYear()
      //   const year2 = time2.getFullYear()
      //   const month1 = time1.getMonth() + 1
      //   const month2 = time2.getMonth() + 1
      //   const day1 = time1.getDate()
      //   const day2 = time2.getDate()
      //   if (year2 === year1) { // 判断两个日期 是否是同年
      //     if (month2 - month1 > m) { // 相差是否 在m个月中
      //       return false
      //     } else if (month2 - month1 === m) { // 如果正好为 m月 判断天数
      //       if (day2 > day1) {
      //         return false
      //       }
      //     }
      //   } else { // 不同年
      //     if (year2 - year1 > 1) {
      //       return false
      //     }
      //     else if (year2 - year1 === 1) {
      //       if (month2 > m || month1 + m - month2 < 12) {
      //         return false
      //       }
      //       else if (month1 + m - month2 === 12) { // 正好为m月 判断天数
      //         if (day2 > day1) {
      //           return false
      //         }
      //       }
      //     }
      //   }
      //   return true
      },

      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'deviceName',text:'设备名称',dictCode:''})
        fieldList.push({type:'string',value:'deviceNo',text:'设备编号',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'temperature',text:'温度',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'temperatureMax',text:'温度上限',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'temperatureMin',text:'温度下限',dictCode:''})
        fieldList.push({type:'int',value:'temperatureAlarm',text:'温度报警（0正常1报警）',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'humidity',text:'湿度',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'humidityMax',text:'湿度上限',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'humidityMin',text:'湿度下限',dictCode:''})
        fieldList.push({type:'int',value:'humidityAlarm',text:'湿度报警（0正常1报警）',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'currentA',text:'A相电流',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'currentAMax',text:'A相电流上限',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'currentAMin',text:'A相电流下限',dictCode:''})
        fieldList.push({type:'int',value:'currentAAlarm',text:'A相电流报警（0正常1报警）',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'currentB',text:'B相电流',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'currentBMax',text:'B相电流上限',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'currentBMin',text:'B相电流下限',dictCode:''})
        fieldList.push({type:'int',value:'currentBAlarm',text:'B相电流报警（0正常1报警）',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'currentC',text:'C相电流',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'currentCMax',text:'C相电流上限',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'currentCMin',text:'C相电流下限',dictCode:''})
        fieldList.push({type:'int',value:'currentCAlarm',text:'C相电流报警（0正常1报警）',dictCode:''})
        fieldList.push({type:'datetime',value:'createTime',text:'创建时间'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
  ::v-deep .ant-card-body{
    padding-top: 0px !important;
    padding-left: 10px !important;
    padding-right: 10px !important;
  }
  .y {
    color: red;
  }
</style>