package org.jeecg.modules.itdmDataHistory.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.itdmDataHistory.entity.ItdmEnvironmentYewei;
import org.jeecg.modules.itdmDataHistory.service.IItdmEnvironmentYeweiService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 水液位历史数据
 * @@author 品讯科技
 * @Date:   2023-05-30
 * @Version: V1.0
 */
@Api(tags="水液位历史数据")
@RestController
@RequestMapping("/itdmDataHistory/itdmEnvironmentYewei")
@Slf4j
public class ItdmEnvironmentYeweiController extends JeecgController<ItdmEnvironmentYewei, IItdmEnvironmentYeweiService> {
	@Autowired
	private IItdmEnvironmentYeweiService itdmEnvironmentYeweiService;
	
	/**
	 * 分页列表查询
	 *
	 * @param itdmEnvironmentYewei
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "水液位历史数据-分页列表查询")
	@ApiOperation(value="水液位历史数据-分页列表查询", notes="水液位历史数据-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ItdmEnvironmentYewei>> queryPageList(ItdmEnvironmentYewei itdmEnvironmentYewei,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ItdmEnvironmentYewei> queryWrapper = QueryGenerator.initQueryWrapper(itdmEnvironmentYewei, req.getParameterMap());
		Page<ItdmEnvironmentYewei> page = new Page<ItdmEnvironmentYewei>(pageNo, pageSize);
		IPage<ItdmEnvironmentYewei> pageList = itdmEnvironmentYeweiService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param itdmEnvironmentYewei
	 * @return
	 */
	@AutoLog(value = "水液位历史数据-添加")
	@ApiOperation(value="水液位历史数据-添加", notes="水液位历史数据-添加")
	//@RequiresPermissions("org.jeecg.modules:itdm_environment_yewei:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ItdmEnvironmentYewei itdmEnvironmentYewei) {
		itdmEnvironmentYeweiService.save(itdmEnvironmentYewei);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param itdmEnvironmentYewei
	 * @return
	 */
	@AutoLog(value = "水液位历史数据-编辑")
	@ApiOperation(value="水液位历史数据-编辑", notes="水液位历史数据-编辑")
	//@RequiresPermissions("org.jeecg.modules:itdm_environment_yewei:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ItdmEnvironmentYewei itdmEnvironmentYewei) {
		itdmEnvironmentYeweiService.updateById(itdmEnvironmentYewei);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "水液位历史数据-通过id删除")
	@ApiOperation(value="水液位历史数据-通过id删除", notes="水液位历史数据-通过id删除")
	//@RequiresPermissions("org.jeecg.modules:itdm_environment_yewei:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		itdmEnvironmentYeweiService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "水液位历史数据-批量删除")
	@ApiOperation(value="水液位历史数据-批量删除", notes="水液位历史数据-批量删除")
	//@RequiresPermissions("org.jeecg.modules:itdm_environment_yewei:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.itdmEnvironmentYeweiService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "水液位历史数据-通过id查询")
	@ApiOperation(value="水液位历史数据-通过id查询", notes="水液位历史数据-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ItdmEnvironmentYewei> queryById(@RequestParam(name="id",required=true) String id) {
		ItdmEnvironmentYewei itdmEnvironmentYewei = itdmEnvironmentYeweiService.getById(id);
		if(itdmEnvironmentYewei==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(itdmEnvironmentYewei);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param itdmEnvironmentYewei
    */
    //@RequiresPermissions("org.jeecg.modules:itdm_environment_yewei:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ItdmEnvironmentYewei itdmEnvironmentYewei) {
        return super.exportXls(request, itdmEnvironmentYewei, ItdmEnvironmentYewei.class, "水液位历史数据");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("itdm_environment_yewei:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ItdmEnvironmentYewei.class);
    }

}
