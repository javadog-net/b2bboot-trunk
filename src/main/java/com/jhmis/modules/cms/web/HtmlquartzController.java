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
import com.jhmis.modules.cms.entity.Htmlquartz;
import com.jhmis.modules.cms.service.HtmlquartzService;

/**
 * 静态化调度Controller
 * @author lydia
 * @version 2019-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/htmlquartz")
public class HtmlquartzController extends BaseController {

	@Autowired
	private HtmlquartzService htmlquartzService;
	
	@ModelAttribute
	public Htmlquartz get(@RequestParam(required=false) String id) {
		Htmlquartz entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = htmlquartzService.get(id);
		}
		if (entity == null){
			entity = new Htmlquartz();
		}
		return entity;
	}
	
	/**
	 * 静态化调度列表页面
	 */
	@RequiresPermissions("cms:htmlquartz:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/htmlquartzList";
	}
	
	/**
	 * 静态化调度列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:htmlquartz:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Htmlquartz htmlquartz, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Htmlquartz> page = htmlquartzService.findPage(new Page<Htmlquartz>(request, response), htmlquartz); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑静态化调度表单页面
	 */
	@RequiresPermissions(value={"cms:htmlquartz:view","cms:htmlquartz:add","cms:htmlquartz:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Htmlquartz htmlquartz, Model model) {
		model.addAttribute("htmlquartz", htmlquartz);
		if(StringUtils.isBlank(htmlquartz.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/cms/htmlquartzForm";
	}

	/**
	 * 保存静态化调度
	 */
	@RequiresPermissions(value={"cms:htmlquartz:add","cms:htmlquartz:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Htmlquartz htmlquartz, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, htmlquartz)){
			return form(htmlquartz, model);
		}
		//新增或编辑表单保存
		htmlquartzService.save(htmlquartz);//保存
		addMessage(redirectAttributes, "保存静态化调度成功");
		return "redirect:"+Global.getAdminPath()+"/cms/htmlquartz/?repage";
	}
	
	/**
	 * 删除静态化调度
	 */
	@ResponseBody
	@RequiresPermissions("cms:htmlquartz:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Htmlquartz htmlquartz, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		htmlquartzService.delete(htmlquartz);
		j.setMsg("删除静态化调度成功");
		return j;
	}
	
	/**
	 * 批量删除静态化调度
	 */
	@ResponseBody
	@RequiresPermissions("cms:htmlquartz:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			htmlquartzService.delete(htmlquartzService.get(id));
		}
		j.setMsg("删除静态化调度成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:htmlquartz:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Htmlquartz htmlquartz, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "静态化调度"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Htmlquartz> page = htmlquartzService.findPage(new Page<Htmlquartz>(request, response, -1), htmlquartz);
    		new ExportExcel("静态化调度", Htmlquartz.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出静态化调度记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:htmlquartz:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Htmlquartz> list = ei.getDataList(Htmlquartz.class);
			for (Htmlquartz htmlquartz : list){
				try{
					htmlquartzService.save(htmlquartz);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条静态化调度记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条静态化调度记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入静态化调度失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/htmlquartz/?repage";
    }
	
	/**
	 * 下载导入静态化调度数据模板
	 */
	@RequiresPermissions("cms:htmlquartz:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "静态化调度数据导入模板.xlsx";
    		List<Htmlquartz> list = Lists.newArrayList(); 
    		new ExportExcel("静态化调度数据", Htmlquartz.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/htmlquartz/?repage";
    }

}