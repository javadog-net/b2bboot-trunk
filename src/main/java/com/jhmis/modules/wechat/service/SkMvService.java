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
import com.jhmis.modules.wechat.entity.SkMv;
import com.jhmis.modules.wechat.mapper.SkMvMapper;

/**
 * 商空视频Service
 * @author Tc
 * @version 2019-05-24
 */
@Service
@Transactional(readOnly = true)
public class SkMvService extends CrudService<SkMvMapper, SkMv> {

	@Autowired
	private SkMvMapper mapper;
	
	public SkMv get(String id) {
		return super.get(id);
	}
	
	public List<SkMv> findList(SkMv skMv) {
		return super.findList(skMv);
	}
	
	public Page<SkMv> findPage(Page<SkMv> page, SkMv skMv) {
		return super.findPage(page, skMv);
	}
	
	@Transactional(readOnly = false)
	public void save(SkMv skMv) {
		super.save(skMv);
	}
	
	@Transactional(readOnly = false)
	public void delete(SkMv skMv) {
		super.delete(skMv);
	}
	@Transactional(readOnly = false)
	public List<SkMv> findAll() {
		return mapper.findAll();
	}

	public List<SkMv> findById(String id) {
		// TODO Auto-generated method stub
		return mapper.findById(id);
	}

	public List<SkMv> findIsTop() {
		// TODO Auto-generated method stub
		return mapper.findIsTop();
	}
	@Transactional(readOnly = false)
	public void isTop(String id, String top) {
		// TODO Auto-generated method stub
		mapper.isTop(id,top);
	}



	
	
	
}