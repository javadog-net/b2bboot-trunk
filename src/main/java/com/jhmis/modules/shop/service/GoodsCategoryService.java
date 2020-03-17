/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.service.TreeService;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.service.HtmlService;
import com.jhmis.modules.cms.utils.CmsEnum;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import com.jhmis.modules.shop.entity.CategoryNode;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.mapper.GoodsCategoryMapper;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 商品管理Service
 * @author tity
 * @version 2018-07-23
 */
@Service
@Transactional(readOnly = true)
public class GoodsCategoryService extends TreeService<GoodsCategoryMapper, GoodsCategory> {
	@Autowired
	private StoreGoodsService storeGoodsService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private HtmlService htmlService;

	public GoodsCategory get(String id) {
		return super.get(id);
	}
	
	public List<GoodsCategory> findList(GoodsCategory goodsCategory) {
		if (StringUtils.isNotBlank(goodsCategory.getParentIds())){
			goodsCategory.setParentIds(","+goodsCategory.getParentIds()+",");
		}
		return super.findList(goodsCategory);
	}
	@Transactional(readOnly = false)
	public List<CategoryNode> findTree(GoodsCategory goodsCategory) {
		List<GoodsCategory> goodsCategoryList = super.findList(goodsCategory);
		List<CategoryNode> listTree = getCategoryNodeList(goodsCategoryList);
		return listTree;
	}
	@Transactional(readOnly = false)
	public void save(GoodsCategory goodsCategory) {
		super.save(goodsCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsCategory goodsCategory) {
		super.delete(goodsCategory);
	}

	@Transactional(readOnly = false)
	public void updatecategoryurl(GoodsCategory category){
		mapper.updatecategoryurl(category);
	}

	@Transactional(readOnly = false)
	public List<GoodsCategory> findStoreAllCat(String storeId) {
		return mapper.findStoreAllCat(storeId);
	}

	public static List<CategoryNode> getCategoryNodeList(List<GoodsCategory> list){
		List<CategoryNode> menuList = Lists.newArrayList();
		for(GoodsCategory goodsCategory :list){
			CategoryNode c = new CategoryNode(goodsCategory.getId(),goodsCategory.getParentId(),goodsCategory.getName(),goodsCategory.getIconUrl(),goodsCategory.getClassId(),goodsCategory.getSort());
			menuList.add(c);
		}
		return bulid(menuList);
	}

	/**
	 * 两层循环实现建树
	 * @param categoryNodes 传入的树节点列表
	 * @return
	 */
	public static List<CategoryNode> bulid(List<CategoryNode> categoryNodes) {

		List<CategoryNode> trees = new ArrayList<>();

		for (CategoryNode categoryNode : categoryNodes) {

			if ("0".equals(categoryNode.getParentId())) {
				trees.add(categoryNode);
			}
			for (CategoryNode it : categoryNodes) {
				if (it.getParentId().equals(categoryNode.getId())) {
					if (categoryNode.getChildNode() == null) {
						categoryNode.setChildNode(new ArrayList<CategoryNode>());
					}
					categoryNode.getChildNode().add(it);
				}
			}
		}
		return trees;
	}


	public List<GoodsCategory> findByIdPath(String id){
		GoodsCategory category = mapper.findUniqueByProperty("id",id);
		List<GoodsCategory> categoryList = Lists.newArrayList();
		if(category != null){
			//如果栏目的所有父栏目信息存在，则查询所有父级栏目信息
			if(org.apache.commons.lang3.StringUtils.isNotEmpty(category.getParentIds()) && !CmsEnum.PARENTIDSNULL.equals(category.getParentIds())){
				String categoryIdArr [] = category.getParentIds().split(",");
				Arrays.asList(categoryIdArr).forEach(categoryId ->{
					if(org.apache.commons.lang3.StringUtils.isNotEmpty(categoryId)){
						GoodsCategory parentCategory = mapper.findUniqueByProperty("id",categoryId);
						if(parentCategory != null){
							categoryList.add(parentCategory);
						}
					}
				});
			}
			categoryList.add(category);
		}
		return categoryList;
	}

    public List<GoodsCategory> findByModelId(String modelId) {
		GoodsCategory category = new GoodsCategory();
			category.setParent(new GoodsCategory());
			dataRuleFilter(category);
			List<GoodsCategory>list = mapper.findList(category);
//			// 将没有父节点的节点，找到父节点
			Set<String> parentIdSet = Sets.newHashSet();
			for (GoodsCategory e : list){
				if (e.getParent()!=null && org.apache.commons.lang3.StringUtils.isNotBlank(e.getParent().getId())){
					boolean isExistParent = false;
					for (GoodsCategory e2 : list){
						if (e.getParent().getId().equals(e2.getId())){
							isExistParent = true;
							break;
						}
					}
					if (!isExistParent){
						parentIdSet.add(e.getParent().getId());
					}
				}
			}

			List<GoodsCategory> categoryList = Lists.newArrayList();
			for (GoodsCategory e : list){
					categoryList.add(e);
				}
			return categoryList;
    }

	/**
	 * 静态化商品信息
	 * @param request
	 */
	public void goodsHtml(HttpServletRequest request,String templatePath){
		List<StoreGoods> goodsList = storeGoodsService.findList(new StoreGoods());
		for (StoreGoods storeGoods : goodsList) {
			try {
				Goods goods = goodsService.findByCode(storeGoods.getCode());
				GoodsCategory category = findUniqueByProperty("id", storeGoods.getCategoryPid());
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

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}