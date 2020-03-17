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
import com.jhmis.modules.shop.entity.purchaser.PurchaserFavorites;
import com.jhmis.modules.shop.service.purchaser.PurchaserFavoritesService;

/**
 * 商品店铺收藏管理Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/purchaser/purchaserFavorites")
public class PurchaserFavoritesController extends BaseController {

	@Autowired
	private PurchaserFavoritesService purchaserFavoritesService;
	
	@ModelAttribute
	public PurchaserFavorites get(@RequestParam(required=false) String id) {
		PurchaserFavorites entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaserFavoritesService.get(id);
		}
		if (entity == null){
			entity = new PurchaserFavorites();
		}
		return entity;
	}
	
	/**
	 * 收藏列表页面
	 */
	@RequiresPermissions("shop:purchaser:purchaserFavorites:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/purchaser/purchaserFavoritesList";
	}
	
	/**
	 * 收藏列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserFavorites:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PurchaserFavorites purchaserFavorites, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaserFavorites> page = purchaserFavoritesService.findPage(new Page<PurchaserFavorites>(request, response), purchaserFavorites); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑收藏表单页面
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaserFavorites:view","shop:purchaser:purchaserFavorites:add","shop:purchaser:purchaserFavorites:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PurchaserFavorites purchaserFavorites, Model model) {
		model.addAttribute("purchaserFavorites", purchaserFavorites);
		return "modules/shop/purchaser/purchaserFavoritesForm";
	}

	/**
	 * 保存收藏
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:purchaser:purchaserFavorites:add","shop:purchaser:purchaserFavorites:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(PurchaserFavorites purchaserFavorites, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, purchaserFavorites)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		purchaserFavoritesService.save(purchaserFavorites);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存收藏成功");
		return j;
	}
	
	/**
	 * 删除收藏
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserFavorites:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PurchaserFavorites purchaserFavorites, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		purchaserFavoritesService.delete(purchaserFavorites);
		j.setMsg("删除收藏成功");
		return j;
	}
	
	/**
	 * 批量删除收藏
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserFavorites:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			purchaserFavoritesService.delete(purchaserFavoritesService.get(id));
		}
		j.setMsg("删除收藏成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserFavorites:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(PurchaserFavorites purchaserFavorites, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "收藏"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PurchaserFavorites> page = purchaserFavoritesService.findPage(new Page<PurchaserFavorites>(request, response, -1), purchaserFavorites);
    		new ExportExcel("收藏", PurchaserFavorites.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出收藏记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:purchaser:purchaserFavorites:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PurchaserFavorites> list = ei.getDataList(PurchaserFavorites.class);
			for (PurchaserFavorites purchaserFavorites : list){
				try{
					purchaserFavoritesService.save(purchaserFavorites);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条收藏记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条收藏记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入收藏失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserFavorites/?repage";
    }
	
	/**
	 * 下载导入收藏数据模板
	 */
	@RequiresPermissions("shop:purchaser:purchaserFavorites:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "收藏数据导入模板.xlsx";
    		List<PurchaserFavorites> list = Lists.newArrayList(); 
    		new ExportExcel("收藏数据", PurchaserFavorites.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserFavorites/?repage";
    }

}