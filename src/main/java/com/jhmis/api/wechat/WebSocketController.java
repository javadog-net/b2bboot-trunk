package com.jhmis.api.wechat;

import com.alibaba.fastjson.JSON;
import com.jhmis.api.app.ApiAppGeTuiToFriendController;
import com.jhmis.api.app.ApiAppGeTuiToGroupController;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.WxAppCid;
import com.jhmis.modules.wechat.entity.WxFriend;
import com.jhmis.modules.wechat.entity.WxFriendMessage;
import com.jhmis.modules.wechat.entity.WxGroupMessage;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.entity.WxMessage;
import com.jhmis.modules.wechat.service.WxAppCidService;
import com.jhmis.modules.wechat.service.WxFriendMessageService;
import com.jhmis.modules.wechat.service.WxFriendService;
import com.jhmis.modules.wechat.service.WxGroupMessageService;
import com.jhmis.modules.wechat.service.WxGroupService;
import com.jhmis.modules.wechat.service.WxGroupUserService;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * webSocket测试
 *
 * @author lihy
 * @version 2018/6/15
 */
@Controller
public class WebSocketController extends BaseController {

	@Autowired
	SimpMessageSendingOperations simpMessageSendingOperations;

	@Autowired
	WxGroupMessageService wxGroupMessageService;

	@Autowired
	WxFriendMessageService wxFriendMessageService;

	@Autowired
	WxFriendService wxFriendService;
	@Autowired
	WxGroupUserService wxGroupUserService;
	@Autowired
	PurchaserService purchaserService;
	@Autowired
	PurchaserAccountService purchaserAccountService;
	@Autowired

	WxAppCidService wxAppCidService;
	@Autowired
	WxGroupService wxGroupService;

	/**
	 * 定时推送消息
	 *
	 * @author lihy
	 */
	@Scheduled(fixedRate = 1000)
	public void callback() {
		// 发现消息
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpMessageSendingOperations.convertAndSend("/topic/greetings", "广播消息" + df.format(new Date()));
	}

	/**
	 * 发送给单一客户端 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message”
	 * ,这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行。
	 *
	 * @return java.lang.String
	 * @author lihy
	 */
	@RequestMapping(path = "/sendToUser")
	public AjaxJson sendToUser(String msg) {
		String openid = "12312";
		try {
			simpMessageSendingOperations.convertAndSendToUser(openid, "/message", "你好" + openid);
		} catch (Exception e) {
			e.getMessage();
			return AjaxJson.fail("未发送成功");
		}
		return AjaxJson.ok();
	}

	/**
	 * 接收客户端发来的群发消息
	 *
	 * @author hdx
	 */
	// 群聊天
	@MessageMapping("/notice/{groupId}")
	public void notice(@DestinationVariable("groupId") String groupId, String msg) {
		logger.info("-------------------群聊开始----------------------");
		WxMessage wxMessage = JSON.parseObject(msg, WxMessage.class);

		logger.info("msg = [" + msg + "]");
		if (!"".equals(groupId)) {
			WxGroupMessage wxGroupMessage = new WxGroupMessage();
			wxGroupMessage.setContent(wxMessage.getMessage());
			wxGroupMessage.setGroupId(groupId);
			wxGroupMessage.setSendTime(new Date());
			wxGroupMessage.setUserId(wxMessage.getUserId());
			wxGroupMessage.setImg(wxMessage.getUrl());
			wxGroupMessage.setType(wxMessage.getMessageType());
			wxGroupMessage.setFileName(wxMessage.getFileName());
			wxGroupMessage.setShareUrl(wxMessage.getShareUrl());
			wxGroupMessage.setShareSummary(wxMessage.getShareSummary());
			wxGroupMessageService.save(wxGroupMessage);
			logger.info("修改群聊状态 未读 0 前");
			wxGroupUserService.updateIsRead(groupId, "0", wxMessage.getUserId());
			logger.info("修改群聊状态 未读 0 后");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Purchaser p = purchaserService.get(purchaserAccountService.get(wxMessage.getUserId()).getPurchaserId());
			wxMessage.setSendTime(sdf2.format(new Date()));
			wxMessage.setUserName(purchaserAccountService.get(wxMessage.getUserId()).getNickName());
			wxMessage.setCompanyName(p.getCompanyName());
			JSONObject wxMessageJson = JSONObject.fromObject(wxMessage);
			msg = wxMessageJson.toString();
			logger.info(groupId);
			// 发送消息给订阅 "/topic/notice" 且在线的用户
			simpMessageSendingOperations.convertAndSend("/topic/notice/" + groupId, msg);
		/*	try {

				String name = "E企碰碰";
				if (wxGroupService.get(groupId) != null) {
					name = wxGroupService.get(groupId).getGroupName();
				}
				String nickName = "";
				String textContent = "";
				// 文字 type =0 content:111,图片：type=1
				if (wxMessage.getMessageType().equals("0")) {
					textContent = wxMessage.getMessage();
				}
				if (wxMessage.getMessageType().equals("1")) {
					textContent = "[图片]";
				}
				PurchaserAccount pa = purchaserAccountService.get(wxMessage.getUserId());

				if (pa != null && StringUtils.isBlank(nickName)) {
					nickName = pa.getNickName();
				}
				if (pa != null && StringUtils.isBlank(nickName)) {
					nickName = pa.getRealName();
				}
				logger.info("查找该群除了自己的所有用户");
				List<WxGroupUser> listgu = wxGroupUserService.findGeTuiUser(groupId, wxMessage.getUserId());
				for (WxGroupUser gu : listgu) {
					if (StringUtils.isNotBlank(gu.getUserId())) {
						List<WxAppCid> listcid = wxAppCidService.findListCidByUserid(gu.getUserId().trim());
						for (WxAppCid cid : listcid) {
							if (StringUtils.isNotBlank(cid.getCId())) {
								logger.info("循环推送消息 群聊");
								ApiAppGeTuiToGroupController.geTuiToGroup(cid.getCId().trim(), groupId.trim(), name,
										textContent, nickName);
							}
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}*/

		}
		// 后台无法通过msg获取好友id，无法判断是否是好友，无法拿到备注，只能拿到个人资料里面的昵称，
		// 因此只能通过前端去调用个人好友的列表接口，判断所发信息之人是否是好友，若是好友拿取自己设置的备注，不是好友则直接显示后台返回的个人资料里面的昵称
		// 前端调用该查询个人好友的接口，将该信息放入缓存里，若修改备注，则更新缓存。
		/*
		 * logger.info("有备注取备注，无备注 取purchaseraccount里面的nickname"); WxFriend
		 * wxFriend = new WxFriend(); wxFriend.setUserId(wxMessage.getUserId());
		 * wxFriend.setTypeId("1");
		 * wxFriend.setFriendId(wxMessage.getFriendId()); List<WxFriend>
		 * wxFriendList= wxFriendService.findList(wxFriend);
		 * logger.info("wxFriendList 长度"+wxFriendList.size());
		 * if(wxFriendList!=null && wxFriendList.size()>0){ WxFriend
		 * wf=wxFriendList.get(0);
		 * if(wf!=null&&StringUtils.isNotBlank(wf.getNickName()) ){
		 * logger.info("是好友，备注也不为空 ，取备注");
		 * wxMessage.setUserName(wf.getNickName()); }else{
		 * logger.info("是好友，备注为空，取个人资料昵称");
		 * wxMessage.setUserName(purchaserAccountService.get(wxMessage.getUserId
		 * ()).getNickName()); } }else{ logger.info("不是好友，取个人资料昵称");
		 * 
		 * wxMessage.setUserName(purchaserAccountService.get(wxMessage.getUserId
		 * ()).getNickName()); }
		 */

	}

	/**
	 * 接收客户端发来的消息
	 *
	 * @author hdx
	 */
	@MessageMapping("/sendFriendsMsg")
	public void receiver(String msg) {
		logger.info("-------------------单聊开始----------------------");
		WxMessage wxMessage = JSON.parseObject(msg, WxMessage.class);
		logger.info("msg = [" + msg + "]");
		if (!"".equals(wxMessage.getFriendId())&&wxMessage.getMessageType().equals("0")||wxMessage.getMessageType().equals("1")) {
			// 证明有此好友
			WxFriendMessage wxFriendMessage = new WxFriendMessage();
			wxFriendMessage.setFromUserId(wxMessage.getUserId());
			wxFriendMessage.setToUserId(wxMessage.getFriendId());
			wxFriendMessage.setContent(wxMessage.getMessage());
			wxFriendMessage.setSendTime(new Date());
			wxFriendMessage.setImg(wxMessage.getUrl());
			wxFriendMessage.setIsRead("0");
			wxFriendMessage.setType(wxMessage.getMessageType());
			wxFriendMessage.setFileName(wxMessage.getFileName());
			wxFriendMessageService.save(wxFriendMessage);
			logger.info("msg = [" + msg + "]");
			simpMessageSendingOperations.convertAndSendToUser(wxMessage.getFriendId(), "/message", msg);
		}
		
		if (!"".equals(wxMessage.getFriendId())&&wxMessage.getMessageType().equals("3")) {
			// 证明有此好友
			WxFriendMessage wxFriendMessage = new WxFriendMessage();
			wxFriendMessage.setFromUserId(wxMessage.getUserId());
			wxFriendMessage.setToUserId(wxMessage.getFriendId());
			wxFriendMessage.setContent(wxMessage.getMessage());
			wxFriendMessage.setSendTime(new Date());
			wxFriendMessage.setImg(wxMessage.getUrl());
			wxFriendMessage.setIsRead("0");
			wxFriendMessage.setShareUrl(wxMessage.getShareUrl());
			wxFriendMessage.setShareSummary(wxMessage.getShareSummary());
			wxFriendMessage.setType(wxMessage.getMessageType());
			wxFriendMessage.setFileName(wxMessage.getFileName());
			wxFriendMessageService.save(wxFriendMessage);
			logger.info("msg = [" + msg + "]");
			simpMessageSendingOperations.convertAndSendToUser(wxMessage.getFriendId(), "/message", msg);
		}
		
		
		
			try {

				String nickName = "";
				String textContent = "";
				// 文字 type =0 content:111,图片：type=1 文件：2 ，分享 3
				if (wxMessage.getMessageType().equals("0")) {
					textContent = wxMessage.getMessage();
				}
				if (wxMessage.getMessageType().equals("1")) {
					textContent = "[图片]";
				}
				if (wxMessage.getMessageType().equals("3")) {
					textContent = "[链接]"+wxMessage.getMessage();
				}

				List<WxAppCid> list = wxAppCidService.findListCidByUserid(wxMessage.getFriendId());
				WxFriend wxFriend = new WxFriend();
				wxFriend.setUserId(wxMessage.getFriendId());
				wxFriend.setFriendId(wxMessage.getUserId());
				List<WxFriend> listfriend = wxFriendService.findListMyFriendByFriendId(wxFriend);
				System.out.println("websocket 私聊 好友listfriend 长度" + listfriend.size());
				if (listfriend != null && listfriend.size() > 0) {
					nickName = listfriend.get(0).getNickName();
				}
				PurchaserAccount pa = purchaserAccountService.get(wxMessage.getUserId());

				if (pa != null && StringUtils.isBlank(nickName)) {
					nickName = pa.getNickName();
				}
				if (pa != null && StringUtils.isBlank(nickName)) {
					nickName = pa.getRealName();
				}
				if (list != null && list.size() > 0) {
					for (WxAppCid cid : list) {
						if (StringUtils.isNotBlank(cid.getCId())) {

							ApiAppGeTuiToFriendController.pushToMsgByCid(cid.getCId().trim(),
									 textContent, nickName,cid.getPhoneType(),"/pages/chat/chat?friendId="+wxMessage.getUserId());
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		

		
		
		
		
	}

}
