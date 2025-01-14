package org.jeecg.modules.itdmPersonManage.entity;

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

/**
 * @Description: 人员管理
 * @@author 品讯科技
 * @Date:   2023-05-17
 * @Version: V1.0
 */
@Data
@TableName("itdm_person_manage")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="itdm_person_manage对象", description="人员管理")
public class ItdmPersonManage implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**人员姓名*/
	@Excel(name = "人员姓名", width = 15)
    @ApiModelProperty(value = "人员姓名")
    private java.lang.String name;
	/**人员性别*/
	@Excel(name = "人员性别", width = 15, dicCode = "sex")
	@Dict(dicCode = "sex")
    @ApiModelProperty(value = "人员性别")
    private java.lang.Integer sex;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**职位*/
	@Excel(name = "职位", width = 15)
    @ApiModelProperty(value = "职位")
    private java.lang.String position;
	/**工作组*/
//	@Excel(name = "工作组", width = 15)
    @ApiModelProperty(value = "工作组")
    private java.lang.String workGroup;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private java.lang.String tel;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
    /**用户id*/
    @ApiModelProperty(value = "用户id")
    private java.lang.String userId;
}
