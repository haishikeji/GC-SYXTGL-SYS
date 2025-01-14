package org.jeecg.modules.largeScreen.task;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.tomcat.websocket.WsSession;
import org.jeecg.modules.itdmDataNow.entity.*;
import org.jeecg.modules.itdmDataNow.service.*;
import org.jeecg.modules.itdmDevice.entity.ItdmDevice;
import org.jeecg.modules.itdmDevice.service.IItdmDeviceService;
import org.jeecg.modules.itdmDeviceRun.entity.ItdmDeviceRun;
import org.jeecg.modules.itdmDeviceRun.service.IItdmDeviceRunService;
import org.jeecg.modules.itdmDeviceStart.entity.ItdmDeviceStart;
import org.jeecg.modules.itdmDeviceStart.service.IItdmDeviceStartService;
import org.jeecg.modules.largeScreen.component.WebSocketServer2;
import org.jeecg.modules.largeScreen.convert.*;
import org.jeecg.modules.largeScreen.entity.ItdmLixueNow;
import org.jeecg.modules.largeScreen.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component("demoTask2")
@SuppressWarnings("all")
public class DemoTask2 {

    @Autowired
    private WebSocketServer2 webSocketServer;
    @Autowired
    private IItdmWenxiangNowService wenxiangNowService;
    @Autowired
    private IItdmDeviceService deviceService;
    @Autowired
    private IItdmAlarmNowService alarmNowService;
    @Autowired
    private IItdmDeviceStartService deviceStartService;
    @Autowired
    private IItdmWenxiangVoltageNowService voltageNowService;
    @Autowired
    private IItdmEnvironmentQuyuNowService quyuNowService;
    @Autowired
    private IItdmEnvironmentWaterNowService waterNowService;
    @Autowired
    private IItdmEnvironmentYeweiNowService yeweiNowService;
    @Autowired
    private IItdmDeviceRunService deviceRunService;

    static List<Map<String,Object>> list = new ArrayList<>();//总的

    /**
     * 数据初始化List<Map<String,Object>
     * */
    public List<Map<String,Object>> chushihua(){

        list= new ArrayList<>();//清空原来的数据
        Map<String, Object> columnMap = new HashMap<>();//用于列表搜索
        HashMap<String,Object> map = new HashMap<String, Object>();//用于返回的结果


        /**检查设备表设备状态及更新时间，如果设备非“未运行状态”，修改实时数据和设备实时状态*/
        Map<String, Object> deviceMap1 = new HashMap<>();
        deviceMap1.put("iz_display",1);
        List<ItdmDevice> deviceDisplayList = deviceService.listByMap(deviceMap1);
        Date now = new Date();

        //检查温箱通用电压实时表的更新时间
        ItdmWenxiangVoltageNow wenxiangVoltageNow = voltageNowService.getById(1);
        Date vDate = wenxiangVoltageNow.getCreateTime();
        if(now.getTime()-vDate.getTime()>180000 ){
            wenxiangVoltageNow.setVoltageA(BigDecimal.valueOf(0));
            wenxiangVoltageNow.setVoltageB(BigDecimal.valueOf(0));
            wenxiangVoltageNow.setVoltageC(BigDecimal.valueOf(0));
            wenxiangVoltageNow.setCreateTime(now);
            voltageNowService.updateById(wenxiangVoltageNow);
        }

        for (ItdmDevice device:deviceDisplayList){
            if(device.getUpdateTime()!=null){
                Date date1 = device.getUpdateTime();
                //时间相差3分钟且原来设备运行标识为1（设备已开始运行），设备可能由于断电或者断网没有数据传过来，修改该设备状态为“未运行”
                //设备结束运行需要新增一条设备运行记录
                //修改实时数据表对应该设备数据为0
                if(now.getTime()-date1.getTime()>180000 && device.getBiaoshi()==1){
                    Long x = (now.getTime() - date1.getTime()); //计算更新时间与当前时间相差时间（毫秒）

                    Integer d = Math.toIntExact(x / (24* 60* 60* 1000)); //天
                    Integer h = Math.toIntExact(x / (60 * 60 * 1000) - d * 24); //小时
                    Integer m = Math.toIntExact((x / (60 * 1000)) - d * 24 * 60 - h * 60);//分钟
                    Integer s = Math.toIntExact(x/1000 - d * 24 * 60 * 60 - h * 60 * 60 - m * 60);//秒

                    String timeRun = d+"天"+h+"小时"+m +"分钟"+ s+ "秒";
                    ItdmDeviceRun run = ItdmDeviceConvert.INSTANCE.toItdmDeviceRun(
                            device.getId(),device.getDeviceName(),device.getDeviceType(),
                            date1, now, d, h, m, s, timeRun);
                    deviceRunService.save(run);

                    //修改设备相关信息
                    device.setDeviceStatus(0); //设备状态（待机0,运行中1,报警2,故障3,非故障4)
                    device.setBiaoshi(0); //设备运行标识(0设备已结束运行1设备已开始运行)
                    device.setRunStartTime(null); //设备开始运行时间改为空
                    deviceService.updateById(device);

                    //修改温箱实时数据表
                    ItdmWenxiangNow queryWenxiangNow = new ItdmWenxiangNow();
                    queryWenxiangNow.setDeviceId(device.getId());
                    QueryWrapper<ItdmWenxiangNow> queryWrapper = new QueryWrapper<>(queryWenxiangNow);
                    ItdmWenxiangNow wenxiangNow = wenxiangNowService.getOne(queryWrapper);
                    wenxiangNow.setCreateTime(now);
                    wenxiangNow.setTemperature(BigDecimal.valueOf(0));
                    wenxiangNow.setHumidity(BigDecimal.valueOf(0));
                    wenxiangNow.setCurrentA(BigDecimal.valueOf(0));
                    wenxiangNow.setCurrentB(BigDecimal.valueOf(0));
                    wenxiangNow.setCurrentC(BigDecimal.valueOf(0));
                    wenxiangNowService.updateById(wenxiangNow);

                    //修改实时报警表状态——改为正常（暂定）
                    Map<String, Object> alarmNowMap = new HashMap<>();
                    alarmNowMap.put("device_id",device.getId());
                    alarmNowMap.put("data_status",1);//数据状态——报警
                    List<ItdmAlarmNow> alarmNowList = alarmNowService.listByMap(alarmNowMap);
                    for (ItdmAlarmNow alarmNow :alarmNowList){
                        alarmNow.setDataStatus(0);//数据状态——正常
                        alarmNow.setCreateTime(now);
                        alarmNowService.updateById(alarmNow);
                    }
                }
            }
        }



        /**温箱实时数据*/
        List<ItdmWenxiangNow> wenxiangNowList = wenxiangNowService.list();
        List<ItdmWenxiangNowWSVO> wenxiangVOList = wenxiangNowList.stream().map(i-> {
            return ItdmWenxiangNowConvert.INSTANCE.convert(i);
                }).collect(Collectors.toList());

        map = new HashMap<String, Object>();
        map.put("data",wenxiangVOList);
        map.put("name","温箱试验数据");
        list.add(map);

        /**力学设备实时数据*/
        List<ItdmLixueNow> lixueVOList= new ArrayList<>();
//        for(int i=1; i<=6; i++){
////            Random random1 = new Random();//随机数
////            int current = random1.nextInt(20)+100;//电流
////            int voltage = random1.nextInt(30)+70;//电压
////            int waterTemperature = random1.nextInt(100);//水温
////            int waterPressure = random1.nextInt(100);//水压
//            int current = 0;//电流
//            int voltage = 0;//电压
//            int waterTemperature = 0;//水温
//            int waterPressure = 0;//水压
//
//            ItdmLixueNow liXue = new ItdmLixueNow();
//            liXue.setDeviceName("力学设备"+i);
//            liXue.setCurrent(BigDecimal.valueOf(current));
//            liXue.setVoltage(BigDecimal.valueOf(voltage));
//            liXue.setWaterTemperature(BigDecimal.valueOf(waterTemperature));
//            liXue.setWaterPressure(BigDecimal.valueOf(waterPressure));
//            lixueVOList.add(liXue);
//        }
        map = new HashMap<String, Object>();
        map.put("data",lixueVOList);
        map.put("name","力学设备试验数据");
        list.add(map);


        /**设备运行状况2
         * 0 待机
         * 1 运行中
         * 2 报警
         * 3 故障——故障状态中，不根据硬件传过来的设备状态而修改故障状态
         * 4 解除故障
         * */
//        List<ItdmDevice> deviceList = deviceService.queryList();
        Map<String, Object> deviceMap = new HashMap<>();
        deviceMap.put("iz_display",1);
        List<ItdmDevice> deviceList = deviceService.listByMap(deviceMap);
        List<ItdmDeviceWSVO> deviceWSVOList = deviceList.stream().map(i->{
                return ItdmDeviceConvert.INSTANCE.convert(i);
        }).collect(Collectors.toList());
        map = new HashMap<String, Object>();
        map.put("data",deviceWSVOList);
        map.put("name","设备运行状况");
        list.add(map);

        /**报警3——实时报警*/
        columnMap = new HashMap<>();
        columnMap.put("data_status",1);//数据状态,0正常1报警
        columnMap.put("voice_status",1);//是否语音播报,0关闭1开启
        List<ItdmAlarmNow> alarmNowList = alarmNowService.listByMap(columnMap);
        List<ItdmAlarmNowWSVO> alarmNowWSVOList = alarmNowList.stream().map(i->{
            Integer alarmType = 1; //数据类型（1数据报警 2通电变温预警 3设备启动报警）
            String alarmMessage = i.getDeviceName()+i.getValueType()+"异常";
            return ItdmAlarmNowConvert.INSTANCE.convert(i,alarmType,alarmMessage);
        }).collect(Collectors.toList());
        /**设备启动报警*/
        ItdmDeviceStart deviceStart = new ItdmDeviceStart();
        deviceStart.setDataStatus(0);
        QueryWrapper<ItdmDeviceStart> queryWrapper = new QueryWrapper<>(deviceStart);
        queryWrapper.orderByAsc("start_time");
        List<ItdmDeviceStart> deviceStartList = deviceStartService.list(queryWrapper);
        List<ItdmAlarmNowWSVO> deviceStartWSVOList = deviceStartList.stream().map(i->{
            Integer alarmType = 3; //数据类型（1数据报警 2通电变温预警 3设备启动报警）
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String alarmMessage = i.getDeviceName()+sdf.format(i.getStartTime())+"已启动";
            return ItdmDeviceStartConvert.INSTANCE.convert(i,alarmType,alarmMessage);
        }).collect(Collectors.toList());

        alarmNowWSVOList.addAll(deviceStartWSVOList);

        map = new HashMap<String, Object>();
        map.put("data",alarmNowWSVOList);
        map.put("name","报警列表");
        list.add(map);

        /**已展示的数据状态更新*/
        for (ItdmDeviceStart start : deviceStartList){
            start.setDataStatus(1);
            deviceStartService.updateById(start);
        }

        /**语音列表*/
        List<ItdmVoiceWSVO> voiceWSVOList = alarmNowWSVOList.stream().map(i->{
            return ItdmVoiceConvert.INSTANCE.convert(i);
        }).collect(Collectors.toList());

        map = new HashMap<String, Object>();
        map.put("data",voiceWSVOList);
        map.put("name","语音列表");
        list.add(map);

        /**温箱通用数据1（a线电压 b线电压 c线电压 进出水口温度、水压，水箱液位）*/
        ItdmWenxiangVoltageNow voltageNow = voltageNowService.getById(1);
        ItdmEnvironmentWaterNow inWaterNow = waterNowService.getById(1);//进水口
        ItdmEnvironmentWaterNow outWaterNow = waterNowService.getById(2);//出水口
        ItdmEnvironmentYeweiNow yeweiNow = yeweiNowService.getById(1);
        TongyongDataVO tongyongDataVO = TongyongDataVOConvert.INSTANCE.convert(voltageNow,inWaterNow,outWaterNow,yeweiNow);

        map = new HashMap<String, Object>();
        map.put("data",tongyongDataVO);
        map.put("name","通用数据1");
        list.add(map);

        /**温箱通用数据2（温箱、力学、霉菌区域的温度、湿度、气压）*/
        List<ItdmEnvironmentQuyuNow> quyuNowList = quyuNowService.list();
        map = new HashMap<String, Object>();
        map.put("data",quyuNowList);
        map.put("name","通用数据2");
        list.add(map);

        /**设备维护预警列表*/
        ItdmDevice device = new ItdmDevice();
        device.setIsRepair(0);//0未维护

        QueryWrapper<ItdmDevice> wrapper  = new QueryWrapper<>(device);
        wrapper.isNotNull("repair_time");
        //维护时间小于等于当前时间+15天
        Date afterNow15 = new Date(new Date().getTime() + 15*1000*3600*24);
        wrapper.le("repair_time",afterNow15);
        wrapper.orderByAsc("repair_time");
        List<ItdmDevice> deviceList1 = deviceService.list(wrapper);

        List<ItdmDeviceRepairAlarmListVO>  repairAlarmListVOList = deviceList1.stream().map(i->{
            Date star = new Date();
            Long starTime=star.getTime();
            Long endTime=i.getRepairTime().getTime();
            Long num=endTime-starTime;//时间戳相差的毫秒数
            Integer day = Math.toIntExact(num / (24 * 60 * 60 * 1000));
            return ItdmDeviceConvert.INSTANCE.toList(i,day);
        }).collect(Collectors.toList());

        map = new HashMap<String, Object>();
        map.put("data",repairAlarmListVOList);
        map.put("name","设备维护预警");
        list.add(map);

        return list;
    }



    //一级大屏
    public void taskWithParamsList(WsSession session) {
        System.out.println("一级大屏调用初始化方法发送数据");
        webSocketServer.sendUser(this.chushihua(), session);
    }







}

