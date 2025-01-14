package org.jeecg.modules.itdmGongDan.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.itdmGongDan.entity.ItdmGongdanMaster;
import org.jeecg.modules.itdmGongDan.service.IItdmGongdanMasterService;
import org.jeecg.modules.itdmGongDan.vo.TMDYVO;
import org.jeecg.modules.weituo.convert.WeituoConvert;
import org.jeecg.modules.weituo.entity.ItdmWeituoInfo;
import org.jeecg.modules.weituo.service.IItdmWeituoInfoService;
import org.jeecg.modules.weituo.vo.ItdmWeituoInfoTMDYVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 条码打印
 */
@Api(tags="条码打印")
@RestController
@RequestMapping("/tmdy")
@Slf4j
public class TMDYController {

    @Autowired
    @SuppressWarnings("all")
    private IItdmGongdanMasterService itdmGongdanMasterService;
    @Autowired
    @SuppressWarnings("all")
    private IItdmWeituoInfoService weituoInfoService;


    /**
     * 根据委托id查工单master
     */
    @ApiOperation(value="根据委托id查工单master", notes="根据委托id查工单master")
    @GetMapping(value = "/listByWeituoId")
    public TMDYVO listByWeituoId(@RequestParam(name="weituoId") String weituoId){
        TMDYVO tmdyvo = new TMDYVO();
        ItdmWeituoInfo weituoInfo = weituoInfoService.getById(weituoId);
        ItdmWeituoInfoTMDYVO infoTMDYVO = WeituoConvert.INSTANCE.toWeituoInfoTMDYVO(weituoInfo);

        List<ItdmGongdanMaster> list = itdmGongdanMasterService.listByWeituoId(weituoId);

        tmdyvo.setInfoTMDYVO(infoTMDYVO);
        tmdyvo.setList(list);
        return tmdyvo;
    }

}
