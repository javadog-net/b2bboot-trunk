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
import com.jhmis.modules.cms.entity.CmsConfigExt;
import com.jhmis.modules.cms.service.CmsConfigExtService;

/**
 * 内容管理-水印功能Controller
 * @author lydia
 * @version 2019-09-02
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsConfigExt")
public class CmsConfigExtController extends BaseController {

	@Autowired
	private CmsConfigExtService cmsConfigExtService;
	
	@ModelAttribute
	public CmsConfigExt get(@RequestParam(required=false) String id) {
		CmsConfigExt entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsConfigExtService.get(id);
		}
		if (entity == null){
			entity = new CmsConfigExt();
		}
		return entity;
	}
	
	/**
	 * 内容管理-水印功能列表页面
	 */
	@RequiresPermissions("cms:cmsConfigExt:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/cmsConfigExtList";
	}
	
	/**
	 * 内容管理-水印功能列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsConfigExt:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsConfigExt cmsConfigExt, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsConfigExt> page = cmsConfigExtService.findPage(new Page<CmsConfigExt>(request, response), cmsConfigExt); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑内容管理-水印功能表单页面
	 */
	@RequiresPermissions(value={"cms:cmsConfigExt:view","cms:cmsConfigExt:add","cms:cmsConfigExt:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsConfigExt cmsConfigExt, Model model) {
		model.addAttribute("cmsConfigExt", cmsConfigExt);
		if(StringUtils.isBlank(cmsConfigExt.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/cms/cmsConfigExtForm";
	}

	/**
	 * 保存内容管理-水印功能
	 */
	@RequiresPermissions(value={"cms:cmsConfigExt:add","cms:cmsConfigExt:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsConfigExt cmsConfigExt, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cmsConfigExt)){
			return form(cmsConfigExt, model);
		}
		//新增或编辑表单保存
		cmsConfigExtService.save(cmsConfigExt);//保存
		addMessage(redirectAttributes, "保存内容管理-水印功能成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsConfigExt/?repage";
	}
	
	/**
	 * 删除内容管理-水印功能
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsConfigExt:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CmsConfigExt cmsConfigExt, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsConfigExtService.delete(cmsConfigExt);
		j.setMsg("删除内容管理-水印功能成功");
		return j;
	}
	
	/**
	 * 批量删除内容管理-水印功能
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsConfigExt:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cmsConfigExtService.delete(cmsConfigExtService.get(id));
		}
		j.setMsg("删除内容管理-水印功能成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsConfigExt:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CmsConfigExt cmsConfigExt, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "内容管理-水印功能"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsConfigExt> page = cmsConfigExtService.findPage(new Page<CmsConfigExt>(request, response, -1), cmsConfigExt);
    		new ExportExcel("内容管理-水印功能", CmsConfigExt.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出内容管理-水印功能记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:cmsConfigExt:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsConfigExt> list = ei.getDataList(CmsConfigExt.class);
			for (CmsConfigExt cmsConfigExt : list){
				try{
					cmsConfigExtService.save(cmsConfigExt);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条内容管理-水印功能记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条内容管理-水印功能记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入内容管理-水印功能失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsConfigExt/?repage";
    }
	
	/**
	 * 下载导入内容管理-水印功能数据模板
	 */
	@RequiresPermissions("cms:cmsConfigExt:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "内容管理-水印功能数据导入模板.xlsx";
    		List<CmsConfigExt> list = Lists.newArrayList(); 
    		new ExportExcel("内容管理-水印功能数据", CmsConfigExt.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsConfigExt/?repage";
    }

}