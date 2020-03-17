/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.web;

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
import com.jhmis.modules.old.entity.OldShopProjectProduct;
import com.jhmis.modules.old.service.OldShopProjectProductService;

/**
 * 1Controller
 * @author 1
 * @version 2019-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/old/oldShopProjectProduct")
public class OldShopProjectProductController extends BaseController {

	@Autowired
	private OldShopProjectProductService oldShopProjectProductService;
	
	@ModelAttribute
	public OldShopProjectProduct get(@RequestParam(required=false) String id) {
		OldShopProjectProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oldShopProjectProductService.get(id);
		}
		if (entity == null){
			entity = new OldShopProjectProduct();
		}
		return entity;
	}
	
	/**
	 * 1列表页面
	 */
	@RequiresPermissions("old:oldShopProjectProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/old/oldShopProjectProductList";
	}
	
	/**
	 * 1列表数据
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectProduct:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OldShopProjectProduct oldShopProjectProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OldShopProjectProduct> page = oldShopProjectProductService.findPage(new Page<OldShopProjectProduct>(request, response), oldShopProjectProduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑1表单页面
	 */
	@RequiresPermissions(value={"old:oldShopProjectProduct:view","old:oldShopProjectProduct:add","old:oldShopProjectProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OldShopProjectProduct oldShopProjectProduct, Model model) {
		model.addAttribute("oldShopProjectProduct", oldShopProjectProduct);
		if(StringUtils.isBlank(oldShopProjectProduct.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/old/oldShopProjectProductForm";
	}

	/**
	 * 保存1
	 */
	@RequiresPermissions(value={"old:oldShopProjectProduct:add","old:oldShopProjectProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OldShopProjectProduct oldShopProjectProduct, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, oldShopProjectProduct)){
			return form(oldShopProjectProduct, model);
		}
		//新增或编辑表单保存
		oldShopProjectProductService.save(oldShopProjectProduct);//保存
		addMessage(redirectAttributes, "保存1成功");
		return "redirect:"+Global.getAdminPath()+"/old/oldShopProjectProduct/?repage";
	}
	
	/**
	 * 删除1
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectProduct:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OldShopProjectProduct oldShopProjectProduct, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oldShopProjectProductService.delete(oldShopProjectProduct);
		j.setMsg("删除1成功");
		return j;
	}
	
	/**
	 * 批量删除1
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectProduct:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oldShopProjectProductService.delete(oldShopProjectProductService.get(id));
		}
		j.setMsg("删除1成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectProduct:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OldShopProjectProduct oldShopProjectProduct, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "1"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OldShopProjectProduct> page = oldShopProjectProductService.findPage(new Page<OldShopProjectProduct>(request, response, -1), oldShopProjectProduct);
    		new ExportExcel("1", OldShopProjectProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出1记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("old:oldShopProjectProduct:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OldShopProjectProduct> list = ei.getDataList(OldShopProjectProduct.class);
			for (OldShopProjectProduct oldShopProjectProduct : list){
				try{
					oldShopProjectProductService.save(oldShopProjectProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条1记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条1记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入1失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/old/oldShopProjectProduct/?repage";
    }
	
	/**
	 * 下载导入1数据模板
	 */
	@RequiresPermissions("old:oldShopProjectProduct:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "1数据导入模板.xlsx";
    		List<OldShopProjectProduct> list = Lists.newArrayList(); 
    		new ExportExcel("1数据", OldShopProjectProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/old/oldShopProjectProduct/?repage";
    }

}