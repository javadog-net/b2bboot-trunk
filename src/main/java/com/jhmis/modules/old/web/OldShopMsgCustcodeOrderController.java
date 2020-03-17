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
import com.jhmis.modules.old.entity.OldShopMsgCustcodeOrder;
import com.jhmis.modules.old.service.OldShopMsgCustcodeOrderService;

/**
 * oldController
 * @author old
 * @version 2019-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/old/oldShopMsgCustcodeOrder")
public class OldShopMsgCustcodeOrderController extends BaseController {

	@Autowired
	private OldShopMsgCustcodeOrderService oldShopMsgCustcodeOrderService;
	
	@ModelAttribute
	public OldShopMsgCustcodeOrder get(@RequestParam(required=false) String id) {
		OldShopMsgCustcodeOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oldShopMsgCustcodeOrderService.get(id);
		}
		if (entity == null){
			entity = new OldShopMsgCustcodeOrder();
		}
		return entity;
	}
	
	/**
	 * old列表页面
	 */
	@RequiresPermissions("old:oldShopMsgCustcodeOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/old/oldShopMsgCustcodeOrderList";
	}
	
	/**
	 * old列表数据
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsgCustcodeOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OldShopMsgCustcodeOrder> page = oldShopMsgCustcodeOrderService.findPage(new Page<OldShopMsgCustcodeOrder>(request, response), oldShopMsgCustcodeOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑old表单页面
	 */
	@RequiresPermissions(value={"old:oldShopMsgCustcodeOrder:view","old:oldShopMsgCustcodeOrder:add","old:oldShopMsgCustcodeOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder, Model model) {
		model.addAttribute("oldShopMsgCustcodeOrder", oldShopMsgCustcodeOrder);
		if(StringUtils.isBlank(oldShopMsgCustcodeOrder.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/old/oldShopMsgCustcodeOrderForm";
	}

	/**
	 * 保存old
	 */
	@RequiresPermissions(value={"old:oldShopMsgCustcodeOrder:add","old:oldShopMsgCustcodeOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, oldShopMsgCustcodeOrder)){
			return form(oldShopMsgCustcodeOrder, model);
		}
		//新增或编辑表单保存
		oldShopMsgCustcodeOrderService.save(oldShopMsgCustcodeOrder);//保存
		addMessage(redirectAttributes, "保存old成功");
		return "redirect:"+Global.getAdminPath()+"/old/oldShopMsgCustcodeOrder/?repage";
	}
	
	/**
	 * 删除old
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsgCustcodeOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oldShopMsgCustcodeOrderService.delete(oldShopMsgCustcodeOrder);
		j.setMsg("删除old成功");
		return j;
	}
	
	/**
	 * 批量删除old
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsgCustcodeOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oldShopMsgCustcodeOrderService.delete(oldShopMsgCustcodeOrderService.get(id));
		}
		j.setMsg("删除old成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopMsgCustcodeOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "old"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OldShopMsgCustcodeOrder> page = oldShopMsgCustcodeOrderService.findPage(new Page<OldShopMsgCustcodeOrder>(request, response, -1), oldShopMsgCustcodeOrder);
    		new ExportExcel("old", OldShopMsgCustcodeOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("old:oldShopMsgCustcodeOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OldShopMsgCustcodeOrder> list = ei.getDataList(OldShopMsgCustcodeOrder.class);
			for (OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder : list){
				try{
					oldShopMsgCustcodeOrderService.save(oldShopMsgCustcodeOrder);
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
		return "redirect:"+Global.getAdminPath()+"/old/oldShopMsgCustcodeOrder/?repage";
    }
	
	/**
	 * 下载导入old数据模板
	 */
	@RequiresPermissions("old:oldShopMsgCustcodeOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "old数据导入模板.xlsx";
    		List<OldShopMsgCustcodeOrder> list = Lists.newArrayList(); 
    		new ExportExcel("old数据", OldShopMsgCustcodeOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/old/oldShopMsgCustcodeOrder/?repage";
    }

}