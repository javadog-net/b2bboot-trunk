/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;

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
import com.jhmis.modules.cms.entity.Visit;
import com.jhmis.modules.cms.service.VisitService;

/**
 * 访问记录Controller
 * @author lydia
 * @version 2019-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/visit")
public class VisitController extends BaseController {

	@Autowired
	private VisitService visitService;
	
	@ModelAttribute
	public Visit get(@RequestParam(required=false) String id) {
		Visit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = visitService.get(id);
		}
		if (entity == null){
			entity = new Visit();
		}
		return entity;
	}
	
	/**
	 * 访问记录列表页面
	 */
	@RequiresPermissions("cms:visit:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/visitList";
	}
	
	/**
	 * 访问记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:visit:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Visit visit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Visit> page = visitService.findPage(new Page<Visit>(request, response), visit); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑访问记录表单页面
	 */
	@RequiresPermissions(value={"cms:visit:view","cms:visit:add","cms:visit:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Visit visit, Model model) {
		model.addAttribute("visit", visit);
		if(StringUtils.isBlank(visit.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/cms/visitForm";
	}

	/**
	 * 保存访问记录
	 */
	@RequiresPermissions(value={"cms:visit:add","cms:visit:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Visit visit, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, visit)){
			return form(visit, model);
		}
		//新增或编辑表单保存
		visitService.save(visit);//保存
		addMessage(redirectAttributes, "保存访问记录成功");
		return "redirect:"+Global.getAdminPath()+"/cms/visit/?repage";
	}
	
	/**
	 * 删除访问记录
	 */
	@ResponseBody
	@RequiresPermissions("cms:visit:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Visit visit, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		visitService.delete(visit);
		j.setMsg("删除访问记录成功");
		return j;
	}
	
	/**
	 * 批量删除访问记录
	 */
	@ResponseBody
	@RequiresPermissions("cms:visit:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			visitService.delete(visitService.get(id));
		}
		j.setMsg("删除访问记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:visit:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Visit visit, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "访问记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Visit> page = visitService.findPage(new Page<Visit>(request, response, -1), visit);
    		new ExportExcel("访问记录", Visit.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出访问记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:visit:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Visit> list = ei.getDataList(Visit.class);
			for (Visit visit : list){
				try{
					visitService.save(visit);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条访问记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条访问记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入访问记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/visit/?repage";
    }
	
	/**
	 * 下载导入访问记录数据模板
	 */
	@RequiresPermissions("cms:visit:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "访问记录数据导入模板.xlsx";
    		List<Visit> list = Lists.newArrayList(); 
    		new ExportExcel("访问记录数据", Visit.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/visit/?repage";
    }

}