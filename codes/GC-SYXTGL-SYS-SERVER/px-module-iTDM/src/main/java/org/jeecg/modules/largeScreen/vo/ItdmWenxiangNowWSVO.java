package org.jeecg.modules.largeScreen.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ItdmWenxiangNowWSVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**设备名称*/
    @Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private java.lang.String deviceName;
    /**温度*/
    @Excel(name = "温度", width = 15)
    @ApiModelProperty(value = "温度")
    private java.math.BigDecimal temperature;
    /**湿度*/
    @Excel(name = "湿度", width = 15)
    @ApiModelProperty(value = "湿度")
    private java.math.BigDecimal humidity;
    /**A相电流*/
    @Excel(name = "A相电流", width = 15)
    @ApiModelProperty(value = "A相电流")
    private java.math.BigDecimal currentA;
    /**B相电流*/
    @Excel(name = "B相电流", width = 15)
    @ApiModelProperty(value = "B相电流")
    private java.math.BigDecimal currentB;
    /**C相电流*/
    @Excel(name = "C相电流", width = 15)
    @ApiModelProperty(value = "C相电流")
    private java.math.BigDecimal currentC;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
}
