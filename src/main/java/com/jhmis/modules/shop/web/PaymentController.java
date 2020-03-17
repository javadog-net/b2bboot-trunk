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
import com.jhmis.modules.shop.entity.Payment;
import com.jhmis.modules.shop.service.PaymentService;

/**
 * 支付方式Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/payment")
public class PaymentController extends BaseController {

	@Autowired
	private PaymentService paymentService;
	
	@ModelAttribute
	public Payment get(@RequestParam(required=false) String id) {
		Payment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = paymentService.get(id);
		}
		if (entity == null){
			entity = new Payment();
		}
		return entity;
	}
	
	/**
	 * 支付方式列表页面
	 */
	@RequiresPermissions("shop:payment:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/paymentList";
	}
	
	/**
	 * 支付方式列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:payment:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Payment payment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Payment> page = paymentService.findPage(new Page<Payment>(request, response), payment); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑支付方式表单页面
	 */
	@RequiresPermissions(value={"shop:payment:view","shop:payment:add","shop:payment:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Payment payment, Model model) {
		model.addAttribute("payment", payment);
		return "modules/shop/paymentForm";
	}

	/**
	 * 保存支付方式
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:payment:add","shop:payment:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Payment payment, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, payment)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		paymentService.save(payment);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存支付方式成功");
		return j;
	}
	
	/**
	 * 删除支付方式
	 */
	@ResponseBody
	@RequiresPermissions("shop:payment:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Payment payment, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		paymentService.delete(payment);
		j.setMsg("删除支付方式成功");
		return j;
	}
	
	/**
	 * 批量删除支付方式
	 */
	@ResponseBody
	@RequiresPermissions("shop:payment:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			paymentService.delete(paymentService.get(id));
		}
		j.setMsg("删除支付方式成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:payment:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Payment payment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "支付方式"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Payment> page = paymentService.findPage(new Page<Payment>(request, response, -1), payment);
    		new ExportExcel("支付方式", Payment.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出支付方式记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:payment:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Payment> list = ei.getDataList(Payment.class);
			for (Payment payment : list){
				try{
					paymentService.save(payment);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条支付方式记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条支付方式记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入支付方式失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/payment/?repage";
    }
	
	/**
	 * 下载导入支付方式数据模板
	 */
	@RequiresPermissions("shop:payment:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "支付方式数据导入模板.xlsx";
    		List<Payment> list = Lists.newArrayList(); 
    		new ExportExcel("支付方式数据", Payment.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/payment/?repage";
    }

}