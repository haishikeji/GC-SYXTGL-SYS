package org.jeecg.modules.largeScreen.task;

import org.apache.tomcat.websocket.WsSession;
import org.jeecg.modules.largeScreen.component.WebSocketSecondScreenServer;
import org.jeecg.modules.largeScreen.service.IASecondScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 二级大屏折线图sTask
 * **/
@Component("sTask")
public class SecondScreenTask {

    @Autowired
    @SuppressWarnings("all")
    private IASecondScreenService secondScreenService;

    @Autowired
    @SuppressWarnings("all")
    private WebSocketSecondScreenServer webSocketSecondScreenServer;


    //二级大屏
    public void taskWithParamsListSecond(String id, WsSession session) {
        this.getList(id);
        System.out.println("发送数据");
        webSocketSecondScreenServer.sendUser(this.getList(id), session);
    }

    /**
     * 数据
     * */
    public Map<String,Object> getList(String id){
        System.out.println("=================================websocket初始化=================================id="+id);
        return secondScreenService.getList(id);
    }


}


