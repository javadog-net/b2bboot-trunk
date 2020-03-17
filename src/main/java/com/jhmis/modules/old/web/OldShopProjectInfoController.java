/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.web;

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
import com.jhmis.modules.old.entity.OldShopProjectInfo;
import com.jhmis.modules.old.service.OldShopProjectInfoService;

/**
 * projectController
 * @author hdx
 * @version 2019-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/old/oldShopProjectInfo")
public class OldShopProjectInfoController extends BaseController {

	@Autowired
	private OldShopProjectInfoService oldShopProjectInfoService;
	
	@ModelAttribute
	public OldShopProjectInfo get(@RequestParam(required=false) String id) {
		OldShopProjectInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oldShopProjectInfoService.get(id);
		}
		if (entity == null){
			entity = new OldShopProjectInfo();
		}
		return entity;
	}
	
	/**
	 * project列表页面
	 */
	@RequiresPermissions("old:oldShopProjectInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/old/oldShopProjectInfoList";
	}
	
	/**
	 * project列表数据
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OldShopProjectInfo oldShopProjectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OldShopProjectInfo> page = oldShopProjectInfoService.findPage(new Page<OldShopProjectInfo>(request, response), oldShopProjectInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑project表单页面
	 */
	@RequiresPermissions(value={"old:oldShopProjectInfo:view","old:oldShopProjectInfo:add","old:oldShopProjectInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OldShopProjectInfo oldShopProjectInfo, Model model) {
		model.addAttribute("oldShopProjectInfo", oldShopProjectInfo);
		if(StringUtils.isBlank(oldShopProjectInfo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/old/oldShopProjectInfoForm";
	}

	/**
	 * 保存project
	 */
	@RequiresPermissions(value={"old:oldShopProjectInfo:add","old:oldShopProjectInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OldShopProjectInfo oldShopProjectInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, oldShopProjectInfo)){
			return form(oldShopProjectInfo, model);
		}
		//新增或编辑表单保存
		oldShopProjectInfoService.save(oldShopProjectInfo);//保存
		addMessage(redirectAttributes, "保存project成功");
		return "redirect:"+Global.getAdminPath()+"/old/oldShopProjectInfo/?repage";
	}
	
	/**
	 * 删除project
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OldShopProjectInfo oldShopProjectInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oldShopProjectInfoService.delete(oldShopProjectInfo);
		j.setMsg("删除project成功");
		return j;
	}
	
	/**
	 * 批量删除project
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oldShopProjectInfoService.delete(oldShopProjectInfoService.get(id));
		}
		j.setMsg("删除project成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OldShopProjectInfo oldShopProjectInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "project"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OldShopProjectInfo> page = oldShopProjectInfoService.findPage(new Page<OldShopProjectInfo>(request, response, -1), oldShopProjectInfo);
    		new ExportExcel("project", OldShopProjectInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出project记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("old:oldShopProjectInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OldShopProjectInfo> list = ei.getDataList(OldShopProjectInfo.class);
			for (OldShopProjectInfo oldShopProjectInfo : list){
				try{
					oldShopProjectInfoService.save(oldShopProjectInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条project记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条project记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入project失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/old/oldShopProjectInfo/?repage";
    }
	
	/**
	 * 下载导入project数据模板
	 */
	@RequiresPermissions("old:oldShopProjectInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "project数据导入模板.xlsx";
    		List<OldShopProjectInfo> list = Lists.newArrayList(); 
    		new ExportExcel("project数据", OldShopProjectInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/old/oldShopProjectInfo/?repage";
    }

}