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
import com.jhmis.modules.customer.entity.CustomerProjectProduct;
import com.jhmis.modules.customer.service.CustomerProjectProductService;

/**
 * 客单漏斗项目产品Controller
 * @author hdx
 * @version 2019-04-16
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerProjectProduct")
public class CustomerProjectProductController extends BaseController {

	@Autowired
	private CustomerProjectProductService customerProjectProductService;
	
	@ModelAttribute
	public CustomerProjectProduct get(@RequestParam(required=false) String id) {
		CustomerProjectProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerProjectProductService.get(id);
		}
		if (entity == null){
			entity = new CustomerProjectProduct();
		}
		return entity;
	}
	
	/**
	 * 客单漏斗项目产品列表页面
	 */
	@RequiresPermissions("customer:customerProjectProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/customerProjectProductList";
	}
	
	/**
	 * 客单漏斗项目产品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerProjectProduct:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CustomerProjectProduct customerProjectProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerProjectProduct> page = customerProjectProductService.findPage(new Page<CustomerProjectProduct>(request, response), customerProjectProduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客单漏斗项目产品表单页面
	 */
	@RequiresPermissions(value={"customer:customerProjectProduct:view","customer:customerProjectProduct:add","customer:customerProjectProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CustomerProjectProduct customerProjectProduct, Model model) {
		model.addAttribute("customerProjectProduct", customerProjectProduct);
		if(StringUtils.isBlank(customerProjectProduct.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/customerProjectProductForm";
	}

	/**
	 * 保存客单漏斗项目产品
	 */
	@RequiresPermissions(value={"customer:customerProjectProduct:add","customer:customerProjectProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CustomerProjectProduct customerProjectProduct, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, customerProjectProduct)){
			return form(customerProjectProduct, model);
		}
		//新增或编辑表单保存
		customerProjectProductService.save(customerProjectProduct);//保存
		addMessage(redirectAttributes, "保存客单漏斗项目产品成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerProjectProduct/?repage";
	}
	
	/**
	 * 删除客单漏斗项目产品
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerProjectProduct:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CustomerProjectProduct customerProjectProduct, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		customerProjectProductService.delete(customerProjectProduct);
		j.setMsg("删除客单漏斗项目产品成功");
		return j;
	}
	
	/**
	 * 批量删除客单漏斗项目产品
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerProjectProduct:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerProjectProductService.delete(customerProjectProductService.get(id));
		}
		j.setMsg("删除客单漏斗项目产品成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerProjectProduct:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CustomerProjectProduct customerProjectProduct, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客单漏斗项目产品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CustomerProjectProduct> page = customerProjectProductService.findPage(new Page<CustomerProjectProduct>(request, response, -1), customerProjectProduct);
    		new ExportExcel("客单漏斗项目产品", CustomerProjectProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客单漏斗项目产品记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:customerProjectProduct:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CustomerProjectProduct> list = ei.getDataList(CustomerProjectProduct.class);
			for (CustomerProjectProduct customerProjectProduct : list){
				try{
					customerProjectProductService.save(customerProjectProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客单漏斗项目产品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条客单漏斗项目产品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入客单漏斗项目产品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerProjectProduct/?repage";
    }
	
	/**
	 * 下载导入客单漏斗项目产品数据模板
	 */
	@RequiresPermissions("customer:customerProjectProduct:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客单漏斗项目产品数据导入模板.xlsx";
    		List<CustomerProjectProduct> list = Lists.newArrayList(); 
    		new ExportExcel("客单漏斗项目产品数据", CustomerProjectProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerProjectProduct/?repage";
    }

}