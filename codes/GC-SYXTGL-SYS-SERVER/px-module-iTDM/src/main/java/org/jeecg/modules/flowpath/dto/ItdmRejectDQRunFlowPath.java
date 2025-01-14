package org.jeecg.modules.flowpath.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItdmRejectDQRunFlowPath {
    @ApiModelProperty(value = "运行流程id")
    private String runFlowPath;

    @ApiModelProperty(value = "原因")

    private String yuanyin;


}
