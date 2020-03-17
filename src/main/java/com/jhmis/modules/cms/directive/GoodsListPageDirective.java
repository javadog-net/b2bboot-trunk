package com.jhmis.modules.cms.directive;

import com.jhmis.common.config.Global;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.utils.FreemarkerPager;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import freemarker.core.Environment;
import freemarker.ext.beans.ArrayModel;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: GoodsListDirective
 * </p>
 * <p>
 * Description:
 * </p>
 * 商品列表 静态化
 * 
 * @author tc
 * @date 2019年10月22日 上午9:47:48
 */
@FreemarkerComponent("goodsListPage")
public class GoodsListPageDirective extends BaseDirective {
	@Autowired
	private StoreGoodsService storeGoodsService;
	@Autowired
	private GoodsCategoryService goodsCategoryService;

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		//海尔自营店   0
		String storeId =getParam(params,"storeId");
		//品牌Id
		String brandId =getParam(params,"brandId");
		// 栏目id 二级
		String categoryId = getParam(params, "categoryId");
		// 一级
		String categoryParId = getParam(params, "categoryParId");
		// 显示数量
		int num = getParamInt(params, "num", 10);
		int beginnum = getParamInt(params, "beginnum", 0);
		int pagesize = getParamInt(params, "pagesize", 0);
		int pageno = getParamInt(params, "pageno", 0);
		int pagenum = getParamInt(params, "pagenum", 0);
		// 排序
		String order = getParam(params, "order", "1");
		// 几天内为最新
		int newdays = getParamInt(params, "newdays", 0);
		// 是否 销量查询
		String isSaleNum = getParam(params, "saleNum");
		// 是否推荐
		String isRecommend = getParam(params, "recommend");
		// 是否促销
		String isPromotion = getParam(params, "promotion");
		// 是否热卖
		String isHot = getParam(params, "hot");
		// 是否按照(浏览量) 排序
		String isClickNum = getParam(params, "isClickNum");
		//是否按照 收藏量 排序
		String goodsCollect=getParam(params,"goodsCollect");
		//是否按照 价格 排序
		String goodsPrice=getParam(params,"goodsPrice");
		// 是否置顶
		String isTop = getParam(params, "top");
		String fieldParam=getParam(params, "fieldParam");
		// 日期格式
		String dateFormat = getParam(params, "dateFormat");
		//是否发布至 企业购严选
		String isToIndexYanxuan = getParam(params, "isToIndexYanxuan");
		// 是否发布至 品牌专区
		String isToIndexBrand = getParam(params, "isToIndexBrand");
		//是否发布至 政采专区
		String isToIndexZhengcai = getParam(params, "isToIndexZhengcai");
		// 栏目页面标识
		String categoryPagemark = getParam(params, "categoryPagemark");

		// 是否只提取允许移动app访问的数据 1是
		String ismobile = getParam(params, "ismobile");
		Writer out = env.getOut();
		if (body != null) {
			GoodsCategory goodsCategory = null;
			GoodsCategory goodsCategory2 = null;
			StoreGoods goods = new StoreGoods();
			// 设置循环变量
			if (loopVars != null && loopVars.length > 0) {
				// 栏目信息
				if (StringUtils.isNotEmpty(categoryId)) {
					goodsCategory = goodsCategoryService.findUniqueByProperty("id", categoryId);
				} else {
					if (StringUtils.isNotEmpty(categoryPagemark)) {
						goodsCategory = goodsCategoryService.findUniqueByProperty("page_mark", categoryPagemark);
					}
				}
				if (goodsCategory != null && StringUtils.isNotEmpty(goodsCategory.getId())
						&& !goodsCategory.getParentId().equals("0")) {
					goods.setCategoryId(goodsCategory.getId());
				}
				if (StringUtils.isNotEmpty(categoryParId)) {
					goodsCategory2 = goodsCategoryService.findUniqueByProperty("id", categoryParId);
				} else {
					if (StringUtils.isNotEmpty(categoryPagemark)) {
						goodsCategory2 = goodsCategoryService.findUniqueByProperty("page_mark", categoryPagemark);
					}
				}
				if (goodsCategory2 != null && StringUtils.isNotEmpty(goodsCategory2.getId())
						&& goodsCategory2.getParentId().equals("0")) {
					goods.setCategoryPid(goodsCategory2.getId());
				}
				if(StringUtils.isNotBlank(storeId)){
					goods.setStoreId(storeId);
				}
				if (newdays > 0) {
					goods.setNewDays(newdays);
				}
				if (StringUtils.isNotEmpty(isTop)) {
					goods.setIsTop(isTop);
				}
				if (StringUtils.isNotEmpty(isRecommend)) {
					goods.setIsRecommend(isRecommend);
				}
				if (StringUtils.isNotEmpty(isPromotion)) {
					goods.setIsPromotion(isPromotion);
				}
				if (StringUtils.isNotEmpty(isToIndexZhengcai)) {
					goods.setIsToIndexZhengcai(isToIndexZhengcai);
				}
				if (StringUtils.isNotEmpty(isToIndexBrand) && StringUtils.isNotBlank(brandId)) {
					goods.setBrand(brandId);
					goods.setIsToIndexBrand(isToIndexBrand);
				}
				if (StringUtils.isNotEmpty(isToIndexYanxuan)) {
					goods.setIsToIndexYanxuan(isToIndexYanxuan);
				}
				String orderSql = "";
				if (StringUtils.isNotEmpty(isHot) && "1".equals(isHot)) {// 按照是否热卖
					goods.setIsHotSale(isHot);
				}
				if (StringUtils.isNotEmpty(isClickNum) && "1".equals(isClickNum)) {// 按照浏览量倒序
					orderSql = "a.goods_click desc";
				}
				if (StringUtils.isNotEmpty(isSaleNum)) {// 是否按照销量倒序
					orderSql = orderSql + ",a.goods_salenum desc";
				}
				if (StringUtils.isNotEmpty(goodsCollect)) {// 是否按照收藏倒序
					orderSql = orderSql + ",a.goods_collect desc";
				}
				if (StringUtils.isNotEmpty(goodsPrice)) {// 是否按照价钱倒序
					orderSql = orderSql + ",a.market_price desc";
				}
				if ("1".equals(order)) {
					// 固顶有效并降序,发布时间降序(默认)
					orderSql = orderSql + ",a.is_top desc,a.shelf_time desc";
				} else if ("2".equals(order)) {
					// 固顶有效并降序,发布时间升序
					orderSql = orderSql + ",a.is_top desc,a.shelf_time";
				} else if ("3".equals(order)) {
					// 发布时间倒序
					orderSql = orderSql + ",a.shelf_time desc";
				} else if ("4".equals(order)) {
					// 发布时间升序
					orderSql = orderSql + ",a.shelf_time";
				}
				//传递自定义字段参数
				if (StringUtils.isNotEmpty(fieldParam)) {
					String paramArr[]=fieldParam.split(",");
					if (paramArr!=null && paramArr.length>0) {
						Map<String, String> fieldValues=new HashMap<String, String>();
						for (String string2 : paramArr) {
							if (StringUtils.isNotEmpty(string2) && env.getDataModel().get(string2)!=null) {
								if(string2.equals("isClickNum")) {
									orderSql = orderSql + ",a.goods_click desc";
								}
								if(string2.equals("goodsCollect")) {
									//(env.getDataModel().get(string2).toString());
									orderSql = orderSql + ",a.goods_collect desc";
								}
								if(string2.equals("goodsPrice")) {
									//(env.getDataModel().get(string2).toString());
									orderSql = orderSql + ",a.market_price desc";
								}
							}
						}
					}
				}
				if (orderSql.startsWith(",")) {// 除去首字符的 ,
					orderSql = orderSql.substring(1);
				}
				if (StringUtils.isNotEmpty(orderSql)) {
					goods.setOrderBySql(orderSql);
				}


				goods.setIsShelf(Global.YES);// 只查询上架的
				Page<StoreGoods> page = new Page<StoreGoods>();
				page.setPageNo(1);
				page.setPageSize(beginnum + num);
				Page<StoreGoods> pageGoodsList = storeGoodsService.findPage(page, goods);
				List<StoreGoods> goodsList=null;
				if (pageGoodsList != null) {
					String goodsMainUrl = "";
					goodsList = pageGoodsList.getList();
					if (goodsList != null && goodsList.size() > 0) {
						for (int i = 0; i < goodsList.size(); i++) {
							if (i >= beginnum) {
								if(StringUtils.isNotEmpty(goodsList.get(i).getMainPicUrl())){
									if(goodsList.get(i).getMainPicUrl().indexOf("\\|")>0){
										goodsMainUrl = goodsList.get(i).getMainPicUrl().split("\\|")[0];
										goodsList.get(i).setMainOnePic(goodsMainUrl);
									}else{
										goodsList.get(i).setMainOnePic(goods.getMainPicUrl());
									}
								}

							}
						}
					}
				}

				FreemarkerPager pager=new FreemarkerPager();
				pager.setCurrPage(pageno);
				pager.setTotalCount(pageGoodsList.getTotalPage());
				pager.setPageSize(num);
				pager.setUrl("index");
				//如果总页数大于最多显示页数，则设置总页数为最多显示页数
				if (pagenum > 0 && pagenum < pageGoodsList.getPageSize()) {
					pager.setTotalPage(pagenum);
				}
				//如果有下一页，则输入下一页标识
				if (pager.getTotalPage() > pageno && !"1".equals(ismobile)) {
					env.getOut().write(CategoryService.HASNEXTPAGE);
				}
				loopVars[0] = new ArrayModel(goodsList.toArray(), new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
				if (loopVars.length > 1) {
					loopVars[1] = new BeanModel(pager, new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
				}
				body.render(env.getOut());



			}
		}
	}
}
