/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.CustomerQuotes;

/**
 * 报价信息MAPPER接口
 * @author mll
 * @version 2019-08-05
 */
@MyBatisMapper
public interface CustomerQuotesMapper{
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public CustomerQuotes get(String id);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public CustomerQuotes get(CustomerQuotes entity);
	
	
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<CustomerQuotes>());
	 * @param entity
	 * @return
	 */
	public List<CustomerQuotes> findList(CustomerQuotes entity);
	
	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<CustomerQuotes> findAllList(CustomerQuotes entity);
	
	
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insert(CustomerQuotes entity);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int update(CustomerQuotes entity);
	

	
	/**
	 * 删除数据（物理删除，从数据库中彻底删除）
	 * @param entity
	 * @return
	 */
	public int delete(CustomerQuotes entity);
	
	/**
	 * 根据msgId删除
	 * @param msgId
	 */
	public void deleteByMsgid(String msgId);
	
	
	@Select("${sql}")
	public List<Object> execSelectSql(@Param(value = "sql") String sql);

	@Update("${sql}")
	public void execUpdateSql(@Param(value = "sql") String sql);

	@Insert("${sql}")
	public void execInsertSql(@Param(value = "sql") String sql);

	@Delete("${sql}")
	public void execDeleteSql(@Param(value = "sql") String sql);
}