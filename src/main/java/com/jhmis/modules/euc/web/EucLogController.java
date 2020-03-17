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
import com.jhmis.modules.euc.entity.EucLog;
import com.jhmis.modules.euc.service.EucLogService;

/**
 * euc_log日志Controller
 * @author hdx
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/euc/eucLog")
public class EucLogController extends BaseController {

	@Autowired
	private EucLogService eucLogService;
	
	@ModelAttribute
	public EucLog get(@RequestParam(required=false) String id) {
		EucLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = eucLogService.get(id);
		}
		if (entity == null){
			entity = new EucLog();
		}
		return entity;
	}
	
	/**
	 * euc_log日志列表页面
	 */
	@RequiresPermissions("euc:eucLog:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/euc/eucLogList";
	}
	
	/**
	 * euc_log日志列表数据
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucLog:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(EucLog eucLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EucLog> page = eucLogService.findPage(new Page<EucLog>(request, response), eucLog); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑euc_log日志表单页面
	 */
	@RequiresPermissions(value={"euc:eucLog:view","euc:eucLog:add","euc:eucLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(EucLog eucLog, Model model) {
		model.addAttribute("eucLog", eucLog);
		if(StringUtils.isBlank(eucLog.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/euc/eucLogForm";
	}

	/**
	 * 保存euc_log日志
	 */
	@RequiresPermissions(value={"euc:eucLog:add","euc:eucLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(EucLog eucLog, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, eucLog)){
			return form(eucLog, model);
		}
		//新增或编辑表单保存
		eucLogService.save(eucLog);//保存
		addMessage(redirectAttributes, "保存euc_log日志成功");
		return "redirect:"+Global.getAdminPath()+"/euc/eucLog/?repage";
	}
	
	/**
	 * 删除euc_log日志
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucLog:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(EucLog eucLog, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		eucLogService.delete(eucLog);
		j.setMsg("删除euc_log日志成功");
		return j;
	}
	
	/**
	 * 批量删除euc_log日志
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucLog:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			eucLogService.delete(eucLogService.get(id));
		}
		j.setMsg("删除euc_log日志成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucLog:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(EucLog eucLog, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "euc_log日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<EucLog> page = eucLogService.findPage(new Page<EucLog>(request, response, -1), eucLog);
    		new ExportExcel("euc_log日志", EucLog.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出euc_log日志记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("euc:eucLog:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<EucLog> list = ei.getDataList(EucLog.class);
			for (EucLog eucLog : list){
				try{
					eucLogService.save(eucLog);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条euc_log日志记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条euc_log日志记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入euc_log日志失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/euc/eucLog/?repage";
    }
	
	/**
	 * 下载导入euc_log日志数据模板
	 */
	@RequiresPermissions("euc:eucLog:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "euc_log日志数据导入模板.xlsx";
    		List<EucLog> list = Lists.newArrayList(); 
    		new ExportExcel("euc_log日志数据", EucLog.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/euc/eucLog/?repage";
    }

}