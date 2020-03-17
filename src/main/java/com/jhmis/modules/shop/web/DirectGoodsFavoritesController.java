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
import com.jhmis.modules.shop.entity.DirectGoodsFavorites;
import com.jhmis.modules.shop.service.DirectGoodsFavoritesService;

/**
 * 直采Controller
 * @author tc
 * @version 2019-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/directGoodsFavorites")
public class DirectGoodsFavoritesController extends BaseController {

	@Autowired
	private DirectGoodsFavoritesService directGoodsFavoritesService;
	
	@ModelAttribute
	public DirectGoodsFavorites get(@RequestParam(required=false) String id) {
		DirectGoodsFavorites entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = directGoodsFavoritesService.get(id);
		}
		if (entity == null){
			entity = new DirectGoodsFavorites();
		}
		return entity;
	}
	
	/**
	 * 直采列表页面
	 */
	@RequiresPermissions("shop:directGoodsFavorites:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/directGoodsFavoritesList";
	}
	
	/**
	 * 直采列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:directGoodsFavorites:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(DirectGoodsFavorites directGoodsFavorites, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DirectGoodsFavorites> page = directGoodsFavoritesService.findPage(new Page<DirectGoodsFavorites>(request, response), directGoodsFavorites); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑直采表单页面
	 */
	@RequiresPermissions(value={"shop:directGoodsFavorites:view","shop:directGoodsFavorites:add","shop:directGoodsFavorites:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DirectGoodsFavorites directGoodsFavorites, Model model) {
		model.addAttribute("directGoodsFavorites", directGoodsFavorites);
		if(StringUtils.isBlank(directGoodsFavorites.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/directGoodsFavoritesForm";
	}

	/**
	 * 保存直采
	 */
	@RequiresPermissions(value={"shop:directGoodsFavorites:add","shop:directGoodsFavorites:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DirectGoodsFavorites directGoodsFavorites, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, directGoodsFavorites)){
			return form(directGoodsFavorites, model);
		}
		//新增或编辑表单保存
		directGoodsFavoritesService.save(directGoodsFavorites);//保存
		addMessage(redirectAttributes, "保存直采成功");
		return "redirect:"+Global.getAdminPath()+"/shop/directGoodsFavorites/?repage";
	}
	
	/**
	 * 删除直采
	 */
	@ResponseBody
	@RequiresPermissions("shop:directGoodsFavorites:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(DirectGoodsFavorites directGoodsFavorites, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		directGoodsFavoritesService.delete(directGoodsFavorites);
		j.setMsg("删除直采成功");
		return j;
	}
	
	/**
	 * 批量删除直采
	 */
	@ResponseBody
	@RequiresPermissions("shop:directGoodsFavorites:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			directGoodsFavoritesService.delete(directGoodsFavoritesService.get(id));
		}
		j.setMsg("删除直采成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:directGoodsFavorites:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(DirectGoodsFavorites directGoodsFavorites, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "直采"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DirectGoodsFavorites> page = directGoodsFavoritesService.findPage(new Page<DirectGoodsFavorites>(request, response, -1), directGoodsFavorites);
    		new ExportExcel("直采", DirectGoodsFavorites.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出直采记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:directGoodsFavorites:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DirectGoodsFavorites> list = ei.getDataList(DirectGoodsFavorites.class);
			for (DirectGoodsFavorites directGoodsFavorites : list){
				try{
					directGoodsFavoritesService.save(directGoodsFavorites);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条直采记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条直采记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入直采失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/directGoodsFavorites/?repage";
    }
	
	/**
	 * 下载导入直采数据模板
	 */
	@RequiresPermissions("shop:directGoodsFavorites:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "直采数据导入模板.xlsx";
    		List<DirectGoodsFavorites> list = Lists.newArrayList(); 
    		new ExportExcel("直采数据", DirectGoodsFavorites.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/directGoodsFavorites/?repage";
    }

}