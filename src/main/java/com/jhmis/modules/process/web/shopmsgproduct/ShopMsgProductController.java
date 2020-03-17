/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.web.shopmsgproduct;

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
import com.jhmis.modules.process.entity.shopmsgproduct.ShopMsgProduct;
import com.jhmis.modules.process.service.shopmsgproduct.ShopMsgProductService;

/**
 * 需求产品子表Controller
 * @author hdx
 * @version 2019-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/process/shopmsgproduct/shopMsgProduct")
public class ShopMsgProductController extends BaseController {

	@Autowired
	private ShopMsgProductService shopMsgProductService;
	
	@ModelAttribute
	public ShopMsgProduct get(@RequestParam(required=false) String id) {
		ShopMsgProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopMsgProductService.get(id);
		}
		if (entity == null){
			entity = new ShopMsgProduct();
		}
		return entity;
	}
	
	/**
	 * 需求产品表列表页面
	 */
	@RequiresPermissions("process:shopmsgproduct:shopMsgProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/process/shopmsgproduct/shopMsgProductList";
	}
	
	/**
	 * 需求产品表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgproduct:shopMsgProduct:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ShopMsgProduct shopMsgProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopMsgProduct> page = shopMsgProductService.findPage(new Page<ShopMsgProduct>(request, response), shopMsgProduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑需求产品表表单页面
	 */
	@RequiresPermissions(value={"process:shopmsgproduct:shopMsgProduct:view","process:shopmsgproduct:shopMsgProduct:add","process:shopmsgproduct:shopMsgProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ShopMsgProduct shopMsgProduct, Model model) {
		model.addAttribute("shopMsgProduct", shopMsgProduct);
		if(StringUtils.isBlank(shopMsgProduct.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/process/shopmsgproduct/shopMsgProductForm";
	}

	/**
	 * 保存需求产品表
	 */
	@RequiresPermissions(value={"process:shopmsgproduct:shopMsgProduct:add","process:shopmsgproduct:shopMsgProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ShopMsgProduct shopMsgProduct, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, shopMsgProduct)){
			return form(shopMsgProduct, model);
		}
		//新增或编辑表单保存
		shopMsgProductService.save(shopMsgProduct);//保存
		addMessage(redirectAttributes, "保存需求产品表成功");
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgproduct/shopMsgProduct/?repage";
	}
	
	/**
	 * 删除需求产品表
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgproduct:shopMsgProduct:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ShopMsgProduct shopMsgProduct, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		shopMsgProductService.delete(shopMsgProduct);
		j.setMsg("删除需求产品表成功");
		return j;
	}
	
	/**
	 * 批量删除需求产品表
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgproduct:shopMsgProduct:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(";");
		for(String id : idArray){
			shopMsgProductService.delete(shopMsgProductService.get(id));
		}
		j.setMsg("删除需求产品表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgproduct:shopMsgProduct:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ShopMsgProduct shopMsgProduct, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "需求产品表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ShopMsgProduct> page = shopMsgProductService.findPage(new Page<ShopMsgProduct>(request, response, -1), shopMsgProduct);
    		new ExportExcel("需求产品表", ShopMsgProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出需求产品表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:shopmsgproduct:shopMsgProduct:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ShopMsgProduct> list = ei.getDataList(ShopMsgProduct.class);
			for (ShopMsgProduct shopMsgProduct : list){
				try{
					shopMsgProductService.save(shopMsgProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条需求产品表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条需求产品表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入需求产品表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgproduct/shopMsgProduct/?repage";
    }
	
	/**
	 * 下载导入需求产品表数据模板
	 */
	@RequiresPermissions("process:shopmsgproduct:shopMsgProduct:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "需求产品表数据导入模板.xlsx";
    		List<ShopMsgProduct> list = Lists.newArrayList(); 
    		new ExportExcel("需求产品表数据", ShopMsgProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgproduct/shopMsgProduct/?repage";
    }

}