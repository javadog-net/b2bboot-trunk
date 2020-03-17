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
import com.jhmis.modules.wechat.entity.SkMvComment;
import com.jhmis.modules.wechat.mapper.SkMvCommentMapper;

/**
 * 评论Service
 * @author tc
 * @version 2019-05-28
 */
@Service
@Transactional(readOnly = true)
public class SkMvCommentService extends CrudService<SkMvCommentMapper, SkMvComment> {
	
	@Autowired
	private SkMvCommentMapper mapper;
	

	public SkMvComment get(String id) {
		return super.get(id);
	}
	
	public List<SkMvComment> findList(SkMvComment skMvComment) {
		return super.findList(skMvComment);
	}
	
	public Page<SkMvComment> findPage(Page<SkMvComment> page, SkMvComment skMvComment) {
		return super.findPage(page, skMvComment);
	}
	
	@Transactional(readOnly = false)
	public void save(SkMvComment skMvComment) {
		super.save(skMvComment);
	}
	
	@Transactional(readOnly = false)
	public void delete(SkMvComment skMvComment) {
		super.delete(skMvComment);
	}
	@Transactional(readOnly = false)
	public void updateState(String id, String state,String username) {
		// TODO Auto-generated method stub
		mapper.updateState(id,state,username);
	}

	public List<SkMvComment> findListById(String id) {
		// TODO Auto-generated method stub
		return mapper.findListById(id);
	}
	
}