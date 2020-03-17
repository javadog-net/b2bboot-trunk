/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.bid.entity.BidCheckRecord;
import com.jhmis.modules.bid.mapper.BidCheckRecordMapper;

/**
 * 招投标Service
 * @author hdx
 * @version 2019-04-11
 */
@Service
@Transactional(readOnly = true)
public class BidCheckRecordService extends CrudService<BidCheckRecordMapper, BidCheckRecord> {

	public BidCheckRecord get(String id) {
		return super.get(id);
	}
	
	public List<BidCheckRecord> findList(BidCheckRecord bidCheckRecord) {
		return super.findList(bidCheckRecord);
	}
	
	public Page<BidCheckRecord> findPage(Page<BidCheckRecord> page, BidCheckRecord bidCheckRecord) {
		return super.findPage(page, bidCheckRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(BidCheckRecord bidCheckRecord) {
		super.save(bidCheckRecord);
		mapper.updateInfo(bidCheckRecord);

	}
	
	@Transactional(readOnly = false)
	public void delete(BidCheckRecord bidCheckRecord) {
		super.delete(bidCheckRecord);
	}
	
}