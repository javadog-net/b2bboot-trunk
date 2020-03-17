/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.bid.entity.BidInfoDetail;
import com.jhmis.modules.bid.mapper.BidInfoDetailMapper;

/**
 * 招投标Service
 * @author tc
 * @version 2019-07-23
 */
@Service
@Transactional(readOnly = true)
public class BidInfoDetailService extends CrudService<BidInfoDetailMapper, BidInfoDetail> {
@Autowired
private BidInfoDetailMapper mapper;
	public BidInfoDetail get(String id) {
		return super.get(id);
	}
	
	public List<BidInfoDetail> findList(BidInfoDetail bidInfoDetail) {
		return super.findList(bidInfoDetail);
	}
	
	public Page<BidInfoDetail> findPage(Page<BidInfoDetail> page, BidInfoDetail bidInfoDetail) {
		return super.findPage(page, bidInfoDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(BidInfoDetail bidInfoDetail) {
		super.save(bidInfoDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(BidInfoDetail bidInfoDetail) {
		super.delete(bidInfoDetail);
	}

	@Transactional(readOnly = false)
	public void updateValid(String id,String valid) {
		mapper.updateValid(id,valid);  
	}

	public List<BidInfoDetail> getTimeProjectList() {
		// TODO Auto-generated method stub
		return mapper.getTimeProjectList();
	}
	public List<BidInfoDetail> getVisitProjectList() {
		// TODO Auto-generated method stub
		return mapper.getVisitProjectList();
	}
}