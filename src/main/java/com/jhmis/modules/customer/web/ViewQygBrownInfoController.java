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
import com.jhmis.modules.customer.entity.ViewQygBrownInfo;
import com.jhmis.modules.customer.service.ViewQygBrownInfoService;

/**
 * hps视图工程版可验收列表Controller
 * @author hdx
 * @version 2019-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/viewQygBrownInfo")
public class ViewQygBrownInfoController extends BaseController {

	@Autowired
	private ViewQygBrownInfoService viewQygBrownInfoService;
	
	@ModelAttribute
	public ViewQygBrownInfo get(@RequestParam(required=false) String id) {
		ViewQygBrownInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = viewQygBrownInfoService.get(id);
		}
		if (entity == null){
			entity = new ViewQygBrownInfo();
		}
		return entity;
	}
	
	/**
	 * hps视图工程版可验收列表列表页面
	 */
	@RequiresPermissions("customer:viewQygBrownInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/viewQygBrownInfoList";
	}
	
	/**
	 * hps视图工程版可验收列表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrownInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ViewQygBrownInfo viewQygBrownInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ViewQygBrownInfo> page = viewQygBrownInfoService.findPage(new Page<ViewQygBrownInfo>(request, response), viewQygBrownInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑hps视图工程版可验收列表表单页面
	 */
	@RequiresPermissions(value={"customer:viewQygBrownInfo:view","customer:viewQygBrownInfo:add","customer:viewQygBrownInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ViewQygBrownInfo viewQygBrownInfo, Model model) {
		model.addAttribute("viewQygBrownInfo", viewQygBrownInfo);
		if(StringUtils.isBlank(viewQygBrownInfo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/viewQygBrownInfoForm";
	}

	/**
	 * 保存hps视图工程版可验收列表
	 */
	@RequiresPermissions(value={"customer:viewQygBrownInfo:add","customer:viewQygBrownInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ViewQygBrownInfo viewQygBrownInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, viewQygBrownInfo)){
			return form(viewQygBrownInfo, model);
		}
		//新增或编辑表单保存
		viewQygBrownInfoService.save(viewQygBrownInfo);//保存
		addMessage(redirectAttributes, "保存hps视图工程版可验收列表成功");
		return "redirect:"+Global.getAdminPath()+"/customer/viewQygBrownInfo/?repage";
	}
	
	/**
	 * 删除hps视图工程版可验收列表
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrownInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ViewQygBrownInfo viewQygBrownInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		viewQygBrownInfoService.delete(viewQygBrownInfo);
		j.setMsg("删除hps视图工程版可验收列表成功");
		return j;
	}
	
	/**
	 * 批量删除hps视图工程版可验收列表
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrownInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			viewQygBrownInfoService.delete(viewQygBrownInfoService.get(id));
		}
		j.setMsg("删除hps视图工程版可验收列表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrownInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ViewQygBrownInfo viewQygBrownInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "hps视图工程版可验收列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ViewQygBrownInfo> page = viewQygBrownInfoService.findPage(new Page<ViewQygBrownInfo>(request, response, -1), viewQygBrownInfo);
    		new ExportExcel("hps视图工程版可验收列表", ViewQygBrownInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出hps视图工程版可验收列表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:viewQygBrownInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ViewQygBrownInfo> list = ei.getDataList(ViewQygBrownInfo.class);
			for (ViewQygBrownInfo viewQygBrownInfo : list){
				try{
					viewQygBrownInfoService.save(viewQygBrownInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条hps视图工程版可验收列表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条hps视图工程版可验收列表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入hps视图工程版可验收列表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/viewQygBrownInfo/?repage";
    }
	
	/**
	 * 下载导入hps视图工程版可验收列表数据模板
	 */
	@RequiresPermissions("customer:viewQygBrownInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "hps视图工程版可验收列表数据导入模板.xlsx";
    		List<ViewQygBrownInfo> list = Lists.newArrayList(); 
    		new ExportExcel("hps视图工程版可验收列表数据", ViewQygBrownInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/viewQygBrownInfo/?repage";
    }

}