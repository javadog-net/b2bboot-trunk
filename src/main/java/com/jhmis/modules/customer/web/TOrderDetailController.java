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
import com.jhmis.modules.customer.entity.TOrderDetail;
import com.jhmis.modules.customer.service.TOrderDetailService;

/**
 * 巨商会订单信息详情推送表Controller
 * @author hdx
 * @version 2020-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/tOrderDetail")
public class TOrderDetailController extends BaseController {

	@Autowired
	private TOrderDetailService tOrderDetailService;
	
	@ModelAttribute
	public TOrderDetail get(@RequestParam(required=false) String id) {
		TOrderDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tOrderDetailService.get(id);
		}
		if (entity == null){
			entity = new TOrderDetail();
		}
		return entity;
	}
	
	/**
	 * 巨商会订单信息详情推送表列表页面
	 */
	@RequiresPermissions("customer:tOrderDetail:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/tOrderDetailList";
	}
	
	/**
	 * 巨商会订单信息详情推送表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:tOrderDetail:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TOrderDetail tOrderDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TOrderDetail> page = tOrderDetailService.findPage(new Page<TOrderDetail>(request, response), tOrderDetail); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑巨商会订单信息详情推送表表单页面
	 */
	@RequiresPermissions(value={"customer:tOrderDetail:view","customer:tOrderDetail:add","customer:tOrderDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TOrderDetail tOrderDetail, Model model) {
		model.addAttribute("tOrderDetail", tOrderDetail);
		if(StringUtils.isBlank(tOrderDetail.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/tOrderDetailForm";
	}

	/**
	 * 保存巨商会订单信息详情推送表
	 */
	@RequiresPermissions(value={"customer:tOrderDetail:add","customer:tOrderDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TOrderDetail tOrderDetail, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, tOrderDetail)){
			return form(tOrderDetail, model);
		}
		//新增或编辑表单保存
		tOrderDetailService.save(tOrderDetail);//保存
		addMessage(redirectAttributes, "保存巨商会订单信息详情推送表成功");
		return "redirect:"+Global.getAdminPath()+"/customer/tOrderDetail/?repage";
	}
	
	/**
	 * 删除巨商会订单信息详情推送表
	 */
	@ResponseBody
	@RequiresPermissions("customer:tOrderDetail:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TOrderDetail tOrderDetail, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		tOrderDetailService.delete(tOrderDetail);
		j.setMsg("删除巨商会订单信息详情推送表成功");
		return j;
	}
	
	/**
	 * 批量删除巨商会订单信息详情推送表
	 */
	@ResponseBody
	@RequiresPermissions("customer:tOrderDetail:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			tOrderDetailService.delete(tOrderDetailService.get(id));
		}
		j.setMsg("删除巨商会订单信息详情推送表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:tOrderDetail:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(TOrderDetail tOrderDetail, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "巨商会订单信息详情推送表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TOrderDetail> page = tOrderDetailService.findPage(new Page<TOrderDetail>(request, response, -1), tOrderDetail);
    		new ExportExcel("巨商会订单信息详情推送表", TOrderDetail.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出巨商会订单信息详情推送表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:tOrderDetail:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TOrderDetail> list = ei.getDataList(TOrderDetail.class);
			for (TOrderDetail tOrderDetail : list){
				try{
					tOrderDetailService.save(tOrderDetail);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条巨商会订单信息详情推送表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条巨商会订单信息详情推送表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入巨商会订单信息详情推送表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/tOrderDetail/?repage";
    }
	
	/**
	 * 下载导入巨商会订单信息详情推送表数据模板
	 */
	@RequiresPermissions("customer:tOrderDetail:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "巨商会订单信息详情推送表数据导入模板.xlsx";
    		List<TOrderDetail> list = Lists.newArrayList(); 
    		new ExportExcel("巨商会订单信息详情推送表数据", TOrderDetail.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/tOrderDetail/?repage";
    }

}