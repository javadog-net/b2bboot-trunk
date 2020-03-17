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
import com.jhmis.modules.customer.entity.QygTOrderInfo;
import com.jhmis.modules.customer.service.QygTOrderInfoService;

/**
 * 巨商会订单info非中转表Controller
 * @author hdx
 * @version 2020-02-12
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/qygTOrderInfo")
public class QygTOrderInfoController extends BaseController {

	@Autowired
	private QygTOrderInfoService qygTOrderInfoService;
	
	@ModelAttribute
	public QygTOrderInfo get(@RequestParam(required=false) String id) {
		QygTOrderInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qygTOrderInfoService.get(id);
		}
		if (entity == null){
			entity = new QygTOrderInfo();
		}
		return entity;
	}
	
	/**
	 * 巨商会订单info列表页面
	 */
	@RequiresPermissions("customer:qygTOrderInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/qygTOrderInfoList";
	}
	
	/**
	 * 巨商会订单info列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:qygTOrderInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(QygTOrderInfo qygTOrderInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QygTOrderInfo> page = qygTOrderInfoService.findPage(new Page<QygTOrderInfo>(request, response), qygTOrderInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑巨商会订单info表单页面
	 */
	@RequiresPermissions(value={"customer:qygTOrderInfo:view","customer:qygTOrderInfo:add","customer:qygTOrderInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(QygTOrderInfo qygTOrderInfo, Model model) {
		model.addAttribute("qygTOrderInfo", qygTOrderInfo);
		if(StringUtils.isBlank(qygTOrderInfo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/qygTOrderInfoForm";
	}

	/**
	 * 保存巨商会订单info
	 */
	@RequiresPermissions(value={"customer:qygTOrderInfo:add","customer:qygTOrderInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(QygTOrderInfo qygTOrderInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, qygTOrderInfo)){
			return form(qygTOrderInfo, model);
		}
		//新增或编辑表单保存
		qygTOrderInfoService.save(qygTOrderInfo);//保存
		addMessage(redirectAttributes, "保存巨商会订单info成功");
		return "redirect:"+Global.getAdminPath()+"/customer/qygTOrderInfo/?repage";
	}
	
	/**
	 * 删除巨商会订单info
	 */
	@ResponseBody
	@RequiresPermissions("customer:qygTOrderInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(QygTOrderInfo qygTOrderInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		qygTOrderInfoService.delete(qygTOrderInfo);
		j.setMsg("删除巨商会订单info成功");
		return j;
	}
	
	/**
	 * 批量删除巨商会订单info
	 */
	@ResponseBody
	@RequiresPermissions("customer:qygTOrderInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qygTOrderInfoService.delete(qygTOrderInfoService.get(id));
		}
		j.setMsg("删除巨商会订单info成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:qygTOrderInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(QygTOrderInfo qygTOrderInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "巨商会订单info"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QygTOrderInfo> page = qygTOrderInfoService.findPage(new Page<QygTOrderInfo>(request, response, -1), qygTOrderInfo);
    		new ExportExcel("巨商会订单info", QygTOrderInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出巨商会订单info记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:qygTOrderInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QygTOrderInfo> list = ei.getDataList(QygTOrderInfo.class);
			for (QygTOrderInfo qygTOrderInfo : list){
				try{
					qygTOrderInfoService.save(qygTOrderInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条巨商会订单info记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条巨商会订单info记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入巨商会订单info失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/qygTOrderInfo/?repage";
    }
	
	/**
	 * 下载导入巨商会订单info数据模板
	 */
	@RequiresPermissions("customer:qygTOrderInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "巨商会订单info数据导入模板.xlsx";
    		List<QygTOrderInfo> list = Lists.newArrayList(); 
    		new ExportExcel("巨商会订单info数据", QygTOrderInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/qygTOrderInfo/?repage";
    }

}