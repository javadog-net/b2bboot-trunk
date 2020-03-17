/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.app;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.tools.utils.PinyinTool;
import com.jhmis.modules.wechat.entity.WxAppCid;
import com.jhmis.modules.wechat.entity.WxFriend;
import com.jhmis.modules.wechat.entity.WxFriendMessage;
import com.jhmis.modules.wechat.entity.WxMember;
import com.jhmis.modules.wechat.entity.WxPending;
import com.jhmis.modules.wechat.service.WxAppCidService;
import com.jhmis.modules.wechat.service.WxFriendMessageService;
import com.jhmis.modules.wechat.service.WxFriendService;
import com.jhmis.modules.wechat.service.WxMemberService;
import com.jhmis.modules.wechat.service.WxPendingService;

import net.sf.json.JSONObject;

/**
 * 好友相关Controller
 *
 * @author hdx
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/app/friends")
public class AppApiFriendsController extends BaseController {
	@Autowired
	SimpMessageSendingOperations simpMessageSendingOperations;
	@Autowired
	private WxMemberService wxMemberService;

	@Autowired
	private PurchaserService purchaserService;

	@Autowired
	private PurchaserAccountService purchaserAccountService;

	@Autowired
	private WxFriendService wxFriendService;
	@Autowired
	private WxFriendMessageService wxFriendMessageService;
	@Autowired
	private WxPendingService wxPendingService;
@Autowired
private WxAppCidService wxAppCidService;  
	/**
	 * 根据手机号查询好友
	 * 
	 * @return 好友的微信信息
	 */
	@RequestMapping(value = "/getList/{mobile}")
	public AjaxJson getList(@PathVariable("mobile") String mobile) throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		PurchaserAccount friend = new PurchaserAccount();
		friend.setMobile(mobile);
		List<PurchaserAccount> friends = purchaserAccountService.findList(friend);
		return AjaxJson.ok(friends);
	}

	/**
	 * 用户根据好友的id,查询好友信息
	 * 
	 * @param friendId
	 *            好友id
	 * @return
	 */
	@RequestMapping(value = "/getPurchaserByFriendId")
	@ResponseBody
	public AjaxJson getPurchaserByUserId(String friendId) throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaserAccount.getId();
		Map<String, Object> map = new HashMap<>();
		// 是否是自己
		map.put("isMine", 0);
		boolean isFriends = false;
		// 获取采购商信息
		PurchaserAccount currentAccount = purchaserAccountService.get(friendId);
		currentAccount.setAvatar(purchaserAccountService.getAvatarById(friendId));
		currentAccount.setNickName(purchaserAccountService.getNicknameById(friendId));
		Purchaser purchaser = purchaserService.get(currentAccount.getPurchaserId());
		// 获取基本信息
		WxMember wxMember = wxMemberService.findUniqueByProperty("user_id", friendId);
		// 查看是否已经是好友
		WxFriend wxFriend = new WxFriend();
		wxFriend.setUserId(userId);
		wxFriend.setFriendId(friendId);
		wxFriend.setTypeId("1");
		List<WxFriend> wxFriendList = wxFriendService.findList(wxFriend);
		if (wxFriendList != null && wxFriendList.size() > 0) {
			isFriends = true;
			map.put("wxFriend", wxFriendList.get(0));
		}
		if (friendId.equals(userId)) {
			// 查询自己
			map.put("isMine", 1);
		}
		map.put("isFriends", isFriends);
		map.put("currentAccount", currentAccount);
		map.put("purchaser", purchaser);
		map.put("wxMember", wxMember);
		return AjaxJson.ok(map);
	}

	/**
	 * 添加好友
	 *
	 * @param userId
	 * @param nickName
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public AjaxJson list(String userId, String nickName) throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String id = purchaserAccount.getId();
		if (StringUtils.isBlank(id)) {
			return AjaxJson.fail("参数异常");
		}
		if ("".equals(userId)) {
			return AjaxJson.fail("参数异常");
		}
		if (id.equals(userId)) {
			return AjaxJson.fail("你不能添加自己到通讯录");
		}
		WxFriend wf = new WxFriend();
		wf.setFriendId(userId);
		wf.setUserId(id);
		List<WxFriend> lsitWxFriend = wxFriendService.findList(wf);
		// 创建待处理对象
		WxPending pending = new WxPending();
		pending.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		pending.setUserId(userId);
		pending.setType("2");
		pending.setTypeId(id);
		pending.setFriendName(purchaserAccountService.getNicknameById(id));
		pending.setStatus("0");
		pending.setTime(new Date());
		pending.setSignStatus(purchaserAccount.getAvatar());
		if (lsitWxFriend != null && lsitWxFriend.size() > 0) {
			if ("2".equals(lsitWxFriend.get(0).getTypeId())) {
				// 添加好友被拒绝忽略
				WxFriend refuseWx = lsitWxFriend.get(0);
				refuseWx.setTypeId("0");
				wxFriendService.update(refuseWx);
				pending.setMeetingName("申请添加您为好友，请及时处理。");
				wxPendingService.insert(pending);
				JSONObject pendingJsonObject = JSONObject.fromObject(pending);// 将java对象转换为json对象
				String pendingJson = pendingJsonObject.toString();
				simpMessageSendingOperations.convertAndSendToUser(userId, "/pending", pendingJson);
				return AjaxJson.fail("您已再次申请");
			}
			return AjaxJson.fail("您已申请请耐心等待");
		}
		PurchaserAccount fuser = purchaserAccountService.get(userId);
		if (fuser == null) {
			return AjaxJson.fail("您添加的好友不是企业购用户");
		}
		WxFriend wxFriend = new WxFriend();
		// 没有设置昵称，则取nickName
		if (StringUtils.isBlank(nickName)) {
			// 获取采要添加购商信息
			nickName = fuser.getNickName();
			if (StringUtils.isBlank(nickName)) {
				nickName = fuser.getRealName();
			}
			// 获取本人信息
			wxFriend.setUserName(purchaserAccountService.getNicknameById(id));
		}
		// 待处理状态
		wxFriend.setTypeId("0");
		wxFriend.setUserId(id);
		wxFriend.setFriendId(userId);
		wxFriend.setAddTime(new Date());
		wxFriend.setAddPerson(id);

		// 获取添加者用户名称
		PurchaserAccount ca = purchaserAccountService.get(id);
		if (ca == null) {
			return AjaxJson.fail("您不是企业购用户");
		}
		if (StringUtils.isBlank(wxFriend.getUserName())) {
			wxFriend.setUserName(ca.getRealName());
		}
		wxFriend.setNickName(nickName);
		// 主动添加标识
		wxFriend.setStatus("0");
		// 昵称首字母排序转化
		PinyinTool tool = new PinyinTool();
		String nameLetter = tool.toPinYin(nickName, "", PinyinTool.Type.LOWERCASE);
		wxFriend.setNameLetter(nameLetter);

		wxFriendService.save(wxFriend);
		pending.setMeetingName("申请添加您为好友，请及时处理。");
		wxPendingService.insert(pending);
		JSONObject pendingJsonObject = JSONObject.fromObject(pending);// 将java对象转换为json对象
		String pendingJson = pendingJsonObject.toString();
		simpMessageSendingOperations.convertAndSendToUser(userId, "/pending", pendingJson);
		
	List<WxAppCid>	 list=wxAppCidService.findListCidByUserid(id);
	for(WxAppCid cid:list){
		if(StringUtils.isNotBlank(cid.getCId())&&StringUtils.isNotBlank(cid.getPhoneType())){
		ApiAppGeTuiToFriendController.pushToMsgByCid(cid.getCId(), "有人申请添加您为好友，请及时处理。", "好友申请",
				cid.getPhoneType(), "/pages/new_firend/new_firend");
		}
	}
		return AjaxJson.ok();
	}

	/** 
	  * @Title: setFriendNickname 
	  * @Description: TODO  设置好友昵称
	  * @param friendId
	  * @param nickname
	  * @return
	  * @throws Exception 
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年6月25日上午10:37:46
	  */
	@RequestMapping(value = "/setFriendNickname")
	public AjaxJson setFriendNickname(String friendId, String nickname) throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaserAccount.getId();
		WxFriend wxFriend = new WxFriend();
		wxFriend.setUserId(userId);
		wxFriend.setFriendId(friendId);
		wxFriend.setTypeId("1");
		List<WxFriend> wfs = wxFriendService.findList(wxFriend);
		if (wfs != null&&wfs.size()>0) {
			for (WxFriend wf : wfs) {
				wf.setNickName(nickname);
				// 昵称首字母排序转化
				PinyinTool tool = new PinyinTool();
				String nameLetter = tool.toPinYin(nickname, "", PinyinTool.Type.LOWERCASE);
				wf.setNameLetter(nameLetter);
				System.out.println(wf);
				wxFriendService.save(wf);

				return AjaxJson.ok("修改备注成功！");
			}
		} else {
			return AjaxJson.fail("对方不是您的好友，请先添加对方好友再修改备注！");
		}
		return AjaxJson.fail("对方不是您的好友，请先添加对方好友再修改备注！");
	}

	/**
	 * 查询当前用户的好友列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUserFriends")
	public AjaxJson getUserFriends() throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaserAccount.getId();

		WxFriend wxFriend = new WxFriend();
		wxFriend.setUserId(userId);
		wxFriend.setTypeId("1");
		List<WxFriend> wxFriendList = wxFriendService.findList(wxFriend);
		for (WxFriend wf : wxFriendList) {
			wf.setAddPerson(purchaserAccountService.getAvatarById(wf.getFriendId()));
		}
		return AjaxJson.ok(wxFriendList);
	}

	/**
	 * 查询当前用户新的朋友信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUserNewInfo")
	public AjaxJson getUserNewInfo() throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaserAccount.getId();

		List<WxFriend> wxFriendList = wxFriendService.getUserNewInfo(userId);
		for (WxFriend wf : wxFriendList) {
			if (wf.getTypeId().equals("1")) {
				wf.setNameLetter(purchaserAccountService.getAvatarById(wf.getFriendId()));
			} else {
				wf.setNameLetter(purchaserAccountService.getAvatarById(wf.getUserId()));
			}
		}
		return AjaxJson.ok(wxFriendList);
	}

	/**
	 * 接受或者忽略用户请求
	 * 
	 * @param friendId
	 * @param tag
	 * @return
	 */
	@RequestMapping(value = "/handle")
	public AjaxJson handel(String friendId, String tag) throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaserAccount.getId();
		if (StringUtils.isBlank(friendId) || StringUtils.isBlank(tag)) {
			return AjaxJson.fail("参数异常");
		}

		Boolean flag = wxFriendService.handle(friendId, userId, tag);
		return AjaxJson.ok(flag);
	}

	/**
	 * 查询好友聊天列表
	 *
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/findFriendMsgInfoList")
	public AjaxJson findFriendMsgInfoList() throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaserAccount.getId();
		// String userId="4ac0b5fc00cc453eaeeaaeb298e62ce3";
		List<WxFriendMessage> WxFriendMessageList = wxFriendMessageService.findFriendMsgInfoList(userId);

		for (WxFriendMessage m : WxFriendMessageList) {
			String fromuserid = wxFriendMessageService.getlastmessage(userId, m.getFriendId()).getFromUserId();
			String a = wxFriendMessageService.getlastmessage(userId, m.getFriendId()).getContent();
			System.out.println(a + "a");
			System.out.println("fromuserid" + fromuserid);
			if (fromuserid.equals(userId)) {
				m.setIsRead("1");
			}
		}

		return AjaxJson.ok(WxFriendMessageList);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJson delete(String friendId) throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		WxFriend wxFriend = new WxFriend();
		wxFriend.setUserId(purchaserAccount.getId());
		wxFriend.setFriendId(friendId);
		wxFriendService.delete(wxFriend);
		return AjaxJson.ok("删除成功");
	}

}