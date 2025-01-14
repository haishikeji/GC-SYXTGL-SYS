<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="委托单位名称">
              <j-search-select-tag
                placeholder="请选择委托单位名称"
                v-model="queryParam.weituoClient"
                :dictOptions="weituoClientList"
                @change="handlewcChange"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="委托编号">
              <j-search-select-tag
                ref="weituoNo"
                placeholder="请选择委托编号"
                v-model="queryParam.weituoNo"
                :dictOptions="weituoNoList"
              />
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
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
      <a-button @click="handleAdd" type="primary" icon="plus">生成报告</a-button>

      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete" />
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          批量操作
          <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{ x: true }"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        class="j-table-force-nowrap"
        @change="handleTableChange"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text, record">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无图片</span>
          <img
            v-else
            :src="getImgView(text)"
            :preview="record.id"
            height="25px"
            alt=""
            style="max-width: 80px; font-size: 12px; font-style: italic"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <!-- <a @click="wordedit(record)">编辑</a>

          <a-divider type="vertical" /> -->
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>

    <itdm-baogao-modal ref="modalForm" @ok="modalFormOk"></itdm-baogao-modal>
    <word-edit ref="wordedit" @ok="modalFormOk"></word-edit>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import ItdmBaogaoModal from './modules/ItdmBaogaoModal'
import WordEdit from './modules/wordEdit'

import { filterMultiDictText } from '@comp/dict/JDictSelectUtil'
import { getAction } from '@api/manage'
// import WordEdit from '@views/module-iTDM/baogao/modules/wordEdit'

export default {
  name: 'ItdmBaogaoList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    WordEdit,
    ItdmBaogaoModal,
  },
  data() {
    return {
      weituoClientList: [],
      weituoNoList: [],
      description: '报告生成管理页面',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '委托编号',
          align: 'center',
          dataIndex: 'weituoNo',
        },

        {
          title: '委托单位名称',
          align: 'center',
          dataIndex: 'weituoClient',
        },

        {
          title: '委托联系人',
          align: 'center',
          dataIndex: 'weituoLxr',
        },

        {
          title: '报告模板',
          align: 'center',
          dataIndex: 'baogaoType_dictText',
        },
        {
          title: '报告文件地址',
          align: 'center',
          dataIndex: 'filePath',
          scopedSlots: { customRender: 'fileSlot' },
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/baogao/itdmBaogao/list',
        delete: '/baogao/itdmBaogao/delete',
        deleteBatch: '/baogao/itdmBaogao/deleteBatch',
        exportXlsUrl: '/baogao/itdmBaogao/exportXls',
        importExcelUrl: 'baogao/itdmBaogao/importExcel',
      },
      dictOptions: {},
      superFieldList: [],
    }
  },
  created() {
    this.getSuperFieldList()
    this.init()
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    handlewcChange(data) {
      this.$refs.weituoNo.handleChange('')
      var weituoClient = { weituoClient: data }
      getAction('/weituo/itdmWeituoInfo/weituoNoList', weituoClient).then((res) => {
        if (res.success) {
          console.log('xxxxxxxxxxxxxxxxxxx')
          console.log(res.result)
          this.weituoNoList = res.result.map((i) => {
            return { value: i.weituoNo, text: i.weituoNo }
          })
          console.log(this.weituoNoList)
        }
      })
    },

    init() {
      getAction('/weituo/itdmWeituoInfo/distinctClientList', {}).then((res) => {
        if (res.success) {
          console.log(res.result)
          this.weituoClientList = res.result.map((i) => {
            return { value: i, text: i }
          })
          console.log(this.weituoClientList)
        }
      })
    },
    wordedit(record) {
      this.$refs.wordedit.edit(record)
      this.$refs.wordedit.visible = true
    },

    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'weituoNo', text: '委托编号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'weituoClient', text: '委托单位名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'sampleNames', text: '委托样品列表', dictCode: '' })
      fieldList.push({ type: 'string', value: 'weituoLxr', text: '委托联系人', dictCode: '' })
      fieldList.push({ type: 'string', value: 'testItemsNames', text: '委托样品检测列表', dictCode: '' })
      fieldList.push({ type: 'string', value: 'baogaoType_dictText', text: '报告模板', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'filePath', text: '报告文件地址', dictCode: '' })
      this.superFieldList = fieldList
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>
