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
import com.jhmis.modules.wechat.entity.SkMvDetails;
import com.jhmis.modules.wechat.mapper.SkMvDetailsMapper;

/**
 * 商空视频详情Service
 * @author tc
 * @version 2019-05-23
 */
@Service
@Transactional(readOnly = true)
public class SkMvDetailsService extends CrudService<SkMvDetailsMapper, SkMvDetails> {

	
	@Autowired
	private SkMvDetailsMapper mapper;
	public SkMvDetails get(String id) {
		return super.get(id);
	}
	
	public List<SkMvDetails> findList(SkMvDetails skMvDetails) {
		return super.findList(skMvDetails);
	}
	
	public Page<SkMvDetails> findPage(Page<SkMvDetails> page, SkMvDetails skMvDetails) {
		return super.findPage(page, skMvDetails);
	}
	
	@Transactional(readOnly = false)
	public void save(SkMvDetails skMvDetails) {
		super.save(skMvDetails);
	}
	
	@Transactional(readOnly = false)
	public void delete(SkMvDetails skMvDetails) {
		super.delete(skMvDetails);
	}
	
	
	@Transactional(readOnly = false)
	public void updateVisits(String i, String id) {
		// TODO Auto-generated method stub
		mapper.updateVisits(i,id);
	}
	@Transactional(readOnly = false)
	public void updateLike(String i, String id) {
		// TODO Auto-generated method stub
		mapper.updateLike(i,id);
	}

	public SkMvDetails getSkMvById(String id) {
		// TODO Auto-generated method stub
		return mapper.getSkMvById(id);
	}
	@Transactional(readOnly = false)
	public void updateComment(String i, String id) {
		// TODO Auto-generated method stub
		mapper.updateComment(i,id);
	}

}