/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

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
import com.jhmis.modules.shop.entity.DirectMsg;
import com.jhmis.modules.shop.service.DirectMsgService;

/**
 * 直采需求Controller
 * @author hdx
 * @version 2019-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/directMsg")
public class DirectMsgController extends BaseController {

	@Autowired
	private DirectMsgService directMsgService;
	
	@ModelAttribute
	public DirectMsg get(@RequestParam(required=false) String id) {
		DirectMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = directMsgService.get(id);
		}
		if (entity == null){
			entity = new DirectMsg();
		}
		return entity;
	}
	
	/**
	 * 直采需求列表页面
	 */
	@RequiresPermissions("shop:directMsg:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/directMsgList";
	}
	
	/**
	 * 直采需求列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:directMsg:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(DirectMsg directMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DirectMsg> page = directMsgService.findPage(new Page<DirectMsg>(request, response), directMsg); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑直采需求表单页面
	 */
	@RequiresPermissions(value={"shop:directMsg:view","shop:directMsg:add","shop:directMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DirectMsg directMsg, Model model) {
		model.addAttribute("directMsg", directMsg);
		if(StringUtils.isBlank(directMsg.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/directMsgForm";
	}

	/**
	 * 保存直采需求
	 */
	@RequiresPermissions(value={"shop:directMsg:add","shop:directMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DirectMsg directMsg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, directMsg)){
			return form(directMsg, model);
		}
		//新增或编辑表单保存
		directMsgService.save(directMsg);//保存
		addMessage(redirectAttributes, "保存直采需求成功");
		return "redirect:"+Global.getAdminPath()+"/shop/directMsg/?repage";
	}
	
	/**
	 * 删除直采需求
	 */
	@ResponseBody
	@RequiresPermissions("shop:directMsg:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(DirectMsg directMsg, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		directMsgService.delete(directMsg);
		j.setMsg("删除直采需求成功");
		return j;
	}
	
	/**
	 * 批量删除直采需求
	 */
	@ResponseBody
	@RequiresPermissions("shop:directMsg:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			directMsgService.delete(directMsgService.get(id));
		}
		j.setMsg("删除直采需求成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:directMsg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(DirectMsg directMsg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "直采需求"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DirectMsg> page = directMsgService.findPage(new Page<DirectMsg>(request, response, -1), directMsg);
    		new ExportExcel("直采需求", DirectMsg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出直采需求记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:directMsg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DirectMsg> list = ei.getDataList(DirectMsg.class);
			for (DirectMsg directMsg : list){
				try{
					directMsgService.save(directMsg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条直采需求记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条直采需求记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入直采需求失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/directMsg/?repage";
    }
	
	/**
	 * 下载导入直采需求数据模板
	 */
	@RequiresPermissions("shop:directMsg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "直采需求数据导入模板.xlsx";
    		List<DirectMsg> list = Lists.newArrayList(); 
    		new ExportExcel("直采需求数据", DirectMsg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/directMsg/?repage";
    }

}