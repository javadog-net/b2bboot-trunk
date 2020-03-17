package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.jhmis.modules.customer.entity.CustomerQuotes;
import com.jhmis.modules.customer.mapper.CustomerQuotesMapper;

@Service
@Transactional(readOnly = true)
public class CustomerQuotesService {

	@Autowired
	public CustomerQuotesMapper customerQuotesMapper;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public CustomerQuotes get(String id){
		return customerQuotesMapper.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public CustomerQuotes get(CustomerQuotes entity){
		return customerQuotesMapper.get(entity);
	}
	
	
	
	/**
	 * 查询数据列表
	 * @param entity
	 * @return
	 */
	public List<CustomerQuotes> findList(CustomerQuotes entity){
		return customerQuotesMapper.findList(entity);
	}
	

	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<CustomerQuotes> findAllList(CustomerQuotes entity){
		return customerQuotesMapper.findAllList(entity);
	}
	
	
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public int insert(CustomerQuotes entity){
		return customerQuotesMapper.insert(entity);
	}
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public int update(CustomerQuotes entity){
		return customerQuotesMapper.update(entity);
	}
	

	
	/**
	 * 删除数据（物理删除，从数据库中彻底删除）
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public int delete(CustomerQuotes entity){
		return customerQuotesMapper.delete(entity);
	}
	
	/**
	 * 根据msgId删除
	 * @param msgId
	 */
	@Transactional(readOnly = false)
	public void deleteByMsgid(String msgId){
		customerQuotesMapper.deleteByMsgid(msgId);
	}
	
}
