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
import com.jhmis.modules.customer.entity.ViewQygBrownItem;
import com.jhmis.modules.customer.service.ViewQygBrownItemService;

/**
 * 工程版信息详情视图Controller
 * @author hdx
 * @version 2019-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/viewQygBrownItem")
public class ViewQygBrownItemController extends BaseController {

	@Autowired
	private ViewQygBrownItemService viewQygBrownItemService;
	
	@ModelAttribute
	public ViewQygBrownItem get(@RequestParam(required=false) String id) {
		ViewQygBrownItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = viewQygBrownItemService.get(id);
		}
		if (entity == null){
			entity = new ViewQygBrownItem();
		}
		return entity;
	}
	
	/**
	 * 工程版信息详情视图列表页面
	 */
	@RequiresPermissions("customer:viewQygBrownItem:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/viewQygBrownItemList";
	}
	
	/**
	 * 工程版信息详情视图列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrownItem:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ViewQygBrownItem viewQygBrownItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ViewQygBrownItem> page = viewQygBrownItemService.findPage(new Page<ViewQygBrownItem>(request, response), viewQygBrownItem); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工程版信息详情视图表单页面
	 */
	@RequiresPermissions(value={"customer:viewQygBrownItem:view","customer:viewQygBrownItem:add","customer:viewQygBrownItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ViewQygBrownItem viewQygBrownItem, Model model) {
		model.addAttribute("viewQygBrownItem", viewQygBrownItem);
		if(StringUtils.isBlank(viewQygBrownItem.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/viewQygBrownItemForm";
	}

	/**
	 * 保存工程版信息详情视图
	 */
	@RequiresPermissions(value={"customer:viewQygBrownItem:add","customer:viewQygBrownItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ViewQygBrownItem viewQygBrownItem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, viewQygBrownItem)){
			return form(viewQygBrownItem, model);
		}
		//新增或编辑表单保存
		viewQygBrownItemService.save(viewQygBrownItem);//保存
		addMessage(redirectAttributes, "保存工程版信息详情视图成功");
		return "redirect:"+Global.getAdminPath()+"/customer/viewQygBrownItem/?repage";
	}
	
	/**
	 * 删除工程版信息详情视图
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrownItem:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ViewQygBrownItem viewQygBrownItem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		viewQygBrownItemService.delete(viewQygBrownItem);
		j.setMsg("删除工程版信息详情视图成功");
		return j;
	}
	
	/**
	 * 批量删除工程版信息详情视图
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrownItem:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			viewQygBrownItemService.delete(viewQygBrownItemService.get(id));
		}
		j.setMsg("删除工程版信息详情视图成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:viewQygBrownItem:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ViewQygBrownItem viewQygBrownItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工程版信息详情视图"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ViewQygBrownItem> page = viewQygBrownItemService.findPage(new Page<ViewQygBrownItem>(request, response, -1), viewQygBrownItem);
    		new ExportExcel("工程版信息详情视图", ViewQygBrownItem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工程版信息详情视图记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:viewQygBrownItem:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ViewQygBrownItem> list = ei.getDataList(ViewQygBrownItem.class);
			for (ViewQygBrownItem viewQygBrownItem : list){
				try{
					viewQygBrownItemService.save(viewQygBrownItem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工程版信息详情视图记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条工程版信息详情视图记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入工程版信息详情视图失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/viewQygBrownItem/?repage";
    }
	
	/**
	 * 下载导入工程版信息详情视图数据模板
	 */
	@RequiresPermissions("customer:viewQygBrownItem:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "工程版信息详情视图数据导入模板.xlsx";
    		List<ViewQygBrownItem> list = Lists.newArrayList(); 
    		new ExportExcel("工程版信息详情视图数据", ViewQygBrownItem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/viewQygBrownItem/?repage";
    }

}