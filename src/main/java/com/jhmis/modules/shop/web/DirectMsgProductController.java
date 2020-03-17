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
import com.jhmis.modules.shop.entity.DirectMsgProduct;
import com.jhmis.modules.shop.service.DirectMsgProductService;

/**
 * 直采需求商品Controller
 * @author hdx
 * @version 2019-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/directMsgProduct")
public class DirectMsgProductController extends BaseController {

	@Autowired
	private DirectMsgProductService directMsgProductService;
	
	@ModelAttribute
	public DirectMsgProduct get(@RequestParam(required=false) String id) {
		DirectMsgProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = directMsgProductService.get(id);
		}
		if (entity == null){
			entity = new DirectMsgProduct();
		}
		return entity;
	}
	
	/**
	 * 直采需求商品列表页面
	 */
	@RequiresPermissions("shop:directMsgProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/directMsgProductList";
	}
	
	/**
	 * 直采需求商品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:directMsgProduct:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(DirectMsgProduct directMsgProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DirectMsgProduct> page = directMsgProductService.findPage(new Page<DirectMsgProduct>(request, response), directMsgProduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑直采需求商品表单页面
	 */
	@RequiresPermissions(value={"shop:directMsgProduct:view","shop:directMsgProduct:add","shop:directMsgProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DirectMsgProduct directMsgProduct, Model model) {
		model.addAttribute("directMsgProduct", directMsgProduct);
		if(StringUtils.isBlank(directMsgProduct.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/directMsgProductForm";
	}

	/**
	 * 保存直采需求商品
	 */
	@RequiresPermissions(value={"shop:directMsgProduct:add","shop:directMsgProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DirectMsgProduct directMsgProduct, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, directMsgProduct)){
			return form(directMsgProduct, model);
		}
		//新增或编辑表单保存
		directMsgProductService.save(directMsgProduct);//保存
		addMessage(redirectAttributes, "保存直采需求商品成功");
		return "redirect:"+Global.getAdminPath()+"/shop/directMsgProduct/?repage";
	}
	
	/**
	 * 删除直采需求商品
	 */
	@ResponseBody
	@RequiresPermissions("shop:directMsgProduct:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(DirectMsgProduct directMsgProduct, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		directMsgProductService.delete(directMsgProduct);
		j.setMsg("删除直采需求商品成功");
		return j;
	}
	
	/**
	 * 批量删除直采需求商品
	 */
	@ResponseBody
	@RequiresPermissions("shop:directMsgProduct:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			directMsgProductService.delete(directMsgProductService.get(id));
		}
		j.setMsg("删除直采需求商品成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:directMsgProduct:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(DirectMsgProduct directMsgProduct, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "直采需求商品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DirectMsgProduct> page = directMsgProductService.findPage(new Page<DirectMsgProduct>(request, response, -1), directMsgProduct);
    		new ExportExcel("直采需求商品", DirectMsgProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出直采需求商品记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:directMsgProduct:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DirectMsgProduct> list = ei.getDataList(DirectMsgProduct.class);
			for (DirectMsgProduct directMsgProduct : list){
				try{
					directMsgProductService.save(directMsgProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条直采需求商品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条直采需求商品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入直采需求商品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/directMsgProduct/?repage";
    }
	
	/**
	 * 下载导入直采需求商品数据模板
	 */
	@RequiresPermissions("shop:directMsgProduct:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "直采需求商品数据导入模板.xlsx";
    		List<DirectMsgProduct> list = Lists.newArrayList(); 
    		new ExportExcel("直采需求商品数据", DirectMsgProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/directMsgProduct/?repage";
    }

}