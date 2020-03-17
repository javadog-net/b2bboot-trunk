/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.web.shopmsgstatus;

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
import com.jhmis.modules.process.entity.shopmsgstatus.ShopMsgStatus;
import com.jhmis.modules.process.service.shopmsgstatus.ShopMsgStatusService;

/**
 * 需求状态履历表Controller
 * @author hdx
 * @version 2019-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/process/shopmsgstatus/shopMsgStatus")
public class ShopMsgStatusController extends BaseController {

	@Autowired
	private ShopMsgStatusService shopMsgStatusService;
	
	@ModelAttribute
	public ShopMsgStatus get(@RequestParam(required=false) String id) {
		ShopMsgStatus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopMsgStatusService.get(id);
		}
		if (entity == null){
			entity = new ShopMsgStatus();
		}
		return entity;
	}
	
	/**
	 * 需求状态履历列表页面
	 */
	@RequiresPermissions("process:shopmsgstatus:shopMsgStatus:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/process/shopmsgstatus/shopMsgStatusList";
	}
	
	/**
	 * 需求状态履历列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgstatus:shopMsgStatus:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ShopMsgStatus shopMsgStatus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopMsgStatus> page = shopMsgStatusService.findPage(new Page<ShopMsgStatus>(request, response), shopMsgStatus); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑需求状态履历表单页面
	 */
	@RequiresPermissions(value={"process:shopmsgstatus:shopMsgStatus:view","process:shopmsgstatus:shopMsgStatus:add","process:shopmsgstatus:shopMsgStatus:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ShopMsgStatus shopMsgStatus, Model model) {
		model.addAttribute("shopMsgStatus", shopMsgStatus);
		if(StringUtils.isBlank(shopMsgStatus.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/process/shopmsgstatus/shopMsgStatusForm";
	}

	/**
	 * 保存需求状态履历
	 */
	@RequiresPermissions(value={"process:shopmsgstatus:shopMsgStatus:add","process:shopmsgstatus:shopMsgStatus:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ShopMsgStatus shopMsgStatus, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, shopMsgStatus)){
			return form(shopMsgStatus, model);
		}
		//新增或编辑表单保存
		shopMsgStatusService.save(shopMsgStatus);//保存
		addMessage(redirectAttributes, "保存需求状态履历成功");
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgstatus/shopMsgStatus/?repage";
	}
	
	/**
	 * 删除需求状态履历
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgstatus:shopMsgStatus:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ShopMsgStatus shopMsgStatus, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		shopMsgStatusService.delete(shopMsgStatus);
		j.setMsg("删除需求状态履历成功");
		return j;
	}
	
	/**
	 * 批量删除需求状态履历
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgstatus:shopMsgStatus:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			shopMsgStatusService.delete(shopMsgStatusService.get(id));
		}
		j.setMsg("删除需求状态履历成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgstatus:shopMsgStatus:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ShopMsgStatus shopMsgStatus, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "需求状态履历"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ShopMsgStatus> page = shopMsgStatusService.findPage(new Page<ShopMsgStatus>(request, response, -1), shopMsgStatus);
    		new ExportExcel("需求状态履历", ShopMsgStatus.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出需求状态履历记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:shopmsgstatus:shopMsgStatus:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ShopMsgStatus> list = ei.getDataList(ShopMsgStatus.class);
			for (ShopMsgStatus shopMsgStatus : list){
				try{
					shopMsgStatusService.save(shopMsgStatus);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条需求状态履历记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条需求状态履历记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入需求状态履历失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgstatus/shopMsgStatus/?repage";
    }
	
	/**
	 * 下载导入需求状态履历数据模板
	 */
	@RequiresPermissions("process:shopmsgstatus:shopMsgStatus:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "需求状态履历数据导入模板.xlsx";
    		List<ShopMsgStatus> list = Lists.newArrayList(); 
    		new ExportExcel("需求状态履历数据", ShopMsgStatus.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgstatus/shopMsgStatus/?repage";
    }

}