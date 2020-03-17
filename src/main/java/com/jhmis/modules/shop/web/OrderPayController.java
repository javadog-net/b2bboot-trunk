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
import com.jhmis.modules.shop.entity.OrderPay;
import com.jhmis.modules.shop.service.OrderPayService;

/**
 * 订单支付Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/orderPay")
public class OrderPayController extends BaseController {

	@Autowired
	private OrderPayService orderPayService;
	
	@ModelAttribute
	public OrderPay get(@RequestParam(required=false) String id) {
		OrderPay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderPayService.get(id);
		}
		if (entity == null){
			entity = new OrderPay();
		}
		return entity;
	}
	
	/**
	 * 订单支付列表页面
	 */
	@RequiresPermissions("shop:orderPay:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/orderPayList";
	}
	
	/**
	 * 订单支付列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderPay:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderPay orderPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderPay> page = orderPayService.findPage(new Page<OrderPay>(request, response), orderPay); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑订单支付表单页面
	 */
	@RequiresPermissions(value={"shop:orderPay:view","shop:orderPay:add","shop:orderPay:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderPay orderPay, Model model) {
		model.addAttribute("orderPay", orderPay);
		return "modules/shop/orderPayForm";
	}

	/**
	 * 保存订单支付
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:orderPay:add","shop:orderPay:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(OrderPay orderPay, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, orderPay)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		orderPayService.save(orderPay);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存订单支付成功");
		return j;
	}
	
	/**
	 * 删除订单支付
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderPay:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderPay orderPay, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderPayService.delete(orderPay);
		j.setMsg("删除订单支付成功");
		return j;
	}
	
	/**
	 * 批量删除订单支付
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderPay:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderPayService.delete(orderPayService.get(id));
		}
		j.setMsg("删除订单支付成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderPay:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderPay orderPay, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "订单支付"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderPay> page = orderPayService.findPage(new Page<OrderPay>(request, response, -1), orderPay);
    		new ExportExcel("订单支付", OrderPay.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出订单支付记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:orderPay:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderPay> list = ei.getDataList(OrderPay.class);
			for (OrderPay orderPay : list){
				try{
					orderPayService.save(orderPay);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条订单支付记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订单支付记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订单支付失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/orderPay/?repage";
    }
	
	/**
	 * 下载导入订单支付数据模板
	 */
	@RequiresPermissions("shop:orderPay:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单支付数据导入模板.xlsx";
    		List<OrderPay> list = Lists.newArrayList(); 
    		new ExportExcel("订单支付数据", OrderPay.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/orderPay/?repage";
    }

}