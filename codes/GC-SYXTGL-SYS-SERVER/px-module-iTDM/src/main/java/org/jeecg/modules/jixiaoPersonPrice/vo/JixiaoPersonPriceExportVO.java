package org.jeecg.modules.jixiaoPersonPrice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
public class JixiaoPersonPriceExportVO {

    /**委托编号*/
    @Excel(name = "委托编号", width = 15)
    @ApiModelProperty(value = "委托编号")
    private java.lang.String weituoNo;
    /**量化积分*/
    @Excel(name = "量化积分", width = 15)
    @ApiModelProperty(value = "量化积分")
    private java.math.BigDecimal lhJifen;
    /**绩效人员*/
    @Excel(name = "绩效人员", width = 15)
    @ApiModelProperty(value = "绩效人员")
    private java.lang.String name;

}
