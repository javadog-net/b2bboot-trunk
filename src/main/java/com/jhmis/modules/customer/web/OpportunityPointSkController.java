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
import com.jhmis.modules.customer.entity.OpportunityPointSk;
import com.jhmis.modules.customer.service.OpportunityPointSkService;

/**
 * 商空机会点Controller
 * @author mll
 * @version 2019-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/opportunityPointSk")
public class OpportunityPointSkController extends BaseController {

	@Autowired
	private OpportunityPointSkService opportunityPointSkService;
	
	@ModelAttribute
	public OpportunityPointSk get(@RequestParam(required=false) String id) {
		OpportunityPointSk entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opportunityPointSkService.get(id);
		}
		if (entity == null){
			entity = new OpportunityPointSk();
		}
		return entity;
	}
	
	/**
	 * 商空机会点列表页面
	 */
	@RequiresPermissions("customer:opportunityPointSk:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/opportunityPointSkList";
	}
	
	/**
	 * 商空机会点列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:opportunityPointSk:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OpportunityPointSk opportunityPointSk, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpportunityPointSk> page = opportunityPointSkService.findPage(new Page<OpportunityPointSk>(request, response), opportunityPointSk); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商空机会点表单页面
	 */
	@RequiresPermissions(value={"customer:opportunityPointSk:view","customer:opportunityPointSk:add","customer:opportunityPointSk:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OpportunityPointSk opportunityPointSk, Model model) {
		model.addAttribute("opportunityPointSk", opportunityPointSk);
		if(StringUtils.isBlank(opportunityPointSk.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/opportunityPointSkForm";
	}

	/**
	 * 保存商空机会点
	 */
	@RequiresPermissions(value={"customer:opportunityPointSk:add","customer:opportunityPointSk:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OpportunityPointSk opportunityPointSk, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, opportunityPointSk)){
			return form(opportunityPointSk, model);
		}
		//新增或编辑表单保存
		opportunityPointSkService.save(opportunityPointSk);//保存
		addMessage(redirectAttributes, "保存商空机会点成功");
		return "redirect:"+Global.getAdminPath()+"/customer/opportunityPointSk/?repage";
	}
	
	/**
	 * 删除商空机会点
	 */
	@ResponseBody
	@RequiresPermissions("customer:opportunityPointSk:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OpportunityPointSk opportunityPointSk, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		opportunityPointSkService.delete(opportunityPointSk);
		j.setMsg("删除商空机会点成功");
		return j;
	}
	
	/**
	 * 批量删除商空机会点
	 */
	@ResponseBody
	@RequiresPermissions("customer:opportunityPointSk:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			opportunityPointSkService.delete(opportunityPointSkService.get(id));
		}
		j.setMsg("删除商空机会点成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:opportunityPointSk:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OpportunityPointSk opportunityPointSk, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商空机会点"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OpportunityPointSk> page = opportunityPointSkService.findPage(new Page<OpportunityPointSk>(request, response, -1), opportunityPointSk);
    		new ExportExcel("商空机会点", OpportunityPointSk.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商空机会点记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:opportunityPointSk:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OpportunityPointSk> list = ei.getDataList(OpportunityPointSk.class);
			for (OpportunityPointSk opportunityPointSk : list){
				try{
					opportunityPointSkService.save(opportunityPointSk);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商空机会点记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商空机会点记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商空机会点失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/opportunityPointSk/?repage";
    }
	
	/**
	 * 下载导入商空机会点数据模板
	 */
	@RequiresPermissions("customer:opportunityPointSk:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商空机会点数据导入模板.xlsx";
    		List<OpportunityPointSk> list = Lists.newArrayList(); 
    		new ExportExcel("商空机会点数据", OpportunityPointSk.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/opportunityPointSk/?repage";
    }

}