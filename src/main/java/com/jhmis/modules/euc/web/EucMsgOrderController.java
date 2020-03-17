/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.euc.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.Exception.EucException;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.modules.euc.service.EucMsgService;
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
import com.jhmis.modules.euc.entity.EucMsgOrder;
import com.jhmis.modules.euc.service.EucMsgOrderService;

/**
 * euc订单Controller
 * @author hdx
 * @version 2019-12-25
 */
@Controller
@RequestMapping(value = "${adminPath}/euc/eucMsgOrder")
public class EucMsgOrderController extends BaseController {

	@Autowired
	private EucMsgOrderService eucMsgOrderService;


	@ModelAttribute
	public EucMsgOrder get(@RequestParam(required=false) String id) {
		EucMsgOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = eucMsgOrderService.get(id);
		}
		if (entity == null){
			entity = new EucMsgOrder();
		}
		return entity;
	}
	
	/**
	 * euc订单列表页面
	 */
	@RequiresPermissions("euc:eucMsgOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/euc/eucMsgOrderList";
	}
	
	/**
	 * euc订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucMsgOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(EucMsgOrder eucMsgOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<EucMsgOrder> page = new Page<EucMsgOrder>(request, response);
        page.setOrderBy("update_time desc");
		Page<EucMsgOrder> pageEucMsgOrder = eucMsgOrderService.findPage(page, eucMsgOrder);
		return getBootstrapData(pageEucMsgOrder);
	}

	/**
	 * 查看，增加，编辑euc订单表单页面
	 */
	@RequiresPermissions(value={"euc:eucMsgOrder:view","euc:eucMsgOrder:add","euc:eucMsgOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(EucMsgOrder eucMsgOrder, Model model) {
		model.addAttribute("eucMsgOrder", eucMsgOrder);
		if(StringUtils.isBlank(eucMsgOrder.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/euc/eucMsgOrderForm";
	}

	/**
	 * 保存euc订单
	 */
	@RequiresPermissions(value={"euc:eucMsgOrder:add","euc:eucMsgOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(EucMsgOrder eucMsgOrder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, eucMsgOrder)){
			return form(eucMsgOrder, model);
		}
		//新增或编辑表单保存
		eucMsgOrderService.save(eucMsgOrder);//保存
		addMessage(redirectAttributes, "保存euc订单成功");
		return "redirect:"+Global.getAdminPath()+"/euc/eucMsgOrder/?repage";
	}
	
	/**
	 * 删除euc订单
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucMsgOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(EucMsgOrder eucMsgOrder, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		eucMsgOrderService.delete(eucMsgOrder);
		j.setMsg("删除euc订单成功");
		return j;
	}
	
	/**
	 * 批量删除euc订单
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucMsgOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			eucMsgOrderService.delete(eucMsgOrderService.get(id));
		}
		j.setMsg("删除euc订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("euc:eucMsgOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(EucMsgOrder eucMsgOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "euc订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<EucMsgOrder> page = eucMsgOrderService.findPage(new Page<EucMsgOrder>(request, response, -1), eucMsgOrder);
    		new ExportExcel("euc订单", EucMsgOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出euc订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("euc:eucMsgOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<EucMsgOrder> list = ei.getDataList(EucMsgOrder.class);
			for (EucMsgOrder eucMsgOrder : list){
				try{
					eucMsgOrderService.save(eucMsgOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条euc订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条euc订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入euc订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/euc/eucMsgOrder/?repage";
    }
	
	/**
	 * 下载导入euc订单数据模板
	 */
	@RequiresPermissions("euc:eucMsgOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "euc订单数据导入模板.xlsx";
    		List<EucMsgOrder> list = Lists.newArrayList(); 
    		new ExportExcel("euc订单数据", EucMsgOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/euc/eucMsgOrder/?repage";
    }

	/**
	 * 审核零售管理
	 */
	@ResponseBody
	@RequestMapping(value = "check")
	public AjaxJson check(@RequestParam String orderId,@RequestParam String ischeck) {
		try {
			eucMsgOrderService.check(orderId,ischeck);
		} catch (EucException e) {
			logger.error("/check-审核零售管理异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
	}


}