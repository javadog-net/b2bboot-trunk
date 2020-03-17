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
import com.jhmis.modules.customer.entity.HpsMessageReminder;
import com.jhmis.modules.customer.service.HpsMessageReminderService;

/**
 * 消息通知Controller
 * @author hdx
 * @version 2020-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/hpsMessageReminder")
public class HpsMessageReminderController extends BaseController {

	@Autowired
	private HpsMessageReminderService hpsMessageReminderService;
	
	@ModelAttribute
	public HpsMessageReminder get(@RequestParam(required=false) String id) {
		HpsMessageReminder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hpsMessageReminderService.get(id);
		}
		if (entity == null){
			entity = new HpsMessageReminder();
		}
		return entity;
	}
	
	/**
	 * 消息通知列表页面
	 */
	@RequiresPermissions("customer:hpsMessageReminder:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/hpsMessageReminderList";
	}
	
	/**
	 * 消息通知列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:hpsMessageReminder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HpsMessageReminder hpsMessageReminder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HpsMessageReminder> page = hpsMessageReminderService.findPage(new Page<HpsMessageReminder>(request, response), hpsMessageReminder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑消息通知表单页面
	 */
	@RequiresPermissions(value={"customer:hpsMessageReminder:view","customer:hpsMessageReminder:add","customer:hpsMessageReminder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HpsMessageReminder hpsMessageReminder, Model model) {
		model.addAttribute("hpsMessageReminder", hpsMessageReminder);
		if(StringUtils.isBlank(hpsMessageReminder.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/hpsMessageReminderForm";
	}

	/**
	 * 保存消息通知
	 */
	@RequiresPermissions(value={"customer:hpsMessageReminder:add","customer:hpsMessageReminder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HpsMessageReminder hpsMessageReminder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, hpsMessageReminder)){
			return form(hpsMessageReminder, model);
		}
		//新增或编辑表单保存
		hpsMessageReminderService.save(hpsMessageReminder);//保存
		addMessage(redirectAttributes, "保存消息通知成功");
		return "redirect:"+Global.getAdminPath()+"/customer/hpsMessageReminder/?repage";
	}
	
	/**
	 * 删除消息通知
	 */
	@ResponseBody
	@RequiresPermissions("customer:hpsMessageReminder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HpsMessageReminder hpsMessageReminder, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		hpsMessageReminderService.delete(hpsMessageReminder);
		j.setMsg("删除消息通知成功");
		return j;
	}
	
	/**
	 * 批量删除消息通知
	 */
	@ResponseBody
	@RequiresPermissions("customer:hpsMessageReminder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hpsMessageReminderService.delete(hpsMessageReminderService.get(id));
		}
		j.setMsg("删除消息通知成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:hpsMessageReminder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(HpsMessageReminder hpsMessageReminder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "消息通知"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HpsMessageReminder> page = hpsMessageReminderService.findPage(new Page<HpsMessageReminder>(request, response, -1), hpsMessageReminder);
    		new ExportExcel("消息通知", HpsMessageReminder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出消息通知记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:hpsMessageReminder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HpsMessageReminder> list = ei.getDataList(HpsMessageReminder.class);
			for (HpsMessageReminder hpsMessageReminder : list){
				try{
					hpsMessageReminderService.save(hpsMessageReminder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条消息通知记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条消息通知记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入消息通知失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/hpsMessageReminder/?repage";
    }
	
	/**
	 * 下载导入消息通知数据模板
	 */
	@RequiresPermissions("customer:hpsMessageReminder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "消息通知数据导入模板.xlsx";
    		List<HpsMessageReminder> list = Lists.newArrayList(); 
    		new ExportExcel("消息通知数据", HpsMessageReminder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/hpsMessageReminder/?repage";
    }

}