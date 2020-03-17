/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.web.shophicback;

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
import com.jhmis.modules.process.entity.shophicback.ShopHicbackpassLog;
import com.jhmis.modules.process.service.shophicback.ShopHicbackpassLogService;

/**
 * shophicbackController
 * @author hdx
 * @version 2019-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/process/shophicback/shopHicbackpassLog")
public class ShopHicbackpassLogController extends BaseController {

	@Autowired
	private ShopHicbackpassLogService shopHicbackpassLogService;
	
	@ModelAttribute
	public ShopHicbackpassLog get(@RequestParam(required=false) String id) {
		ShopHicbackpassLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopHicbackpassLogService.get(id);
		}
		if (entity == null){
			entity = new ShopHicbackpassLog();
		}
		return entity;
	}
	
	/**
	 * hic 400回传列表页面
	 */
	@RequiresPermissions("process:shophicback:shopHicbackpassLog:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/process/shophicback/shopHicbackpassLogList";
	}
	
	/**
	 * hic 400回传列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:shophicback:shopHicbackpassLog:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ShopHicbackpassLog shopHicbackpassLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopHicbackpassLog> page = shopHicbackpassLogService.findPage(new Page<ShopHicbackpassLog>(request, response), shopHicbackpassLog); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑hic 400回传表单页面
	 */
	@RequiresPermissions(value={"process:shophicback:shopHicbackpassLog:view","process:shophicback:shopHicbackpassLog:add","process:shophicback:shopHicbackpassLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ShopHicbackpassLog shopHicbackpassLog, Model model) {
		model.addAttribute("shopHicbackpassLog", shopHicbackpassLog);
		if(StringUtils.isBlank(shopHicbackpassLog.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/process/shophicback/shopHicbackpassLogForm";
	}

	/**
	 * 保存hic 400回传
	 */
	@RequiresPermissions(value={"process:shophicback:shopHicbackpassLog:add","process:shophicback:shopHicbackpassLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ShopHicbackpassLog shopHicbackpassLog, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, shopHicbackpassLog)){
			return form(shopHicbackpassLog, model);
		}
		//新增或编辑表单保存
		shopHicbackpassLogService.save(shopHicbackpassLog);//保存
		addMessage(redirectAttributes, "保存hic 400回传成功");
		return "redirect:"+Global.getAdminPath()+"/process/shophicback/shopHicbackpassLog/?repage";
	}
	
	/**
	 * 删除hic 400回传
	 */
	@ResponseBody
	@RequiresPermissions("process:shophicback:shopHicbackpassLog:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ShopHicbackpassLog shopHicbackpassLog, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		shopHicbackpassLogService.delete(shopHicbackpassLog);
		j.setMsg("删除hic 400回传成功");
		return j;
	}
	
	/**
	 * 批量删除hic 400回传
	 */
	@ResponseBody
	@RequiresPermissions("process:shophicback:shopHicbackpassLog:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			shopHicbackpassLogService.delete(shopHicbackpassLogService.get(id));
		}
		j.setMsg("删除hic 400回传成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("process:shophicback:shopHicbackpassLog:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ShopHicbackpassLog shopHicbackpassLog, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "hic 400回传"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ShopHicbackpassLog> page = shopHicbackpassLogService.findPage(new Page<ShopHicbackpassLog>(request, response, -1), shopHicbackpassLog);
    		new ExportExcel("hic 400回传", ShopHicbackpassLog.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出hic 400回传记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:shophicback:shopHicbackpassLog:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ShopHicbackpassLog> list = ei.getDataList(ShopHicbackpassLog.class);
			for (ShopHicbackpassLog shopHicbackpassLog : list){
				try{
					shopHicbackpassLogService.save(shopHicbackpassLog);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条hic 400回传记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条hic 400回传记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入hic 400回传失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shophicback/shopHicbackpassLog/?repage";
    }
	
	/**
	 * 下载导入hic 400回传数据模板
	 */
	@RequiresPermissions("process:shophicback:shopHicbackpassLog:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "hic 400回传数据导入模板.xlsx";
    		List<ShopHicbackpassLog> list = Lists.newArrayList(); 
    		new ExportExcel("hic 400回传数据", ShopHicbackpassLog.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shophicback/shopHicbackpassLog/?repage";
    }

}