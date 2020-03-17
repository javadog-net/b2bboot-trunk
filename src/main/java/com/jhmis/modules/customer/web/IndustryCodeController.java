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
import com.jhmis.modules.customer.entity.IndustryCode;
import com.jhmis.modules.customer.service.IndustryCodeService;

/**
 * 行业类别Controller
 * @author hdx
 * @version 2019-04-27
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/industryCode")
public class IndustryCodeController extends BaseController {

	@Autowired
	private IndustryCodeService industryCodeService;
	
	@ModelAttribute
	public IndustryCode get(@RequestParam(required=false) String id) {
		IndustryCode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = industryCodeService.get(id);
		}
		if (entity == null){
			entity = new IndustryCode();
		}
		return entity;
	}
	
	/**
	 * 行业类别列表页面
	 */
	@RequiresPermissions("customer:industryCode:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/industryCodeList";
	}
	
	/**
	 * 行业类别列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:industryCode:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(IndustryCode industryCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IndustryCode> page = industryCodeService.findPage(new Page<IndustryCode>(request, response), industryCode); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑行业类别表单页面
	 */
	@RequiresPermissions(value={"customer:industryCode:view","customer:industryCode:add","customer:industryCode:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(IndustryCode industryCode, Model model) {
		model.addAttribute("industryCode", industryCode);
		if(StringUtils.isBlank(industryCode.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/industryCodeForm";
	}

	/**
	 * 保存行业类别
	 */
	@RequiresPermissions(value={"customer:industryCode:add","customer:industryCode:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(IndustryCode industryCode, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, industryCode)){
			return form(industryCode, model);
		}
		//新增或编辑表单保存
		industryCodeService.save(industryCode);//保存
		addMessage(redirectAttributes, "保存行业类别成功");
		return "redirect:"+Global.getAdminPath()+"/customer/industryCode/?repage";
	}
	
	/**
	 * 删除行业类别
	 */
	@ResponseBody
	@RequiresPermissions("customer:industryCode:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(IndustryCode industryCode, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		industryCodeService.delete(industryCode);
		j.setMsg("删除行业类别成功");
		return j;
	}
	
	/**
	 * 批量删除行业类别
	 */
	@ResponseBody
	@RequiresPermissions("customer:industryCode:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			industryCodeService.delete(industryCodeService.get(id));
		}
		j.setMsg("删除行业类别成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:industryCode:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(IndustryCode industryCode, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "行业类别"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<IndustryCode> page = industryCodeService.findPage(new Page<IndustryCode>(request, response, -1), industryCode);
    		new ExportExcel("行业类别", IndustryCode.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出行业类别记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:industryCode:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<IndustryCode> list = ei.getDataList(IndustryCode.class);
			for (IndustryCode industryCode : list){
				try{
					industryCodeService.save(industryCode);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条行业类别记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条行业类别记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入行业类别失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/industryCode/?repage";
    }
	
	/**
	 * 下载导入行业类别数据模板
	 */
	@RequiresPermissions("customer:industryCode:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "行业类别数据导入模板.xlsx";
    		List<IndustryCode> list = Lists.newArrayList(); 
    		new ExportExcel("行业类别数据", IndustryCode.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/industryCode/?repage";
    }

}