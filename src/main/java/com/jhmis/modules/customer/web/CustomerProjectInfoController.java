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
import com.jhmis.modules.customer.entity.CustomerProjectInfo;
import com.jhmis.modules.customer.service.CustomerProjectInfoService;

/**
 * 客单漏斗项目Controller
 * @author hdx
 * @version 2019-04-16
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerProjectInfo")
public class CustomerProjectInfoController extends BaseController {

	@Autowired
	private CustomerProjectInfoService customerProjectInfoService;
	
	@ModelAttribute
	public CustomerProjectInfo get(@RequestParam(required=false) String id) {
		CustomerProjectInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerProjectInfoService.get(id);
		}
		if (entity == null){
			entity = new CustomerProjectInfo();
		}
		return entity;
	}
	
	/**
	 * 客单漏斗项目列表页面
	 */
	@RequiresPermissions("customer:customerProjectInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/customerProjectInfoList";
	}
	
	/**
	 * 客单漏斗项目列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerProjectInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CustomerProjectInfo customerProjectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerProjectInfo> page = customerProjectInfoService.findPage(new Page<CustomerProjectInfo>(request, response), customerProjectInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客单漏斗项目表单页面
	 */
	@RequiresPermissions(value={"customer:customerProjectInfo:view","customer:customerProjectInfo:add","customer:customerProjectInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CustomerProjectInfo customerProjectInfo, Model model) {
		model.addAttribute("customerProjectInfo", customerProjectInfo);
		if(StringUtils.isBlank(customerProjectInfo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/customerProjectInfoForm";
	}

	/**
	 * 保存客单漏斗项目
	 */
	@RequiresPermissions(value={"customer:customerProjectInfo:add","customer:customerProjectInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CustomerProjectInfo customerProjectInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, customerProjectInfo)){
			return form(customerProjectInfo, model);
		}
		//新增或编辑表单保存
		customerProjectInfoService.save(customerProjectInfo);//保存
		addMessage(redirectAttributes, "保存客单漏斗项目成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerProjectInfo/?repage";
	}
	
	/**
	 * 删除客单漏斗项目
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerProjectInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CustomerProjectInfo customerProjectInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		customerProjectInfoService.delete(customerProjectInfo);
		j.setMsg("删除客单漏斗项目成功");
		return j;
	}
	
	/**
	 * 批量删除客单漏斗项目
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerProjectInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerProjectInfoService.delete(customerProjectInfoService.get(id));
		}
		j.setMsg("删除客单漏斗项目成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerProjectInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CustomerProjectInfo customerProjectInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客单漏斗项目"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CustomerProjectInfo> page = customerProjectInfoService.findPage(new Page<CustomerProjectInfo>(request, response, -1), customerProjectInfo);
    		new ExportExcel("客单漏斗项目", CustomerProjectInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客单漏斗项目记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:customerProjectInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CustomerProjectInfo> list = ei.getDataList(CustomerProjectInfo.class);
			for (CustomerProjectInfo customerProjectInfo : list){
				try{
					customerProjectInfoService.save(customerProjectInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客单漏斗项目记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条客单漏斗项目记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入客单漏斗项目失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerProjectInfo/?repage";
    }
	
	/**
	 * 下载导入客单漏斗项目数据模板
	 */
	@RequiresPermissions("customer:customerProjectInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客单漏斗项目数据导入模板.xlsx";
    		List<CustomerProjectInfo> list = Lists.newArrayList(); 
    		new ExportExcel("客单漏斗项目数据", CustomerProjectInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerProjectInfo/?repage";
    }

}