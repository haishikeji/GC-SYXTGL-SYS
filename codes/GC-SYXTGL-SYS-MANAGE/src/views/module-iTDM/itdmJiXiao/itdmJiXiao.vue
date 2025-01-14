<template>
  <div >
    <vxe-toolbar>
      <template #buttons>
        <vxe-button icon="vxe-icon-square-plus" @click="insertEvent()">新增</vxe-button>
        <vxe-button @click="$refs.xTable.removeCheckboxRow()">删除选中</vxe-button>
      </template>
    </vxe-toolbar>

    <vxe-table
      border
      show-overflow
      ref="xTable"
      height="500"
      :data="tableData"
      :mouse-config="{selected: true}"
      :checkbox-config="{range: true}"
      :menu-config="tableMenu"
      :keyboard-config="{isArrow: true, isDel: true, isEnter: true, isTab: true, isEdit: true, isChecked: true}"
      :edit-config="{trigger: 'dblclick', mode: 'cell'}">
      <vxe-column type="seq" width="60"></vxe-column>
      <vxe-column type="checkbox" width="60"></vxe-column>
      <vxe-column field="name" title="Name" :edit-render="{autofocus: '.myinput'}">
        <template #edit="{ row }">
          <input v-model="row.name" type="text" class="myinput" />
        </template>
      </vxe-column>
      <vxe-column field="role" title="Role" :edit-render="{autofocus: '.vxe-input--inner'}">
        <template #edit="{ row }">
          <vxe-input v-model="row.role" type="text"></vxe-input>
        </template>
      </vxe-column>
      <vxe-column field="nickname" title="Nickname" :edit-render="{autofocus: '.vxe-input--inner'}">
        <template #edit="{ row }">
          <vxe-input v-model="row.nickname" type="text"></vxe-input>
        </template>
      </vxe-column>
      <vxe-column field="address" title="Address" :edit-render="{autofocus: '.vxe-input--inner'}">
        <template #edit="{ row }">
          <vxe-input v-model="row.address" type="text"></vxe-input>
        </template>
      </vxe-column>
    </vxe-table>
  </div>
</template>

<script>
import VXETable from 'vxe-table'

  export default {
        data () {
          return {
            tableData: [
              { id: 10001, name: 'Test1', role: 'Develop', sex: 'Man', age: 28, address: 'test abc' },
              { id: 10002, name: 'Test2', role: 'Test', sex: 'Women', age: 22, address: 'Guangzhou' },
              { id: 10003, name: 'Test3', role: 'PM', sex: 'Man', age: 32, address: 'Shanghai' },
              { id: 10004, name: 'Test4', role: 'Designer', sex: 'Women', age: 23, address: 'test abc' },
              { id: 10005, name: 'Test5', role: 'Develop', sex: 'Women', age: 30, address: 'Shanghai' },
              { id: 10006, name: 'Test6', role: 'Designer', sex: 'Women', age: 21, address: 'test abc' },
              { id: 10007, name: 'Test7', role: 'Test', sex: 'Man', age: 29, address: 'test abc' },
              { id: 10008, name: 'Test8', role: 'Develop', sex: 'Man', age: 35, address: 'test abc' }
            ],
            tableMenu: {
              body: {
                options: [
                  [
                    { code: 'copy', name: '复制', prefixIcon: 'vxe-icon-copy', disabled: false },
                    { code: 'remove', name: '删除', disabled: false },
                    { code: 'save', name: '保存', prefixIcon: 'vxe-icon-save', disabled: false }
                  ]
                ]
              }
            },
          }
        },
        methods: {
          async insertEvent () {
            const $table = this.$refs.xTable
            const record = {
              sex: '1'
            }
            const { row: newRow } = await $table.insertAt(record)
            await $table.setActiveCell(newRow, 'name')
          },
          getInsertEvent () {
            const $table = this.$refs.xTable
            const insertRecords = $table.getInsertRecords()
            VXETable.modal.alert(insertRecords.length)
          },
          getSelectionEvent () {
            const $table = this.$refs.xTable
            const selectRecords = $table.getCheckboxRecords()
            VXETable.modal.alert(selectRecords.length)
          }
        }
      }

</script>