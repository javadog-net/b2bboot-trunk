/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.WxFriend;
import com.jhmis.modules.wechat.entity.WxFriendMessage;
import com.jhmis.modules.wechat.entity.WxGroupMessage;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.service.WxFriendMessageService;
import com.jhmis.modules.wechat.service.WxFriendService;
import com.jhmis.modules.wechat.service.WxGroupMessageService;
import com.jhmis.modules.wechat.service.WxGroupUserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 信息相关Controller
 * 
 * @author hdx
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/app/message")
public class AppApiMessageController extends BaseController {

	@Autowired
	private WxFriendMessageService wxFriendMessageService;

	@Autowired
	private WxGroupMessageService wxGroupMessageService;
	@Autowired
	private WxGroupUserService wxGroupUserService;
	@Autowired
	private PurchaserService purchaserService;
	@Autowired
	private WxFriendService wxFriendService;

	/**
	 * 获取聊天信息列表
	 * 
	 * @param friendId
	 * @return
	 */
	@ApiOperation(notes = "getMsgList", httpMethod = "POST", value = "获取聊天信息列表", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParam(name = "friendId", value = "朋友id", required = true, paramType = "form", dataType = "String")
	@RequestMapping("/getMsgList")
	public AjaxJson getMsgList(String friendId, HttpServletRequest request, HttpServletResponse response) {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaserAccount.getId();

		Page<WxFriendMessage> page = new Page<WxFriendMessage>(request, response, -1);
		page.setOrderBy("a.send_time");
		WxFriendMessage wxFriendMessage = new WxFriendMessage();
		wxFriendMessage.setToUserId(userId);
		wxFriendMessage.setFromUserId(friendId);
		System.out.println("修改已读前"); // friendid-->from_userid
										// userid-->to_userid
		wxFriendMessageService.updateIsRead(friendId, userId);
		System.out.println("修改已读后");
		Page<WxFriendMessage> wxFriendMessagePage = wxFriendMessageService.findPage(page, wxFriendMessage);
		return AjaxJson.ok(wxFriendMessagePage);
	}

	/**
	 * 获取聊天信息列表(带分页功能)
	 * 
	 * @param friendId
	 *            好友id
	 * @param pageNo
	 *            显示第几页信息
	 * @param pageSize
	 *            每页的信息条数
	 * @return
	 */
	@ApiOperation(notes = "getMsgListP", httpMethod = "POST", value = "获取聊天信息列表", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParam(name = "friendId", value = "朋友id", required = true, paramType = "form", dataType = "String")
	@RequestMapping("/getMsgListP")
	public AjaxJson getMsgListP(String friendId, int pageNo, int pageSize) {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaserAccount.getId();
		Page<WxFriendMessage> page = new Page<WxFriendMessage>(pageNo, pageSize);
		WxFriendMessage wxFriendMessage = new WxFriendMessage();
		wxFriendMessage.setToUserId(userId);
		wxFriendMessage.setFromUserId(friendId);
		int count = wxFriendMessageService.messageNumber(userId, friendId);
		int number = (int) (count % pageSize);
		if (number == 0) {
			page.setPageNo((int) (count / pageSize + 1 - pageNo));
		} else {
			page.setPageNo((int) (count / pageSize + 2 - pageNo));
		}
		page.setPageSize(pageSize);
		page.setOrderBy("a.send_time");

		Page<WxFriendMessage> wxFriendMessagePage = wxFriendMessageService.findPage(page, wxFriendMessage);
		return AjaxJson.ok(wxFriendMessagePage);
	}

	/**
	 * 获取群信息列表
	 * 
	 * @param groupId
	 * @return
	 */
	@ApiOperation(notes = "getGroupMsgList", httpMethod = "POST", value = "获取群信息列表", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParam(name = "groupId", value = "群组id", required = true, paramType = "form", dataType = "String")
	@RequestMapping("/getGroupMsgList")
	public AjaxJson getGroupMsgList(String groupId, HttpServletRequest request, HttpServletResponse response) {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		Page<WxGroupMessage> page = new Page<WxGroupMessage>(request, response, -1);
		page.setOrderBy("a.send_time");
		WxGroupMessage wxGroupMessage = new WxGroupMessage();
		wxGroupMessage.setGroupId(groupId);
		logger.info("wxGroupMessagePage 前");
		Page<WxGroupMessage> wxGroupMessagePage = wxGroupMessageService.findPage(page, wxGroupMessage);
		logger.info("wxGroupMessagePage 后");
		try {
			List<WxGroupMessage> listgm = wxGroupMessagePage.getList();
			for (WxGroupMessage wgm : listgm) {
				String id = wgm.getPurchaserId();
				if (id != null && !id.equals("")) {
					logger.info("id是i" + id);
					Purchaser purchaser = purchaserService.getone(id);
					if (purchaser != null) {
						logger.info("purchaser not null");
						String companyName = purchaser.getCompanyName();
						if (StringUtils.isNotBlank(companyName)) {
							logger.info("companyName" + companyName);
							wgm.setCompanyName(companyName);
						}
					}
					logger.info("有备注取备注，无备注 取purchaseraccount里面的nickname");
					WxFriend wxFriend = new WxFriend();
			        wxFriend.setUserId(purchaserAccount.getId());
			        wxFriend.setTypeId("1");
			        wxFriend.setFriendId(wgm.getUserId());
			        List<WxFriend> wxFriendList= wxFriendService.findList(wxFriend);
			        logger.info("wxFriendList 长度"+wxFriendList.size());
			        if(wxFriendList!=null && wxFriendList.size()>0){
			        	WxFriend wf=wxFriendList.get(0);
			        	if(wf!=null&&StringUtils.isNotBlank(wf.getNickName()) ){
			        		logger.info("是好友，备注也不为空 ，取备注");
			        	wgm.setUserName(wf.getNickName());
			        	}
			        }
				}
				
				
				
			}
			
			// 修改用户已读标记

			logger.info("updateIsReadByUidAndGid 前");
			wxGroupUserService.updateIsReadByUidAndGid(groupId, purchaserAccount.getId());
			logger.info("updateIsReadByUidAndGid 后");
			logger.info("查看用户是否禁言前");
			WxGroupUser wxGroupUser=wxGroupUserService.findIsOrAdmin(groupId, purchaserAccount.getId());
			if(wxGroupUser==null){
				return AjaxJson.fail("信息有误，请检查后再重试");
			}
			//isStopSpeak ：0 未禁言  ， 1 禁言
			if(StringUtils.isNotBlank(wxGroupUser.getIsStopSpeak())&&wxGroupUser.getIsStopSpeak().equals("1")){
				wxGroupMessagePage.setMessage("1");
			}else{
				wxGroupMessagePage.setMessage("0");
			}
			
			logger.info("查看用户是否禁言后");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 去除欢迎语中时间。
		wxGroupMessagePage.getList().get(0).setSendTime(null);
		
		return AjaxJson.ok(wxGroupMessagePage);
	}

	/**
	 * 获取群聊信息(带分页儿功能)
	 * 
	 * @param groupId
	 *            群id
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(notes = "getGroupMsgListP", httpMethod = "POST", value = "获取群信息列表", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParam(name = "groupId", value = "群组id", required = true, paramType = "form", dataType = "String")
	@RequestMapping("/getGroupMsgListP")
	public AjaxJson getGroupMsgListP(String groupId, int pageNo, int pageSize) {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		Page<WxGroupMessage> page = new Page<WxGroupMessage>(pageNo, pageSize);
		WxGroupMessage wxGroupMessage = new WxGroupMessage();
		wxGroupMessage.setGroupId(groupId);

		int count = wxGroupMessageService.messageNumber(groupId);
		int number = (int) (count % pageSize);
		if (number == 0) {
			page.setPageNo((int) (count / pageSize + 1 - pageNo));
		} else {
			page.setPageNo((int) (count / pageSize + 2 - pageNo));
		}
		page.setPageSize(pageSize);
		page.setOrderBy("a.send_time");

		Page<WxGroupMessage> wxGroupMessagePage = wxGroupMessageService.findPage(page, wxGroupMessage);
		// 去除欢迎语中时间。
		wxGroupMessagePage.getList().get(0).setSendTime(null);
		return AjaxJson.ok(wxGroupMessagePage);
	}

}