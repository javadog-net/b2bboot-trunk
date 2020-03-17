/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.modules.customer.entity.ViewQygBrown;
import com.jhmis.modules.customer.service.ViewQygBrownService;

/**
 * 工程版信息视图Controller
 * @author hdx
 * @version 2019-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/viewQygBrown")
public class ViewQygBrownController extends BaseController {

	@Autowired
	private ViewQygBrownService viewQygBrownService;
	
	@ModelAttribute
	public ViewQygBrown get(@RequestParam(required=false) String id) {
		ViewQygBrown entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = viewQygBrownService.get(id);
		}
		if (entity == null){
			entity = new ViewQygBrown();
		}
		return entity;
	}
	
	/**
	 * 工程版信息视图列表页面
	 */
	@RequiresPermissions("customer:viewQygBrown:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/viewQygBrownList";
	}
	
	/**
	 * 工程版信息视图列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrown:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ViewQygBrown viewQygBrown, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ViewQygBrown> page = viewQygBrownService.findPage(new Page<ViewQygBrown>(request, response), viewQygBrown); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工程版信息视图表单页面
	 */
	@RequiresPermissions(value={"customer:viewQygBrown:view","customer:viewQygBrown:add","customer:viewQygBrown:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ViewQygBrown viewQygBrown, Model model) {
		model.addAttribute("viewQygBrown", viewQygBrown);
		if(StringUtils.isBlank(viewQygBrown.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/viewQygBrownForm";
	}

	/**
	 * 保存工程版信息视图
	 */
	@RequiresPermissions(value={"customer:viewQygBrown:add","customer:viewQygBrown:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ViewQygBrown viewQygBrown, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, viewQygBrown)){
			return form(viewQygBrown, model);
		}
		//新增或编辑表单保存
		viewQygBrownService.save(viewQygBrown);//保存
		addMessage(redirectAttributes, "保存工程版信息视图成功");
		return "redirect:"+Global.getAdminPath()+"/customer/viewQygBrown/?repage";
	}
	
	/**
	 * 删除工程版信息视图
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrown:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ViewQygBrown viewQygBrown, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		viewQygBrownService.delete(viewQygBrown);
		j.setMsg("删除工程版信息视图成功");
		return j;
	}
	
	/**
	 * 批量删除工程版信息视图
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrown:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			viewQygBrownService.delete(viewQygBrownService.get(id));
		}
		j.setMsg("删除工程版信息视图成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrown:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ViewQygBrown viewQygBrown, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工程版信息视图"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ViewQygBrown> page = viewQygBrownService.findPage(new Page<ViewQygBrown>(request, response, -1), viewQygBrown);
    		new ExportExcel("工程版信息视图", ViewQygBrown.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工程版信息视图记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:viewQygBrown:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ViewQygBrown> list = ei.getDataList(ViewQygBrown.class);
			for (ViewQygBrown viewQygBrown : list){
				try{
					viewQygBrownService.save(viewQygBrown);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工程版信息视图记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条工程版信息视图记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入工程版信息视图失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/viewQygBrown/?repage";
    }
	
	/**
	 * 下载导入工程版信息视图数据模板
	 */
	@RequiresPermissions("customer:viewQygBrown:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "工程版信息视图数据导入模板.xlsx";
    		List<ViewQygBrown> list = Lists.newArrayList(); 
    		new ExportExcel("工程版信息视图数据", ViewQygBrown.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/viewQygBrown/?repage";
    }

}