package com.jhmis.api.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.wechat.entity.WxAppCid;
import com.jhmis.modules.wechat.service.CmsSignupDrawService;
import com.jhmis.modules.wechat.service.CmsWinPrizeService;
import com.jhmis.modules.wechat.service.WxAppCidService;
import com.jhmis.modules.wechat.service.WxFriendMessageService;
import com.jhmis.modules.wechat.service.WxFriendService;
import com.jhmis.modules.wechat.service.WxGroupUserService;
import com.jhmis.modules.wechat.service.WxMeetingSignupService;
import com.jhmis.modules.wechat.service.WxPendingService;
import com.jhmis.modules.wechat.service.WxSignInService;
import com.jhmis.modules.wechat.service.WxTravelService;

@RestController
@RequestMapping("/api/app/delete")
public class AppApiDeleteMyInfo extends BaseController {
	@Autowired
	PurchaserService purchaserservice;
	@Autowired
	PurchaserAccountService purchaseraccountservice;
	@Autowired
	WxFriendService wxFriendService;
	@Autowired
	WxFriendMessageService wxFriendMessageService;
	@Autowired
	WxGroupUserService wxgroupuserservice;
	@Autowired
	WxMeetingSignupService wxMeetingSignupService;
	@Autowired
	WxSignInService wxSignInService;
	@Autowired
	WxTravelService wxTravelService;
	@Autowired
	WxPendingService wxPendingService;
	@Autowired
	CmsSignupDrawService cmsSignupDrawService;
	@Autowired
	CmsWinPrizeService cmsWinPrizeService;
	@Autowired
	private WxAppCidService wxAppCidService;

	@RequestMapping(value = "/deleteInfo")
	public AjaxJson deleteInfo(@RequestParam(value = "mobiles", required = true) String mobiles) {
		System.out.println("mobiles" + mobiles);

		if (StringUtils.isBlank(mobiles)) {
			return AjaxJson.ok("再不输入手机号我打死你！！！");

		}
		if (null == mobiles || mobiles.equals("") || mobiles.equals(" ")) {
			return AjaxJson.ok("再不输入手机号我打死你！！！");

		}

		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		int i4 = 0;
		int i5 = 0;
		int i6 = 0;
		int i7 = 0;
		int i8 = 0;
		int i9 = 0;
		int i10 = 0;
		int i11 = 0;
		int i12 = 0;
		int i13 = 0;

		try {

			

			List<Purchaser> list = purchaserservice.findListbymobile(mobiles);
			for (Purchaser p : list) {

				PurchaserAccount a = new PurchaserAccount();
				a.setPurchaserId(p.getId());
				List<PurchaserAccount> lista = purchaseraccountservice.findList(a);

				System.out.println("lista 'purchaseraccount" + lista.size());
				i1 = purchaserservice.deleteById(p.getId());// 删除purchaser
				System.out.println("i1影响purchaser的行数" + i1);
				for (PurchaserAccount account : lista) {
					
					wxAppCidService.deleteByUserId(account.getId());//删除cid
					wxAppCidService.deleteByUserPhone(account.getMobile());//删除cid
					i2 = wxFriendService.deletebyuserid(account.getId());// 删除好友
					System.out.println("i1影响wxFriendService--userid的行数" + i2);
					i3 = wxFriendService.deletebufriendid(account.getId());// 删除好友
					int i0 = wxFriendService.deleteaddperson(account.getId());// 删除addperson
					System.out.println(i0 + "删除addperson");
					System.out.println("i1影响wxFriendService--friendid的行数" + i3);
					i4 = wxFriendMessageService.deletebyfromuserid(account.getId());// 删除好友信息
					System.out.println("i1影响wxFriendMessageService--fromuserid的行数" + i4);
					i5 = wxFriendMessageService.deletebytouserid(account.getId());// 删除好友信息
					System.out.println("i1影响wxFriendMessageService--touserid的行数" + i5);
					i6 = wxgroupuserservice.deletebyuserid(account.getId());// 删除群聊好友
					System.out.println("i1影响wxgroupuserservice--userid的行数" + i6);
					i7 = wxMeetingSignupService.deletebyuserid(account.getId());// 删除参与会议的用户
					System.out.println("i1影响wxMeetingSignupService--userid的行数" + i7);
					i8 = wxSignInService.deletebyuserid(account.getId());// 删除签到的人
					System.out.println("i1影响wxSignInService--userid的行数" + i8);
					i9 = wxTravelService.deletebyuserid(account.getId());// 删除行程的人
					System.out.println("i1影响wxTravelService--userid的行数" + i9);
					i10 = wxPendingService.deletebyuserid(account.getId());// 删除待处理的信息
					System.out.println("i1影响wxPendingService--userid的行数" + i10);
					i11 = cmsSignupDrawService.deletebyuserid(account.getId());// 删除抽奖参与人
					System.out.println("i1影响cmsSignupDrawService--userid的行数" + i11);
					i12 = cmsWinPrizeService.deletebyuserid(account.getId());// 删除中奖人
					System.out.println("i1影响cmsWinPrizeService--userid的行数" + i12);
					i13 = purchaseraccountservice.deleteBypurchaserid(account.getId());// 删除account
					System.out.println("i1影响purchaseraccountservice--userid的行数" + i13);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("i的总数" + i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13);

		List<Purchaser> listtwo = purchaserservice.findListbymobile(mobiles);
		for (Purchaser pu : listtwo) {
			System.out.println("二次删除purchaser前");
			purchaserservice.delete(pu);
			System.out.println("二次删除purchaser后");
		}
		PurchaserAccount purchaserAccount = new PurchaserAccount();
		purchaserAccount.setLoginName(mobiles);
		List<PurchaserAccount> acc = purchaseraccountservice.findList(purchaserAccount);
		for (PurchaserAccount pa : acc) {
			System.out.println("二次删除account前");
			purchaseraccountservice.delete(pa);
			System.out.println("二次删除account后");
		}
		purchaseraccountservice.deletebymobiles(mobiles);
		return AjaxJson.ok("删除成功");
	}

	@RequestMapping(value = "/SendMessage")
	public AjaxJson testSendMessage(@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "text", required = true) String text) {
		String result = "";
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(text)) {
			result = "请输入手机号或短信内容！";
		} else {
			try {

				result =SendMsgApi.SendMessage(mobile, text);
			} catch (Exception e) {
			}
		}
		return AjaxJson.ok(result);
	}

}
