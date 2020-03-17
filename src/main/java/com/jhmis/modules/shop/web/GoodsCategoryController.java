/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.FileUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.service.HtmlService;
import com.jhmis.modules.cms.service.TemplateService;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import com.jhmis.modules.index.IndexUtil;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.GoodsClass;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.shop.service.GoodsClassService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.management.resources.agent_ja;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 商品管理Controller
 * @author tity
 * @version 2018-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/goodsCategory")
public class GoodsCategoryController extends BaseController {

	@Autowired
	private TemplateService templateService;
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Autowired
	private StoreGoodsService storeGoodsService;
	@Autowired
	private GoodsClassService goodsClassService;
	@Autowired
	private HtmlService htmlService;
	
	@ModelAttribute
	public GoodsCategory get(@RequestParam(required=false) String id) {
		GoodsCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsCategoryService.get(id);
		}
		if (entity == null){
			entity = new GoodsCategory();
		}
		return entity;
	}
	
	/**
	 * 商品分类列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(GoodsCategory goodsCategory,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/shop/goodsCategoryList";
	}
	/**
	 * 查看，增加，编辑商品分类表单页面
	 */
	@RequestMapping(value = "form")
	public String form(GoodsCategory goodsCategory, Model model) {
		if (goodsCategory.getParent()!=null && StringUtils.isNotBlank(goodsCategory.getParent().getId())){
			goodsCategory.setParent(goodsCategoryService.get(goodsCategory.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(goodsCategory.getId())){
				GoodsCategory goodsCategoryChild = new GoodsCategory();
				goodsCategoryChild.setParent(new GoodsCategory(goodsCategory.getParent().getId()));
				List<GoodsCategory> list = goodsCategoryService.findList(goodsCategory); 
				if (list.size() > 0){
					goodsCategory.setSort(list.get(list.size()-1).getSort());
					if (goodsCategory.getSort() != null){
						goodsCategory.setSort(goodsCategory.getSort() + 30);
					}
				}
			}
		}
		if (goodsCategory.getSort() == null){
			goodsCategory.setSort(30);
		}
		GoodsClass goodsClass = new GoodsClass();
		List<GoodsClass> goodsClassList =  goodsClassService.findList(goodsClass);
		model.addAttribute("goodsClassList", goodsClassList);
		model.addAttribute("goodsCategory", goodsCategory);
		Map<String,List<String>> mapList = templateService.getTemplate(templatePath);
		mapList.forEach((key,value)->{
			model.addAttribute(key,value);
		});
		return "modules/shop/goodsCategoryForm";
	}

	
	
	/** 
	  * @Title: checkGoodsCategoryMarkName 
	  * @Description: TODO  检查是否有重名的页面标识
	  * @param category
	  * @return 
	  * @return String
	  * @author tc
	  * @date 2019年10月24日下午4:31:48
	  */
	@ResponseBody
	@RequestMapping("/checkGoodsCategoryMarkName")
	public String checkGoodsCategoryMarkName(GoodsCategory category){
		System.out.println("category:===="+category);

		GoodsCategory category1=new GoodsCategory();
		category1.setPageMark(category.getPageMark());
			List<GoodsCategory> categoryList = goodsCategoryService.findList(category1);
			if(null!=categoryList && categoryList.size()>0){
				/*Iterator<GoodsCategory> it = categoryList.iterator();
				while(it.hasNext()) {
					GoodsCategory next = it.next();
					if(StringUtils.isNotBlank(category.getId())&&next.getId().equals(category.getId())){
						it.remove();
					}
				}*/
				for(int i=0;i<categoryList.size();){
                     if(StringUtils.isNotBlank(category.getId())&&categoryList.get(i).getId().equals(category.getId())){
						 categoryList.remove(i);
					 }else{
                     	i++;
					 }
				}
			}
				if(null!=categoryList && categoryList.size()>0){
				     return "false";
				}
		return "true";
	}
	/** 
	  * @Title: checkCategoryName 
	  * @Description: TODO  检测页面分类 名称是否存在
	  * @param category
	  * @return 
	  * @return String
	  * @author tc
	  * @date 2019年10月24日下午4:44:45
	  */
	@ResponseBody
	@RequestMapping("/checkGoodsCategoryName")
	public String checkGoodsCategoryName(GoodsCategory category){
		System.out.println("category:===="+category);

		GoodsCategory category1=new GoodsCategory();
		category1.setRepeatName(category.getName());
		List<GoodsCategory> categoryList = goodsCategoryService.findList(category1);
		if(null!=categoryList && categoryList.size()>0){
			/*Iterator<GoodsCategory> it = categoryList.iterator();
			while(it.hasNext()) {
				GoodsCategory next = it.next();
				if(StringUtils.isNotBlank(category.getId())&&next.getId().equals(category.getId())){
					it.remove();
				}
			}*/
				for(int i=0;i<categoryList.size();){
					if(StringUtils.isNotBlank(category.getId())&&categoryList.get(i).getId().equals(category.getId())){
						categoryList.remove(i);
					}else{
						i++;
					}
				}
			}
			if(null!=categoryList && categoryList.size()>0){
				return "false";
			}
		return "true";
	}
	
	/**
	 * 保存商品分类
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(GoodsCategory goodsCategory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		
		//新增或编辑表单保存
		//goodsCategoryService.save(goodsCategory);//保存
		//addMessage(redirectAttributes, "保存商品分类成功");
		//redirectAttributes.addFlashAttribute("goodsCategory",goodsCategory);
		//return "modules/shop/goodsCategoryMakeHtml";
		//return "redirect:"+Global.getAdminPath()+"/shop/goodsCategory/confirmHtml";
		//return  "modules/shop/goodsList";

		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, goodsCategory)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		//新增或编辑表单保存
		goodsCategoryService.save(goodsCategory);//保存
		j.setSuccess(true);
		j.put("goodsCategory", goodsCategory);
		j.setMsg("保存商品分类成功");
		return j;


	}
	
	@RequestMapping(value = "confirmHtml")
	public String confirmHtml(GoodsCategory goodsCategory, Model model) {
		model.addAttribute("goodsCategory", goodsCategory);
		if(StringUtils.isBlank(goodsCategory.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		System.out.println("进入静态化"+goodsCategory);
		return "modules/shop/goodsCategoryMakeHtml";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<GoodsCategory> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return goodsCategoryService.getChildren(parentId);
	}
	
	/**
	 * 删除商品分类
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(GoodsCategory goodsCategory, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		goodsCategoryService.delete(goodsCategory);
		j.setSuccess(true);
		j.setMsg("删除商品分类成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<GoodsCategory> list = goodsCategoryService.findList(new GoodsCategory());
		for (int i=0; i<list.size(); i++){
			GoodsCategory e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}

	
	
	/** 
	  * @Title: goodsCategoryMakeHtml 
	  * @Description: TODO  商品栏目静态化
	  * @param ids
	  * @param request
	  * @param htmlCategory
	  * @param htmlCategoryPar
	  * @param htmlIndex
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年10月24日下午2:39:03
	  */
	@ResponseBody
	@RequestMapping(value = "goodsCategoryMakeHtml", method=RequestMethod.POST)
    public AjaxJson goodsCategoryMakeHtml(String ids,HttpServletRequest request, String htmlCategory, String htmlCategoryPar, String htmlIndex){
		if(StringUtils.isEmpty(ids)){
			return AjaxJson.fail("参数不能为空！");
		}
		String idArr[] = ids.split(",");
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
        Map<String,Object> data = getData(request);
		for(int i = 0;i<idArr.length;i++){
			GoodsCategory goodsCategory=null;
			goodsCategory = goodsCategoryService.findUniqueByProperty("id",idArr[i]);
			if(goodsCategory != null){
				try {
					// 栏目静态化
                    if(null!=htmlCategory && Global.YES.equals(htmlCategory)){
                        htmlService.htmlGoodsCategory(data,goodsCategory,CmsUtils.getTemplateWholePath(templatePath),1);
                    }

                    // 所属栏目的父栏目静态化  只有二级 可直接获取 parentId
                    if(null!=htmlCategoryPar && Global.YES.equals(htmlCategoryPar)){
						if(StringUtils.isNotEmpty(goodsCategory.getParentIds())){
							String str=goodsCategory.getParentIds();
							int start=str.indexOf(",")+1;
							int end=str.lastIndexOf(",");
                    		GoodsCategory goodsCategoryPar=goodsCategoryService.findUniqueByProperty("id",str.substring(start,end).trim());
                    		if(null!=goodsCategoryPar){
                    		   htmlService.htmlGoodsCategory(data,goodsCategoryPar,CmsUtils.getTemplateWholePath(templatePath),1);
                    		}
                    	}
                    }
                    //首页静态化
                    if(Global.YES.equals(htmlIndex)){
                        String htmlPath = staticPath+ Htmlpath.htmlIndexPath(CmsUtils.getCmsConfig());
                        File htmlPathDir = new File(htmlPath);
                        if(!htmlPathDir.exists()){
                            htmlPathDir.mkdirs();
                        }
                        htmlService.htmlIndex(data);
                    }
                    j.setMsg("静态化成功！");
					j.setSuccess(true);
				} catch (IOException e) {
					j.setSuccess(false);
					e.printStackTrace();
				} catch (TemplateException e) {
					j.setSuccess(false);
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	
	/** 
	  * @Title: diyGoodsCategoryMakeHtml 
	  * @Description: TODO  自定义 栏目静态化
	  * @param ids
	  * @param request
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年10月25日下午4:01:56
	  */
	@ResponseBody
	@RequestMapping(value = "diyGoodsCategoryMakeHtml", method=RequestMethod.POST)
    public AjaxJson diyGoodsCategoryMakeHtml(String ids,HttpServletRequest request){
		if(StringUtils.isEmpty(ids)){
			return AjaxJson.fail("参数不能为空！");
		}
		String idArr[] = ids.split(",");
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
        Map<String,Object> data = getData(request);
		for(int i = 0;i<idArr.length;i++){
			GoodsCategory goodsCategory=null;
			goodsCategory = goodsCategoryService.findUniqueByProperty("id",idArr[i]);
			if(goodsCategory != null){
				try {
					// 栏目静态化
                        htmlService.htmlGoodsCategory(data,goodsCategory,CmsUtils.getTemplateWholePath(templatePath),1);
                   /* // 所属栏目的父栏目静态化  只有二级 可直接获取 parentId
                    	if(StringUtils.isNotEmpty(goodsCategory.getParentIds())) {
							String str = goodsCategory.getParentIds();
							String[] arr = str.split(",");
							if (arr.length > 1){
							int start = str.indexOf(",") + 1;
							int end = str.lastIndexOf(",");
							GoodsCategory goodsCategoryPar = goodsCategoryService.findUniqueByProperty("id", str.substring(start, end).trim());
							if (null != goodsCategoryPar) {
								htmlService.htmlGoodsCategory(data, goodsCategoryPar, CmsUtils.getTemplateWholePath(templatePath), 1);
							}
						 }
                        }*/
                    
				} catch (IOException e) {
					j.setSuccess(false);
					e.printStackTrace();
				} catch (TemplateException e) {
					j.setSuccess(false);
					e.printStackTrace();
				}
			}
		}
		//首页静态化
        String htmlPath = staticPath+ Htmlpath.htmlIndexPath(CmsUtils.getCmsConfig());
        File htmlPathDir = new File(htmlPath);
        if(!htmlPathDir.exists()){
            htmlPathDir.mkdirs();
        }
        try {
			htmlService.htmlIndex(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        j.setMsg("静态化成功！");
		j.setSuccess(true);
		return j;
	}




	/**
	 * 静态化商品大类页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/createGoodsCategoryParConfirm")
	public String indexClearProductConfirm(ModelMap map, HttpServletResponse response){
		return "modules/cms/createGoodsCategoryParConfirm";
	}
	/**
	 * 静态化商品大类 商品
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createGoodsCategoryPar")
	public AjaxJson createGoodsCategoryPar(ModelMap map, HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> data = getData(request);
		try {
			htmlService.htmlGoodsCategoryPar(data,CmsUtils.getTemplateWholePath(templatePath),1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return AjaxJson.ok("静态化成功");
	}


	/**
	*@Description 静态化所有商品栏目
	*@Param
	*@Return
	*@Author t.c
	*@Date 2019-12-13
	*/
	@RequestMapping("/allGoodsCategoryCreateHtml")
	@ResponseBody
	public AjaxJson allGoodsCategoryCreateHtml(HttpServletRequest request ){
		Map<String,Object> data = getData(request);
		List<GoodsCategory> list=goodsCategoryService.findList(new GoodsCategory());
		AjaxJson j=new AjaxJson();
		for(GoodsCategory gc:list) {
			try {
				htmlService.htmlGoodsCategory(data, gc, CmsUtils.getTemplateWholePath(templatePath), 1);
			} catch (IOException e) {
				e.printStackTrace();
				j.setMsg(e.getMessage());
				j.setSuccess(false);
				return j;
			} catch (TemplateException e) {
				e.printStackTrace();
				j.setMsg(e.getMessage());
				j.setSuccess(false);
				return j;
			}
		}
		return AjaxJson.ok("静态化成功！");
	}



	/**
	 * 静态化商品大类页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/createAllGoodsHtmlConfirm")
	public String createAllGoodsHtmlConfirm(ModelMap map, HttpServletResponse response){
		return "modules/cms/createAllGoodsHtmlConfirm";
	}
	/**
	 * 静态化商品全部
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createAllGoodsHtml")
	public AjaxJson createAllGoodsHtml(ModelMap map, HttpServletRequest request, HttpServletResponse response){
			List<StoreGoods> goodsList = storeGoodsService.findList(new StoreGoods());
			String errorId="";
			String errorMsg="";
			int success=0;
			int error=0;

			for (StoreGoods storeGoods : goodsList) {
				try {
					GoodsCategory category = goodsCategoryService.findUniqueByProperty("id", storeGoods.getCategoryPid());
					CmsConfig cmsConfig = CmsUtils.getCmsConfig();
					if(StringUtils.isBlank(storeGoods.getHtmlpath())){
						// 获取最大索引号
						Integer num = storeGoodsService.selectMaxNum();
						if(null == num){
							num=0;
						}
						storeGoods.setHtmlIndexNum(num+1);
					// 获取静态化的路径
					String htmlPath = Htmlpath.goodsPath(cmsConfig, category, storeGoods);
					storeGoods.setHtmlpath(htmlPath);
					}
					storeGoodsService.update(storeGoods,request,CmsUtils.getTemplateWholePath(templatePath));
					success++;
				} catch (Exception e) {
					error++;
					e.printStackTrace();
					errorMsg=errorMsg+"异常信息：该商品"+storeGoods.getId()+"出现:"+e.toString()+"||";
					 errorId=errorId+storeGoods.getId()+",";
					 continue;
				}

			}
		return AjaxJson.ok("全部商品静态化完成，成功："+success+"条!"+"失败:"+error+"条!失败商品Id："+errorId+"详细错误信息："+errorMsg+"详细错误信息已||分隔，一般出现问题是由于上架时间为空，或者商品的code码为空，请自行检查处理。如处理不了，请联系管理员！");
		}
	}




