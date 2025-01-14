{{ src/components/Gantt.vue }}
<template>
    <div ref="gantt"></div>
</template>
<script>
import {gantt} from 'dhtmlx-gantt';
import { notification } from 'ant-design-vue';
import {getDeviceList, getJhList, getManagerList, getCgqList, saveProject} from '@/api/api'
import 'chosen-js/chosen.jquery'
import 'chosen-js/chosen.min.css'
export default {
  name: 'gantt',
  // props: {
  //   tasks: {
  //     type: Object,
  //     default () {
  //       return {data: [], links: [], collections:{}}
  //     }
  //   }
  // },
  data () {
    return {
      tasks: {
        data: [
        ],
        collections: {
          staff:[],
          manager:[],
          chuanganqi:[],
        },
        queryParam:{
          weituoNo: undefined
        }
      },
    }
  },
  created() {
    // 清空数据
    gantt.clearAll();
    this.getDevicelist();
    this.getCgqlist();
    this.getManagerList();
    console.log(1112,this.tasks)
  },
  beforeDestroy() {
    gantt.clearAll();
  },
  mounted() {
      // 启用动态加载
      gantt.config.branch_loading = true
      //日期格式化
      gantt.config.xml_date = "%Y-%m-%d";
      gantt.config.order_branch = true;
      gantt.config.order_branch_free = true;
      //左侧是否自适应
      gantt.config.autofit = true;
      gantt.config.drag_links = false;//连线
      gantt.config.readonly = false;  //只读
      gantt.config.date_scale = "%m月%d日"; //右侧显示列名
      gantt.config.layout = {//拖拽布局
        css: "gantt_container",
        rows: [
          {
            cols: [
              { view: "grid", id: "grid", scrollX: "scrollHor", scrollY: "scrollVer" },
              { resizer: true, width: 1 },
              { view: "timeline", id: "timeline", scrollX: "scrollHor", scrollY: "scrollVer" },
              { view: "scrollbar", scroll: "y", id: "scrollVer" }
            ]
          },
          { view: "scrollbar", scroll: "x", id: "scrollHor", height: 20 }
        ],
        // rows: [
        //   {
        //     cols: [
        //       {
        //         width:400,
        //         min_width: 300,
        //         rows:[
        //           {view: "grid", scrollX: "gridScroll", scrollable: true, scrollY: "scrollVer"},
        //           {view: "scrollbar", id: "gridScroll", group:"horizontal"}            ]
        //       },
        //       {resizer: true, width: 1},
        //       {
        //         rows:[
        //           {view: "timeline", scrollX: "scrollHor", scrollY: "scrollVer"},
        //           {view: "scrollbar", id: "scrollHor", group:"horizontal"}            ]
        //       },
        //       {view: "scrollbar", id: "scrollVer"}
        //     ]
        //   },
        // ],
        // cols: [
        //   {
        //     width:400,
        //     min_width: 300,
        //     rows:[
        //       {view: "grid", scrollX: "gridScroll", scrollable: true, scrollY: "scrollVer"},
        //       {view: "scrollbar", id: "gridScroll", group:"horizontal"}            ]
        //   },
        //   {resizer: true, width: 1},
        //   {
        //     rows:[
        //       {view: "timeline", scrollX: "scrollHor", scrollY: "scrollVer"},
        //       {view: "scrollbar", id: "scrollHor", group:"horizontal"}            ]
        //   },
        //   {view: "scrollbar", id: "scrollVer"}
        // ]
      };
      // 重置布局
      gantt.resetLayout();
      gantt.config.start_on_monday = true;
      // 注意取消工作日！！！！（gantt.config.work_time = false;)
      gantt.config.work_time = false;
      gantt.config.fit_tasks = true;   //自动调整图表坐标轴区间用于适配task的长度
      // 甘特图汉化
      gantt.locale = {
        date: {
          month_full: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
          month_short: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
          day_full: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
          day_short: ["日", "一", "二", "三", "四", "五", "六"]
        },
        labels: {
          dhx_cal_today_button: "今天",
          day_tab: "日",
          week_tab: "周",
          month_tab: "月",
          new_event: "新建日程",
          icon_save: "保存",
          icon_cancel: "关闭",
          icon_details: "详细",
          icon_edit: "编辑",
          icon_delete: "删除",
          confirm_closing: "请确认是否撤销修改!", //Your changes will be lost, are your sure?
          confirm_deleting: "是否删除计划?",
          section_description: "描述:",
          section_time: "时间范围:",
          section_type: "类型:",
          section_text: "计划名称:",
          section_test: "测试:",
          section_projectClass: "项目类型:",
          taskProjectType_0: "项目任务",
          taskProjectType_1: "普通任务",
          section_head: "项目经理:",
          section_device: "试验设备:",
          section_sensor: "传感器:",
          section_reportNo: "报告编号:",
          section_priority: '优先级:',
          status: '任务状态',
          status_0: "未确认",
          status_1: "已确认",
          status_2: "完成",
          status_3: "已延期",
          status_4: "搁置中",
          section_template: 'Details',
          /* grid columns */
          column_text: "计划名称",
          column_sampleName: "样品名称",
          column_weituoNo: "委托编号",
          column_testItems: "检测项目",
          column_start_date: "开始时间",
          column_duration: "持续时间",
          column_lastTime: "持续时间",
          column_add: "",
          column_priority: "难度",
          /* link confirmation */
          link: "关联",
          confirm_link_deleting: "将被删除",
          link_start: " (开始)",
          link_end: " (结束)",
          type_task: "任务",
          type_project: "项目",
          type_milestone: "里程碑",

          minutes: "分钟",
          hours: "小时",
          days: "天",
          weeks: "周",
          months: "月",
          years: "年"
        }
      };

      //定义各种编辑器及类型(可直接双击左侧进行编辑，不需要弹窗)
      // var textEditor = {type: "text", map_to: "text"};
      // var startDateEditor = {type: "date", map_to: "start_date"};
      // var endDateEditor = {type: "date", map_to: "end_date"};
      //设置甘特图中各个列相关配置
      // gantt.config.columns = [
      //   {
      //     name: 'text',
      //     label: '项目名称',
      //     editor: textEditor,
      //     resize: true,
      //     tree: true,
      //     width: '100',
      //   },
      //   {
      //     name: 'start_date',
      //     label: '项目开始时间',
      //     align: "center",
      //     editor: startDateEditor,
      //     resize:true,
      //     tree: false,
      //     width: '130',
      //   },
      //   {
      //     name: 'end_date',
      //     label: '项目结束时间',
      //     width: '130',
      //     align: 'center',
      //     editor: endDateEditor,
      //     resize: true,
      //   },
      // ];
      // 左侧列表内容

      gantt.config.columns = [
        // { name: "text", min_width:100,max_width:200, label:"任务", align: "left",resize: true, tree: true },
        { name: "weituoNo", label: "委托编号", width: 140, resize: true, align: "left",resize: true, tree: true },
        { name: "testItems", label: "检测项目", width: 80, resize: true, align: "center" },
        { name: "sampleName", label: "样品名称", width: 90, resize: true, align: "center" },
        { name: "start_date", label: "开始时间", width: 90, resize: true, align: "center" },
        { name: "end_date", label: "结束时间", width: 90, resize: true, align: "center" },
        { name: "statusC", label: "状态", width: 60, resize: true, align: "center" },
        // { name: "izShiji", label: "类型", width: 60, resize: true, align: "center" },
        // { name: "status", label: "状态", width: 130, resize: true, align: "center",
        //   // options: [
        //   //   { key: "0", label: gantt.locale.labels.status_0 },
        //   //   { key: "1", label: gantt.locale.labels.status_1 },
        //   //   { key: "2", label: gantt.locale.labels.status_2 },
        //   // ],
        //   // template:function(obj){
        //   //   console.log(obj)
        //   //   console.log(obj.status)
        //   //   let re = '';
        //   //   switch (obj.status) {
        //   //     case "0":
        //   //       re = `<b>未开始</b>`
        //   //       break;
        //   //     case "1":
        //   //       re = `<b >进行中</b>`
        //   //       break;
        //   //     case "2":
        //   //       re = `<b>已完成</b>`
        //   //       break;
        //   //   }
        //   //   return re
        //   // }
        // },
        // { name: "add", label:"", width: 30 }
      ];
      // 改变任务颜色
      gantt.templates.task_class=function(start, end, task){
        // console.log("变颜色",start, end, task)
            switch (task.izShiji){
              case "实际":
                  return "high";
                  break;
              // case "2":
              //     return "medium";
              //     break;
              // case "3":
              //     return "low";
              //     break;
          }
      };
      // 更改左侧显示的结束日期时间
      gantt.templates.task_end_date = function(date){
        return gantt.templates.task_date(new Date(date.valueOf() - 1));
      };
      var gridDateToStr = gantt.date.date_to_str("%Y-%m-%d");
      gantt.templates.grid_date_format = function(date, column){
        if(column === "end_date"){
          return gridDateToStr(new Date(date.valueOf() - 1));
        }else{
          return gridDateToStr(date);
        }
      }
      // 右侧任务展示
      gantt.templates.task_text = function (start, end, task) {
        // console.log(task)
        // return "<b>设备：</b> " + task.shebeiId + ",<b> 项目经理:</b> " + task.pm;
        return task.pm + ", " + task.deviceName;
      };
      // 灯箱多选框
      gantt.form_blocks["multiselect"] = {
        render: function (sns) {
          var height = (sns.height || "23") + "px";
          var html = "<div class='gantt_cal_ltext gantt_cal_chosen gantt_cal_multiselect'"+
            "style='height:"+ height + ";'><select data-placeholder='...'"+
            "class='chosen-select' multiple>";
          if (sns.options) {
            for (var i = 0; i < sns.options.length; i++) {
              if(sns.unassigned_value !== undefined && sns.options[i].key==sns.unassigned_value){
                continue;
              }
              html+="<option value='" +sns.options[i].key+ "'>"+sns.options[i].label+"</option>";
            }}
          html += "</select></div>";
          return html;},

        set_value: function (node, value, ev, sns) {
          node.style.overflow = "visible";
          node.parentNode.style.overflow = "visible";
          node.style.display = "inline-block";
          var select = $(node.firstChild);

          if (value) {
            value = (value + "").split(",");
            select.val(value);
          }
          else {
            select.val([]);
          }

          select.chosen();
          if(sns.onchange){
            select.change(function(){
              sns.onchange.call(this);
            })
          }
          select.trigger('chosen:updated');
          select.trigger("change");},

        get_value: function (node, ev) {
          var value = $(node.firstChild).val();
          //value = value ? value.join(",") : null
          return value;},
        focus: function (node) {
          $(node.firstChild).focus();
        }};
        // 左侧列表任务垂直移动
      gantt.attachEvent("onBeforeTaskMove", function(id, parent, tindex){
          const task = gantt.getTask(id);
          // console.log("yidoong",task)
          if(task.izShiji == "实际"){
            return false;
          }else{
            return true;
          }
      });
      // 右侧网格任务水平移动+改变长度
      gantt.attachEvent("onBeforeTaskDrag", function(id, mode, e){
          // console.log("右侧",id, mode, e)
          const task = gantt.getTask(id);
          console.log("yidoong",task)
          if(task.izShiji == "实际"){
            return false;
          }else{
            return true;
          }
      });
      // 重置灯箱
      gantt.attachEvent("onBeforeLightbox", function(task_id) {
        gantt.resetLightbox();
        var task = gantt.getTask(task_id);
        console.log(task)
        if (task.izShiji == "实际") {
          return false;
        } else {
          gantt.config.lightbox.sections = restricted_lightbox;
          // 删除灯箱中的删除按钮
          gantt.config.buttons_right = [];
          // 保留灯箱的关闭和保存按钮
          gantt.config.buttons_left = ["gantt_save_btn", "gantt_cancel_btn"];
          return true;
        }
        // gantt.config.lightbox.sections = restricted_lightbox;
        // // 删除灯箱中的删除按钮
        // gantt.config.buttons_right = [];
        // // 保留灯箱的关闭和保存按钮
        // gantt.config.buttons_left = ["gantt_save_btn", "gantt_cancel_btn"];
        // return true;
      });
      //弹出层(灯箱)
      var restricted_lightbox = [
        // { name: "text", height: 30, map_to: "text", type: "textarea", focus: true, width: "*" },
        {
          name: "time", height: 40, map_to: "auto", type: "duration",
          time_format: ["%Y", "%m", "%d"],

        },
        {
          name: "reportNo", height: 30, map_to: "reportNo", type: "textarea",focus: true,
        },
        { name: "head", height: 30, map_to: "pm", type: "select", options:gantt.serverList("manager") },
        {
          name: "device", height: 30, map_to: "shebeiId", type: "select", options: gantt.serverList('staff',[]),
        },
        {
          name: "sensor", height: 30, map_to: "chuanganqi", type: "multiselect", options: gantt.serverList('chuanganqi',[]),
        },

      ];
      gantt.attachEvent("onLightboxSave", function(id, task, is_new){
        // gantt.init(this.$refs.gantt);
        // antt.parse(this.tasks);
        return true;
      })
      // 自定义弹框
      // let that = this
      // gantt.config.details_on_dblclick = false;
      // gantt.attachEvent("onTaskDblClick", function (id, e){
      //   console.log(111,id, e);
      //   that.id = id
      //   // gantt.getTask(id);
      //   // console.log(gantt.getTask(id));
      //   gantt.getTask(that.id);
      //   console.log("双击获取当前行信息",gantt.getTask(that.id));
      //   if (gantt.getTask(that.id).status == 0) {
      //     gantt.config.details_on_dblclick = true;
      //   } else {
      //     that.visible = true;
      //   }
      //     // that.visible = true;
      //     return true;
      // });



      gantt.init(this.$refs.gantt);
      // gantt.parse(this.$props.tasks);
      // gantt.parse(this.tasks);

  },
  methods: {
    // 获取设备下拉列表
    getDevicelist(){
      getDeviceList().then((res) => {
        this.tasks.collections.staff = res.map(response =>{
          return {
            value: response.id,
            label: response.deviceName
          }
        })
        //this.initData()
      })
    },
    // 获取项目经理下拉列表
    getManagerList(){
      var query ={
        position: "项目经理"
      }
      getManagerList(query).then((res) => {
        this.tasks.collections.manager = res.map(response =>{
          return {
            key: response.name,
            value: response.id,
            label: response.name
          }
        })
        //this.initData()
      })
    },
    // 获取传感器下拉列表
    getCgqlist(){
      getCgqList().then((res) => {
        console.log(111111,res)
        this.tasks.collections.chuanganqi = res.map(response =>{
          return {
            key: response.id,
            value: response.id,
            label: response.xuliehao
          }
        })
        //this.initData()
      })
    },
    // 通过委托编号查询检测项目
    getinit(queryParam){
      this.tasks.queryParam = queryParam
      console.log(23232,queryParam)
      if (queryParam.weituoClient != undefined && queryParam.weituoNo == undefined) {
        this.$message.error("请选择委托编号后再进行查询！")
      } else {
        getJhList(queryParam).then((res) => {
          console.log(17,res)
          this.tasks.data = []
          gantt.clearAll();
          if (res.result.length > 0 ) {
            this.tasks.data = res.result.map(res => {
              return {
                id: res.id,

                weituoId: res.weituoId,
                testItemsId: res.testItemsId,
                yangpinId:res.yangpinId,
                shebeiId:res.shebeiId,
                pm: res.pm,
                reportNo: res.reportNo,
                chuanganqi:res.chuanganqiArray,
                testPlanId:res.testPlanId,
                status:res.status,

                // text:res.reportNo,
                start_date: res.jihuaStartDate,
                // jihuaEndDate: res.jihuaEndDate,
                weituoNo: res.weituoNo,
                duration: res.lastTime,
                sampleName: res.sampleName,
                testItems: res.testItems,
                deviceName: res.deviceName,
                statusC: res.statusC,
                izShiji: res.izShiji,

                jhpqStatus: res.jhpqStatus
              }
            })

            // gantt.init(this.$refs.gantt);
            // // gantt.parse(this.$props.tasks);
            // gantt.parse(this.tasks);
          }
          // } else {
          //   notification.open({
          //     message: '注意',
          //     description: '该委托编号不存在',
          //   });
          // }
          gantt.init(this.$refs.gantt);
          // gantt.parse(this.$props.tasks);
          gantt.parse(this.tasks);
        })
      }
    },
    // 提交
    searchReset() {
      var json = gantt.serialize();
      console.log(json)
      var data = json.data.map( res => {
        return{
          weituoId: res.weituoId,
            testItemsId: res.testItemsId,
            yangpinId:res.yangpinId,
            shebeiId:res.shebeiId,
            pm: res.pm,
            jihuaStartDate: res.start_date,
            lastTime: res.duration,
            // jihuaEndDate: res.jihuaEndDate,
            reportNo: res.reportNo,
            chuanganqiArray:res.chuanganqi,
            testPlanId:res.testPlanId,
            status:res.status,
            testItems: res.testItems,
            izShiji: res.izShiji
        }
      })
      console.log(data.length)
      if (data.length > 0) {
        saveProject(data).then((res) => {
          console.log(res)
          notification.open({
            message: '注意',
            description: res.message,
          });
          this.getinit(this.tasks.queryParam)
        })
      }else {
        notification.open({
          message: '注意',
          description: '数据为空，提交失败',
        });
      }
    },
    // // 今日线
    // createTodayLine() {
    //       var dateToStr = gantt.date.date_to_str("%Y年%M%d日");
    //       var markerId = gantt.addMarker({
    //         id: 'markerLine',
    //         start_date: new Date(),
    //         css: "today",
    //         text: "今日",
    //         title: dateToStr(new Date())
    //       });
    //       gantt.updateMarker(markerId);
    //     },
    // //定位到今日线
    // changeToday() {
    //       this.$nextTick(() => {
    //         let ganTT = document.getElementsByClassName('gantt_marker today')
    //         gantt.scrollTo(ganTT[0].offsetLeft-300, null);
    //       })
    // },

  }
}
</script>

<style>
    @import "~dhtmlx-gantt/codebase/dhtmlxgantt.css";
    .gantt_cal_larea{
			overflow:visible;
		}
    .gantt_cal_chosen,
		.gantt_cal_chosen select{
			width: 400px;
      margin-bottom: 20px;
		}
    .status{
      margin: 0 auto;
      margin-top: 5px;
      height: 24px;
      width: 65px;
      font-size: 12px;
      line-height: 24px;
      font-weight: bold;
      color: #f7fbfe;
      border-radius: 20px;
    }
    .color_bg_1{
      background-color:#60a3bc ;
    }
    .color_bg_2{
      background-color:#079992 ;
    }
    .color_bg_3{
      background-color:#78e08f ;
    }
    .gantt_task_line{
        border-color: rgba(0, 0, 0, 0.25);
    }
    .gantt_task_line .gantt_task_progress {
        background-color: rgba(0, 0, 0, 0.25);
    }
    .gantt_task_line.high {
        background-color: #78e08f;
    }
    .gantt_task_line.high .gantt_task_content {
        color: #fff;
    }
</style>
