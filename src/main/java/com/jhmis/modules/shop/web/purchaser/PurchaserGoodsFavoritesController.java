/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.purchaser;

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
import com.jhmis.modules.shop.entity.purchaser.PurchaserGoodsFavorites;
import com.jhmis.modules.shop.service.purchaser.PurchaserGoodsFavoritesService;

/**
 * 商品收藏Controller
 * @author hdx
 * @version 2018-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/purchaser/purchaserGoodsFavorites")
public class PurchaserGoodsFavoritesController extends BaseController {

	@Autowired
	private PurchaserGoodsFavoritesService purchaserGoodsFavoritesService;
	
	@ModelAttribute
	public PurchaserGoodsFavorites get(@RequestParam(required=false) String id) {
		PurchaserGoodsFavorites entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaserGoodsFavoritesService.get(id);
		}
		if (entity == null){
			entity = new PurchaserGoodsFavorites();
		}
		return entity;
	}
	
	/**
	 * 商品收藏列表页面
	 */
	@RequiresPermissions("shop:purchaser:purchaserGoodsFavorites:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/purchaser/purchaserGoodsFavoritesList";
	}
	
	/**
	 * 商品收藏列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserGoodsFavorites:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PurchaserGoodsFavorites purchaserGoodsFavorites, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaserGoodsFavorites> page = purchaserGoodsFavoritesService.findPage(new Page<PurchaserGoodsFavorites>(request, response), purchaserGoodsFavorites); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品收藏表单页面
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaserGoodsFavorites:view","shop:purchaser:purchaserGoodsFavorites:add","shop:purchaser:purchaserGoodsFavorites:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PurchaserGoodsFavorites purchaserGoodsFavorites, Model model) {
		model.addAttribute("purchaserGoodsFavorites", purchaserGoodsFavorites);
		if(StringUtils.isBlank(purchaserGoodsFavorites.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/purchaser/purchaserGoodsFavoritesForm";
	}

	/**
	 * 保存商品收藏
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaserGoodsFavorites:add","shop:purchaser:purchaserGoodsFavorites:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PurchaserGoodsFavorites purchaserGoodsFavorites, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, purchaserGoodsFavorites)){
			return form(purchaserGoodsFavorites, model);
		}
		//新增或编辑表单保存
		purchaserGoodsFavoritesService.save(purchaserGoodsFavorites);//保存
		addMessage(redirectAttributes, "保存商品收藏成功");
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserGoodsFavorites/?repage";
	}
	
	/**
	 * 删除商品收藏
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserGoodsFavorites:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PurchaserGoodsFavorites purchaserGoodsFavorites, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		purchaserGoodsFavoritesService.delete(purchaserGoodsFavorites);
		j.setMsg("删除商品收藏成功");
		return j;
	}
	
	/**
	 * 批量删除商品收藏
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserGoodsFavorites:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			purchaserGoodsFavoritesService.delete(purchaserGoodsFavoritesService.get(id));
		}
		j.setMsg("删除商品收藏成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserGoodsFavorites:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(PurchaserGoodsFavorites purchaserGoodsFavorites, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品收藏"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PurchaserGoodsFavorites> page = purchaserGoodsFavoritesService.findPage(new Page<PurchaserGoodsFavorites>(request, response, -1), purchaserGoodsFavorites);
    		new ExportExcel("商品收藏", PurchaserGoodsFavorites.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品收藏记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:purchaser:purchaserGoodsFavorites:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PurchaserGoodsFavorites> list = ei.getDataList(PurchaserGoodsFavorites.class);
			for (PurchaserGoodsFavorites purchaserGoodsFavorites : list){
				try{
					purchaserGoodsFavoritesService.save(purchaserGoodsFavorites);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品收藏记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品收藏记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品收藏失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserGoodsFavorites/?repage";
    }
	
	/**
	 * 下载导入商品收藏数据模板
	 */
	@RequiresPermissions("shop:purchaser:purchaserGoodsFavorites:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品收藏数据导入模板.xlsx";
    		List<PurchaserGoodsFavorites> list = Lists.newArrayList(); 
    		new ExportExcel("商品收藏数据", PurchaserGoodsFavorites.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserGoodsFavorites/?repage";
    }

}