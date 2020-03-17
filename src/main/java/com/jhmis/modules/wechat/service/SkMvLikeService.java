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
import com.jhmis.modules.wechat.entity.SkMvLike;
import com.jhmis.modules.wechat.mapper.SkMvLikeMapper;

/**
 * 商空点赞Service
 * @author tc
 * @version 2019-05-28
 */
@Service
@Transactional(readOnly = true)
public class SkMvLikeService extends CrudService<SkMvLikeMapper, SkMvLike> {

	@Autowired
	private SkMvLikeMapper mapper;
	public SkMvLike get(String id) {
		return super.get(id);
	}
	
	public List<SkMvLike> findList(SkMvLike skMvLike) {
		return super.findList(skMvLike);
	}
	
	public Page<SkMvLike> findPage(Page<SkMvLike> page, SkMvLike skMvLike) {
		return super.findPage(page, skMvLike);
	}
	
	@Transactional(readOnly = false)
	public void save(SkMvLike skMvLike) {
		super.save(skMvLike);
	}
	
	@Transactional(readOnly = false)
	public void delete(SkMvLike skMvLike) {
		super.delete(skMvLike);
	}

	public SkMvLike getByMidAndUid(String id, String userid) {
		// TODO Auto-generated method stub
		return mapper.getByMidAndUid(id,userid);
	}
	@Transactional(readOnly = false)
	public void deleteByMidAndUid(String id, String userid) {
		// TODO Auto-generated method stub
		mapper.deleteByMidAndUid(id,userid);
	}

	public List<SkMvLike> findOrLike(String userid, String id) {
		// TODO Auto-generated method stub
		return mapper.findOrLike(userid,id);
	}
	
}