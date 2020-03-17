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
import com.jhmis.modules.shop.entity.OrderLog;
import com.jhmis.modules.shop.service.OrderLogService;

/**
 * 订单日志Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/orderLog")
public class OrderLogController extends BaseController {

	@Autowired
	private OrderLogService orderLogService;
	
	@ModelAttribute
	public OrderLog get(@RequestParam(required=false) String id) {
		OrderLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderLogService.get(id);
		}
		if (entity == null){
			entity = new OrderLog();
		}
		return entity;
	}
	
	/**
	 * 订单日志列表页面
	 */
	@RequiresPermissions("shop:orderLog:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/orderLogList";
	}
	
	/**
	 * 订单日志列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderLog:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderLog orderLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderLog> page = orderLogService.findPage(new Page<OrderLog>(request, response), orderLog); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑订单日志表单页面
	 */
	@RequiresPermissions(value={"shop:orderLog:view","shop:orderLog:add","shop:orderLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderLog orderLog, Model model) {
		model.addAttribute("orderLog", orderLog);
		return "modules/shop/orderLogForm";
	}

	/**
	 * 保存订单日志
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:orderLog:add","shop:orderLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(OrderLog orderLog, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, orderLog)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		orderLogService.save(orderLog);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存订单日志成功");
		return j;
	}
	
	/**
	 * 删除订单日志
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderLog:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderLog orderLog, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderLogService.delete(orderLog);
		j.setMsg("删除订单日志成功");
		return j;
	}
	
	/**
	 * 批量删除订单日志
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderLog:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderLogService.delete(orderLogService.get(id));
		}
		j.setMsg("删除订单日志成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:orderLog:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderLog orderLog, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "订单日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderLog> page = orderLogService.findPage(new Page<OrderLog>(request, response, -1), orderLog);
    		new ExportExcel("订单日志", OrderLog.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出订单日志记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:orderLog:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderLog> list = ei.getDataList(OrderLog.class);
			for (OrderLog orderLog : list){
				try{
					orderLogService.save(orderLog);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条订单日志记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订单日志记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订单日志失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/orderLog/?repage";
    }
	
	/**
	 * 下载导入订单日志数据模板
	 */
	@RequiresPermissions("shop:orderLog:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单日志数据导入模板.xlsx";
    		List<OrderLog> list = Lists.newArrayList(); 
    		new ExportExcel("订单日志数据", OrderLog.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/orderLog/?repage";
    }

}