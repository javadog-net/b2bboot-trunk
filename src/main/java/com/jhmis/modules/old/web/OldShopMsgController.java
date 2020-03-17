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
import com.jhmis.modules.old.entity.OldShopMsg;
import com.jhmis.modules.old.service.OldShopMsgService;

/**
 * oldController
 * @author old
 * @version 2019-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/old/oldShopMsg")
public class OldShopMsgController extends BaseController {

	@Autowired
	private OldShopMsgService oldShopMsgService;
	
	@ModelAttribute
	public OldShopMsg get(@RequestParam(required=false) String id) {
		OldShopMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oldShopMsgService.get(id);
		}
		if (entity == null){
			entity = new OldShopMsg();
		}
		return entity;
	}
	
	/**
	 * old列表页面
	 */
	@RequiresPermissions("old:oldShopMsg:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/old/oldShopMsgList";
	}
	
	/**
	 * old列表数据
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsg:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OldShopMsg oldShopMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OldShopMsg> page = oldShopMsgService.findPage(new Page<OldShopMsg>(request, response), oldShopMsg); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑old表单页面
	 */
	@RequiresPermissions(value={"old:oldShopMsg:view","old:oldShopMsg:add","old:oldShopMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OldShopMsg oldShopMsg, Model model) {
		model.addAttribute("oldShopMsg", oldShopMsg);
		if(StringUtils.isBlank(oldShopMsg.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/old/oldShopMsgForm";
	}

	/**
	 * 保存old
	 */
	@RequiresPermissions(value={"old:oldShopMsg:add","old:oldShopMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OldShopMsg oldShopMsg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, oldShopMsg)){
			return form(oldShopMsg, model);
		}
		//新增或编辑表单保存
		oldShopMsgService.save(oldShopMsg);//保存
		addMessage(redirectAttributes, "保存old成功");
		return "redirect:"+Global.getAdminPath()+"/old/oldShopMsg/?repage";
	}
	
	/**
	 * 删除old
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsg:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OldShopMsg oldShopMsg, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oldShopMsgService.delete(oldShopMsg);
		j.setMsg("删除old成功");
		return j;
	}
	
	/**
	 * 批量删除old
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsg:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oldShopMsgService.delete(oldShopMsgService.get(id));
		}
		j.setMsg("删除old成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OldShopMsg oldShopMsg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "old"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OldShopMsg> page = oldShopMsgService.findPage(new Page<OldShopMsg>(request, response, -1), oldShopMsg);
    		new ExportExcel("old", OldShopMsg.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("old:oldShopMsg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OldShopMsg> list = ei.getDataList(OldShopMsg.class);
			for (OldShopMsg oldShopMsg : list){
				try{
					oldShopMsgService.save(oldShopMsg);
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
		return "redirect:"+Global.getAdminPath()+"/old/oldShopMsg/?repage";
    }
	
	/**
	 * 下载导入old数据模板
	 */
	@RequiresPermissions("old:oldShopMsg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "old数据导入模板.xlsx";
    		List<OldShopMsg> list = Lists.newArrayList(); 
    		new ExportExcel("old数据", OldShopMsg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/old/oldShopMsg/?repage";
    }

}