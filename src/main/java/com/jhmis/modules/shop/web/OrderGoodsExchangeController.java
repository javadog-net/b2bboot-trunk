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
import com.jhmis.modules.shop.entity.OrderGoodsExchange;
import com.jhmis.modules.shop.service.OrderGoodsExchangeService;

/**
 * Controller
 * @author cuihj
 * @version 2018-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/orderGoodsExchange")
public class OrderGoodsExchangeController extends BaseController {

	@Autowired
	private OrderGoodsExchangeService orderGoodsExchangeService;
	
	@ModelAttribute
	public OrderGoodsExchange get(@RequestParam(required=false) String id) {
		OrderGoodsExchange entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderGoodsExchangeService.get(id);
		}
		if (entity == null){
			entity = new OrderGoodsExchange();
		}
		return entity;
	}
	
	/**
	 * 换货管理列表页面
	 */
	@RequiresPermissions("shop:orderGoodsExchange:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/orderGoodsExchangeList";
	}
	
	/**
	 * 换货管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderGoodsExchange:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderGoodsExchange orderGoodsExchange, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderGoodsExchange> page = orderGoodsExchangeService.findPage(new Page<OrderGoodsExchange>(request, response), orderGoodsExchange); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑换货管理表单页面
	 */
	@RequiresPermissions(value={"shop:orderGoodsExchange:view","shop:orderGoodsExchange:add","shop:orderGoodsExchange:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderGoodsExchange orderGoodsExchange, Model model) {
		model.addAttribute("orderGoodsExchange", orderGoodsExchange);
		return "modules/shop/orderGoodsExchangeForm";
	}

	/**
	 * 保存换货管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:orderGoodsExchange:add","shop:orderGoodsExchange:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(OrderGoodsExchange orderGoodsExchange, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, orderGoodsExchange)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		orderGoodsExchangeService.save(orderGoodsExchange);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存换货管理成功");
		return j;
	}
	
	/**
	 * 删除换货管理
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderGoodsExchange:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderGoodsExchange orderGoodsExchange, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderGoodsExchangeService.delete(orderGoodsExchange);
		j.setMsg("删除换货管理成功");
		return j;
	}
	
	/**
	 * 批量删除换货管理
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderGoodsExchange:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderGoodsExchangeService.delete(orderGoodsExchangeService.get(id));
		}
		j.setMsg("删除换货管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderGoodsExchange:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderGoodsExchange orderGoodsExchange, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "换货管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderGoodsExchange> page = orderGoodsExchangeService.findPage(new Page<OrderGoodsExchange>(request, response, -1), orderGoodsExchange);
    		new ExportExcel("换货管理", OrderGoodsExchange.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出换货管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:orderGoodsExchange:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderGoodsExchange> list = ei.getDataList(OrderGoodsExchange.class);
			for (OrderGoodsExchange orderGoodsExchange : list){
				try{
					orderGoodsExchangeService.save(orderGoodsExchange);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条换货管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条换货管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入换货管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/orderGoodsExchange/?repage";
    }
	
	/**
	 * 下载导入换货管理数据模板
	 */
	@RequiresPermissions("shop:orderGoodsExchange:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "换货管理数据导入模板.xlsx";
    		List<OrderGoodsExchange> list = Lists.newArrayList(); 
    		new ExportExcel("换货管理数据", OrderGoodsExchange.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/orderGoodsExchange/?repage";
    }

}