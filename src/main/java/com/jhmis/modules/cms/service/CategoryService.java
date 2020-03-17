/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jhmis.core.service.TreeService;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.mapper.CategoryMapper;
import com.jhmis.modules.cms.utils.CmsEnum;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 栏目管理Service
 * @author lydia
 * @version 2019-09-02
 */
@Service
@Transactional(readOnly = true)
public class CategoryService extends TreeService<CategoryMapper, Category> {
	@Autowired
	private CmsConfigService cmsConfigService;
	@Autowired
	private HtmlService htmlService;

	public static final String CACHE_CATEGORY_LIST = "categoryList";
	public static final String HASNEXTPAGE = "<!--CMS_category_info_list_hasNextPage-->";
	
	private Category entity = new Category();
	
	@SuppressWarnings("unchecked")
	public List<Category> findByModelId(String modelId){
			Category category = new Category();
			category.setParent(new Category());
			dataRuleFilter(category);
			List<Category>list = mapper.findList(category);
			Set<String> parentIdSet = Sets.newHashSet();
			for (Category e : list){
				if (e.getParent()!=null && StringUtils.isNotBlank(e.getParent().getId())){
					boolean isExistParent = false;
					for (Category e2 : list){
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

			List<Category> categoryList = Lists.newArrayList();
			for (Category e : list){
				if (StringUtils.isNotEmpty(modelId)){
					if (modelId.equals(e.getModelId())){
						categoryList.add(e);
					}
				}else{
					categoryList.add(e);
				}
			}
			return categoryList;
	}

	public List<Category> findByParentId(Category category){
		return mapper.findByParentId(category);
	}
	@Transactional(readOnly = false)
	public void save(Category category) {
		category.setDirName(Htmlpath.htmlCmsCategoryPath(CmsUtils.getCmsConfig(),category));
		super.save(category);
		//TODO 做导航的时候再考虑这块
//		UserUtils.removeCache(CACHE_CATEGORY_LIST);
//		CmsUtils.removeCache("mainNavList_"+category.getSite().getId());
	}
	
	@Transactional(readOnly = false)
	public void delete(Category category) {
		//sql 中删除该栏目和该栏目下的所有子栏目
		super.deleteAllByLogic(category);
		//TODO 做导航的时候再考虑这块
//		UserUtils.removeCache(CACHE_CATEGORY_LIST);
//		CmsUtils.removeCache("mainNavList_"+category.getSite().getId());
	}
	
	/**
	 * 通过编号获取栏目列表
	 */
	public List<Category> findByIds(String ids) {
		List<Category> list = Lists.newArrayList();
		String[] idss = StringUtils.split(ids,",");
		if (idss.length>0){
//			List<Category> l = dao.findByIdIn(idss);
//			for (String id : idss){
//				for (Category e : l){
//					if (e.getId().equals(id)){
//						list.add(e);
//						break;
//					}
//				}
//			}
			for(String id : idss){
				Category e = mapper.get(id);
				if(null != e){
					//System.out.println("e.id:"+e.getId()+",e.name:"+e.getName());
					list.add(e);
				}
				//list.add(dao.get(id));
				
			}
		}
		return list;
	}

	/**
	 * 查询该栏目所在父级下的所有栏目信息
	 * @param id
	 * @return
	 */
	public List<Category> findByIdPath(String id){
		List<Category> categoryList = findByParPath(id);
		return categoryList;
	}

	/**
	 * 查询该栏目下的所有子栏目信息
	 * @param parid
	 * @return
	 */
	public List<Category> findSonById(String parid){
		List<Category> categoryList = null;
		if(StringUtils.isNotEmpty(parid)){
			Category category = new Category();
			category.setParentIds(parid);
			categoryList = mapper.findList(category);
		}
		return categoryList;
	}

	/**
	 * 查询所有栏目信息
	 * @param id
	 * @return
	 */
	public List<Category> findByParPath(String id){
		Category category = mapper.findUniqueByProperty("id",id);
		List<Category> categoryList = Lists.newArrayList();
		if(category != null){
			//如果栏目的所有父栏目信息存在，则查询所有父级栏目信息
			if(StringUtils.isNotEmpty(category.getParentIds()) && !CmsEnum.PARENTIDSNULL.equals(category.getParentIds())){
				String categoryIdArr [] = category.getParentIds().split(",");
				Arrays.asList(categoryIdArr).forEach(categoryId ->{
					if(StringUtils.isNotEmpty(categoryId)){
						Category parentCategory = mapper.findUniqueByProperty("id",categoryId);
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

	/**
	 * 获取同级栏目下的最大排序号
	 * @param category
	 * @return
	 */
	public int maxNum(Category category){
		Integer maxSort = mapper.findMaxSort(category);
		if(maxSort != null){
			return maxSort+1;
		}else{
			return 1;
		}
	}

	/**
	 * 栏目排序
	 * @param category
	 */
	@Transactional(readOnly = false)
	public void categorySort(Category category) {
		if (category!=null) {
			//查询同级别上面第一个菜单的排序
			Category queryCategory = new Category();
			queryCategory.setParent(category.getParent());
			List<Category> categoryList = mapper.categorySort(category);
			if (categoryList!=null && categoryList.size()>0) {
				Category nextCategory=categoryList.get(0);
				if (nextCategory!=null) {
					if(CmsEnum.UP.getCode().equals(category.getSortType())) {
						if (nextCategory.getSort().equals(category.getSort())) {
							//栏目列表是按升序查询的,所以此处应该减1
							category.setSort(nextCategory.getSort() -1);
						} else {
							int nextNum = nextCategory.getSort();
							nextCategory.setSort(category.getSort());
							category.setSort(nextNum);
						}
					}
					if(CmsEnum.DOWN.getCode().equals(category.getSortType())) {
						if (nextCategory.getSort().equals(category.getSort())) {
							category.setSort(nextCategory.getSort() + 1);
						} else {
							int nextNum = nextCategory.getSort();
							nextCategory.setSort(category.getSort());
							category.setSort(nextNum);
						}
					}
					mapper.updateSort(category);
                    mapper.updateSort(nextCategory);
				}
			}
		}
	}

	/**
	 * 模糊查询      查询父级ID 包含categoryParId的所有栏目信息
	 * @param categoryParId
	 * @return
	 */
	public List<Category> findByParentIdsLike(String categoryParId){
		Category category = new Category();
		category.setParentIds(categoryParId);
		return mapper.findByParentIdsLike(category);
	}
	@Transactional(readOnly = false)
	public int updateParentIdsTemp(Category category){
		return mapper.updateParentIdsTemp(category);

	}



}
