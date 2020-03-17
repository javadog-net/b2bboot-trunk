/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.CmsModel;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.entity.InfoImg;
import com.jhmis.modules.cms.service.*;
import com.jhmis.modules.cms.utils.CmsEnum;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 栏目管理Controller
 * @author lydia
 * @version 2019-09-02
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;
    @Autowired
   	private HtmlService htmlService;
    @Autowired
	private CmsModelService cmsModelService;
	@Autowired
	private TemplateService templateService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private InfoImgService infoImgService;

	
	@ModelAttribute("category")
	public Category get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return categoryService.get(id);
		}else{
			return new Category();
		}
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Category> list = Lists.newArrayList();
		List<Category> sourcelist = categoryService.findByModelId( null);
		Category.sortList(list, sourcelist, "0");
        model.addAttribute("list", list);
		return "modules/cms/categoryList";
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = "form")
	public String form(Category category, Model model) {
		Category parent = null;
		if (category.getParent()==null||category.getParent().getId()==null || "0".equals(category.getParent().getId()) || StringUtils.isBlank(category.getParent().getId())){
			parent = new Category("0");
		} else{
			parent = categoryService.get(category.getParent().getId());;
		}
		category.setParent(parent);
		model.addAttribute("category", category);
		List<CmsModel> modelList = cmsModelService.findCmsModelList();
		model.addAttribute("modelList",modelList);
		Map<String,List<String>> mapList = templateService.getTemplate(templatePath);
		mapList.forEach((key,value)->{
			model.addAttribute(key,value);
		});
		return "modules/cms/categoryForm";
	}
	
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "save")
	public String save(Category category, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, category)){
			return form(category, model);
		}
		//新增的时候 栏目序号为空 ，把栏目需要设置为最大号
		//TODO 是否有必要设置为同级栏目的最大序号？?
		if (category.getSort()==null) {
			category.setSort(categoryService.maxNum(category));
		}
		categoryService.save(category);
//		addMessage(redirectAttributes, "保存栏目'" + category.getName() + "'成功");
//		return "redirect:" + adminPath + "/cms/category/";
		return "modules/cms/categoryMakehtml";
	}
	@ResponseBody
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Category category, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		if (Category.isRoot(category.getId())){
			AjaxJson.fail("删除栏目失败, 不允许删除顶级栏目或编号为空");
		}else{
			categoryService.delete(category);
			AjaxJson.ok("删除栏目成功");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String model, @RequestParam(required=false) String extId, HttpServletResponse response) {
 		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Category> list = categoryService.findByModelId(model);
		int j = 0;
		for (int i=0; i<list.size(); i++){
			Category e = list.get(i);
			if (StringUtils.isEmpty(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds() != null && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				if("0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					//TODO 下面这句话有问题
					if(0==j){
						state.put("selected", true);
					}
					j++;
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				map.put("name", e.getName());
				map.put("text", e.getName());
				map.put("module", "info");
				map.put("data", "info");
				Map<String,String> map2 = Maps.newHashMap();
				map2.put("module","info");
				map.put("a_attr",map2);
				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 * 查询栏目标识是否已存在
	 * @param category
	 * @return
	 */
	@ResponseBody
   	@RequestMapping("/checkCategoryMark")
   	public String checkCategoryMark(Category category,String oldCategoryMark){
		if(category == null){
			return "false";
		}
		if(StringUtils.isNotEmpty(oldCategoryMark) || category.getCategoryMark().equals(oldCategoryMark)){
			return "true";
		}else{
			List<Category> categoryList = categoryService.findList(category);
			if(categoryList.size()>0){
				return "false";
			}
		}
		return "true";
	}

	/**
	 * 判断栏目名称是否已存在
	 * @param category
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkCategoryName")
	public String checkCategoryName(Category category,String oldCategoryName){
		if(category == null){
			return "false";
		}
		if(StringUtils.isNotEmpty(oldCategoryName) || category.getName().equals(oldCategoryName)){
			return "true";
		}else{
			List<Category> categoryList = categoryService.findList(category);
			if(categoryList.size()>0){
				return "false";
			}
		}
		return "true";
	}


	/**
	 * 栏目页静态化
	 * @param id
	 * @param htmlCategory
	 * @param htmlCategoryPar
	 * @param htmlIndex
	 * @return
	 */
	@RequestMapping("/categoryMakehtml")
	public String categoryMakehtml(HttpServletRequest request,String id, String htmlCategory, String htmlCategoryPar, String htmlIndex,RedirectAttributes redirectAttributes){
		Category category = null;
		if(StringUtils.isNotEmpty(id)){
			//查询栏目信息
			 category = categoryService.findUniqueByProperty("id",id);
			//本栏目页静态化
			try {
				Map<String,Object> data = getData(request);
				if (Global.YES.equals(htmlCategory)) {
					data.put("currCategory", category);
					htmlService.htmlCategory(data,category,1);
				}
				//本栏目的父栏目静态化
				if (Global.YES.equals(htmlCategoryPar)) {
					//查询本栏目下的
                    List<Category> categoryList = categoryService.findByIdPath(id);
                   	for(Category category1:categoryList){
						data.put("currCategory", category1);
						htmlService.htmlCategory(data,category1,1);
					}
                }
				//首页静态化
				if (Global.YES.equals(htmlIndex)) {
                    htmlService.htmlIndex(data);
				}
			}catch (IOException e){
				addMessage(redirectAttributes, "静态化栏目'" + category.getName() + "'失败");
				logger.error("静态化栏目页失败："+e.getMessage());
				e.printStackTrace();
			}catch (TemplateException te){
				addMessage(redirectAttributes, "静态化栏目'" + category.getName() + "'失败");
				logger.error("静态化栏目页失败："+te.getMessage());
				te.printStackTrace();
			}
		}
		addMessage(redirectAttributes, "静态化栏目'" + category.getName() + "'成功");
        return "redirect:" + adminPath + "/cms/category/";

	}

	/**
	 * 栏目排序
	 * @param category
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("/categorySort")
    public String categorySort(Category category, Model model, HttpServletResponse response){
		if(category != null && StringUtils.isNotEmpty(category.getId())){
				String msg="";
				try {
					if(CmsEnum.UP.getCode().equals(category.getSortType())){
						msg+="上升";
					}else if (CmsEnum.DOWN.getCode().equals(category.getSortType())) {
						msg+="下降";
					}
					categoryService.categorySort(category);
				} catch (Exception e) {
					msg+="栏目 "+category.getName()+" 失败:"+e.toString();
				}
				msg+="栏目 "+category.getName()+" 成功";
		}
		return list(model);
	}
	@ResponseBody
	@RequestMapping("/changeCategory")
	public AjaxJson changeCategory(String id,String parId){
		if(StringUtils.isEmpty(id) || StringUtils.isEmpty(id)){
			AjaxJson.fail("参数错误！");
		}
		String msg = "";
		Category category = categoryService.findUniqueByProperty("id",id);
		Category distCategory = categoryService.findUniqueByProperty("id",parId);
		Category parentCategory = new Category();
		if(category != null && distCategory != null){
			parentCategory.setId(distCategory.getId());
			category.setParentIds(distCategory.getParentIds()+","+distCategory.getId());
			msg = "转移目录 "+category.getName()+" 至"+distCategory.getName();
		}else{
			category.setParentIds("0,");
			parentCategory.setId("0");
			msg = "转移目录"+category.getName()+" 至顶级目录 ";
		}
		category.setParent(parentCategory);
		categoryService.save(category);
		return AjaxJson.ok(msg +"成功");
	}

	/**
	 * 进入选择栏目信息的页面
	 * @param
	 * @return
	 */
	@RequestMapping(value = "categorySelect")
	public String categorySelect(String type,String id,Model model){
		Category category = null;
		//移动栏目信息
		if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(id)){
			if(CmsEnum.CATEGORY.getCode().equals(type)){
				category = categoryService.findUniqueByProperty("id",id);
			}else{
				//移动文章信息
				String infoIdArr [] =id.split(",");
				//获取栏目信息
				Info info = infoService.findUniqueByProperty("id",infoIdArr[0]);
				if(info != null && StringUtils.isNotEmpty(info.getCategoryId())){
					category = categoryService.findUniqueByProperty("id",info.getCategoryId());
					//移动文章信息时，把栏目信息的ID 设置为空
					category.setId("");
				}
			}
		}
		model.addAttribute("ids",id);
		model.addAttribute("category",category);
		model.addAttribute("type",type);
		return "modules/cms/categorySelect";
	}

	/**
	 * 根据modelId 获取模板信息
	 * @param modelId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTemplate")
	public Map getTemplate(String modelId){
		Map<String,String> map = null;
		if(StringUtils.isNotEmpty(modelId)){
			map = templateService.getCategoryTemplateName(modelId);
		}
		return map;
	}

	@RequestMapping("/testCategory")
	public void testCategory(){
		Category category = new Category();
		category.setId("0");
		category.setParentIds("0,");
		updateParentIds(category);
	}

	/**
	 * 更新infoImage
	 */
	@RequestMapping("/testInfoImage")
	public void updateInfoImage(){
		List<InfoImg> imgList = infoImgService.findList(new InfoImg());
		for(InfoImg infoImg:imgList){
			List<InfoImg> infoImgList = infoImgService.findList(infoImg);
			StringBuffer buffer = new StringBuffer();
			int i = 1;
			for(InfoImg infoImg1:infoImgList){
				buffer.append(infoImg1.getImg());
				if(i<infoImgList.size()){
                    buffer .append("|");
                }
                i++;
			}
			Info info = new Info();
			info.setImages(buffer.toString());
			info.setId(infoImg.getInfoId());
			infoService.updateImages(info);
		}

	}
	public  void  updateParentIds(Category category){

		List<Category> categoryList = categoryService.findByParentId(category);
		//parent =0  55
		for (Category c : categoryList) {
				updateByPar(c);

		}
	}

	public void updateByPar(Category category){
		List<Category> categoryList = categoryService.findByParentId(category);
		for (Category c : categoryList) {
			Category cat = new Category();
			cat.setParentIds(category.getParentIds() + c.getParent().getId() + ",");
			cat.setId(c.getId());
			categoryService.updateParentIdsTemp(cat);
			List<Category> categoryList1 = categoryService.findByParentId(c);
			if (categoryList1.size() > 0) {
				updateByPar(cat);
			}
		}
	}


}
