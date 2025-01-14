package org.jeecg.modules.flowpath.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.flowpath.convert.FlowPathConvert;
import org.jeecg.modules.flowpath.dto.*;
import org.jeecg.modules.flowpath.entity.ItdmRunFlowPath;
import org.jeecg.modules.flowpath.entity.ItdmRunFlowPathStep;
import org.jeecg.modules.flowpath.mapper.ItdmRunFlowPathLogMapper;
import org.jeecg.modules.flowpath.mapper.ItdmRunFlowPathMapper;
import org.jeecg.modules.flowpath.mapper.ItdmRunFlowPathStepMapper;
import org.jeecg.modules.flowpath.service.IItdmFlowPathStepService;
import org.jeecg.modules.flowpath.service.IItdmRunFlowPathStepService;
import org.jeecg.modules.flowpath.vo.ItdmBuohuiRunFlowPathStepVO;
import org.jeecg.modules.itdmFirstPage.entity.ItdmTask;
import org.jeecg.modules.itdmFirstPage.mapper.ItdmTaskMapper;
import org.jeecg.modules.itdmFirstPage.service.IItdmTaskService;
import org.jeecg.modules.qita.GlobalVariables;
import org.jeecg.modules.system.service.ISysRoleService;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.modules.weituo.entity.ItdmWeituoInfo;
import org.jeecg.modules.weituo.service.IItdmWeituoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 运行流程步骤表
 * @@author 品讯科技
 * @Date: 2023-07-20
 * @Version: V1.0
 */
@Service
public class ItdmRunFlowPathStepServiceImpl extends ServiceImpl<ItdmRunFlowPathStepMapper, ItdmRunFlowPathStep> implements IItdmRunFlowPathStepService {


    @Autowired
    @SuppressWarnings("all")
    private ItdmRunFlowPathMapper itdmRunFlowPathMapper;

    @Autowired
    @SuppressWarnings("all")
    private ItdmRunFlowPathLogMapper logMapper;

    @Autowired
    private ItdmFZRunFlowPathLogService logService;

    @Autowired
    @SuppressWarnings("all")
    private ItdmRunFlowPathStepMapper runFlowPathStepMapper;


    @Autowired
    @SuppressWarnings("all")
    private IItdmTaskService taskService;
    @Autowired
    @SuppressWarnings("all")
    private ItdmTaskMapper taskMapper;
    @Autowired
    @SuppressWarnings("all")
    private ISysRoleService roleService;
    @Autowired
    @SuppressWarnings("all")
    private ISysUserService userService;
    @Autowired
    @SuppressWarnings("all")
    private IItdmWeituoInfoService weituoInfoService;
    @Autowired
    @SuppressWarnings("all")
    private IItdmFlowPathStepService flowPathStepService;


    @Override
    @Transactional

    public void saveData(ItdmSaveRunFlowPath saveRunFlowPath) {
        ItdmRunFlowPathStep select = this.baseMapper.selectById(saveRunFlowPath.getRunFlowPathStep());
        ItdmRunFlowPath itdmRunFlowPath = this.itdmRunFlowPathMapper.selectById(select.getRunFlowPath());
        if (!itdmRunFlowPath.getDqSetp().equals(select.getFlowPathSetp())) {
            throw new JeecgBootException("当前节点不是在进行中");
        }
        ItdmRunFlowPathStep itdmRunFlowPathStep = new ItdmRunFlowPathStep();
        itdmRunFlowPathStep.setId(saveRunFlowPath.getRunFlowPathStep());
        itdmRunFlowPathStep.setSaveData(saveRunFlowPath.getSaveData());

        this.baseMapper.updateById(itdmRunFlowPathStep);

    }

    @Override
    @Transactional
    public void pass(ItdmSaveRunFlowPath saveRunFlowPath) {

        ItdmRunFlowPathStep select = this.baseMapper.selectById(saveRunFlowPath.getRunFlowPathStep());
        if (StringUtils.isBlank(saveRunFlowPath.getSaveData())) {

            throw new JeecgBootException("数据为空");

        }
        select.setSaveData(saveRunFlowPath.getSaveData());
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        select.setFqUser(user.getUsername());
        select.setShenheTime(new Date());
        this.baseMapper.updateById(select);
        ItdmRunFlowPathStep nextstep = this.baseMapper.selectOne(Wrappers.lambdaQuery(ItdmRunFlowPathStep.class)
                .eq(ItdmRunFlowPathStep::getRunFlowPath, select.getRunFlowPath())
                .eq(ItdmRunFlowPathStep::getSort, Integer.parseInt(select.getSort()) + 1)

        );
        ItdmRunFlowPath itdmRunFlowPath = this.itdmRunFlowPathMapper.selectById(select.getRunFlowPath());
        if (!itdmRunFlowPath.getDqSetp().equals(select.getFlowPathSetp())) {
            throw new JeecgBootException("当前节点不是在进行中");
        }

        itdmRunFlowPath.setId(select.getRunFlowPath());
        itdmRunFlowPath.setDqSetp(nextstep.getFlowPathSetp());
        itdmRunFlowPath.setDqSetpUserIds(nextstep.getUserIds());
        itdmRunFlowPath.setDqSetpRoleId(nextstep.getRoleId());
        itdmRunFlowPathMapper.updateById(itdmRunFlowPath);

        logService.insert(select.getRunFlowPath(), select.getId(), nextstep.getId(), "通过", "");

    }

    /**通过时选择下一步操作的人（不是id，是用户账号）*/
    public List<String> selectNextStepUser(ItdmPassSelectNextStepUserRunFlowPath runFlowPath) {
        ItdmRunFlowPath itdmRunFlowPath = this.itdmRunFlowPathMapper.selectById(runFlowPath.getRunFlowPath());

        ItdmRunFlowPathStep select = this.baseMapper.selectOne(Wrappers.lambdaQuery(ItdmRunFlowPathStep.class)
                .eq(ItdmRunFlowPathStep::getRunFlowPath, itdmRunFlowPath.getId())
                .eq(ItdmRunFlowPathStep::getFlowPathSetp, itdmRunFlowPath.getDqSetp())

        );
        ItdmRunFlowPathStep nextstep = this.baseMapper.selectOne(Wrappers.lambdaQuery(ItdmRunFlowPathStep.class)
                .eq(ItdmRunFlowPathStep::getRunFlowPath, select.getRunFlowPath())
                .eq(ItdmRunFlowPathStep::getSort, Integer.parseInt(select.getSort()) + 1)

        );
        String userIds = nextstep.getUserIds();
        List<String> userIdList = new ArrayList<>();
        if(userIds!=null && !userIds.equals("")){
            userIdList = Arrays.asList(userIds.split(","));
        }

        return userIdList;
    }

    /**通过*/
    @Override
    @Transactional

    public void pass1(ItdmPassRunFlowPath itdmRunFlowPathStep) {
        String nextStepUser = "";//下一步操作人员的登录账号
        if(itdmRunFlowPathStep.getNextStepUser()!=null) nextStepUser = itdmRunFlowPathStep.getNextStepUser();

        //根据当前运行流程id查当前运行流程
        ItdmRunFlowPath itdmRunFlowPath = this.itdmRunFlowPathMapper.selectById(itdmRunFlowPathStep.getRunFlowPath());

        //根据当前运行流程的主键id和当前步骤id——查对应当前运行流程步骤
        ItdmRunFlowPathStep select = this.baseMapper.selectOne(Wrappers.lambdaQuery(ItdmRunFlowPathStep.class)
                .eq(ItdmRunFlowPathStep::getRunFlowPath, itdmRunFlowPath.getId())
                .eq(ItdmRunFlowPathStep::getFlowPathSetp, itdmRunFlowPath.getDqSetp())
        );

        if (StringUtils.isBlank(select.getSaveData())) {
            throw new JeecgBootException("流程编辑无数据，请编辑后进行下一步");
        }

        //运行流程步骤表修改——最后修改人员（当前登录用户）、审核时间
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        select.setFqUser(user.getUsername());
        select.setShenheTime(new Date());
        this.baseMapper.updateById(select);

        //根据当前运行流程步骤的运行流程id、步骤顺序+1查————当前运行流程的下一运行步骤
        ItdmRunFlowPathStep nextstep = this.baseMapper.selectOne(Wrappers.lambdaQuery(ItdmRunFlowPathStep.class)
                .eq(ItdmRunFlowPathStep::getRunFlowPath, select.getRunFlowPath())
                .eq(ItdmRunFlowPathStep::getSort, Integer.parseInt(select.getSort()) + 1)
        );

        //下一步为空，运行流程改为结束状态
        if (nextstep == null) {
            itdmRunFlowPath.setStatus("2");
            itdmRunFlowPathMapper.updateById(itdmRunFlowPath);

            //委托状态改为完结状态
            weituoInfoService.updateCompleteStatusByWeituoNo(itdmRunFlowPath.getWeituoNo());
            //修改对应委托的委托进度——此时结束
            weituoInfoService.updateWeituoStepByWeituoNo(itdmRunFlowPath.getWeituoNo(), GlobalVariables.js);

            //修改该委托对应的所有任务状态为2完结
            if(itdmRunFlowPath!=null){
                Map<String, Object> weituoMap = new HashMap<>();
                weituoMap.put("weituo_no",itdmRunFlowPath.getWeituoNo());
                String weituoId = weituoInfoService.listByMap(weituoMap).get(0).getId();
                taskService.updateByWeituoId(weituoId);
            }

            return;
        }else {
            //修改运行流程步骤表的下一步骤的用户
            nextstep.setUserIds(nextStepUser);
            runFlowPathStepMapper.updateById(nextstep);

            //修改对应委托的委托进度——从当前运行流程的下一运行步骤对象中获得下一步骤名称
            weituoInfoService.updateWeituoStepByWeituoNo(itdmRunFlowPath.getWeituoNo(), nextstep.getName());
        }

        //原待办任务名、委托id
        if(itdmRunFlowPath!=null){
            String content = flowPathStepService.getById(itdmRunFlowPath.getDqSetp()).getName();
            Map<String, Object> weituoMap = new HashMap<>();
            weituoMap.put("weituo_no",itdmRunFlowPath.getWeituoNo());
            String weituoId = weituoInfoService.listByMap(weituoMap).get(0).getId();

            //修改当前运行流程的 当前步骤id、当前步骤用户————由下一运行步骤的所属用户改为选择的用户、当前步骤角色
            itdmRunFlowPath.setId(select.getRunFlowPath());
            itdmRunFlowPath.setDqSetp(nextstep.getFlowPathSetp());
//            itdmRunFlowPath.setDqSetpUserIds(nextstep.getUserIds());
            itdmRunFlowPath.setDqSetpUserIds(nextStepUser);
            itdmRunFlowPath.setDqSetpRoleId(nextstep.getRoleId());
            itdmRunFlowPathMapper.updateById(itdmRunFlowPath);

            //该步骤通过后，原步骤状态改为进行中，
            ItdmTask yuanTask = new ItdmTask();
            yuanTask.setWeituoId(weituoId);//委托id
            yuanTask.setTaskContent(content);
            QueryWrapper<ItdmTask> queryWrapper = new QueryWrapper<>(yuanTask);
            ItdmTask yuanTask1 = taskService.getOne(queryWrapper);
            if(yuanTask1!=null){
                yuanTask1.setTaskStatus(1);//进行中
                taskService.updateById(yuanTask1);
            }
            // 然后新增一条该步骤的我的任务
            String role = roleService.getById(itdmRunFlowPath.getDqSetpRoleId()).getRoleName();
//            String userName = userService.getUserByName(itdmRunFlowPath.getDqSetpUserIds().split(",")[0]).getRealname();
            String userName = userService.getUserByName(itdmRunFlowPath.getDqSetpUserIds().split(",")[0]).getRealname();//真实姓名
            ItdmTask itdmTask = new ItdmTask();
            itdmTask.setTaskRole(role);
            itdmTask.setTaskUser(userName);//真实姓名
            itdmTask.setWeituoId(weituoId);//委托id
            String taskContent = flowPathStepService.getById(nextstep.getFlowPathSetp()).getName();
            itdmTask.setTaskContent(taskContent);//当前步骤名
            itdmTask.setTaskStatus(0);//待办
            taskService.insertTaskAfterJudge(itdmTask);
        }

        logService.insert(select.getRunFlowPath(), select.getId(), nextstep.getId(), "通过", "");
    }

    @Override
    @Transactional
    public void rejectDQ(ItdmRejectDQRunFlowPath itdmRejectDQRunFlowPath) {
        ItdmRunFlowPath itdmRunFlowPath = this.itdmRunFlowPathMapper.selectById(itdmRejectDQRunFlowPath.getRunFlowPath());
        ItdmRunFlowPathStep select = this.baseMapper.selectOne(Wrappers.lambdaQuery(ItdmRunFlowPathStep.class)
                .eq(ItdmRunFlowPathStep::getRunFlowPath, itdmRunFlowPath.getId())
                .eq(ItdmRunFlowPathStep::getFlowPathSetp, itdmRunFlowPath.getDqSetp())

        );


        ItdmRunFlowPathStep prvestep = this.baseMapper.selectOne(Wrappers.lambdaQuery(ItdmRunFlowPathStep.class)
                .eq(ItdmRunFlowPathStep::getRunFlowPath, select.getRunFlowPath())
                .eq(ItdmRunFlowPathStep::getSort, Integer.parseInt(select.getSort()) - 1)

        );

        //原待办人员和角色、委托id（本次的，改之前的）
        String yuanRole = roleService.getById(itdmRunFlowPath.getDqSetpRoleId()).getRoleName();
        String yuanUserName = userService.getUserByName(itdmRunFlowPath.getDqSetpUserIds().split(",")[0]).getRealname();
        String yuanContent = flowPathStepService.getById(itdmRunFlowPath.getDqSetp()).getName();
        Map<String, Object> weituoMap = new HashMap<>();
        weituoMap.put("weituo_no",itdmRunFlowPath.getWeituoNo());
        String weituoId = weituoInfoService.listByMap(weituoMap).get(0).getId();

        itdmRunFlowPath.setDqSetp(prvestep.getFlowPathSetp());
        itdmRunFlowPath.setDqSetpUserIds(prvestep.getUserIds());
        itdmRunFlowPath.setDqSetpRoleId(prvestep.getRoleId());
        itdmRunFlowPathMapper.updateById(itdmRunFlowPath);


        //修改对应委托的委托进度——从当前运行流程的上一运行步骤对象中获得上一步骤名称
        weituoInfoService.updateWeituoStepByWeituoNo(itdmRunFlowPath.getWeituoNo(), prvestep.getName());

        //驳回到上一步，修改上一步状态为待办，
        String content = flowPathStepService.getById(prvestep.getFlowPathSetp()).getName();
        ItdmTask sybTask = new ItdmTask();
        sybTask.setWeituoId(weituoId);//委托id
        sybTask.setTaskContent(content);
        QueryWrapper<ItdmTask> queryWrapper = new QueryWrapper<>(sybTask);
        ItdmTask sybTask1 = taskService.getOne(queryWrapper);
        if(sybTask1!=null){
            sybTask1.setTaskStatus(0);//改为待办
            taskService.updateById(sybTask1);
        }
        //删掉此次步骤的任务
        ItdmTask bcTask = new ItdmTask();
        bcTask.setWeituoId(weituoId);//委托id
        bcTask.setTaskUser(yuanUserName);//本次人员
        bcTask.setTaskRole(yuanRole);//本次角色
        bcTask.setTaskContent(yuanContent);//本次任务
        QueryWrapper<ItdmTask> bcQueryWrapper = new QueryWrapper<>(bcTask);
        ItdmTask bcTask1 = taskService.getOne(bcQueryWrapper);
        if(bcTask1!=null){
            taskService.removeById(bcTask1);
        }


        logService.insert(select.getRunFlowPath(), select.getId(), prvestep.getId(), "驳回上一节点", itdmRejectDQRunFlowPath.getYuanyin());

    }


    @Override
    public List<ItdmBuohuiRunFlowPathStepVO> yrunList(String runFlowPath) {
        ItdmRunFlowPath itdmRunFlowPath = this.itdmRunFlowPathMapper.selectById(runFlowPath);
        ItdmRunFlowPathStep select = this.baseMapper.selectOne(Wrappers.lambdaQuery(ItdmRunFlowPathStep.class)
                .eq(ItdmRunFlowPathStep::getRunFlowPath, itdmRunFlowPath.getId())
                .eq(ItdmRunFlowPathStep::getFlowPathSetp, itdmRunFlowPath.getDqSetp())

        );
        List<ItdmRunFlowPathStep> prvestep = this.baseMapper.selectList(Wrappers.lambdaQuery(ItdmRunFlowPathStep.class)
                .eq(ItdmRunFlowPathStep::getRunFlowPath, select.getRunFlowPath())
                .lt(ItdmRunFlowPathStep::getSort, Integer.parseInt(select.getSort()))

        );

        return prvestep.stream().map(FlowPathConvert.INSTANCE::to).collect(Collectors.toList());
    }


    @Override
    @Transactional

    public void rejectzd(ItdmRejectZDRunFlowPath zdRunFlowPath) {

        ItdmRunFlowPath itdmRunFlowPath = this.itdmRunFlowPathMapper.selectById(zdRunFlowPath.getRunFlowPath());
        ItdmRunFlowPathStep prvestep = this.baseMapper.selectById(zdRunFlowPath.getRunFlowPathSept());

        ItdmRunFlowPathStep select = this.baseMapper.selectOne(Wrappers.lambdaQuery(ItdmRunFlowPathStep.class)
                .eq(ItdmRunFlowPathStep::getRunFlowPath, itdmRunFlowPath.getId())
                .eq(ItdmRunFlowPathStep::getFlowPathSetp, itdmRunFlowPath.getDqSetp())

        );

        itdmRunFlowPath.setDqSetp(prvestep.getFlowPathSetp());
        itdmRunFlowPath.setDqSetpUserIds(prvestep.getUserIds());
        itdmRunFlowPath.setDqSetpRoleId(prvestep.getRoleId());
        itdmRunFlowPathMapper.updateById(itdmRunFlowPath);
        logService.insert(prvestep.getRunFlowPath(), select.getId(), prvestep.getId(), "驳回指定节点", zdRunFlowPath.getYuanyin());

        //修改对应委托的委托进度——从选择的运行步骤对象中获得选择的当前步骤名称
        weituoInfoService.updateWeituoStepByWeituoNo(itdmRunFlowPath.getWeituoNo(), prvestep.getName());

        //驳回到指定节点，修改指定节点的任务状态改为待办0
        Map<String, Object> weituoMap = new HashMap<>();
        weituoMap.put("weituo_no",itdmRunFlowPath.getWeituoNo());
        List<ItdmWeituoInfo> weituoInfoList = weituoInfoService.listByMap(weituoMap);
        String weituoId = weituoInfoList.get(0).getId();
        String zdjdContent = flowPathStepService.getById(prvestep.getFlowPathSetp()).getName();
        ItdmTask zdjdTask = new ItdmTask();
        zdjdTask.setWeituoId(weituoId);//委托id
        zdjdTask.setTaskContent(zdjdContent);
        QueryWrapper<ItdmTask> queryWrapper = new QueryWrapper<>(zdjdTask);
        ItdmTask zdjdTask1 = taskService.getOne(queryWrapper);
        if(zdjdTask1!=null){
            zdjdTask1.setTaskStatus(0);//改为待办
            taskService.updateById(zdjdTask1);
        }

        //指定节点之后的所有步骤的任务全部删除
        String runFlowPath = prvestep.getRunFlowPath();//指定节点的运行流程id
        int sort = Integer.parseInt(prvestep.getSort());
        ItdmRunFlowPathStep step = new ItdmRunFlowPathStep();
        step.setRunFlowPath(runFlowPath);
        Wrapper<ItdmRunFlowPathStep> stepWrapper = new QueryWrapper<>(step);
        List<ItdmRunFlowPathStep> stepList = runFlowPathStepMapper.selectList(stepWrapper);//该委托发起的流程对应的所有运行流程步骤
        for(ItdmRunFlowPathStep s:stepList){
            int sSort = Integer.parseInt(s.getSort());
            if(sSort>sort){//该步骤对应的任务需要删除
                //先查该步骤对应的任务
                ItdmTask t = new ItdmTask();
                t.setWeituoId(weituoId);//委托id
                t.setTaskContent(s.getName());//任务名///////////////////////////////////////////
                QueryWrapper<ItdmTask> bcQueryWrapper = new QueryWrapper<>(t);
                ItdmTask t1 = taskService.getOne(bcQueryWrapper);
                if(t1!=null){
                    taskService.removeById(t1);
                }
            }
        }

    }


    @Override
    public void jieshu(String s) {
        ItdmRunFlowPath itdmRunFlowPath = this.itdmRunFlowPathMapper.selectById(entityClass);
        itdmRunFlowPath.setStatus("1");

        itdmRunFlowPathMapper.updateById(itdmRunFlowPath);


    }
}
