/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.Store;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import com.jhmis.modules.shop.service.StoreService;
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
import com.jhmis.modules.shop.entity.RecommendCat;
import com.jhmis.modules.shop.service.RecommendCatService;

/**
 * 分类推荐表Controller
 * @author hdx
 * @version 2018-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/recommendCat")
public class RecommendCatController extends BaseController {

	@Autowired
	private RecommendCatService recommendCatService;

	@Autowired
	private GoodsCategoryService goodsCategoryService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreGoodsService storeGoodsService;
	
	@ModelAttribute
	public RecommendCat get(@RequestParam(required=false) String id) {
		RecommendCat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = recommendCatService.get(id);
		}
		if (entity == null){
			entity = new RecommendCat();
		}
		return entity;
	}
	
	/**
	 * 分类推荐列表页面
	 */
	@RequiresPermissions("shop:recommendCat:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/recommendCatList";
	}
	
	/**
	 * 分类推荐列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:recommendCat:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(RecommendCat recommendCat, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RecommendCat> page = recommendCatService.findPage(new Page<RecommendCat>(request, response), recommendCat); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑分类推荐表单页面
	 */
	@RequiresPermissions(value={"shop:recommendCat:view","shop:recommendCat:add","shop:recommendCat:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(RecommendCat recommendCat, Model model) {
		//父级分类（大类）
		GoodsCategory goodsParCategory = new GoodsCategory();
		GoodsCategory goodsCategory = new GoodsCategory();
		goodsCategory.setId("0");
		goodsCategory.setParent(goodsCategory);
		//大类相关信息
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.findList(goodsCategory);
		if(goodsCategoryList.size()>0){
			model.addAttribute("goodsCategoryList", goodsCategoryList);
		}
		//店铺相关信息
		Store s = new Store();
		List<Store> storeList = storeService.findList(s);
		if(storeList.size()>0){
			model.addAttribute("storeList", storeList);
		}
		//店铺产品
		StoreGoods sg = new StoreGoods();
		List<StoreGoods> sgList =  storeGoodsService.findList(sg);
		if(sgList.size()>0){
			model.addAttribute("sgList", sgList);
		}
		model.addAttribute("recommendCat", recommendCat);
		if(StringUtils.isBlank(recommendCat.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/recommendCatForm";
	}

	/**
	 * 保存分类推荐
	 */
	@RequiresPermissions(value={"shop:recommendCat:add","shop:recommendCat:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(RecommendCat recommendCat, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, recommendCat)){
			return form(recommendCat, model);
		}
		//新增或编辑表单保存
		recommendCatService.save(recommendCat);//保存
		addMessage(redirectAttributes, "保存分类推荐成功");
		return "redirect:"+Global.getAdminPath()+"/shop/recommendCat/?repage";
	}
	
	/**
	 * 删除分类推荐
	 */
	@ResponseBody
	@RequiresPermissions("shop:recommendCat:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(RecommendCat recommendCat, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		recommendCatService.delete(recommendCat);
		j.setMsg("删除分类推荐成功");
		return j;
	}
	
	/**
	 * 批量删除分类推荐
	 */
	@ResponseBody
	@RequiresPermissions("shop:recommendCat:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			recommendCatService.delete(recommendCatService.get(id));
		}
		j.setMsg("删除分类推荐成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:recommendCat:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(RecommendCat recommendCat, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "分类推荐"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<RecommendCat> page = recommendCatService.findPage(new Page<RecommendCat>(request, response, -1), recommendCat);
    		new ExportExcel("分类推荐", RecommendCat.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出分类推荐记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:recommendCat:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<RecommendCat> list = ei.getDataList(RecommendCat.class);
			for (RecommendCat recommendCat : list){
				try{
					recommendCatService.save(recommendCat);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条分类推荐记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条分类推荐记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入分类推荐失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/recommendCat/?repage";
    }
	
	/**
	 * 下载导入分类推荐数据模板
	 */
	@RequiresPermissions("shop:recommendCat:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分类推荐数据导入模板.xlsx";
    		List<RecommendCat> list = Lists.newArrayList(); 
    		new ExportExcel("分类推荐数据", RecommendCat.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/recommendCat/?repage";
    }

}