package org.jeecg.modules.flowpath.convert;

import org.jeecg.modules.flowpath.entity.ItdmFlowPathStep;
import org.jeecg.modules.flowpath.entity.ItdmRunFlowPathStep;
import org.jeecg.modules.flowpath.vo.ItdmBuohuiRunFlowPathStepVO;
import org.jeecg.modules.flowpath.vo.ItdmRunFlowPathStepVO;
import org.jeecg.modules.weituo.entity.ItdmWeituoInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 敏感词 Convert
 *
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface FlowPathConvert {

    FlowPathConvert INSTANCE = Mappers.getMapper(FlowPathConvert.class);

    @Mapping(target = "flowPath", source = "step.flowPathId")
    @Mapping(target = "flowPathSetp", source = "step.id")
    @Mapping(target = "name", source = "step.name")
    @Mapping(target = "sort", source = "step.sort")
    @Mapping(target = "saveData", source = "saveData")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userIds", source = "step.userIds")
    @Mapping(target = "roleId", source = "step.roleId")
    ItdmRunFlowPathStep to(ItdmFlowPathStep step, String saveData, String runFlowPath);

    @Mapping(target = "id", source = "step.id")
    @Mapping(target = "flowPath", source = "step.flowPath")
    @Mapping(target = "flowPathSetp", source = "step.flowPathSetp")
    @Mapping(target = "name", source = "step.name")
    @Mapping(target = "fqUser", source = "step.fqUser")
    @Mapping(target = "saveData", source = "step.saveData")
    @Mapping(target = "isUpdate", source = "isUpdate")
    @Mapping(target = "dangqian", source = "dangqian")
    @Mapping(target = "itdmWeituoInfo", source = "itdmWeituoInfo")
    @Mapping(target = "sort", source = "step.sort")
    @Mapping(target = "shenheTime", source = "step.shenheTime")

    ItdmRunFlowPathStepVO to(ItdmRunFlowPathStep step, Boolean isUpdate, Boolean dangqian, ItdmWeituoInfo itdmWeituoInfo);


    @Mapping(target = "value", source = "id")
    @Mapping(target = "text", source = "name")

    ItdmBuohuiRunFlowPathStepVO to(ItdmRunFlowPathStep step);
}
