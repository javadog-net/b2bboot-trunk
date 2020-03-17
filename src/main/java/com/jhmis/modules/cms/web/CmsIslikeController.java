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
import com.jhmis.modules.cms.entity.CmsIslike;
import com.jhmis.modules.cms.service.CmsIslikeService;

/**
 * info信息  是否喜欢 Controller
 * @author tc
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsIslike")
public class CmsIslikeController extends BaseController {

	@Autowired
	private CmsIslikeService cmsIslikeService;
	
	@ModelAttribute
	public CmsIslike get(@RequestParam(required=false) String id) {
		CmsIslike entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsIslikeService.get(id);
		}
		if (entity == null){
			entity = new CmsIslike();
		}
		return entity;
	}
	
	/**
	 * info信息  是否喜欢 列表页面
	 */
	@RequiresPermissions("cms:cmsIslike:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/cmsIslikeList";
	}
	
	/**
	 * info信息  是否喜欢 列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsIslike:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsIslike cmsIslike, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsIslike> page = cmsIslikeService.findPage(new Page<CmsIslike>(request, response), cmsIslike); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑info信息  是否喜欢 表单页面
	 */
	@RequiresPermissions(value={"cms:cmsIslike:view","cms:cmsIslike:add","cms:cmsIslike:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsIslike cmsIslike, Model model) {
		model.addAttribute("cmsIslike", cmsIslike);
		if(StringUtils.isBlank(cmsIslike.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/cms/cmsIslikeForm";
	}

	/**
	 * 保存info信息  是否喜欢 
	 */
	@RequiresPermissions(value={"cms:cmsIslike:add","cms:cmsIslike:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsIslike cmsIslike, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cmsIslike)){
			return form(cmsIslike, model);
		}
		//新增或编辑表单保存
		cmsIslikeService.save(cmsIslike);//保存
		addMessage(redirectAttributes, "保存info信息  是否喜欢 成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsIslike/?repage";
	}
	
	/**
	 * 删除info信息  是否喜欢 
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsIslike:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CmsIslike cmsIslike, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsIslikeService.delete(cmsIslike);
		j.setMsg("删除info信息  是否喜欢 成功");
		return j;
	}
	
	/**
	 * 批量删除info信息  是否喜欢 
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsIslike:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cmsIslikeService.delete(cmsIslikeService.get(id));
		}
		j.setMsg("删除info信息  是否喜欢 成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsIslike:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CmsIslike cmsIslike, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "info信息  是否喜欢 "+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsIslike> page = cmsIslikeService.findPage(new Page<CmsIslike>(request, response, -1), cmsIslike);
    		new ExportExcel("info信息  是否喜欢 ", CmsIslike.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出info信息  是否喜欢 记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:cmsIslike:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsIslike> list = ei.getDataList(CmsIslike.class);
			for (CmsIslike cmsIslike : list){
				try{
					cmsIslikeService.save(cmsIslike);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条info信息  是否喜欢 记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条info信息  是否喜欢 记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入info信息  是否喜欢 失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsIslike/?repage";
    }
	
	/**
	 * 下载导入info信息  是否喜欢 数据模板
	 */
	@RequiresPermissions("cms:cmsIslike:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "info信息  是否喜欢 数据导入模板.xlsx";
    		List<CmsIslike> list = Lists.newArrayList(); 
    		new ExportExcel("info信息  是否喜欢 数据", CmsIslike.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsIslike/?repage";
    }

}