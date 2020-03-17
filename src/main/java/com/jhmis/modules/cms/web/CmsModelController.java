/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.CmsModel;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.CmsModelService;
import com.jhmis.modules.cms.service.TemplateService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

/**
 * 模型管理Controller
 * @author lydia
 * @version 2019-08-29
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsModel")
public class CmsModelController extends BaseController {

	@Autowired
	private CmsModelService cmsModelService;
	@Autowired
	private TemplateService templateService;
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public CmsModel get(@RequestParam(required=false) String id) {
		CmsModel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsModelService.get(id);
		}
		if (entity == null){
			entity = new CmsModel();
		}
		return entity;
	}
	
	/**
	 * 模型管理列表页面
	 */
	@RequiresPermissions("cms:cmsModel:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/cmsModelList";
	}
	
	/**
	 * 模型管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsModel:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsModel cmsModel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsModel> page = cmsModelService.findPage(new Page<CmsModel>(request, response), cmsModel); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑模型管理表单页面
	 */
	@RequiresPermissions(value={"cms:cmsModel:view","cms:cmsModel:add","cms:cmsModel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsModel cmsModel, Model model) {
		model.addAttribute("cmsModel", cmsModel);
		Map<String,List<String>> mapList = templateService.getTemplate(templatePath);
		mapList.forEach((key,value)->{
			model.addAttribute(key,value);
		});
		return "modules/cms/cmsModelForm";
	}

	/**
	 * 保存模型管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"cms:cmsModel:add","cms:cmsModel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CmsModel cmsModel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, cmsModel)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		cmsModelService.save(cmsModel);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存模型管理成功");
		return j;
	}

	/**
	 * 判断数据库表名是否唯一
	 * @return
	 */
	public AjaxJson checkTableName(String tableName,String oldTableName){
		AjaxJson j = new AjaxJson();
		if(StringUtils.isEmpty(tableName)){
			j.setSuccess(false);
			j.setMsg("非法参数");
			return j;
		}
		if(StringUtils.isNotEmpty(oldTableName) && oldTableName.equals(tableName)){
			j.setSuccess(true);
			return j;
		}else{
			CmsModel cmsModel = cmsModelService.findUniqueByProperty("table_name",tableName);
			if(cmsModel != null){
				j.setSuccess(false);
				j.setMsg("表名重复");
				return j ;
			}
			return j;
		}

	}

	/**
	 * 安装模块
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsModel:install")
	@RequestMapping(value = "install")
	public AjaxJson install(CmsModel cmsModel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsModel.setIsInstall(Global.YES);
		cmsModelService.save(cmsModel);
		j.setMsg("删除模型管理成功");
		return j;
	}
	
	/**
	 * 卸载模块
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsModel:unInstall")
	@RequestMapping(value = "unInstall")
	public AjaxJson unInstall(CmsModel cmsModel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsModelService.delete(cmsModel);
		j.setMsg("删除模型管理成功");
		return j;
	}
	
	/**
	 * 批量删除模型管理
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsModel:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		StringBuffer successBuffer = new StringBuffer("删除模型");
		StringBuffer failBuffer = new StringBuffer();
		int successNum = 0;
		int failNum = 0;
		for(int i = 0;i<idArray.length;i++){
			//判断该模型是否已使用
			Category category = new Category();
			category.setModelId(idArray[i]);
			List<Category> categoryList = categoryService.findList(category);
			CmsModel cmsModel = cmsModelService.findUniqueByProperty("id",idArray[i]);
			if(categoryList.size()==0){
				if(successNum > 0){
					successBuffer.append(",");
				}
				cmsModelService.delete(cmsModelService.get(idArray[i]));
				successNum++;
				successBuffer.append(cmsModel.getName());
			}else{
				if(failNum >0){
					failBuffer.append(",");
				}
				failBuffer.append(cmsModel.getName());
			}
			if(successNum > 0){
				successBuffer.append("成功！");
			}
			if(failNum > 0){
				j.setSuccess(false);
				failBuffer.append("失败！");
			}

		}
		j.setMsg(successBuffer.toString()+failBuffer.toString());
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsModel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CmsModel cmsModel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "模型管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsModel> page = cmsModelService.findPage(new Page<CmsModel>(request, response, -1), cmsModel);
    		new ExportExcel("模型管理", CmsModel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出模型管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:cmsModel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsModel> list = ei.getDataList(CmsModel.class);
			for (CmsModel cmsModel : list){
				try{
					cmsModelService.save(cmsModel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条模型管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条模型管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模型管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsModel/?repage";
    }
	
	/**
	 * 下载导入模型管理数据模板
	 */
	@RequiresPermissions("cms:cmsModel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "模型管理数据导入模板.xlsx";
    		List<CmsModel> list = Lists.newArrayList(); 
    		new ExportExcel("模型管理数据", CmsModel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsModel/?repage";
    }

}