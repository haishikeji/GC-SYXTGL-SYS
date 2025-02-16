package org.jeecg.modules.itdmPeizhi.controller;

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
import org.jeecg.modules.itdmPeizhi.entity.ItdmPeizhi;
import org.jeecg.modules.itdmPeizhi.service.IItdmPeizhiService;

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
 * @Description: 系统配置
 * @@author 品讯科技
 * @Date:   2023-05-18
 * @Version: V1.0
 */
@Api(tags="系统配置")
@RestController
@RequestMapping("/itdmPeizhi/itdmPeizhi")
@Slf4j
public class ItdmPeizhiController extends JeecgController<ItdmPeizhi, IItdmPeizhiService> {
	@Autowired
	private IItdmPeizhiService itdmPeizhiService;
	
	/**
	 * 分页列表查询
	 *
	 * @param itdmPeizhi
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "系统配置-分页列表查询")
	@ApiOperation(value="系统配置-分页列表查询", notes="系统配置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ItdmPeizhi>> queryPageList(ItdmPeizhi itdmPeizhi,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ItdmPeizhi> queryWrapper = QueryGenerator.initQueryWrapper(itdmPeizhi, req.getParameterMap());
		Page<ItdmPeizhi> page = new Page<ItdmPeizhi>(pageNo, pageSize);
		IPage<ItdmPeizhi> pageList = itdmPeizhiService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param itdmPeizhi
	 * @return
	 */
	@AutoLog(value = "系统配置-添加")
	@ApiOperation(value="系统配置-添加", notes="系统配置-添加")
	//@RequiresPermissions("org.jeecg.modules:itdm_peizhi:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ItdmPeizhi itdmPeizhi) {
		itdmPeizhiService.save(itdmPeizhi);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param itdmPeizhi
	 * @return
	 */
	@AutoLog(value = "系统配置-编辑")
	@ApiOperation(value="系统配置-编辑", notes="系统配置-编辑")
	//@RequiresPermissions("org.jeecg.modules:itdm_peizhi:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ItdmPeizhi itdmPeizhi) {
		itdmPeizhiService.updateById(itdmPeizhi);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "系统配置-通过id删除")
	@ApiOperation(value="系统配置-通过id删除", notes="系统配置-通过id删除")
	//@RequiresPermissions("org.jeecg.modules:itdm_peizhi:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		itdmPeizhiService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "系统配置-批量删除")
	@ApiOperation(value="系统配置-批量删除", notes="系统配置-批量删除")
	//@RequiresPermissions("org.jeecg.modules:itdm_peizhi:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.itdmPeizhiService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "系统配置-通过id查询")
	@ApiOperation(value="系统配置-通过id查询", notes="系统配置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ItdmPeizhi> queryById(@RequestParam(name="id",required=true) String id) {
		ItdmPeizhi itdmPeizhi = itdmPeizhiService.getById(id);
		if(itdmPeizhi==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(itdmPeizhi);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param itdmPeizhi
    */
    //@RequiresPermissions("org.jeecg.modules:itdm_peizhi:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ItdmPeizhi itdmPeizhi) {
        return super.exportXls(request, itdmPeizhi, ItdmPeizhi.class, "系统配置");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("itdm_peizhi:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ItdmPeizhi.class);
    }

}
