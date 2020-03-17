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
import com.jhmis.modules.shop.service.*;
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
import com.jhmis.modules.shop.entity.RecommendSpecialtopic;

/**
 * 特别推荐表Controller
 * @author hdx
 * @version 2018-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/recommendSpecialtopic")
public class RecommendSpecialtopicController extends BaseController {

	@Autowired
	private RecommendSpecialtopicService recommendSpecialtopicService;

	@Autowired
	private GoodsCategoryService goodsCategoryService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreGoodsService storeGoodsService;

	@Autowired
	private DictTypeService dictTypeService;
	
	@ModelAttribute
	public RecommendSpecialtopic get(@RequestParam(required=false) String id) {
		RecommendSpecialtopic entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = recommendSpecialtopicService.get(id);
		}
		if (entity == null){
			entity = new RecommendSpecialtopic();
		}
		return entity;
	}
	
	/**
	 * 特别推荐列表页面
	 */
	@RequiresPermissions("shop:recommendSpecialtopic:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/recommendSpecialtopicList";
	}
	
	/**
	 * 特别推荐列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:recommendSpecialtopic:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(RecommendSpecialtopic recommendSpecialtopic, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RecommendSpecialtopic> page = recommendSpecialtopicService.findPage(new Page<RecommendSpecialtopic>(request, response), recommendSpecialtopic); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑特别推荐表单页面
	 */
	@RequiresPermissions(value={"shop:recommendSpecialtopic:view","shop:recommendSpecialtopic:add","shop:recommendSpecialtopic:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(RecommendSpecialtopic recommendSpecialtopic, Model model) {
		DictType dtn = dictTypeService.findUniqueByProperty("type","recommend_specialtopic");
		List<DictValue> dictValueList = dictTypeService.get(dtn.getId()).getDictValueList();
		//字典分类相关
		if(dictValueList!=null && dictValueList.size()>0){
			model.addAttribute("dictValueList", dictValueList);
		}
		//店铺相关信息
		Store s = new Store();
		List<Store> storeList = storeService.findList(s);
		if(storeList!=null && storeList.size()>0){
			model.addAttribute("storeList", storeList);
		}
		//店铺产品
		StoreGoods sg = new StoreGoods();
		List<StoreGoods> sgList =  storeGoodsService.findList(sg);
		if(sgList.size()>0){
			model.addAttribute("sgList", sgList);
		}
		model.addAttribute("recommendSpecialtopic", recommendSpecialtopic);
		if(StringUtils.isBlank(recommendSpecialtopic.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/recommendSpecialtopicForm";
	}

	/**
	 * 保存特别推荐
	 */
	@RequiresPermissions(value={"shop:recommendSpecialtopic:add","shop:recommendSpecialtopic:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(RecommendSpecialtopic recommendSpecialtopic, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, recommendSpecialtopic)){
			return form(recommendSpecialtopic, model);
		}
		//新增或编辑表单保存
		recommendSpecialtopicService.save(recommendSpecialtopic);//保存
		addMessage(redirectAttributes, "保存特别推荐成功");
		return "redirect:"+Global.getAdminPath()+"/shop/recommendSpecialtopic/?repage";
	}
	
	/**
	 * 删除特别推荐
	 */
	@ResponseBody
	@RequiresPermissions("shop:recommendSpecialtopic:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(RecommendSpecialtopic recommendSpecialtopic, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		recommendSpecialtopicService.delete(recommendSpecialtopic);
		j.setMsg("删除特别推荐成功");
		return j;
	}
	
	/**
	 * 批量删除特别推荐
	 */
	@ResponseBody
	@RequiresPermissions("shop:recommendSpecialtopic:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			recommendSpecialtopicService.delete(recommendSpecialtopicService.get(id));
		}
		j.setMsg("删除特别推荐成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:recommendSpecialtopic:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(RecommendSpecialtopic recommendSpecialtopic, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "特别推荐"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<RecommendSpecialtopic> page = recommendSpecialtopicService.findPage(new Page<RecommendSpecialtopic>(request, response, -1), recommendSpecialtopic);
    		new ExportExcel("特别推荐", RecommendSpecialtopic.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出特别推荐记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:recommendSpecialtopic:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<RecommendSpecialtopic> list = ei.getDataList(RecommendSpecialtopic.class);
			for (RecommendSpecialtopic recommendSpecialtopic : list){
				try{
					recommendSpecialtopicService.save(recommendSpecialtopic);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条特别推荐记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条特别推荐记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入特别推荐失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/recommendSpecialtopic/?repage";
    }
	
	/**
	 * 下载导入特别推荐数据模板
	 */
	@RequiresPermissions("shop:recommendSpecialtopic:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "特别推荐数据导入模板.xlsx";
    		List<RecommendSpecialtopic> list = Lists.newArrayList(); 
    		new ExportExcel("特别推荐数据", RecommendSpecialtopic.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/recommendSpecialtopic/?repage";
    }

}