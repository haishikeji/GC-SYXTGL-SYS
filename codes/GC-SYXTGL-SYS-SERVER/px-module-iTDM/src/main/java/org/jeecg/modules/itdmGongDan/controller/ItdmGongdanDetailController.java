package org.jeecg.modules.itdmGongDan.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.itdmGongDan.convert.GongDanDetailConvert;
import org.jeecg.modules.itdmGongDan.entity.ItdmGongdanDetail;
import org.jeecg.modules.itdmGongDan.entity.ItdmGongdanMaster;
import org.jeecg.modules.itdmGongDan.entity.ItdmTestPlan;
import org.jeecg.modules.itdmGongDan.mapper.ItdmGongdanMasterMapper;
import org.jeecg.modules.itdmGongDan.service.IItdmGongdanDetailService;
import org.jeecg.modules.itdmGongDan.service.IItdmGongdanMasterService;
import org.jeecg.modules.itdmGongDan.service.IItdmTestPlanService;
import org.jeecg.modules.itdmGongDan.vo.ItdmGongdanDetailListVO;
import org.jeecg.modules.weituo.entity.ItdmWeituoInfo;
import org.jeecg.modules.weituo.entity.ItdmWeituoYangpin;
import org.jeecg.modules.weituo.mapper.ItdmWeituoYangpinMapper;
import org.jeecg.modules.weituo.service.IItdmWeituoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 工单detail
 * @@author 品讯科技
 * @Date:   2023-05-21
 * @Version: V1.0
 */
@Api(tags="工单detail")
@RestController
@RequestMapping("/itdmGongdanDetail/itdmGongdanDetail")
@Slf4j
public class ItdmGongdanDetailController extends JeecgController<ItdmGongdanDetail, IItdmGongdanDetailService> {
	@Autowired
	@SuppressWarnings("all")
	private IItdmGongdanDetailService itdmGongdanDetailService;
	@Autowired
	@SuppressWarnings("all")
	private IItdmGongdanMasterService iItdmGongdanMasterService;
	@Autowired
	@SuppressWarnings("all")
	private IItdmTestPlanService itdmTestPlanService;
	@Autowired
	@SuppressWarnings("all")
	private ItdmGongdanMasterMapper itdmGongdanMasterMapper;
	@Autowired
	@SuppressWarnings("all")
	private IItdmWeituoInfoService iItdmWeituoInfoService;
	@Autowired
	@SuppressWarnings("all")
	private ItdmWeituoYangpinMapper itdmWeituoYangpinMapper;


	@ApiOperation(value="工单detail-分页列表查询", notes="工单detail-分页列表查询")
	@GetMapping(value = "/list")
	public Result<Page<ItdmGongdanDetailListVO>> queryPageList1(ItdmGongdanDetail itdmGongdanDetail,
															@RequestParam(name="weituoId", defaultValue="") String weituoId,
														   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														   HttpServletRequest req) {

		QueryWrapper<ItdmGongdanDetail> queryWrapper = QueryGenerator.initQueryWrapper(itdmGongdanDetail, req.getParameterMap());

		List<String> woIdList = new ArrayList<>();
		if(!weituoId.equals("")){ //前端有传过来委托单id
			woIdList = itdmGongdanMasterMapper.selectWoIdListByWeituoId(weituoId);
			if(woIdList != null && !woIdList.isEmpty() && woIdList.get(0)!=null){
				queryWrapper.in("wo_id",woIdList);
			}else return Result.OK(new Page<ItdmGongdanDetailListVO>());
		}//否则直接查询所有

		Page<ItdmGongdanDetail> page = new Page<ItdmGongdanDetail>(pageNo, pageSize);
		Page<ItdmGongdanDetail> pageList = itdmGongdanDetailService.page(page, queryWrapper);

//		Page<ItdmGongdanDetail> page = new Page<ItdmGongdanDetail>(pageNo, pageSize);
//		Page<ItdmGongdanDetail> pageList = itdmGongdanDetailService.getDetailListByWoIdList(page,woIdList);

		if(pageList != null ){
			List<ItdmGongdanDetailListVO> list=pageList.getRecords().stream().map(i -> {
				ItdmGongdanMaster master = iItdmGongdanMasterService.getById(i.getWoId());
				ItdmTestPlan itdmTestPlan = master==null?null:itdmTestPlanService.getById(master.getJihuaShiyanId());
				ItdmWeituoInfo itdmWeituoInfo = itdmTestPlan==null?null:iItdmWeituoInfoService.getById(itdmTestPlan.getWeituoId());
				ItdmWeituoYangpin itdmWeituoYangpin = master==null?null:itdmWeituoYangpinMapper.selectById(master.getWeituoYangpinId());
				return GongDanDetailConvert.INSTANCE.toList(i, master,itdmTestPlan,itdmWeituoInfo,itdmWeituoYangpin.getSampleModelSpecification());
			}).collect(Collectors.toList());
			return Result.OK(GongDanDetailConvert.INSTANCE.converter(pageList,list));
		}else return Result.OK(new Page<ItdmGongdanDetailListVO>());
	}


//	@ApiOperation(value="工单detail-分页列表查询", notes="工单detail-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<ItdmGongdanDetail>> queryPageList1(ItdmGongdanDetail itdmGongdanDetail,
//														  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//														  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//														  HttpServletRequest req) {
//		QueryWrapper<ItdmGongdanDetail> queryWrapper = QueryGenerator.initQueryWrapper(itdmGongdanDetail, req.getParameterMap());
//		Page<ItdmGongdanDetail> page = new Page<ItdmGongdanDetail>(pageNo, pageSize);
//		IPage<ItdmGongdanDetail> pageList = itdmGongdanDetailService.page(page, queryWrapper);
//		return Result.OK(pageList);
//	}


//	/**
//	 * 分页列表查询——自定义sql查询
//	 */
//	//@AutoLog(value = "工单detail-分页列表查询")
//	@ApiOperation(value="分页列表查询——自定义sql查询", notes="分页列表查询——自定义sql查询")
//	@GetMapping(value = "/list")
//	public Result<Page<ItdmGongdanDetailListVO>> queryPageList(ItdmGongdanDetail itdmGongdanDetail,
//															   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//															   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//															   HttpServletRequest req) {
//		Result<Page<ItdmGongdanDetailListVO>> result = new Result<Page<ItdmGongdanDetailListVO>>();
//		Page<ItdmGongdanDetailListVO> pageList = new Page<>(pageNo,pageSize);
//		pageList = itdmGongdanDetailService.getDetailList(pageList,itdmGongdanDetail.getWoId());//通知公告消息
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}


	/**
	 *   添加
	 *
	 * @param itdmGongdanDetail
	 * @return
	 */
	@AutoLog(value = "工单detail-添加")
	@ApiOperation(value="工单detail-添加", notes="工单detail-添加")
	//@RequiresPermissions("org.jeecg.modules:itdm_gongdan_detail:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ItdmGongdanDetail itdmGongdanDetail) {
		itdmGongdanDetailService.save(itdmGongdanDetail);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param itdmGongdanDetail
	 * @return
	 */
	@AutoLog(value = "工单detail-编辑")
	@ApiOperation(value="工单detail-编辑", notes="工单detail-编辑")
	//@RequiresPermissions("org.jeecg.modules:itdm_gongdan_detail:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ItdmGongdanDetail itdmGongdanDetail) {
		itdmGongdanDetailService.updateById(itdmGongdanDetail);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "工单detail-通过id删除")
	@ApiOperation(value="工单detail-通过id删除", notes="工单detail-通过id删除")
	//@RequiresPermissions("org.jeecg.modules:itdm_gongdan_detail:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		itdmGongdanDetailService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "工单detail-批量删除")
	@ApiOperation(value="工单detail-批量删除", notes="工单detail-批量删除")
	//@RequiresPermissions("org.jeecg.modules:itdm_gongdan_detail:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.itdmGongdanDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "工单detail-通过id查询")
	@ApiOperation(value="工单detail-通过id查询", notes="工单detail-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ItdmGongdanDetail> queryById(@RequestParam(name="id",required=true) String id) {
		ItdmGongdanDetail itdmGongdanDetail = itdmGongdanDetailService.getById(id);
		if(itdmGongdanDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(itdmGongdanDetail);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param itdmGongdanDetail
    */
    //@RequiresPermissions("org.jeecg.modules:itdm_gongdan_detail:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ItdmGongdanDetail itdmGongdanDetail) {
        return super.exportXls(request, itdmGongdanDetail, ItdmGongdanDetail.class, "工单detail");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("itdm_gongdan_detail:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ItdmGongdanDetail.class);
    }

}
