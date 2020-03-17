/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.bid.entity.BidInfo;
import com.jhmis.modules.bid.mapper.BidInfoMapper;

/**
 * 招投标Service
 * @author hdx
 * @version 2019-04-11
 */
@Service
@Transactional(readOnly = true)
public class BidInfoService extends CrudService<BidInfoMapper, BidInfo> {

	public BidInfo get(String id) {
		return super.get(id);
	}
	
	public List<BidInfo> findList(BidInfo bidInfo) {
		return super.findList(bidInfo);
	}
	
	public List<BidInfo> getVisitProjectList() {
		return mapper.getVisitProjectList();
	}
	
	public Page<BidInfo> findPage(Page<BidInfo> page, BidInfo bidInfo) {
		return super.findPage(page, bidInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(BidInfo bidInfo) {
		super.save(bidInfo);
	}
	
	@Transactional(readOnly = false)
	public void updateValid(String id,String valid) {
		mapper.updateValid(id,valid);
	}
	
	@Transactional(readOnly = false)
	public void delete(BidInfo bidInfo) {
		super.delete(bidInfo);
	}
	
}