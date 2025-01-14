package org.jeecg.modules.itdmGongDan.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
//@TableName("itdm_gongdan_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="itdm_gongdan_detail对象拓展", description="工单detail拓展")
public class ItdmGongdanDetailListVO implements Serializable {

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**工单id*/
    @Excel(name = "工单id", width = 15)
    @ApiModelProperty(value = "工单id")
    private java.lang.String woId;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**实绩设备id*/
    @Excel(name = "实绩设备id", width = 15)
    @ApiModelProperty(value = "实绩设备id")
    @Dict(dictTable = "itdm_device", dicText = "device_name", dicCode = "id")
    private java.lang.String shijiShebeiId;
    /**实绩试验类型*/
    @Excel(name = "实绩试验类型", width = 15, dicCode = "shiyan_type")
    @Dict(dicCode = "shiiyan_type")
    @ApiModelProperty(value = "实绩试验类型")
    private java.lang.String shijiShiiyanType;
    /**任务日期*/
    @Excel(name = "任务日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "任务日期")
    private java.util.Date workDate;
    /**试验开始时间*/
    @Excel(name = "试验开始时间", width = 15)
    @ApiModelProperty(value = "试验开始时间")
    private java.lang.String startTime;
    /**试验结束时间*/
    @Excel(name = "试验结束时间", width = 15)
    @ApiModelProperty(value = "试验结束时间")
    private java.lang.String endTime;
    /**实绩任务日期*/
    @Excel(name = "实绩任务日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "实绩任务日期")
    private java.util.Date shijiWorkDate;
    /**实绩试验开始时间*/
    @Excel(name = "实绩试验开始时间", width = 15)
    @ApiModelProperty(value = "实绩试验开始时间")
    private java.lang.String shijiStartTime;
    /**实绩试验结束时间*/
    @Excel(name = "实绩试验结束时间", width = 15)
    @ApiModelProperty(value = "实绩试验结束时间")
    private java.lang.String shijiEndTime;
//    /**备注*/
//    @Excel(name = "备注", width = 15)
//    @ApiModelProperty(value = "备注")
//    private java.lang.String remark;
//    /**创建人*/
//    @ApiModelProperty(value = "创建人")
//    private java.lang.String createBy;
//    /**创建日期*/
//    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "创建日期")
//    private java.util.Date createTime;
//    /**更新人*/
//    @ApiModelProperty(value = "更新人")
//    private java.lang.String updateBy;
//    /**更新日期*/
//    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "更新日期")
//    private java.util.Date updateTime;
//    /**所属部门*/
//    @ApiModelProperty(value = "所属部门")
//    private java.lang.String sysOrgCode;/**传感器*/
//    @Excel(name = "传感器id", width = 20)
    @ApiModelProperty(value = "传感器id")
    @Dict(dictTable = "itdm_chuanganqi", dicText = "xuliehao", dicCode = "id")
    private java.lang.String chuanganqi;


    //来自于工单master（itdm_gongdan_master），通过工单ID（woId）查询
    /**试验计划id*/
    @Excel(name = "试验计划id", width = 15)
    @ApiModelProperty(value = "试验计划id")
    @Dict(dictTable = "itdm_test_plan", dicText = "report_no", dicCode = "id")
    private java.lang.String jihuaShiyanId;
    /**检测项目id*/
    @Excel(name = "检测项目id", width = 15)
    @ApiModelProperty(value = "检测项目id")
    @Dict(dictTable = "itdm_weituo_yangpin_extend", dicText = "test_items", dicCode = "id")
    private java.lang.String testItemsId;
    /**委托样品id*/
    @Excel(name = "委托样品id", width = 15)
    @ApiModelProperty(value = "委托样品id")
    @Dict(dictTable = "itdm_weituo_yangpin", dicText = "sample_name", dicCode = "id")
    private java.lang.String weituoYangpinId;
    /**设备id*/
    @Excel(name = "设备id", width = 15)
    @ApiModelProperty(value = "设备id")
    @Dict(dictTable = "itdm_device", dicText = "device_name", dicCode = "id")
    private java.lang.String shebeiId;

    /**试验类型*/
    @Excel(name = "试验类型", width = 15)
    @ApiModelProperty(value = "试验类型")
    @Dict(dicCode = "shiiyan_type")
    private java.lang.String shiiyanType;
    /**试验员*/
    @Excel(name = "试验员", width = 15)
    @ApiModelProperty(value = "试验员")
    @Dict(dictTable = "itdm_person_manage", dicText = "name", dicCode = "id")
    private java.lang.String worker;
    /**委托样品数量*/
    @Excel(name = "委托样品数量", width = 15)
    @ApiModelProperty(value = "委托样品数量")
    private java.lang.Integer yangpinCount;

    //来自于试验计划表（itdm_test_plan），通过试验计划id（jihuaShiyanId）查询
//    /**报告编号*/
//    @Excel(name = "报告编号", width = 15)
//    @ApiModelProperty(value = "报告编号")
//    private java.lang.String reportNo;
    /**委托id*/
    @Excel(name = "委托id", width = 15)
    @ApiModelProperty(value = "委托id")
    @Dict(dictTable = "itdm_weituo_info", dicText = "weituo_no", dicCode = "id")
    private String weituoId;

    //来自于委托表（itdm_weituo_info），通过委托id（weituoId）查询
    @Excel(name = "委托单位", width = 15)
    @ApiModelProperty(value = "委托单位")
    private String weituoClient;
    /**委托编号*/
    @Excel(name = "委托编号", width = 15)
    @ApiModelProperty(value = "委托编号")
    private String weituoNo;

    /**规格型号*/
    @Excel(name = "规格型号", width = 15)
    @ApiModelProperty(value = "规格型号")
    private java.lang.String sampleModelSpecification;
//
//    //来自于样品表（itdm_weituo_yangpin），通过样品id（weituoYangpinId）查询
//    /**样品名称*/
//    @Excel(name = "样品名称", width = 15)
//    @ApiModelProperty(value = "样品名称")
//    private java.lang.String sampleName;
//
//    //来自于设备表（itdm_device），通过设备id（shebeiId）查询
//    /**设备名称*/
//    @Excel(name = "设备名称", width = 15)
//    @ApiModelProperty(value = "设备名称")
//    private java.lang.String deviceName;

//    //来自于设备表（itdm_device），通过实绩设备id（shijiShebeiId）查询
//    /**实绩设备名称*/
//    @Excel(name = "实绩设备名称", width = 15)
//    @ApiModelProperty(value = "实绩设备名称")
//    private java.lang.String shijiDeviceName;
//
//    //来自于检测项目表（itdm_weituo_yangpin_extend），通过检测项目id（testItemsId）查询
//    /**检测项目*/
//    @Excel(name = "检测项目", width = 15)
//    @ApiModelProperty(value = "检测项目")
//    private String testItems;



}
