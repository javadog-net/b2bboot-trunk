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
import com.jhmis.modules.shop.entity.Brand;
import com.jhmis.modules.shop.service.BrandService;

/**
 * 品牌管理Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/brand")
public class BrandController extends BaseController {

	@Autowired
	private BrandService brandService;
	
	@ModelAttribute
	public Brand get(@RequestParam(required=false) String id) {
		Brand entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = brandService.get(id);
		}
		if (entity == null){
			entity = new Brand();
		}
		return entity;
	}
	
	/**
	 * 品牌列表页面
	 */
	@RequiresPermissions("shop:brand:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/brandList";
	}
	
	/**
	 * 品牌列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:brand:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Brand brand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Brand> page = brandService.findPage(new Page<Brand>(request, response), brand); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑品牌表单页面
	 */
	@RequiresPermissions(value={"shop:brand:view","shop:brand:add","shop:brand:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Brand brand, Model model) {
		model.addAttribute("brand", brand);
		return "modules/shop/brandForm";
	}

	/**
	 * 保存品牌
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:brand:add","shop:brand:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Brand brand, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, brand)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		brandService.save(brand);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存品牌成功");
		return j;
	}
	
	/**
	 * 删除品牌
	 */
	@ResponseBody
	@RequiresPermissions("shop:brand:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Brand brand, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		brandService.delete(brand);
		j.setMsg("删除品牌成功");
		return j;
	}
	
	/**
	 * 批量删除品牌
	 */
	@ResponseBody
	@RequiresPermissions("shop:brand:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			brandService.delete(brandService.get(id));
		}
		j.setMsg("删除品牌成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:brand:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Brand brand, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "品牌"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Brand> page = brandService.findPage(new Page<Brand>(request, response, -1), brand);
    		new ExportExcel("品牌", Brand.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出品牌记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:brand:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Brand> list = ei.getDataList(Brand.class);
			for (Brand brand : list){
				try{
					brandService.save(brand);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条品牌记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条品牌记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入品牌失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/brand/?repage";
    }
	
	/**
	 * 下载导入品牌数据模板
	 */
	@RequiresPermissions("shop:brand:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "品牌数据导入模板.xlsx";
    		List<Brand> list = Lists.newArrayList(); 
    		new ExportExcel("品牌数据", Brand.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/brand/?repage";
    }

}