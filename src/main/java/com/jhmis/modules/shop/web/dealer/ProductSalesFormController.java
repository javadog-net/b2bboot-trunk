/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.dealer;

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
import com.jhmis.modules.shop.entity.dealer.ProductSalesForm;
import com.jhmis.modules.shop.service.dealer.ProductSalesFormService;

/**
 * 销售样表Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/dealer/productSalesForm")
public class ProductSalesFormController extends BaseController {

	@Autowired
	private ProductSalesFormService productSalesFormService;
	
	@ModelAttribute
	public ProductSalesForm get(@RequestParam(required=false) String id) {
		ProductSalesForm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productSalesFormService.get(id);
		}
		if (entity == null){
			entity = new ProductSalesForm();
		}
		return entity;
	}
	
	/**
	 * 销售样表列表页面
	 */
	@RequiresPermissions("shop:dealer:productSalesForm:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/dealer/productSalesFormList";
	}
	
	/**
	 * 销售样表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:productSalesForm:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProductSalesForm productSalesForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductSalesForm> page = productSalesFormService.findPage(new Page<ProductSalesForm>(request, response), productSalesForm); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑销售样表表单页面
	 */
	@RequiresPermissions(value={"shop:dealer:productSalesForm:view","shop:dealer:productSalesForm:add","shop:dealer:productSalesForm:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductSalesForm productSalesForm, Model model) {
		model.addAttribute("productSalesForm", productSalesForm);
		return "modules/shop/dealer/productSalesFormForm";
	}

	/**
	 * 保存销售样表
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:dealer:productSalesForm:add","shop:dealer:productSalesForm:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ProductSalesForm productSalesForm, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, productSalesForm)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		productSalesFormService.save(productSalesForm);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存销售样表成功");
		return j;
	}
	
	/**
	 * 删除销售样表
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:productSalesForm:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProductSalesForm productSalesForm, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		productSalesFormService.delete(productSalesForm);
		j.setMsg("删除销售样表成功");
		return j;
	}
	
	/**
	 * 批量删除销售样表
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:productSalesForm:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productSalesFormService.delete(productSalesFormService.get(id));
		}
		j.setMsg("删除销售样表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:productSalesForm:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProductSalesForm productSalesForm, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "销售样表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProductSalesForm> page = productSalesFormService.findPage(new Page<ProductSalesForm>(request, response, -1), productSalesForm);
    		new ExportExcel("销售样表", ProductSalesForm.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出销售样表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:dealer:productSalesForm:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductSalesForm> list = ei.getDataList(ProductSalesForm.class);
			for (ProductSalesForm productSalesForm : list){
				try{
					productSalesFormService.save(productSalesForm);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条销售样表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条销售样表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入销售样表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/productSalesForm/?repage";
    }
	
	/**
	 * 下载导入销售样表数据模板
	 */
	@RequiresPermissions("shop:dealer:productSalesForm:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "销售样表数据导入模板.xlsx";
    		List<ProductSalesForm> list = Lists.newArrayList(); 
    		new ExportExcel("销售样表数据", ProductSalesForm.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/productSalesForm/?repage";
    }

}