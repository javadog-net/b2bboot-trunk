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
import com.jhmis.modules.customer.entity.ProductSk;
import com.jhmis.modules.customer.service.ProductSkService;

/**
 * 商空系列数据Controller
 * @author mll
 * @version 2019-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/productSk")
public class ProductSkController extends BaseController {

	@Autowired
	private ProductSkService productSkService;
	
	@ModelAttribute
	public ProductSk get(@RequestParam(required=false) String id) {
		ProductSk entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productSkService.get(id);
		}
		if (entity == null){
			entity = new ProductSk();
		}
		return entity;
	}
	
	/**
	 * 商空系列数据列表页面
	 */
	@RequiresPermissions("customer:productSk:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/productSkList";
	}
	
	/**
	 * 商空系列数据列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:productSk:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProductSk productSk, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductSk> page = productSkService.findPage(new Page<ProductSk>(request, response), productSk); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商空系列数据表单页面
	 */
	@RequiresPermissions(value={"customer:productSk:view","customer:productSk:add","customer:productSk:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductSk productSk, Model model) {
		model.addAttribute("productSk", productSk);
		if(StringUtils.isBlank(productSk.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/productSkForm";
	}

	/**
	 * 保存商空系列数据
	 */
	@RequiresPermissions(value={"customer:productSk:add","customer:productSk:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProductSk productSk, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, productSk)){
			return form(productSk, model);
		}
		//新增或编辑表单保存
		productSkService.save(productSk);//保存
		addMessage(redirectAttributes, "保存商空系列数据成功");
		return "redirect:"+Global.getAdminPath()+"/customer/productSk/?repage";
	}
	
	/**
	 * 删除商空系列数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:productSk:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProductSk productSk, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		productSkService.delete(productSk);
		j.setMsg("删除商空系列数据成功");
		return j;
	}
	
	/**
	 * 批量删除商空系列数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:productSk:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productSkService.delete(productSkService.get(id));
		}
		j.setMsg("删除商空系列数据成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:productSk:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProductSk productSk, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商空系列数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProductSk> page = productSkService.findPage(new Page<ProductSk>(request, response, -1), productSk);
    		new ExportExcel("商空系列数据", ProductSk.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商空系列数据记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:productSk:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductSk> list = ei.getDataList(ProductSk.class);
			for (ProductSk productSk : list){
				try{
					productSkService.save(productSk);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商空系列数据记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商空系列数据记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商空系列数据失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/productSk/?repage";
    }
	
	/**
	 * 下载导入商空系列数据数据模板
	 */
	@RequiresPermissions("customer:productSk:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商空系列数据数据导入模板.xlsx";
    		List<ProductSk> list = Lists.newArrayList(); 
    		new ExportExcel("商空系列数据数据", ProductSk.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/productSk/?repage";
    }

}