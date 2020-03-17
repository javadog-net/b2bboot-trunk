/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import com.jhmis.common.config.Global;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.TreeService;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.entity.Navigation;
import com.jhmis.modules.cms.mapper.NavigationMapper;
import com.jhmis.modules.cms.utils.CmsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 导航管理Service
 * @author lydia
 * @version 2019-09-05
 */
@Service
@Transactional(readOnly = true)
public class NavigationService extends TreeService<NavigationMapper, Navigation> {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private InfoService infoService;

	public Navigation get(String id) {
		return super.get(id);
	}
	
	public List<Navigation> findList(Navigation navigation) {
		return mapper.findList(navigation);
	}
	
	public Page<Navigation> findPage(Page<Navigation> page, Navigation navigation) {
		return super.findPage(page, navigation);
	}
	
	@Transactional(readOnly = false)
	public void save(Navigation navigation) {
		//导航的链接地址
		String pageUrl = "";
		//首先判断外部链接地址
		if(StringUtils.isNotEmpty(navigation.getLinkUrl())){
			pageUrl = navigation.getLinkUrl();
		}else if(StringUtils.isNotEmpty(navigation.getLinkType()) && CmsEnum.LINKCATEGORY.getCode().equals(navigation.getLinkType())
		         &&StringUtils.isNotEmpty(navigation.getLinkId())){
			Category category = categoryService.findUniqueByProperty("id",navigation.getLinkId());
			if(category != null && StringUtils.isNotEmpty(category.getPageUrl()))
			pageUrl = category.getPageUrl();
		}else if(StringUtils.isNotEmpty(navigation.getLinkType()) && CmsEnum.LINKCONTENT.getCode().equals(navigation.getLinkType())
			&& StringUtils.isNotEmpty(navigation.getLinkId())){
				Info info = infoService.findUniqueByProperty("id",navigation.getLinkId());
				if(info != null && StringUtils.isNotEmpty(info.getPageUrl())){
					pageUrl = info.getPageUrl();
				}
		}else{
		}
		navigation.setPageUrl(pageUrl);
		super.save(navigation);
	}
	
	@Transactional(readOnly = false)
	public void delete(Navigation navigation) {

		deleteAllByLogic(navigation);
	}

	/**
	 * 查询导航信息，该供导航标签使用
	 * @param parid
	 * @param position
	 * @param isMobile
	 * @return
	 */
	 public List<Navigation> findByPar(String parid,String position,String isMobile ){
		 Navigation navigation = new Navigation();
		 if(StringUtils.isNotEmpty(position)){
			 navigation.setPosition(position);
		 }
		 if(StringUtils.isNotEmpty(isMobile)){
		 	navigation.setNType(CmsEnum.MOBILE.getCode());
		 }else{
		 	navigation.setNType(CmsEnum.PC.getCode());
		 }
		 if(StringUtils.isNotEmpty(parid) ){

		 	Navigation parent = new Navigation();
		 	parent.setId(parid);
			navigation.setParent(parent);
		 }
		 List<Navigation> navigationList = mapper.findList(navigation);
		 //查询是否动态扩展
		 if(StringUtils.isNotEmpty(parid) && !"par".equals(parid)){
			 Navigation na_category = new Navigation();
				Navigation nav = mapper.findUniqueByProperty("id",parid);
				if(nav != null && Global.YES.equals(nav.getIsExtend()) && Global.YES.equals(nav.getIsShow())){
                     Category category= categoryService.get(nav.getLinkId());
                     if(category != null && Global.YES.equals(category.getIsShow())){
						 na_category.setName(category.getName());
						 na_category.setLinkUrl(category.getDirName());
						 navigationList.add(na_category);
					 }


			  }

		 }
			return navigationList;
	 }
	/**
	 * 导航排序
	 * @param navigation
	 */
	@Transactional(readOnly = false)
	public void navigationSort(Navigation navigation) {
		if (navigation!=null) {
			//查询同级别上面第一个菜单的排序
			Navigation queryNavigation = new Navigation();
			queryNavigation.setParent(navigation.getParent());
			List<Navigation> navigationList = mapper.navigationSort(navigation);
			if (navigationList!=null && navigationList.size()>0) {
				Navigation nextNavigation = navigationList.get(0);
				if (nextNavigation!=null) {
					if(CmsEnum.UP.getCode().equals(navigation.getSortType())) {
						if (nextNavigation.getSort().equals(navigation.getSort())) {
							//导航列表是按升序查询的,所以此处应该减1
							navigation.setSort(nextNavigation.getSort() - 1);
						} else {
							int nextNum = nextNavigation.getSort();
							nextNavigation.setSort(navigation.getSort());
							navigation.setSort(nextNum);
						}
					}
					if(CmsEnum.DOWN.getCode().equals(navigation.getSortType())) {
						if (nextNavigation.getSort().equals(navigation.getSort())) {
							navigation.setSort(nextNavigation.getSort() + 1);
						} else {
							int nextNum = nextNavigation.getSort();
							nextNavigation.setSort(navigation.getSort());
							navigation.setSort(nextNum);
						}
					}
					mapper.updateSort(navigation);
					mapper.updateSort(nextNavigation);
				}
			}
		}
	}

	/**
	 * 获取同级导航下的最大排序号
	 * @param navigation
	 * @return
	 */
	public int maxNum(Navigation navigation){
		Integer maxSort = mapper.findMaxSort(navigation);
		if (maxSort!=null) {
				return maxSort+1;
			}
			return 1;
		}
}