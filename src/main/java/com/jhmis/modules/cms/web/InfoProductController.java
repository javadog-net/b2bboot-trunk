/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;

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
import com.jhmis.modules.cms.entity.InfoProduct;
import com.jhmis.modules.cms.service.InfoProductService;

/**
 * 文章关联产品Controller
 * @author lydia
 * @version 2019-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/infoProduct")
public class InfoProductController extends BaseController {

	@Autowired
	private InfoProductService infoProductService;
	
	@ModelAttribute
	public InfoProduct get(@RequestParam(required=false) String id) {
		InfoProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = infoProductService.get(id);
		}
		if (entity == null){
			entity = new InfoProduct();
		}
		return entity;
	}
	
	/**
	 * 文章关联产品列表页面
	 */
	@RequiresPermissions("cms:infoProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/infoProductList";
	}
	
	/**
	 * 文章关联产品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:infoProduct:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(InfoProduct infoProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InfoProduct> page = infoProductService.findPage(new Page<InfoProduct>(request, response), infoProduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑文章关联产品表单页面
	 */
	@RequiresPermissions(value={"cms:infoProduct:view","cms:infoProduct:add","cms:infoProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(InfoProduct infoProduct, Model model) {
		model.addAttribute("infoProduct", infoProduct);
		if(StringUtils.isBlank(infoProduct.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/cms/infoProductForm";
	}

	/**
	 * 保存文章关联产品
	 */
	@RequiresPermissions(value={"cms:infoProduct:add","cms:infoProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(InfoProduct infoProduct, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, infoProduct)){
			return form(infoProduct, model);
		}
		//新增或编辑表单保存
		infoProductService.save(infoProduct);//保存
		addMessage(redirectAttributes, "保存文章关联产品成功");
		return "redirect:"+Global.getAdminPath()+"/cms/infoProduct/?repage";
	}
	
	/**
	 * 删除文章关联产品
	 */
	@ResponseBody
	@RequiresPermissions("cms:infoProduct:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(InfoProduct infoProduct, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		infoProductService.delete(infoProduct);
		j.setMsg("删除文章关联产品成功");
		return j;
	}
	
	/**
	 * 批量删除文章关联产品
	 */
	@ResponseBody
	@RequiresPermissions("cms:infoProduct:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			infoProductService.delete(infoProductService.get(id));
		}
		j.setMsg("删除文章关联产品成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:infoProduct:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(InfoProduct infoProduct, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "文章关联产品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<InfoProduct> page = infoProductService.findPage(new Page<InfoProduct>(request, response, -1), infoProduct);
    		new ExportExcel("文章关联产品", InfoProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出文章关联产品记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:infoProduct:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<InfoProduct> list = ei.getDataList(InfoProduct.class);
			for (InfoProduct infoProduct : list){
				try{
					infoProductService.save(infoProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条文章关联产品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条文章关联产品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入文章关联产品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/infoProduct/?repage";
    }
	
	/**
	 * 下载导入文章关联产品数据模板
	 */
	@RequiresPermissions("cms:infoProduct:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "文章关联产品数据导入模板.xlsx";
    		List<InfoProduct> list = Lists.newArrayList(); 
    		new ExportExcel("文章关联产品数据", InfoProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/infoProduct/?repage";
    }

}