/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.modules.shop.entity.OrderGoods;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.service.OrderGoodsService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAddressService;
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
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.service.OrdersService;

/**
 * 订单管理Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/orders")
public class OrdersController extends BaseController {

	@Autowired
	private OrdersService ordersService;
	@Autowired
	private OrderGoodsService orderGoodsService;
	@Autowired
	private PurchaserAddressService purchaserAddressService;
	@ModelAttribute
	public Orders get(@RequestParam(required=false) String id) {
		Orders entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ordersService.get(id);
		}
		if (entity == null){
			entity = new Orders();
		}
		return entity;
	}
	
	/**
	 * 订单列表页面
	 */
	@RequiresPermissions("shop:orders:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/ordersList";
	}
	
	/**
	 * 订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:orders:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Orders orders, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Orders> page = ordersService.findPageList(new Page<Orders>(request, response), orders);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑订单表单页面
	 */
	@RequiresPermissions(value={"shop:orders:view","shop:orders:add","shop:orders:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Orders orders, Model model) {

		//订单商品
		if(orders != null && StringUtils.isNotEmpty(orders.getId())){
			OrderGoods orderGoods = new OrderGoods();
			orderGoods.setOrderId(orders.getId());
			List<OrderGoods> orderGoodsList = orderGoodsService.findList(orderGoods);
			model.addAttribute("orderGoodsList", orderGoodsList);
		}
		model.addAttribute("orders", orders);
		return "modules/shop/ordersView";
	}

	/**
	 * 保存订单
	 */
	@RequiresPermissions(value={"shop:orders:add","shop:orders:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Orders orders, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orders)){
			return form(orders, model);
		}
		//新增或编辑表单保存
		ordersService.save(orders);//保存
		addMessage(redirectAttributes, "保存订单成功");
		return "redirect:"+Global.getAdminPath()+"/shop/orders/?repage";
	}
	
	/**
	 * 删除订单
	 */
	@ResponseBody
	@RequiresPermissions("shop:orders:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Orders orders, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		ordersService.delete(orders);
		j.setMsg("删除订单成功");
		return j;
	}
	
	/**
	 * 批量删除订单
	 */
	@ResponseBody
	@RequiresPermissions("shop:orders:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			ordersService.delete(ordersService.get(id));
		}
		j.setMsg("删除订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:orders:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Orders orders, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Orders> page = ordersService.findPage(new Page<Orders>(request, response, -1), orders);
    		new ExportExcel("订单", Orders.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:orders:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Orders> list = ei.getDataList(Orders.class);
			for (Orders orders : list){
				try{
					ordersService.save(orders);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/orders/?repage";
    }
	
	/**
	 * 下载导入订单数据模板
	 */
	@RequiresPermissions("shop:orders:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单数据导入模板.xlsx";
    		List<Orders> list = Lists.newArrayList(); 
    		new ExportExcel("订单数据", Orders.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/orders/?repage";
    }

}