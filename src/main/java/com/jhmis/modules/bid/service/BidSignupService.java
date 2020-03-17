/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.bid.entity.BidSignup;
import com.jhmis.modules.bid.mapper.BidSignupMapper;

/**
 * 招投标Service
 * 
 * @author hdx
 * @version 2019-04-11
 */
@Service
@Transactional(readOnly = true)
public class BidSignupService extends CrudService<BidSignupMapper, BidSignup> {

	public BidSignup get(String id) {
		return super.get(id);
	}

	public List<BidSignup> getSign(BidSignup bidSignup) {
		return mapper.getSign(bidSignup);
	}

	public List<BidSignup> findList(BidSignup bidSignup) {
		return super.findList(bidSignup);
	}

	public Page<BidSignup> findPage(Page<BidSignup> page, BidSignup bidSignup) {
		return super.findPage(page, bidSignup);
	}

	@Transactional(readOnly = false)
	public void save(BidSignup bidSignup) {
		super.save(bidSignup);
	}

	@Transactional(readOnly = false)
	public void delete(BidSignup bidSignup) {
		super.delete(bidSignup);
	}

}