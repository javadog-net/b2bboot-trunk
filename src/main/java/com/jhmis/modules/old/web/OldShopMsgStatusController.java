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
import com.jhmis.modules.old.entity.OldShopMsgStatus;
import com.jhmis.modules.old.service.OldShopMsgStatusService;

/**
 * oldController
 * @author old
 * @version 2019-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/old/oldShopMsgStatus")
public class OldShopMsgStatusController extends BaseController {

	@Autowired
	private OldShopMsgStatusService oldShopMsgStatusService;
	
	@ModelAttribute
	public OldShopMsgStatus get(@RequestParam(required=false) String id) {
		OldShopMsgStatus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oldShopMsgStatusService.get(id);
		}
		if (entity == null){
			entity = new OldShopMsgStatus();
		}
		return entity;
	}
	
	/**
	 * old列表页面
	 */
	@RequiresPermissions("old:oldShopMsgStatus:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/old/oldShopMsgStatusList";
	}
	
	/**
	 * old列表数据
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsgStatus:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OldShopMsgStatus oldShopMsgStatus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OldShopMsgStatus> page = oldShopMsgStatusService.findPage(new Page<OldShopMsgStatus>(request, response), oldShopMsgStatus); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑old表单页面
	 */
	@RequiresPermissions(value={"old:oldShopMsgStatus:view","old:oldShopMsgStatus:add","old:oldShopMsgStatus:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OldShopMsgStatus oldShopMsgStatus, Model model) {
		model.addAttribute("oldShopMsgStatus", oldShopMsgStatus);
		if(StringUtils.isBlank(oldShopMsgStatus.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/old/oldShopMsgStatusForm";
	}

	/**
	 * 保存old
	 */
	@RequiresPermissions(value={"old:oldShopMsgStatus:add","old:oldShopMsgStatus:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OldShopMsgStatus oldShopMsgStatus, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, oldShopMsgStatus)){
			return form(oldShopMsgStatus, model);
		}
		//新增或编辑表单保存
		oldShopMsgStatusService.save(oldShopMsgStatus);//保存
		addMessage(redirectAttributes, "保存old成功");
		return "redirect:"+Global.getAdminPath()+"/old/oldShopMsgStatus/?repage";
	}
	
	/**
	 * 删除old
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsgStatus:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OldShopMsgStatus oldShopMsgStatus, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oldShopMsgStatusService.delete(oldShopMsgStatus);
		j.setMsg("删除old成功");
		return j;
	}
	
	/**
	 * 批量删除old
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsgStatus:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oldShopMsgStatusService.delete(oldShopMsgStatusService.get(id));
		}
		j.setMsg("删除old成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsgStatus:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OldShopMsgStatus oldShopMsgStatus, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "old"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OldShopMsgStatus> page = oldShopMsgStatusService.findPage(new Page<OldShopMsgStatus>(request, response, -1), oldShopMsgStatus);
    		new ExportExcel("old", OldShopMsgStatus.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出old记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("old:oldShopMsgStatus:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OldShopMsgStatus> list = ei.getDataList(OldShopMsgStatus.class);
			for (OldShopMsgStatus oldShopMsgStatus : list){
				try{
					oldShopMsgStatusService.save(oldShopMsgStatus);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条old记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条old记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入old失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/old/oldShopMsgStatus/?repage";
    }
	
	/**
	 * 下载导入old数据模板
	 */
	@RequiresPermissions("old:oldShopMsgStatus:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "old数据导入模板.xlsx";
    		List<OldShopMsgStatus> list = Lists.newArrayList(); 
    		new ExportExcel("old数据", OldShopMsgStatus.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/old/oldShopMsgStatus/?repage";
    }

}