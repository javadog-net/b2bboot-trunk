package com.jhmis.api.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.xmp.impl.Utils;
import com.haier.user.api.QixinApi;
import com.haier.user.api.UserApiErrorDesc;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.JWTUtils;
import com.jhmis.common.wsClient.test.main;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.AppIndustry;
import com.jhmis.modules.wechat.entity.EnterpriseDetails;
import com.jhmis.modules.wechat.entity.WxFriend;
import com.jhmis.modules.wechat.entity.WxFriendMessage;
import com.jhmis.modules.wechat.entity.WxGroup;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.entity.WxPending;
import com.jhmis.modules.wechat.entity.WxShareChange;
import com.jhmis.modules.wechat.mapper.AppIndustryMapper;
import com.jhmis.modules.wechat.mapper.WxMemberMapper;
import com.jhmis.modules.wechat.service.AppIndustryService;
import com.jhmis.modules.wechat.service.WxFeedbackService;
import com.jhmis.modules.wechat.service.WxFriendMessageService;
import com.jhmis.modules.wechat.service.WxFriendService;
import com.jhmis.modules.wechat.service.WxGroupService;
import com.jhmis.modules.wechat.service.WxGroupUserService;
import com.jhmis.modules.wechat.service.WxPendingService;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/api/app/myprofile")
public class AppApiMyProfileController {
	@Autowired
	private WxGroupService wxGroupService;
	@Autowired
	PurchaserService purchaserservice;
	@Autowired
	PurchaserAccountService purchaseraccountservice;
	@Autowired
	WxGroupUserService wxgroupuserservice;
	@Autowired
	WxGroupService wxgroupservice;
	@Autowired
	WxFeedbackService wxFeedbaceService;
	@Autowired
	WxMemberMapper wxMemberMapper;
	@Autowired
	private WxPendingService wxPendingService;
	@Autowired
	private WxGroupUserService wxGroupUserService;
	@Autowired
	private WxFriendMessageService wxFriendMessageService;
	@Autowired
	private WxFriendService wxFriendService;
	@Autowired AppIndustryService appIndustryService;

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @Title: findmyprofile
	 * @Description: TODO 我的资料查看
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2018年12月17日上午10:03:03
	 */
	@RequestMapping("/findmyprofile")
	public AjaxJson findmyprofile() {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		purchaserAccount = purchaseraccountservice.get(purchaserAccount.getId());
		purchaserAccount.setAvatar(purchaseraccountservice.getAvatarById(purchaserAccount.getId()));
		purchaserAccount.setNickName(purchaseraccountservice.getNicknameById(purchaserAccount.getId()));
		return AjaxJson.ok(purchaserAccount);
	}

	/**
	 * @Title: updatemyprofile
	 * @Description: TODO 我的资料修改
	 *            accountid
	 * @param address
	 *            详细地址
	 * @param departName
	 *            部门名称
	 * @param industryname
	 *            行业id
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2018年12月17日上午10:03:17, method = RequestMethod.POST
	 */
	@RequestMapping(value = "/updatemyprofile", method = RequestMethod.POST)
	public AjaxJson updatemyprofile(@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "departName", required = true) String departName,
			@RequestParam(value = "industryname", required = true) String industryname,
			@RequestParam(value = "companyname", required = false) String companyname,
			@RequestParam(value = "realname", required = false) String realname,
			@RequestParam(value = "email", required = false) String email) {
		PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
		if (account == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		AjaxJson ret = new AjaxJson();
		try {
			
		
		String id = account.getId();
		account = purchaseraccountservice.get(id);
		PurchaserAccount purchaseraccount1 = account;
		Purchaser purchaser = purchaserservice.get(account.getPurchaserId());
		if (StringUtils.isNotBlank(departName)) {
			account.setDepartName(departName);
			purchaser.setDepart(departName);
		}
		if (StringUtils.isNotBlank(address)) {
			purchaser.setDetailAddress(address);
			account.setDetailAddress(address);
		}
		if (StringUtils.isNotBlank(companyname)) {
			purchaser.setCompanyName(companyname);
			account.setPurchaserName(companyname);
		}
		if (StringUtils.isNotBlank(realname)) {
			account.setRealName(realname);
		}
		if (StringUtils.isNotBlank(email)) {
			account.setEmail(email);
		}

		String msg = "";
		account.setUpdateDate(new Date());
		String industryid = purchaseraccount1.getWxindustryname();// 获取原行业id
		if (StringUtils.isNotBlank(industryid)) {
			if (industryid.equals(industryname)) {
				purchaseraccountservice.updateAccountInfo(account);
				purchaserservice.updateinfo(purchaser);
				System.out.println("行业相同的，至修改了其他信息！");
				msg = "修改信息成功！";
			} else {
				if (StringUtils.isNotBlank(industryname)) {
					wxgroupuserservice.deletebyidandgroupid(id, wxgroupservice.findbysource(industryid).getId());// 删除之前的
					WxGroupUser wxGroupUser1 = new WxGroupUser();
					wxGroupUser1.setGroupId(wxgroupservice.findbysource(industryname).getId());
					wxGroupUser1.setUserId(id);
					wxGroupUser1.setJoinTime(new Date());
					wxGroupUser1.setIsAdmin("1");
					wxGroupUser1.setIsRead("0");
					wxgroupuserservice.save(wxGroupUser1);// ，添加新的
					account.setWxindustryname(industryname);
				}
				purchaseraccountservice.updateAccountInfo(account);
				purchaserservice.updateinfo(purchaser);
				System.out.println("修改了行业，并且删除了之前的行业！");
				msg = "修改信息成功,且您已自动加入该行业群聊！";
			}
		} else {
			System.out.println("industryid 空");
			if (StringUtils.isNotBlank(industryname)) {
				account.setWxindustryname(industryname);
			}
			purchaseraccountservice.updateAccountInfo(account);
			purchaserservice.updateinfo(purchaser);
			WxGroupUser wxGroupUser = new WxGroupUser();
			wxGroupUser.setGroupId(wxgroupservice.findbysource(industryname).getId());
			wxGroupUser.setUserId(id);
			wxGroupUser.setJoinTime(new Date());
			wxGroupUser.setIsAdmin("1");
			wxGroupUser.setIsRead("0");
			wxgroupuserservice.save(wxGroupUser);
			System.out.println("行业相同的，至修改了其他信息！");
			msg = "修改信息成功，且您已自动加入该行业群聊！";
		}
		account.setAvatar(purchaseraccountservice.getAvatarById(account.getId()));
		account.setNickName(purchaseraccountservice.getNicknameById(account.getId()));
		// 先清空账户缓存
		PurchaserUtils.clearCache(account);
		// 再放入缓存
		PurchaserUtils.putCache(account);
		ret.put("account", account);
		ret.put("token", JWTUtils.sign(account.getId(), Global.USER_TYPE_PURCHASER));
		ret.put("msg", msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * @Title: feedback
	 * @Description: TODO
	 * @param content
	 * @return
	 * @return AjaxJson
	 * @author mll
	 * @date 2018年12月18日上午11:14:35
	 */
	@RequestMapping("/feedback")
	public AjaxJson feedback(@RequestParam(value = "content", required = true) String content) {

		PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
		if (account == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		if (StringUtils.isBlank(content)) {
			return AjaxJson.fail("请填写您的宝贵意见，我们能够更快进步！");
		}
		String userId = account.getId();
		wxFeedbaceService.add(userId, content);
		return AjaxJson.ok("意见反馈提交成功！");
	}

	/**
	 * @Title: checkupmyprofile
	 * @Description: TODO
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2018年12月18日下午1:35:53
	 */
	@RequestMapping(value = "/checkupmyprofile")
	public AjaxJson checkupmyprofile() {
		PurchaserAccount purchaseraccount = PurchaserUtils.getPurchaserAccount();
		if (purchaseraccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String id = purchaseraccount.getId();
		purchaseraccount = purchaseraccountservice.get(id);
		if (StringUtils.isBlank(purchaseraccount.getDepartName())) {

			return AjaxJson.fail("请完善部门信息！");
		}
		if (StringUtils.isBlank(purchaseraccount.getDetailAddress())) {

			return AjaxJson.fail("请完善地址信息！");
		}
		if (StringUtils.isBlank(purchaseraccount.getWxindustryname())) {

			return AjaxJson.fail("请完善行业信息！");
		}
		return AjaxJson.ok("检查ok!");
	}

	/**
	 * 设置app头像或昵称等
	 * 
	 * @param imgUrl
	 * @return
	 */
	@RequestMapping(value = "/personalPortrait")
	public AjaxJson personalPortrait(@RequestParam(value = "imgUrl", required = false) String imgUrl,
			@RequestParam(value = "nickName", required = false) String nickName) {

		PurchaserAccount purchaseraccount = PurchaserUtils.getPurchaserAccount();
		if (purchaseraccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String id = purchaseraccount.getId();
		purchaseraccount = purchaseraccountservice.get(id);
		if (StringUtils.isNotBlank(imgUrl)) {
			if (imgUrl.startsWith("http")) {
				purchaseraccount.setAvatar(imgUrl);
			} else {
				purchaseraccount.setAvatar("http://b2b.haier.com/shop" + imgUrl);
			}
		}
		//nickName=nickName.trim();
		if(nickName==null||nickName.equals("")||nickName.equals("undefined")){
			nickName="";
		}
		if (StringUtils.isNotBlank(nickName)) {
			purchaseraccount.setNickName(nickName);
		
		}

		if (StringUtils.isNotBlank(imgUrl) || StringUtils.isNotBlank(nickName)) {
			purchaseraccountservice.updateAccountInfo(purchaseraccount);
		}

		if (StringUtils.isBlank(purchaseraccount.getAvatar())) {
			purchaseraccount.setAvatar(purchaseraccountservice.getAvatarById(purchaseraccount.getId()));
		}
		if (StringUtils.isBlank(purchaseraccount.getNickName())) {
			purchaseraccount.setNickName(purchaseraccountservice.getNicknameById(purchaseraccount.getId()));
		}

		// 先清空账户缓存
		PurchaserUtils.clearCache(purchaseraccount);
		// 再放入缓存
		PurchaserUtils.putCache(purchaseraccount);
		AjaxJson ret = new AjaxJson();
		ret.put("account", purchaseraccount);
		ret.put("token", JWTUtils.sign(purchaseraccount.getId(), Global.USER_TYPE_PURCHASER));
		return ret;
	}

	/**
	 * @Title: findpenind
	 * @Description: TODO查看这个人是否有 未读的消息，或者未读的待处理事项 返回 0 有，1没有
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年5月8日下午8:00:39
	 */
	@RequestMapping(value = "/findpenind")
	public AjaxJson findpenind() {
		PurchaserAccount purchaseraccount = PurchaserUtils.getPurchaserAccount();
		if (purchaseraccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		try {

			wxPendingService.remind(purchaseraccount.getId());
			List<WxPending> listpend = wxPendingService.findPendingList(purchaseraccount.getId());
			if (listpend != null && listpend.size() > 0) {
				for (WxPending wp : listpend) {
					if (wp.getStatus().equals("0")) {
						System.out.println("这是待处理事项未读");
						return AjaxJson.ok("0");
					}
				}
			}
			List<WxGroupUser> listgu = wxGroupUserService.getIsReadGroup(purchaseraccount.getId());
			if (listgu != null && listgu.size() > 0) {
				for (WxGroupUser wgu : listgu) {
					if (wgu.getIsRead().equals("0")) {
						System.out.println("群聊有未读消息");
						return AjaxJson.ok("0");
					}
				}
			}
			List<WxFriendMessage> listmess = wxFriendMessageService.findToUserIdIsMe(purchaseraccount.getId());
			if (listmess != null && listmess.size() > 0) {
				for (WxFriendMessage wfm : listmess) {
					if (wfm.getIsRead().equals("0")) {
						System.out.println("好友消息有未读信息");
						return AjaxJson.ok("0");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("都没有未读，返回1");
		return AjaxJson.ok("1");
	}

	/**
	 * @Title: cleanNoRead
	 * @Description: TODO 清除未读消息
	 * @param id
	 * @param tab
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年6月5日上午9:44:24
	 */
	@RequestMapping(value = "/cleanNoRead")
	public AjaxJson cleanNoRead(String id, String tab) {
		PurchaserAccount purchaseraccount = PurchaserUtils.getPurchaserAccount();
		if (purchaseraccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		if (tab.trim().equals("0")) {
			System.out.println("修改好友聊天已读前"); // friendid-->from_userid
			// userid-->to_userid
			wxFriendMessageService.updateIsRead(id, purchaseraccount.getId());
			System.out.println("修改好友聊天已读后");
		}
		if (tab.trim().equals("1")) {
			System.out.println("修改群聊已读前");
			wxGroupUserService.updateIsReadByUidAndGid(id, purchaseraccount.getId());
			System.out.println("修改群聊已读后");
		}
		return null;
	}
	
	/**
	 * 根据企业名称关键字获取企业列表(分页)每页最多显示20条信息
	 * @param keyword 企业名称关键字
	 * @param skip 跳过的企业信息数目
	 * @return
	 */
	@RequestMapping(value = "/searchEnterprise")
	public String searchEnterprise(String keyword, @RequestParam(value = "skip", defaultValue="0")String skip) {
		String searchList = QixinApi.searchListPaging(keyword,skip);
        return searchList;
	}
	
	/**
	 * 根据企业名称关键字获取企业列表(分页)(测试)每页最多显示20条信息
	 * @param keyword 企业名称关键字
	 * @param skip 跳过的企业信息数目
	 * @return
	 */
	@RequestMapping(value = "/searchEnterpriseTest")
	public String searchEnterpriseTest(String keyword, @RequestParam(value = "skip", defaultValue="0")String skip) {
		String searchList = QixinApi.searchListPagingTest(keyword,skip);
        return searchList;
	}

	/**
	 * 根据企业全名或注册号精确获取企业工商详细信息
	 * @param keyword 企业全名或注册号
	 * @return
	 */
	@RequestMapping(value = "/searchDetailContact")
	public EnterpriseDetails searchDetailContact(String keyword) {
		String contact = QixinApi.searchDetailContact(keyword);
		JSONObject DetailContact = JSONObject.fromObject(contact);
		EnterpriseDetails  eDetails = new EnterpriseDetails();
		eDetails.setStatus(DetailContact.getString("status"));
		eDetails.setMessage(DetailContact.getString("message"));
		JSONObject DetailData = JSONObject.fromObject(DetailContact.getString("data"));
		eDetails.setName(DetailData.getString("name"));
		eDetails.setAddress(DetailData.getString("address"));
		
		String domains = DetailData.getString("domains");
		domains = domains.substring(domains.indexOf('"')+1, domains.lastIndexOf('"'));
		eDetails.setDomains(domains);
		String onelevelid = appIndustryService.findOnelevelidByIndustry(domains);
		if(domains == null){
			domains="e379f05895be46109dca40c30a3b7b71";
		}
		eDetails.setOnelevelid(onelevelid);
				
        return eDetails;
	}
	


	@RequestMapping("/findMyFriendAndGroup")
	public AjaxJson findMyFriendAndGroup() {
		PurchaserAccount purchaseraccount = PurchaserUtils.getPurchaserAccount();
		if (purchaseraccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaseraccount.getId();
		
		List<WxFriend> wxFriendList = wxFriendService.findMyFriendList(userId, "1");
		List<WxGroupUser> groupList = wxGroupUserService.getGroupList(userId);
		 List<WxGroupUser> industryList = wxGroupUserService.getIndustryCircle(userId);
		List<WxShareChange> allList = new ArrayList<>();
		for (WxFriend wf : wxFriendList) {
			WxShareChange share=new WxShareChange();
			share.setId(wf.getFriendId());
			share.setNickName(wf.getNickName());
			share.setType("1");
			share.setAvatar(wf.getAvatar());
			allList.add(share);
		}
		
		for(WxGroupUser g:groupList){
			WxShareChange share=new WxShareChange();
				
			share.setAvatar("http://b2b.haier.com/shop"+g.getImg());
			share.setId(g.getGroupId());
			share.setType("2");
			share.setNickName(g.getGroupName());
			allList.add(share);
		}
		for(WxGroupUser i:industryList){
			WxShareChange share=new WxShareChange();
			share.setAvatar("http://b2b.haier.com/shop/userfiles/app/industry/"+i.getGroupId()+".png");
			share.setId(i.getGroupId());
			share.setType("2");
			share.setNickName(i.getGroupName());
			allList.add(share);
		}
		
		return AjaxJson.ok(allList);
	}

}