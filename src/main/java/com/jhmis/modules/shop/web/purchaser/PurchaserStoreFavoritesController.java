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
import com.jhmis.modules.shop.entity.purchaser.PurchaserStoreFavorites;
import com.jhmis.modules.shop.service.purchaser.PurchaserStoreFavoritesService;

/**
 * 店铺收藏Controller
 * @author hdx
 * @version 2018-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/purchaser/purchaserStoreFavorites")
public class PurchaserStoreFavoritesController extends BaseController {

	@Autowired
	private PurchaserStoreFavoritesService purchaserStoreFavoritesService;
	
	@ModelAttribute
	public PurchaserStoreFavorites get(@RequestParam(required=false) String id) {
		PurchaserStoreFavorites entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaserStoreFavoritesService.get(id);
		}
		if (entity == null){
			entity = new PurchaserStoreFavorites();
		}
		return entity;
	}
	
	/**
	 * 店铺收藏列表页面
	 */
	@RequiresPermissions("shop:purchaser:purchaserStoreFavorites:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/purchaser/purchaserStoreFavoritesList";
	}
	
	/**
	 * 店铺收藏列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserStoreFavorites:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PurchaserStoreFavorites purchaserStoreFavorites, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaserStoreFavorites> page = purchaserStoreFavoritesService.findPage(new Page<PurchaserStoreFavorites>(request, response), purchaserStoreFavorites); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑店铺收藏表单页面
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaserStoreFavorites:view","shop:purchaser:purchaserStoreFavorites:add","shop:purchaser:purchaserStoreFavorites:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PurchaserStoreFavorites purchaserStoreFavorites, Model model) {
		model.addAttribute("purchaserStoreFavorites", purchaserStoreFavorites);
		if(StringUtils.isBlank(purchaserStoreFavorites.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/purchaser/purchaserStoreFavoritesForm";
	}

	/**
	 * 保存店铺收藏
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaserStoreFavorites:add","shop:purchaser:purchaserStoreFavorites:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PurchaserStoreFavorites purchaserStoreFavorites, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, purchaserStoreFavorites)){
			return form(purchaserStoreFavorites, model);
		}
		//新增或编辑表单保存
		purchaserStoreFavoritesService.save(purchaserStoreFavorites);//保存
		addMessage(redirectAttributes, "保存店铺收藏成功");
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserStoreFavorites/?repage";
	}
	
	/**
	 * 删除店铺收藏
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserStoreFavorites:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PurchaserStoreFavorites purchaserStoreFavorites, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		purchaserStoreFavoritesService.delete(purchaserStoreFavorites);
		j.setMsg("删除店铺收藏成功");
		return j;
	}
	
	/**
	 * 批量删除店铺收藏
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserStoreFavorites:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			purchaserStoreFavoritesService.delete(purchaserStoreFavoritesService.get(id));
		}
		j.setMsg("删除店铺收藏成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserStoreFavorites:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(PurchaserStoreFavorites purchaserStoreFavorites, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "店铺收藏"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PurchaserStoreFavorites> page = purchaserStoreFavoritesService.findPage(new Page<PurchaserStoreFavorites>(request, response, -1), purchaserStoreFavorites);
    		new ExportExcel("店铺收藏", PurchaserStoreFavorites.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出店铺收藏记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:purchaser:purchaserStoreFavorites:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PurchaserStoreFavorites> list = ei.getDataList(PurchaserStoreFavorites.class);
			for (PurchaserStoreFavorites purchaserStoreFavorites : list){
				try{
					purchaserStoreFavoritesService.save(purchaserStoreFavorites);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条店铺收藏记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条店铺收藏记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入店铺收藏失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserStoreFavorites/?repage";
    }
	
	/**
	 * 下载导入店铺收藏数据模板
	 */
	@RequiresPermissions("shop:purchaser:purchaserStoreFavorites:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "店铺收藏数据导入模板.xlsx";
    		List<PurchaserStoreFavorites> list = Lists.newArrayList(); 
    		new ExportExcel("店铺收藏数据", PurchaserStoreFavorites.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserStoreFavorites/?repage";
    }

}