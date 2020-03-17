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
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.sys.entity.DictType;
import com.jhmis.modules.sys.entity.DictValue;
import com.jhmis.modules.sys.service.DictTypeService;
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
import com.jhmis.modules.shop.entity.Advert;
import com.jhmis.modules.shop.service.AdvertService;

/**
 * 广告管理Controller
 * @author hdx
 * @version 2018-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/advert")
public class AdvertController extends BaseController {

	@Autowired
	private AdvertService advertService;


	@Autowired
	private DictTypeService dictTypeService;

	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	@ModelAttribute
	public Advert get(@RequestParam(required=false) String id) {
		Advert entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = advertService.get(id);
		}
		if (entity == null){
			entity = new Advert();
		}
		return entity;
	}
	
	/**
	 * 广告列表页面
	 */
	@RequiresPermissions("shop:advert:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/advertList";
	}
	
	/**
	 * 广告列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:advert:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Advert advert, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Advert> page = advertService.findPage(new Page<Advert>(request, response), advert); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑广告表单页面
	 */
	@RequiresPermissions(value={"shop:advert:view","shop:advert:add","shop:advert:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Advert advert, Model model) {
		model.addAttribute("advert", advert);
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

		DictType dtn = dictTypeService.findUniqueByProperty("type","recommend_specialtopic");
		List<DictValue> dictValueList = dictTypeService.get(dtn.getId()).getDictValueList();
		//字典分类相关
		if(dictValueList.size()>0){
			model.addAttribute("dictValueList", dictValueList);
		}

		if(StringUtils.isBlank(advert.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/advertForm";
	}

	/**
	 * 保存广告
	 */
	@RequiresPermissions(value={"shop:advert:add","shop:advert:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Advert advert, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, advert)){
			return form(advert, model);
		}
		if(advert.getRelevantId().length()>32){
			advert.setRelevantId(advert.getRelevantId().replace(",",""));
		}
		//新增或编辑表单保存
		advertService.save(advert);//保存
		addMessage(redirectAttributes, "保存广告成功");
		return "redirect:"+Global.getAdminPath()+"/shop/advert/?repage";
	}
	
	/**
	 * 删除广告
	 */
	@ResponseBody
	@RequiresPermissions("shop:advert:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Advert advert, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		advertService.delete(advert);
		j.setMsg("删除广告成功");
		return j;
	}
	
	/**
	 * 批量删除广告
	 */
	@ResponseBody
	@RequiresPermissions("shop:advert:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			advertService.delete(advertService.get(id));
		}
		j.setMsg("删除广告成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:advert:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Advert advert, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "广告"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Advert> page = advertService.findPage(new Page<Advert>(request, response, -1), advert);
    		new ExportExcel("广告", Advert.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出广告记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:advert:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Advert> list = ei.getDataList(Advert.class);
			for (Advert advert : list){
				try{
					advertService.save(advert);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条广告记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条广告记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入广告失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/advert/?repage";
    }
	
	/**
	 * 下载导入广告数据模板
	 */
	@RequiresPermissions("shop:advert:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "广告数据导入模板.xlsx";
    		List<Advert> list = Lists.newArrayList(); 
    		new ExportExcel("广告数据", Advert.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/advert/?repage";
    }

}