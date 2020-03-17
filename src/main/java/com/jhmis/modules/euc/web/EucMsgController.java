/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.euc.web;

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
import com.jhmis.modules.euc.entity.EucMsg;
import com.jhmis.modules.euc.service.EucMsgService;

/**
 * euc系统相关需求Controller
 * @author hdx
 * @version 2019-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/euc/eucMsg")
public class EucMsgController extends BaseController {

	@Autowired
	private EucMsgService eucMsgService;
	
	@ModelAttribute
	public EucMsg get(@RequestParam(required=false) String id) {
		EucMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = eucMsgService.get(id);
		}
		if (entity == null){
			entity = new EucMsg();
		}
		return entity;
	}
	
	/**
	 * euc系统相关需求列表页面
	 */
	@RequiresPermissions("euc:eucMsg:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/euc/eucMsgList";
	}
	
	/**
	 * euc系统相关需求列表数据
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucMsg:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(EucMsg eucMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EucMsg> page = eucMsgService.findPage(new Page<EucMsg>(request, response), eucMsg); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑euc系统相关需求表单页面
	 */
	@RequiresPermissions(value={"euc:eucMsg:view","euc:eucMsg:add","euc:eucMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(EucMsg eucMsg, Model model) {
		model.addAttribute("eucMsg", eucMsg);
		if(StringUtils.isBlank(eucMsg.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/euc/eucMsgForm";
	}

	/**
	 * 保存euc系统相关需求
	 */
	@RequiresPermissions(value={"euc:eucMsg:add","euc:eucMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(EucMsg eucMsg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, eucMsg)){
			return form(eucMsg, model);
		}
		//新增或编辑表单保存
		eucMsgService.save(eucMsg);//保存
		addMessage(redirectAttributes, "保存euc系统相关需求成功");
		return "redirect:"+Global.getAdminPath()+"/euc/eucMsg/?repage";
	}
	
	/**
	 * 删除euc系统相关需求
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucMsg:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(EucMsg eucMsg, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		eucMsgService.delete(eucMsg);
		j.setMsg("删除euc系统相关需求成功");
		return j;
	}
	
	/**
	 * 批量删除euc系统相关需求
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucMsg:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			eucMsgService.delete(eucMsgService.get(id));
		}
		j.setMsg("删除euc系统相关需求成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucMsg:export")
    @RequestMapping(value = "export", method=RequestMethod.GET)
    public AjaxJson exportFile(EucMsg eucMsg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "euc系统相关需求"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<EucMsg> page = eucMsgService.findPage(new Page<EucMsg>(request, response, -1), eucMsg);
    		new ExportExcel("euc系统相关需求", EucMsg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出euc系统相关需求记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("euc:eucMsg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<EucMsg> list = ei.getDataList(EucMsg.class);
			for (EucMsg eucMsg : list){
				try{
					eucMsgService.save(eucMsg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条euc系统相关需求记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条euc系统相关需求记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入euc系统相关需求失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/euc/eucMsg/?repage";
    }
	
	/**
	 * 下载导入euc系统相关需求数据模板
	 */
	@RequiresPermissions("euc:eucMsg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "euc系统相关需求数据导入模板.xlsx";
    		List<EucMsg> list = Lists.newArrayList(); 
    		new ExportExcel("euc系统相关需求数据", EucMsg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/euc/eucMsg/?repage";
    }

}