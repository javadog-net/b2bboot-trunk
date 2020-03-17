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
import com.jhmis.modules.shop.entity.TrancheProduct;
import com.jhmis.modules.shop.service.TrancheProductService;

/**
 * 一期商品数据Controller
 * @author hdx
 * @version 2018-07-29
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/trancheProduct")
public class TrancheProductController extends BaseController {

	@Autowired
	private TrancheProductService trancheProductService;
	
	@ModelAttribute
	public TrancheProduct get(@RequestParam(required=false) String id) {
		TrancheProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = trancheProductService.get(id);
		}
		if (entity == null){
			entity = new TrancheProduct();
		}
		return entity;
	}
	
	/**
	 * 一期商品数据列表页面
	 */
	@RequiresPermissions("shop:trancheProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/trancheProductList";
	}
	
	/**
	 * 一期商品数据列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:trancheProduct:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TrancheProduct trancheProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TrancheProduct> page = trancheProductService.findPage(new Page<TrancheProduct>(request, response), trancheProduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑一期商品数据表单页面
	 */
	@RequiresPermissions(value={"shop:trancheProduct:view","shop:trancheProduct:add","shop:trancheProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TrancheProduct trancheProduct, Model model) {
		model.addAttribute("trancheProduct", trancheProduct);
		if(StringUtils.isBlank(trancheProduct.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/trancheProductForm";
	}

	/**
	 * 保存一期商品数据
	 */
	@RequiresPermissions(value={"shop:trancheProduct:add","shop:trancheProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TrancheProduct trancheProduct, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, trancheProduct)){
			return form(trancheProduct, model);
		}
		//新增或编辑表单保存
		trancheProductService.save(trancheProduct);//保存
		addMessage(redirectAttributes, "保存一期商品数据成功");
		return "redirect:"+Global.getAdminPath()+"/shop/trancheProduct/?repage";
	}
	
	/**
	 * 删除一期商品数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:trancheProduct:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TrancheProduct trancheProduct, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		trancheProductService.delete(trancheProduct);
		j.setMsg("删除一期商品数据成功");
		return j;
	}
	
	/**
	 * 批量删除一期商品数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:trancheProduct:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			trancheProductService.delete(trancheProductService.get(id));
		}
		j.setMsg("删除一期商品数据成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:trancheProduct:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(TrancheProduct trancheProduct, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "一期商品数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TrancheProduct> page = trancheProductService.findPage(new Page<TrancheProduct>(request, response, -1), trancheProduct);
    		new ExportExcel("一期商品数据", TrancheProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出一期商品数据记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:trancheProduct:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TrancheProduct> list = ei.getDataList(TrancheProduct.class);
			for (TrancheProduct trancheProduct : list){
				try{
					trancheProductService.save(trancheProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条一期商品数据记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条一期商品数据记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入一期商品数据失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/trancheProduct/?repage";
    }
	
	/**
	 * 下载导入一期商品数据数据模板
	 */
	@RequiresPermissions("shop:trancheProduct:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "一期商品数据数据导入模板.xlsx";
    		List<TrancheProduct> list = Lists.newArrayList(); 
    		new ExportExcel("一期商品数据数据", TrancheProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/trancheProduct/?repage";
    }

}