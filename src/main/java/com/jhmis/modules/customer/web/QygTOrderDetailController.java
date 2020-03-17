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
import com.jhmis.modules.customer.entity.QygTOrderDetail;
import com.jhmis.modules.customer.service.QygTOrderDetailService;

/**
 * 巨商会订单details非中转表Controller
 * @author hdx
 * @version 2020-02-12
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/qygTOrderDetail")
public class QygTOrderDetailController extends BaseController {

	@Autowired
	private QygTOrderDetailService qygTOrderDetailService;
	
	@ModelAttribute
	public QygTOrderDetail get(@RequestParam(required=false) String id) {
		QygTOrderDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qygTOrderDetailService.get(id);
		}
		if (entity == null){
			entity = new QygTOrderDetail();
		}
		return entity;
	}
	
	/**
	 * 巨商会订单详情列表页面
	 */
	@RequiresPermissions("customer:qygTOrderDetail:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/qygTOrderDetailList";
	}
	
	/**
	 * 巨商会订单详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:qygTOrderDetail:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(QygTOrderDetail qygTOrderDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QygTOrderDetail> page = qygTOrderDetailService.findPage(new Page<QygTOrderDetail>(request, response), qygTOrderDetail); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑巨商会订单详情表单页面
	 */
	@RequiresPermissions(value={"customer:qygTOrderDetail:view","customer:qygTOrderDetail:add","customer:qygTOrderDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(QygTOrderDetail qygTOrderDetail, Model model) {
		model.addAttribute("qygTOrderDetail", qygTOrderDetail);
		if(StringUtils.isBlank(qygTOrderDetail.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/qygTOrderDetailForm";
	}

	/**
	 * 保存巨商会订单详情
	 */
	@RequiresPermissions(value={"customer:qygTOrderDetail:add","customer:qygTOrderDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(QygTOrderDetail qygTOrderDetail, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, qygTOrderDetail)){
			return form(qygTOrderDetail, model);
		}
		//新增或编辑表单保存
		qygTOrderDetailService.save(qygTOrderDetail);//保存
		addMessage(redirectAttributes, "保存巨商会订单详情成功");
		return "redirect:"+Global.getAdminPath()+"/customer/qygTOrderDetail/?repage";
	}
	
	/**
	 * 删除巨商会订单详情
	 */
	@ResponseBody
	@RequiresPermissions("customer:qygTOrderDetail:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(QygTOrderDetail qygTOrderDetail, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		qygTOrderDetailService.delete(qygTOrderDetail);
		j.setMsg("删除巨商会订单详情成功");
		return j;
	}
	
	/**
	 * 批量删除巨商会订单详情
	 */
	@ResponseBody
	@RequiresPermissions("customer:qygTOrderDetail:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qygTOrderDetailService.delete(qygTOrderDetailService.get(id));
		}
		j.setMsg("删除巨商会订单详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:qygTOrderDetail:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(QygTOrderDetail qygTOrderDetail, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "巨商会订单详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QygTOrderDetail> page = qygTOrderDetailService.findPage(new Page<QygTOrderDetail>(request, response, -1), qygTOrderDetail);
    		new ExportExcel("巨商会订单详情", QygTOrderDetail.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出巨商会订单详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:qygTOrderDetail:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QygTOrderDetail> list = ei.getDataList(QygTOrderDetail.class);
			for (QygTOrderDetail qygTOrderDetail : list){
				try{
					qygTOrderDetailService.save(qygTOrderDetail);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条巨商会订单详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条巨商会订单详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入巨商会订单详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/qygTOrderDetail/?repage";
    }
	
	/**
	 * 下载导入巨商会订单详情数据模板
	 */
	@RequiresPermissions("customer:qygTOrderDetail:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "巨商会订单详情数据导入模板.xlsx";
    		List<QygTOrderDetail> list = Lists.newArrayList(); 
    		new ExportExcel("巨商会订单详情数据", QygTOrderDetail.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/qygTOrderDetail/?repage";
    }

}