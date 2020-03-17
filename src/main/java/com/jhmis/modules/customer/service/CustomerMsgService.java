/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.Date;
import java.util.List;

import com.jhmis.common.utils.IdGen;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.customer.entity.CustomerMsgProduct;
import com.jhmis.modules.customer.entity.CustomerQuotes;
import com.jhmis.modules.customer.mapper.CustomerMsgProductMapper;
import com.jhmis.modules.customer.mapper.CustomerQuotesMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.CustomerMsg;
import com.jhmis.modules.customer.mapper.CustomerMsgMapper;

/**
 * 客单需求Service
 * @author hdx
 * @version 2019-04-25
 */
@Service
@Transactional(readOnly = true)
public class CustomerMsgService extends CrudService<CustomerMsgMapper, CustomerMsg> {

	@Autowired
	CustomerMsgProductMapper customerMsgProductMapper;
	
	@Autowired
	CustomerMsgProductService customerMsgProductService;
	
	@Autowired
	CustomerQuotesMapper customerQuotesMapper;
	
	@Autowired
	CustomerQuotesService customerQuotesService;
	
	@Autowired
	CustomerMsgMapper  customerMsgMapper;
	
	public CustomerMsg get(String id) {
		return super.get(id);
	}
	
	public CustomerMsg getByMsgid(String msgId){
		return customerMsgMapper.getByMsgid(msgId);
	}
	
	public CustomerMsg getByProjectId(String projectId){
		return customerMsgMapper.getByProjectId(projectId);
	}
	
	public List<CustomerMsg> findList(CustomerMsg customerMsg) {
		return super.findList(customerMsg);
	}
	
	public Page<CustomerMsg> findPage(Page<CustomerMsg> page, CustomerMsg customerMsg) {
		return super.findPage(page, customerMsg);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerMsg customerMsg) {
		
		super.save(customerMsg);
		
		List<CustomerMsgProduct> listCustomerMsgProduct = customerMsg.getListCustomerMsgProduct();		
		for(CustomerMsgProduct customerMsgProduct:listCustomerMsgProduct){
			customerMsgProduct.setId(IdGen.uuid());
			customerMsgProduct.setCustomerMsgId(customerMsg.getMsgId());
			customerMsgProductMapper.insert(customerMsgProduct);
		}
		List<CustomerQuotes> listCustomerQuote = customerMsg.getQuotes();
		if(listCustomerQuote != null && listCustomerQuote.size()>0){
			for(CustomerQuotes customerQuote : listCustomerQuote){
				if(StringUtils.isBlank(customerQuote.getProductCode())){
					continue;
				}
				customerQuote.setId(IdGen.uuid());
				customerQuote.setMsgId(customerMsg.getMsgId());
				customerQuotesMapper.insert(customerQuote);
			}
		}
			
	}
	@Transactional(readOnly = false)
	public void saveR(CustomerMsg customerMsg) {
		customerMsg.setAddTime(new Date());
		super.save(customerMsg);
		String msgid = customerMsg.getMsgId();
		
		customerMsgProductService.deleteByMsgid(msgid);
		List<CustomerMsgProduct> listCustomerMsgProduct = customerMsg.getListCustomerMsgProduct();		
		for(CustomerMsgProduct customerMsgProduct:listCustomerMsgProduct){
			if(StringUtils.isBlank(customerMsgProduct.getId())){
				customerMsgProduct.setId(IdGen.uuid());
			}
			customerMsgProduct.setCustomerMsgId(msgid);
			customerMsgProductMapper.insert(customerMsgProduct);
		}
		
		customerQuotesService.deleteByMsgid(msgid);
		List<CustomerQuotes> listCustomerQuote = customerMsg.getQuotes();
		if(listCustomerQuote != null && listCustomerQuote.size()>0){
			for(CustomerQuotes customerQuote : listCustomerQuote){
				if(StringUtils.isBlank(customerQuote.getProductCode())){
					continue;
				}
				if(StringUtils.isBlank(customerQuote.getId())){
					customerQuote.setId(IdGen.uuid());
				}		
				customerQuote.setMsgId(msgid);
				customerQuotesMapper.insert(customerQuote);
			}
		}			
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerMsg customerMsg) {
		super.delete(customerMsg);
	}
	
}