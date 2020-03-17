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
import com.jhmis.modules.customer.entity.ProductGroup;
import com.jhmis.modules.customer.service.ProductGroupService;

/**
 * 产品组编码表Controller
 * @author hdx
 * @version 2019-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/productGroup")
public class ProductGroupController extends BaseController {

	@Autowired
	private ProductGroupService productGroupService;
	
	@ModelAttribute
	public ProductGroup get(@RequestParam(required=false) String id) {
		ProductGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productGroupService.get(id);
		}
		if (entity == null){
			entity = new ProductGroup();
		}
		return entity;
	}
	
	/**
	 * 产品组编码表列表页面
	 */
	@RequiresPermissions("customer:productGroup:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/productGroupList";
	}
	
	/**
	 * 产品组编码表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:productGroup:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProductGroup productGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductGroup> page = productGroupService.findPage(new Page<ProductGroup>(request, response), productGroup); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑产品组编码表表单页面
	 */
	@RequiresPermissions(value={"customer:productGroup:view","customer:productGroup:add","customer:productGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductGroup productGroup, Model model) {
		model.addAttribute("productGroup", productGroup);
		if(StringUtils.isBlank(productGroup.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/productGroupForm";
	}

	/**
	 * 保存产品组编码表
	 */
	@RequiresPermissions(value={"customer:productGroup:add","customer:productGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProductGroup productGroup, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, productGroup)){
			return form(productGroup, model);
		}
		//新增或编辑表单保存
		productGroupService.save(productGroup);//保存
		addMessage(redirectAttributes, "保存产品组编码表成功");
		return "redirect:"+Global.getAdminPath()+"/customer/productGroup/?repage";
	}
	
	/**
	 * 删除产品组编码表
	 */
	@ResponseBody
	@RequiresPermissions("customer:productGroup:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProductGroup productGroup, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		productGroupService.delete(productGroup);
		j.setMsg("删除产品组编码表成功");
		return j;
	}
	
	/**
	 * 批量删除产品组编码表
	 */
	@ResponseBody
	@RequiresPermissions("customer:productGroup:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productGroupService.delete(productGroupService.get(id));
		}
		j.setMsg("删除产品组编码表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:productGroup:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProductGroup productGroup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "产品组编码表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProductGroup> page = productGroupService.findPage(new Page<ProductGroup>(request, response, -1), productGroup);
    		new ExportExcel("产品组编码表", ProductGroup.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出产品组编码表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:productGroup:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductGroup> list = ei.getDataList(ProductGroup.class);
			for (ProductGroup productGroup : list){
				try{
					productGroupService.save(productGroup);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条产品组编码表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条产品组编码表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入产品组编码表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/productGroup/?repage";
    }
	
	/**
	 * 下载导入产品组编码表数据模板
	 */
	@RequiresPermissions("customer:productGroup:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "产品组编码表数据导入模板.xlsx";
    		List<ProductGroup> list = Lists.newArrayList(); 
    		new ExportExcel("产品组编码表数据", ProductGroup.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/productGroup/?repage";
    }

}