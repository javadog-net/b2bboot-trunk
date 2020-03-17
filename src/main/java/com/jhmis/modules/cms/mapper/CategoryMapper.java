/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.mapper;

import com.jhmis.core.persistence.TreeMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.cms.entity.Category;

import java.util.List;

/**
 * 栏目管理MAPPER接口
 * @author lydia
 * @version 2019-09-02
 */
@MyBatisMapper
public interface CategoryMapper extends TreeMapper<Category> {
	//根据父栏目ID 查找子栏目
	 List<Category> findByParentId(Category category);

	/**
	 * 栏目排序
	 * @param category
	 * @return
	 */
	 List<Category> categorySort(Category category);

	/**
	 *
	 * @param category
	 * @return
	 */
	  int updateParentIdsTemp(Category category);

}