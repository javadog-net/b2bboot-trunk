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
import com.jhmis.modules.wechat.entity.WxAppCid;
import com.jhmis.modules.wechat.mapper.WxAppCidMapper;

/**
 * 设备cidService
 * @author tc
 * @version 2019-05-07
 */
@Service
@Transactional(readOnly = true)
public class WxAppCidService extends CrudService<WxAppCidMapper, WxAppCid> {
@Autowired
WxAppCidMapper mapper;
	
	public WxAppCid get(String id) {
		return super.get(id);
	}
	
	public List<WxAppCid> findList(WxAppCid wxAppCid) {
		return super.findList(wxAppCid);
	}
	
	public Page<WxAppCid> findPage(Page<WxAppCid> page, WxAppCid wxAppCid) {
		return super.findPage(page, wxAppCid);
	}
	
	@Transactional(readOnly = false)
	public void save(WxAppCid wxAppCid) {
		super.save(wxAppCid);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxAppCid wxAppCid) {
		super.delete(wxAppCid);
	}
	@Transactional(readOnly = false)
	public void deleteByUserId(String wxAppCid) {
		mapper.deleteByUserId(wxAppCid);
	}
	public List<WxAppCid> findListCidByUserid(String friendId) {
		// TODO Auto-generated method stub
		return mapper.findListCidByUserid(friendId);
	}
	@Transactional(readOnly = false)
	public void deleteByUserPhone(String mobile) {
		// TODO Auto-generated method stub
		mapper.deleteByUserPhone(mobile);
	}

	public List<WxAppCid> findByCid(String cId) {
		// TODO Auto-generated method stub
		return mapper.findByCid(cId);
	}

	public List<WxAppCid> getByCidAndUserId(WxAppCid cid) {
		// TODO Auto-generated method stub
		return mapper.getByCidAndUserId(cid);
	}
	
}