package org.jeecg.modules.apply.lisener;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.jeecg.common.Constant;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.utils.StringUtils;
import org.jeecg.modules.approval.model.dto.ApprovalOpinionDTO;
import org.jeecg.modules.approval.service.ApprovalService;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 全局事件监听
 * <p>
 * Create by YLL on 2020/1/2
 */
@Component
@Slf4j
public class CustomEventListener implements ActivitiEventListener, Serializable {
    @Override
    public void onEvent(ActivitiEvent event) {

//         TODO: 2020/4/15 这段代码发布时需要注释掉!!
        //测试,将所有流程审核人设成admin;
//        if (event.getType().equals(ActivitiEventType.TASK_ASSIGNED)) {
//            ActivitiEntityEvent entityEvent = (ActivitiEntityEvent) event;
//            TaskEntity entity = (TaskEntity) entityEvent.getEntity();
//            entity.setAssignee("e9ca23d68d884d4ebb19d07889727dae");
//        }

        if (event.getType().equals(ActivitiEventType.TASK_CREATED)) {
            ActivitiEntityEvent entityEvent = (ActivitiEntityEvent) event;
            TaskEntity entity = (TaskEntity) entityEvent.getEntity();

            RepositoryService repositoryService = SpringContextUtils.getBean(RepositoryService.class);
            Set<String> userIds = new HashSet<>();

            //是否自己指定的办理人
            Object opinionObject = entity.getVariable(Constant.ACT_APPLY_OPINION_LIST);
            if (opinionObject != null) {
                List<ApprovalOpinionDTO> approvalOpinionDTOS = JSONObject.parseArray(opinionObject.toString(), ApprovalOpinionDTO.class);
                ApprovalOpinionDTO approvalOpinionDTO = approvalOpinionDTOS.get(approvalOpinionDTOS.size() - 1);
                if (Constant.YES.equals(approvalOpinionDTO.getDefNextAssignee()) && approvalOpinionDTO.getNextAssignee() != null) {
                    entity.setAssignee(approvalOpinionDTO.getNextAssignee().getId());
                }
            }

            if (StringUtils.isNotEmpty(entity.getAssignee())) {
                userIds.add(entity.getAssignee());
            } else if (StringUtils.isNotEmpty(entity.getCandidates())) {
                ApprovalService approvalService = SpringContextUtils.getBean(ApprovalService.class);
                userIds = approvalService.getCandiates(entity.getId());
            }
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
