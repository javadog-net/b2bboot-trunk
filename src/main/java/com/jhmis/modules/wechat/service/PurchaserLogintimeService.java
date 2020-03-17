/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.PurchaserLogintime;
import com.jhmis.modules.wechat.mapper.PurchaserLogintimeMapper;

/**
 * 采购商登录统计Service
 * @author a
 * @version 2019-11-29
 */
@Service
@Transactional(readOnly = true)
public class PurchaserLogintimeService extends CrudService<PurchaserLogintimeMapper, PurchaserLogintime> {
	@Autowired
	PurchaserLogintimeMapper purchaserLogintimeMapper;
	
	public PurchaserLogintime get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserLogintime> findList(PurchaserLogintime purchaserLogintime) {
		return super.findList(purchaserLogintime);
	}
	
	public Page<PurchaserLogintime> findPage(Page<PurchaserLogintime> page, PurchaserLogintime purchaserLogintime) {
		return super.findPage(page, purchaserLogintime);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserLogintime purchaserLogintime) {
		super.save(purchaserLogintime);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserLogintime purchaserLogintime) {
		super.delete(purchaserLogintime);
	}
	
	public int findNumByAccountId(String accountId){
		Integer num = purchaserLogintimeMapper.findNumByAccountId(accountId);
		if(num==null){
			return 0;
		}else{
			return num.intValue();
		}
	}
}