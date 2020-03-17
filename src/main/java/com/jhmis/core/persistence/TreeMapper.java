/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.core.persistence;

import java.util.List;

/**
 * DAO支持类实现
 * @author jhmis
 * @version 2017-05-16
 * @param <T>
 */
public interface TreeMapper<T extends TreeEntity<T>> extends BaseMapper<T> {

	/**
	 * 找到所有子节点
	 * @param entity
	 * @return
	 */
	public List<T> findByParentIdsLike(T entity);

	/**
	 * 更新所有父节点字段
	 * @param entity
	 * @return
	 */
	public int updateParentIds(T entity);
	
	
	public int updateSort(T entity);
	
	public List<T> getChildren(String parentId);

	/**
	 * 查询最大序号
	 * @param entity
	 * @return
	 */
	Integer findMaxSort(T entity);
	
	
}