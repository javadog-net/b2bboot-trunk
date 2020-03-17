/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jhmis.modules.wechat.entity.WxPending;
import com.jhmis.modules.wechat.mapper.WxPendingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.WxTravel;
import com.jhmis.modules.wechat.mapper.WxTravelMapper;

import javax.annotation.Resource;

/**
 * 行程Service
 * 
 * @author hdx
 * @version 2019-02-14
 */
@Service
@Transactional(readOnly = true)
public class WxTravelService extends CrudService<WxTravelMapper, WxTravel> {
	@Resource
	WxPendingMapper wxPendingMapper;
	@Resource
	WxTravelMapper wxTravelMapper;

	public WxTravel get(String id) {
		return super.get(id);
	}

	public List<WxTravel> findList(WxTravel wxTravel) {
		return super.findList(wxTravel);
	}

	public Page<WxTravel> findPage(Page<WxTravel> page, WxTravel wxTravel) {
		return super.findPage(page, wxTravel);
	}

	@Transactional(readOnly = false)
	public void save(WxTravel wxTravel) {
		// 首先必须是已存在的id
		if (wxTravel.getId() != null) {
			// 进行出票信息的待处理事项
			if ("1".equals(wxTravel.getStartTicket()) && "1".equals(wxTravel.getReturnTicket())) {
				WxPending pending = new WxPending();
				pending.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				pending.setUserId(wxTravel.getPurchaserAccountId());
				// 出票标识
				pending.setType("3");
				// 行程的id
				pending.setTypeId(wxTravel.getId());
				pending.setStatus("0");
				pending.setSignStatus("0");
				pending.setTime(new Date());
				pending.setFriendName("您的行程信息已确认并出票，请确认");
				wxPendingMapper.insert(pending);
			}
		}
		super.save(wxTravel);
	}

	@Transactional(readOnly = false)
	public void delete(WxTravel wxTravel) {
		wxTravel.setIsDel("1");
		super.save(wxTravel);
	}

	@Transactional(readOnly = false)
	public int deletebyuserid(String id) {
		 return wxTravelMapper.deletebyuserid(id);
	}

}