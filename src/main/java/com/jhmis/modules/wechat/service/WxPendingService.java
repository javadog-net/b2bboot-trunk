package com.jhmis.modules.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.modules.shop.mapper.purchaser.PurchaserAccountMapper;
import com.jhmis.modules.wechat.entity.WxMeeting;
import com.jhmis.modules.wechat.entity.WxMeetingSignup;
import com.jhmis.modules.wechat.entity.WxPending;
import com.jhmis.modules.wechat.mapper.WxFriendMapper;
import com.jhmis.modules.wechat.mapper.WxMeetingMapper;
import com.jhmis.modules.wechat.mapper.WxMemberMapper;
import com.jhmis.modules.wechat.mapper.WxPendingMapper;

@Service
@Transactional(readOnly = true)
public class WxPendingService {
	@Autowired
	WxMeetingSignupService wxMeetingSignupService;
	@Autowired
	WxMeetingMapper wxmeetingmapper;
	@Autowired
	WxFriendMapper wxFriendMapper;
	@Autowired
	WxPendingMapper wxPendingMapper;
	@Autowired
	PurchaserAccountMapper purchaserAccountMapper;
	@Autowired
	WxMemberMapper wxMemberMapper;

	@Transactional(readOnly = false)
	public void insert(WxPending wxPending) {
		wxPendingMapper.insert(wxPending);
	}

	/**
	 * 根据用户id查询待参加会议，将距离开会时间24小时之内的会议存入待处理，并将结果返回
	 * 
	 * @param userId
	 *            用户id
	 * @return 待处理事项链表
	 */
	@Transactional(readOnly = false)
	public void remind(String userId) {
		try {
			List<WxPending> listPending = new ArrayList<WxPending>();
			// 根据用户id查询审核通过的会议
			List<WxMeetingSignup> listYes = wxMeetingSignupService.findmeetingsignupstatus("1", userId);
			List<WxMeetingSignup> listYes4 = wxMeetingSignupService.findmeetingsignupstatus("4", userId);
			listYes.addAll(listYes4);
			if (null == listYes || listYes.size() == 0) {
				return;
			}
			// 根据用户id查询待参加会议，并于开会前一天提醒
			for (WxMeetingSignup wm : listYes) {
				Date nowTime = new Date();
				System.out.println("wm.getMeetingId()" + wm.getMeetingId());
				WxMeeting wxMeeting = wxmeetingmapper.get(wm.getMeetingId());
				System.out.println("wxMeeting" + wxMeeting);
				Date meetingStartTime = wxMeeting.getStartTime();
				long timeDifference = meetingStartTime.getTime() - nowTime.getTime();
				long hour = timeDifference / 3600000;
				if (hour <= 24L && hour > 0L) {
					WxPending pendingM = new WxPending();
					pendingM.setUserId(userId);
					pendingM.setType("1");
					pendingM.setTypeId(wm.getMeetingId());
					pendingM.setMeetingStartTime(meetingStartTime);
					if (wxPendingMapper.getByWxPending(pendingM).size() != 0) {
						continue;
					}
					pendingM.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					pendingM.setStatus("0");
					pendingM.setTime(nowTime);
					pendingM.setMeetingName(wxMeeting.getName());
					pendingM.setMeetingSite(wxMeeting.getSite());
					listPending.add(pendingM);
				}
			}

			// 将listPending中的信息插入到数据库中(wx_pending)
			for (WxPending wp : listPending) {
				wxPendingMapper.insert(wp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据userId从待处理事项表中查询出相应用户的待处理事项
	 * 
	 * @param userId
	 * @return
	 */
	public List<WxPending> findPendingList(String userId) {
		return wxPendingMapper.findAllPending(userId);
	}

	/**
	 * 根据传入的id,设置相应的消息状态为已读
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updataStatus(String id) {
		wxPendingMapper.updateStatusById(id);
		return;
	}

	/**
	 * 根据userId，获取相应的待处理事项的数量
	 * 
	 * @param userId
	 * @return
	 */
	public int pendingNumber(String userId) {
		return wxPendingMapper.getPendingNumber(userId);
	}
	@Transactional(readOnly = false)
	public int deletebyuserid(String id) {
		return wxPendingMapper.deletebyuserid(id);
	}

}
