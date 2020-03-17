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
import com.jhmis.modules.customer.entity.TOrderInfo;
import com.jhmis.modules.customer.service.TOrderInfoService;

/**
 * 巨商会订单信息推送表Controller
 * @author hdx
 * @version 2020-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/tOrderInfo")
public class TOrderInfoController extends BaseController {

	@Autowired
	private TOrderInfoService tOrderInfoService;
	
	@ModelAttribute
	public TOrderInfo get(@RequestParam(required=false) String id) {
		TOrderInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tOrderInfoService.get(id);
		}
		if (entity == null){
			entity = new TOrderInfo();
		}
		return entity;
	}
	
	/**
	 * 巨商会订单信息推送列表页面
	 */
	@RequiresPermissions("customer:tOrderInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/tOrderInfoList";
	}
	
	/**
	 * 巨商会订单信息推送列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:tOrderInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TOrderInfo tOrderInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TOrderInfo> page = tOrderInfoService.findPage(new Page<TOrderInfo>(request, response), tOrderInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑巨商会订单信息推送表单页面
	 */
	@RequiresPermissions(value={"customer:tOrderInfo:view","customer:tOrderInfo:add","customer:tOrderInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TOrderInfo tOrderInfo, Model model) {
		model.addAttribute("tOrderInfo", tOrderInfo);
		if(StringUtils.isBlank(tOrderInfo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/tOrderInfoForm";
	}

	/**
	 * 保存巨商会订单信息推送
	 */
	@RequiresPermissions(value={"customer:tOrderInfo:add","customer:tOrderInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TOrderInfo tOrderInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, tOrderInfo)){
			return form(tOrderInfo, model);
		}
		//新增或编辑表单保存
		tOrderInfoService.save(tOrderInfo);//保存
		addMessage(redirectAttributes, "保存巨商会订单信息推送成功");
		return "redirect:"+Global.getAdminPath()+"/customer/tOrderInfo/?repage";
	}
	
	/**
	 * 删除巨商会订单信息推送
	 */
	@ResponseBody
	@RequiresPermissions("customer:tOrderInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TOrderInfo tOrderInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		tOrderInfoService.delete(tOrderInfo);
		j.setMsg("删除巨商会订单信息推送成功");
		return j;
	}
	
	/**
	 * 批量删除巨商会订单信息推送
	 */
	@ResponseBody
	@RequiresPermissions("customer:tOrderInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			tOrderInfoService.delete(tOrderInfoService.get(id));
		}
		j.setMsg("删除巨商会订单信息推送成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:tOrderInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(TOrderInfo tOrderInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "巨商会订单信息推送"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TOrderInfo> page = tOrderInfoService.findPage(new Page<TOrderInfo>(request, response, -1), tOrderInfo);
    		new ExportExcel("巨商会订单信息推送", TOrderInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出巨商会订单信息推送记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:tOrderInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TOrderInfo> list = ei.getDataList(TOrderInfo.class);
			for (TOrderInfo tOrderInfo : list){
				try{
					tOrderInfoService.save(tOrderInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条巨商会订单信息推送记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条巨商会订单信息推送记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入巨商会订单信息推送失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/tOrderInfo/?repage";
    }
	
	/**
	 * 下载导入巨商会订单信息推送数据模板
	 */
	@RequiresPermissions("customer:tOrderInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "巨商会订单信息推送数据导入模板.xlsx";
    		List<TOrderInfo> list = Lists.newArrayList(); 
    		new ExportExcel("巨商会订单信息推送数据", TOrderInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/tOrderInfo/?repage";
    }

}