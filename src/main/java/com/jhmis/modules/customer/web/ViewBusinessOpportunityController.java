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
import com.jhmis.modules.customer.entity.ViewBusinessOpportunity;
import com.jhmis.modules.customer.service.ViewBusinessOpportunityService;

/**
 * 项目履约视图Controller
 * @author h'd'x
 * @version 2020-02-13
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/viewBusinessOpportunity")
public class ViewBusinessOpportunityController extends BaseController {

	@Autowired
	private ViewBusinessOpportunityService viewBusinessOpportunityService;
	
	@ModelAttribute
	public ViewBusinessOpportunity get(@RequestParam(required=false) String id) {
		ViewBusinessOpportunity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = viewBusinessOpportunityService.get(id);
		}
		if (entity == null){
			entity = new ViewBusinessOpportunity();
		}
		return entity;
	}
	
	/**
	 * 项目履约列表页面
	 */
	@RequiresPermissions("customer:viewBusinessOpportunity:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/viewBusinessOpportunityList";
	}
	
	/**
	 * 项目履约列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewBusinessOpportunity:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ViewBusinessOpportunity viewBusinessOpportunity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ViewBusinessOpportunity> page = viewBusinessOpportunityService.findPage(new Page<ViewBusinessOpportunity>(request, response), viewBusinessOpportunity); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑项目履约表单页面
	 */
	@RequiresPermissions(value={"customer:viewBusinessOpportunity:view","customer:viewBusinessOpportunity:add","customer:viewBusinessOpportunity:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ViewBusinessOpportunity viewBusinessOpportunity, Model model) {
		model.addAttribute("viewBusinessOpportunity", viewBusinessOpportunity);
		if(StringUtils.isBlank(viewBusinessOpportunity.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/viewBusinessOpportunityForm";
	}

	/**
	 * 保存项目履约
	 */
	@RequiresPermissions(value={"customer:viewBusinessOpportunity:add","customer:viewBusinessOpportunity:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ViewBusinessOpportunity viewBusinessOpportunity, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, viewBusinessOpportunity)){
			return form(viewBusinessOpportunity, model);
		}
		//新增或编辑表单保存
		viewBusinessOpportunityService.save(viewBusinessOpportunity);//保存
		addMessage(redirectAttributes, "保存项目履约成功");
		return "redirect:"+Global.getAdminPath()+"/customer/viewBusinessOpportunity/?repage";
	}
	
	/**
	 * 删除项目履约
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewBusinessOpportunity:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ViewBusinessOpportunity viewBusinessOpportunity, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		viewBusinessOpportunityService.delete(viewBusinessOpportunity);
		j.setMsg("删除项目履约成功");
		return j;
	}
	
	/**
	 * 批量删除项目履约
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewBusinessOpportunity:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			viewBusinessOpportunityService.delete(viewBusinessOpportunityService.get(id));
		}
		j.setMsg("删除项目履约成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewBusinessOpportunity:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ViewBusinessOpportunity viewBusinessOpportunity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "项目履约"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ViewBusinessOpportunity> page = viewBusinessOpportunityService.findPage(new Page<ViewBusinessOpportunity>(request, response, -1), viewBusinessOpportunity);
    		new ExportExcel("项目履约", ViewBusinessOpportunity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出项目履约记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:viewBusinessOpportunity:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ViewBusinessOpportunity> list = ei.getDataList(ViewBusinessOpportunity.class);
			for (ViewBusinessOpportunity viewBusinessOpportunity : list){
				try{
					viewBusinessOpportunityService.save(viewBusinessOpportunity);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条项目履约记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条项目履约记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入项目履约失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/viewBusinessOpportunity/?repage";
    }
	
	/**
	 * 下载导入项目履约数据模板
	 */
	@RequiresPermissions("customer:viewBusinessOpportunity:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目履约数据导入模板.xlsx";
    		List<ViewBusinessOpportunity> list = Lists.newArrayList(); 
    		new ExportExcel("项目履约数据", ViewBusinessOpportunity.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/viewBusinessOpportunity/?repage";
    }

}