package com.jhmis.modules.cms.web;

import com.google.common.collect.Lists;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.FileUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.InfoService;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.index.IndexUtil;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsExt;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.shop.service.GoodsExtService;
import com.jhmis.modules.shop.service.GoodsService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 全文索引
 */
@Controller
@RequestMapping(value = "${adminPath}/cms")
public class IndexController extends BaseController {
@Autowired
private CategoryService categoryService;
@Autowired
private InfoService infoService;
@Autowired
private GoodsCategoryService goodsCategoryService;
@Autowired
private StoreGoodsService storeGoodsService;
@Autowired
private GoodsService goodsService;
@Autowired
private GoodsExtService goodsExtService;
	/**
	 * 创建索引页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/indexCreate")
	public String luceneCreate(ModelMap map, HttpServletResponse response) {
		CmsConfig cmsConfig = CmsUtils.getCmsConfig();
		if (cmsConfig!=null) {
			List<Category> categoryList = Lists.newArrayList();
			List<Category> sourcelist = categoryService.findByModelId( null);
			Category.sortList(categoryList, sourcelist, "0");
			map.put("categoryList",categoryList);
		}
		return "modules/cms/indexCreate";
	}

	/**
	 * 创建索引
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/indexCreateDo")
	public AjaxJson luceneCreateDo(Info info, String createType, String[] ids, String startDate,String endDate, ModelMap map, HttpServletRequest request,HttpServletResponse response){
		try {
			boolean ishtml=false;
			List<Info> infoList=null;
			if ("categorys".equals(createType)) {
				//生成所选栏目
				if (ids!=null && ids.length>0) {
					info.setCategoryIds(ids);
					ishtml=true;
				}
			}else if ("all".equals(createType)) {
				//生成所有
				ishtml=true;
			}
			if (ishtml) {
                if(StringUtils.isNotEmpty(startDate)){
                    info.setStartDate(startDate);
                }
                if(StringUtils.isNotEmpty(endDate)){
                    info.setEndDate(endDate);
                }
				info.setDelFlag(info.DEL_FLAG_NORMAL);
				infoList=infoService.findList(info);
				if (infoList!=null && infoList.size()>0) {
					for (int i = 0; i < infoList.size(); i++) {
						if (infoList.get(i)!=null) {
							//更新索引
							IndexUtil.updateIndex(infoList.get(i), request);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("创建信息索引处理失败，原因:"+e.toString());
			return AjaxJson.fail("创建信息索引处理失败！");
		}
		return AjaxJson.ok("创建信息索引处理成功!");
	}
	/**
	 * 删除索引页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/indexDel")
	public String luceneDel(ModelMap map, HttpServletResponse response) {
		CmsConfig  cmsConfig = CmsUtils.getCmsConfig();
		if (cmsConfig!=null) {
			//栏目管理页面
			//获取一级栏目
			List<Category> categoryList = categoryService.findByModelId(null);
			//设置是否有子栏目
			if (categoryList!=null && categoryList.size()>0) {
			}
			map.put("categoryList",categoryList);
		}

		return "modules/cms/indexDel";
	}

	/**
	 * 删除索引
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/indexDelDo")
	public AjaxJson indexDelDo(Info info, String createType, String[] ids, String pagenum, ModelMap map, HttpServletRequest request,HttpServletResponse response) {
		try {
			boolean ishtml=false;
			List<Info> infoList=null;
			if ("categorys".equals(createType)) {
				//生成所选栏目
				if (ids!=null && ids.length>0) {
					info.setCategoryIds(ids);
					ishtml=true;
				}
			}else if ("all".equals(createType)) {
				//去掉了根据权限判断栏目列表
				ishtml=true;
			}
			if (ishtml) {
				infoList=infoService.findList(info);
				if (infoList!=null && infoList.size()>0) {
					for (int i = 0; i < infoList.size(); i++) {
						if (infoList.get(i)!=null && Info.DEL_FLAG_NORMAL.equals(infoList.get(i).getDelFlag())) {
							//更新索引
							IndexUtil.deleteIndex(infoList.get(i), request);
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("原因:"+e.toString());
			return AjaxJson.fail("删除信息索引失败！");
		}
		return AjaxJson.ok("删除信息索引成功！");
	}
	/**
	 * 清空索引页面
	 * @param map
	 * @return
	 */

	@RequestMapping("/indexClearConfirm")
	public String indexClearConfirm(ModelMap map, HttpServletResponse response){
		return "modules/cms/indexClearConfirm";
	}
	/**
	 * 清空索引
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/indexClear")
	public AjaxJson indexClear(ModelMap map, HttpServletRequest request, HttpServletResponse response){
		try {
			String cmsPath=IndexUtil.INDEX_PATH+"/cmsinfo";
			FileUtils.deleteDirectory(cmsPath);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("清空索引失败，原因:"+e.getMessage());
		}
		return AjaxJson.ok("清空索引成功");
	}
	/**
	 * 清空索引 商品页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/indexClearProductConfirm")
	public String indexClearProductConfirm(ModelMap map, HttpServletResponse response){
		return "modules/cms/indexClearProductConfirm";
	}
	/**
	 * 清空索引 商品
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/indexClearProduct")
	public AjaxJson indexClearProduct(ModelMap map, HttpServletRequest request, HttpServletResponse response){
		try {
			String cmsPath=IndexUtil.INDEX_PATH+"/product";
			FileUtils.deleteDirectory(cmsPath);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("清空索引失败，原因:"+e.getMessage());
		}
		return AjaxJson.ok("清空索引成功");
	}
	/**
	 * 创建索引页面 商品
	 * @param map
	 * @return
	 */
	@RequestMapping("/luceneCreateProduct")
	public String luceneCreateProduct(ModelMap map, HttpServletResponse response)
	{
		/*	//商品分类管理页面
			CmsConfig cmsConfig = CmsUtils.getCmsConfig();
			if (cmsConfig!=null) {
				//栏目管理页面
				//获取一级栏目
				List<GoodsCategory> goodsCategoryList = goodsCategoryService.findByModelId(null);
				//设置是否有子栏目
				if (goodsCategoryList!=null && goodsCategoryList.size()>0) {
				}

				map.put("goodsCategoryList",goodsCategoryList);
			}*/

		return "modules/shop/shopIndexCreate";
	}

	/**
	 * 创建索引 商品
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/luceneCreateProductDo")
	public AjaxJson luceneCreateProductDo(StoreGoods storeGoods, String ids, ModelMap map, HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isBlank(ids)){
			return AjaxJson.fail("请重新选择！");
		}
		String [] arry=ids.split(",");
		for(int i=0;i<arry.length;i++) {
			try {
				CmsConfig cmsConfig = CmsUtils.getCmsConfig();
				List<StoreGoods> storeGoodsList = null;
					//生成所选栏目
						storeGoods.setCategoryId(arry[i]);
					//生成所有
					storeGoods.setIsShelf("1");
					storeGoodsList = storeGoodsService.findList(storeGoods);
					if (storeGoodsList != null && storeGoodsList.size() > 0) {
						for (int t = 0; t < storeGoodsList.size(); t++) {
							if (storeGoodsList.get(t) != null) {
								Goods goods = goodsService.findUniqueByProperty("code", storeGoodsList.get(t).getCode());
								GoodsExt goodsExt = goodsExtService.findUniqueByProperty("code", storeGoodsList.get(t).getCode());
								//更新索引
								IndexUtil.updateIndex(goods, storeGoodsList.get(t), goodsExt, request);
							}
						}
					}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("创建商品分类索引处理失败，原因:" + e.getMessage());
				return AjaxJson.fail("创建商品分类索引处理失败！");
			}
		}
		return AjaxJson.ok("创建商品分类索引处理成功!");
	}


	/**
	 * 删除索引  商品
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/luceneDelProductDo")
	public AjaxJson luceneDelProductDo(StoreGoods storeGoods, String ids,  HttpServletRequest request,HttpServletResponse response) {
			if (StringUtils.isBlank(ids)) {
				return AjaxJson.fail("请重新选择！");
			}
			String[] arry = ids.split(",");
			for (int i = 0; i < arry.length; i++) {
				try {
					CmsConfig cmsConfig = CmsUtils.getCmsConfig();
					List<StoreGoods> storeGoodsList = null;
					//生成所选栏目
					storeGoods.setCategoryId(arry[i]);
					//生成所有
					storeGoods.setIsShelf("1");
					storeGoodsList = storeGoodsService.findList(storeGoods);
					if (storeGoodsList != null && storeGoodsList.size() > 0) {
						for (int t = 0; t < storeGoodsList.size(); t++) {
							if (storeGoodsList.get(t) != null) {
								Goods goods = goodsService.findUniqueByProperty("code", storeGoodsList.get(t).getCode());
								//删除索引
								IndexUtil.deleteIndex(goods, request);

							}
						}
					}
				} catch (Exception e) {
					logger.error("创建商品分类索引处理失败，原因:" + e.toString());
					return AjaxJson.fail("创建商品分类索引处理失败！");
				}
			}
			return AjaxJson.ok("删除索引成功！");
		}
//
//	/**
//	 * 日期型数据转换，将页面上的表示日期限的字符串，转换为Date型
//	 * **/
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		dateFormat.setLenient(true);
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(
//				dateFormat, true));
//
//	}
}
