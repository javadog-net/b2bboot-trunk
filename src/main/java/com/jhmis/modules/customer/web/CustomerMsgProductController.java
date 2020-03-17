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
import com.jhmis.modules.customer.entity.CustomerMsgProduct;
import com.jhmis.modules.customer.service.CustomerMsgProductService;

/**
 * 客单产品关联表Controller
 * @author hdx
 * @version 2019-04-16
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerMsgProduct")
public class CustomerMsgProductController extends BaseController {

	@Autowired
	private CustomerMsgProductService customerMsgProductService;
	
	@ModelAttribute
	public CustomerMsgProduct get(@RequestParam(required=false) String id) {
		CustomerMsgProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerMsgProductService.get(id);
		}
		if (entity == null){
			entity = new CustomerMsgProduct();
		}
		return entity;
	}
	
	/**
	 * 客单产品关联表列表页面
	 */
	@RequiresPermissions("customer:customerMsgProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/customerMsgProductList";
	}
	
	/**
	 * 客单产品关联表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerMsgProduct:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CustomerMsgProduct customerMsgProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerMsgProduct> page = customerMsgProductService.findPage(new Page<CustomerMsgProduct>(request, response), customerMsgProduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客单产品关联表表单页面
	 */
	@RequiresPermissions(value={"customer:customerMsgProduct:view","customer:customerMsgProduct:add","customer:customerMsgProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CustomerMsgProduct customerMsgProduct, Model model) {
		model.addAttribute("customerMsgProduct", customerMsgProduct);
		if(StringUtils.isBlank(customerMsgProduct.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/customerMsgProductForm";
	}

	/**
	 * 保存客单产品关联表
	 */
	@RequiresPermissions(value={"customer:customerMsgProduct:add","customer:customerMsgProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CustomerMsgProduct customerMsgProduct, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, customerMsgProduct)){
			return form(customerMsgProduct, model);
		}
		//新增或编辑表单保存
		customerMsgProductService.save(customerMsgProduct);//保存
		addMessage(redirectAttributes, "保存客单产品关联表成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerMsgProduct/?repage";
	}
	
	/**
	 * 删除客单产品关联表
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerMsgProduct:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CustomerMsgProduct customerMsgProduct, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		customerMsgProductService.delete(customerMsgProduct);
		j.setMsg("删除客单产品关联表成功");
		return j;
	}
	
	/**
	 * 批量删除客单产品关联表
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerMsgProduct:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerMsgProductService.delete(customerMsgProductService.get(id));
		}
		j.setMsg("删除客单产品关联表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerMsgProduct:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CustomerMsgProduct customerMsgProduct, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客单产品关联表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CustomerMsgProduct> page = customerMsgProductService.findPage(new Page<CustomerMsgProduct>(request, response, -1), customerMsgProduct);
    		new ExportExcel("客单产品关联表", CustomerMsgProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客单产品关联表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:customerMsgProduct:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CustomerMsgProduct> list = ei.getDataList(CustomerMsgProduct.class);
			for (CustomerMsgProduct customerMsgProduct : list){
				try{
					customerMsgProductService.save(customerMsgProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客单产品关联表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条客单产品关联表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入客单产品关联表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerMsgProduct/?repage";
    }
	
	/**
	 * 下载导入客单产品关联表数据模板
	 */
	@RequiresPermissions("customer:customerMsgProduct:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客单产品关联表数据导入模板.xlsx";
    		List<CustomerMsgProduct> list = Lists.newArrayList(); 
    		new ExportExcel("客单产品关联表数据", CustomerMsgProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerMsgProduct/?repage";
    }

}