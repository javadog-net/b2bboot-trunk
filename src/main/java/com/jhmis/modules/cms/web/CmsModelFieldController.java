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
import com.jhmis.modules.cms.entity.CmsModelField;
import com.jhmis.modules.cms.service.CmsModelFieldService;

/**
 * 模块字段管理Controller
 * @author lydia
 * @version 2019-08-30
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsModelField")
public class CmsModelFieldController extends BaseController {

	@Autowired
	private CmsModelFieldService cmsModelFieldService;
	
	@ModelAttribute
	public CmsModelField get(@RequestParam(required=false) String id) {
		CmsModelField entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsModelFieldService.get(id);
		}
		if (entity == null){
			entity = new CmsModelField();
		}
		return entity;
	}
	
	/**
	 * 模块字段管理列表页面
	 */
	@RequiresPermissions("cms:cmsModelField:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/cmsModelFieldList";
	}
	
	/**
	 * 模块字段管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsModelField:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsModelField cmsModelField, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsModelField> page = cmsModelFieldService.findPage(new Page<CmsModelField>(request, response), cmsModelField); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑模块字段管理表单页面
	 */
	@RequiresPermissions(value={"cms:cmsModelField:view","cms:cmsModelField:add","cms:cmsModelField:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsModelField cmsModelField, Model model) {
		model.addAttribute("cmsModelField", cmsModelField);
		return "modules/cms/cmsModelFieldForm";
	}

	/**
	 * 保存模块字段管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"cms:cmsModelField:add","cms:cmsModelField:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CmsModelField cmsModelField, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, cmsModelField)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		cmsModelFieldService.save(cmsModelField);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存模块字段管理成功");
		return j;
	}
	
	/**
	 * 删除模块字段管理
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsModelField:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CmsModelField cmsModelField, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsModelFieldService.delete(cmsModelField);
		j.setMsg("删除模块字段管理成功");
		return j;
	}
	
	/**
	 * 批量删除模块字段管理
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsModelField:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cmsModelFieldService.delete(cmsModelFieldService.get(id));
		}
		j.setMsg("删除模块字段管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsModelField:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CmsModelField cmsModelField, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "模块字段管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsModelField> page = cmsModelFieldService.findPage(new Page<CmsModelField>(request, response, -1), cmsModelField);
    		new ExportExcel("模块字段管理", CmsModelField.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出模块字段管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:cmsModelField:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsModelField> list = ei.getDataList(CmsModelField.class);
			for (CmsModelField cmsModelField : list){
				try{
					cmsModelFieldService.save(cmsModelField);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条模块字段管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条模块字段管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模块字段管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsModelField/?repage";
    }
	
	/**
	 * 下载导入模块字段管理数据模板
	 */
	@RequiresPermissions("cms:cmsModelField:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "模块字段管理数据导入模板.xlsx";
    		List<CmsModelField> list = Lists.newArrayList(); 
    		new ExportExcel("模块字段管理数据", CmsModelField.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsModelField/?repage";
    }

}