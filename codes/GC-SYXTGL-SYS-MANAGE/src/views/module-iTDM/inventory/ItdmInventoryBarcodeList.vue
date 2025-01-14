<template>
  <a-modal
    :width="width"
    ref="modal"
    :visible="visible"
    @ok="ok"
    @cancel="handleCancel"
  >

    <a-card :bordered="false">
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
          </a-row>
        </a-form>
      </div>
      <!-- 查询区域-END -->

      <!-- 操作按钮区域 -->
      <div class="table-operator">
        <a-button @click="createBarCode" type="primary" icon="plus">生成</a-button>

      </div>

      <!-- table区域-begin -->
      <div>


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


          <template slot="changeSwitch" slot-scope="text,record">


            <a-switch :checked="record.disableStatus==='0'" @change="onChange(record)" />


          </template>

          <template slot="htmlSlot" slot-scope="text">
            <div v-html="text"></div>
          </template>
          <template slot="imgSlot" slot-scope="text,record">
            <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
            <img v-else :src="getImgView(text)" :preview="record.id" height="25px" alt=""
                 style="max-width:80px;font-size: 12px;font-style: italic;" />
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
            <a-popconfirm title="确定删除吗?" @confirm="() => deleteBarcode(record.id)">
                  <a>删除</a>
                </a-popconfirm>


        </span>

        </a-table>
      </div>

    </a-card>
  </a-modal>
</template>


<script>

import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { createBarcode, deleteBarcode, updateBarcode } from '@api/api'

export default {
  name: 'ItdmInventoryBarcodeList',
  mixins: [JeecgListMixin, mixinDevice],

  data() {
    return {
      id: null,
      visible: false,
      width: 1200,
      description: '库存条码管理页面',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function(t, r, index) {
            return parseInt(index) + 1
          }
        },
        {
          title: '条码',
          align: 'center',
          dataIndex: 'barCode'
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'disableStatus',
          scopedSlots: { customRender: 'changeSwitch' }

        },
        {
          title: '数量',
          align: 'center',
          dataIndex: 'num'
        },
        {
          title: '当前使用状态',
          align: 'center',
          dataIndex: 'useStatus_dictText'
        },
        {
          title:'图片地址',
          align:"center",
          dataIndex: 'imgPath',
          scopedSlots: {customRender: 'imgSlot'}
        },
        {
          title:'图片地址',
          align:"center",
          dataIndex: 'imgPath',
          scopedSlots: {customRender: 'fileSlot'}
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/inventory/itdmInventoryBarcode/list',
        delete: '/inventory/itdmInventoryBarcode/delete',
        deleteBatch: '/inventory/itdmInventoryBarcode/deleteBatch',
        exportXlsUrl: '/inventory/itdmInventoryBarcode/exportXls',
        importExcelUrl: 'inventory/itdmInventoryBarcode/importExcel'

      },
      dictOptions: {}

    }
  },
  created() {

  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    handleCancel() {
      this.visible = false

    },
    ok() {
      this.visible = false

    },
    createBarCode() {
      createBarcode({ inventoryId: this.id }).then(response => {
        console.log(response)
        if (!response.success) {
          this.$message.warning(response.message)
        }
        this.init(this.id)
      })

    },
    deleteBarcode(id) {
      deleteBarcode({ id: id }).then(response => {
        if (!response.success) {
          this.$message.warning(response.message)
        }
        this.init(this.id)

      })
    },
    onChange(r) {
      r.disableStatus = r.disableStatus === '1' ? '0' : '1'
      updateBarcode({ id: r.id, disableStatus: r.disableStatus }).then(response => {
        if (!response.success) {
          this.$message.warning(response.message)
        }
        this.init(this.id)

      })

    },

    init(id) {
      console.log('xxxxxxxxxxxxxxxxx')
      this.visible = true
      this.queryParam.inventoryId = id
      this.id = id
      this.searchQuery()

    },
    initDictConfig() {
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>