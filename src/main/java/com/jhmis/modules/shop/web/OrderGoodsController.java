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
import com.jhmis.modules.shop.entity.OrderGoods;
import com.jhmis.modules.shop.service.OrderGoodsService;

/**
 * 订单商品Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/orderGoods")
public class OrderGoodsController extends BaseController {

	@Autowired
	private OrderGoodsService orderGoodsService;
	
	@ModelAttribute
	public OrderGoods get(@RequestParam(required=false) String id) {
		OrderGoods entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderGoodsService.get(id);
		}
		if (entity == null){
			entity = new OrderGoods();
		}
		return entity;
	}
	
	/**
	 * 订单商品列表页面
	 */
	@RequiresPermissions("shop:orderGoods:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/orderGoodsList";
	}
	
	/**
	 * 订单商品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderGoods:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderGoods orderGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderGoods> page = orderGoodsService.findPage(new Page<OrderGoods>(request, response), orderGoods); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑订单商品表单页面
	 */
	@RequiresPermissions(value={"shop:orderGoods:view","shop:orderGoods:add","shop:orderGoods:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderGoods orderGoods, Model model) {
		model.addAttribute("orderGoods", orderGoods);
		return "modules/shop/orderGoodsForm";
	}

	/**
	 * 保存订单商品
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:orderGoods:add","shop:orderGoods:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(OrderGoods orderGoods, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, orderGoods)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		orderGoodsService.save(orderGoods);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存订单商品成功");
		return j;
	}
	
	/**
	 * 删除订单商品
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderGoods:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderGoods orderGoods, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderGoodsService.delete(orderGoods);
		j.setMsg("删除订单商品成功");
		return j;
	}
	
	/**
	 * 批量删除订单商品
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderGoods:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderGoodsService.delete(orderGoodsService.get(id));
		}
		j.setMsg("删除订单商品成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderGoods:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderGoods orderGoods, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "订单商品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderGoods> page = orderGoodsService.findPage(new Page<OrderGoods>(request, response, -1), orderGoods);
    		new ExportExcel("订单商品", OrderGoods.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出订单商品记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:orderGoods:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderGoods> list = ei.getDataList(OrderGoods.class);
			for (OrderGoods orderGoods : list){
				try{
					orderGoodsService.save(orderGoods);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条订单商品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订单商品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订单商品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/orderGoods/?repage";
    }
	
	/**
	 * 下载导入订单商品数据模板
	 */
	@RequiresPermissions("shop:orderGoods:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单商品数据导入模板.xlsx";
    		List<OrderGoods> list = Lists.newArrayList(); 
    		new ExportExcel("订单商品数据", OrderGoods.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/orderGoods/?repage";
    }

}