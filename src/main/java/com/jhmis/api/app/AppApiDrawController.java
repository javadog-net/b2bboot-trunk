package com.jhmis.api.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.CmsActivity;
import com.jhmis.modules.wechat.entity.CmsSignupDraw;
import com.jhmis.modules.wechat.entity.CmsWinPrize;
import com.jhmis.modules.wechat.entity.WxMeetingSignup;
import com.jhmis.modules.wechat.service.CmsActivityService;
import com.jhmis.modules.wechat.service.CmsSignupDrawService;
import com.jhmis.modules.wechat.service.CmsWinPrizeService;
import com.jhmis.modules.wechat.service.WxMeetingSignupService;

/**
 * <p>
 * Title: AppApiDrawController
 * </p>
 * <p>
 * Description:
 * </p>
 * app端进行参与抽奖表的操作
 * 
 * @author tc
 * @date 2019年2月27日 下午2:07:18
 */
@RestController
@RequestMapping("/api/app/draw")
public class AppApiDrawController extends BaseController {

	/**
	 * @Title: signupDraw
	 * @Description: TODO 点击抽奖按钮参与抽奖
	 * @param actvid
	 * @param meetingid
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年2月27日下午1:43:03
	 */
	@Autowired
	private WxMeetingSignupService meetingSignupService;
	@Autowired
	private CmsSignupDrawService drawService;
	@Autowired
	private CmsWinPrizeService winPrizeService;
	@Autowired
	private CmsActivityService cmsActivityService;
	@Autowired
	private CmsSignupDrawService cmsSignupDrawService;

	/**
	 * @Title: signupDraw
	 * @Description: TODO 点击参与按钮 token
	 * @param actvid
	 * @param meetingid
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年3月6日下午12:10:14
	 */
	@RequestMapping(value = "/signupdraw", method = RequestMethod.POST)
	public AjaxJson signupDraw(@RequestParam(value = "actvid", required = true) String actvid,
			@RequestParam(value = "meetingid", required = true) String meetingid) {
		System.out.println("====********报名活动开始=========");
		int i = 0;
		System.out.println("i循环次数初始值" + i);
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		CmsActivity entity=new CmsActivity();
		entity.setId(actvid);
		entity.setMeetingId(meetingid);
		entity=cmsActivityService.get(entity);
		if(entity!=null){
		  Date da=entity.getStartTime();
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String d= df.format(new Date());//系统时间
          String date=df.format(da);//设定时间
          System.out.println(d);
          Integer c=d.compareTo(date);
          System.out.println(c);
          if(c<0){
        		return AjaxJson.fail("未到抽奖时间，请耐心等待！");
          }
		}
		String userid = purchaserAccount.getId();
		List<WxMeetingSignup> list = meetingSignupService.findMeetingByIdAndIdtwo(userid, meetingid);
		try {

			if (null == list || list.size() == 0) {
				return AjaxJson.fail("请先报名参会");
			} else if (!list.get(0).getSignStatus().equals("3")) {
				return AjaxJson.fail("请等待管理员审核！");
			} else {
				CmsSignupDraw cmsSignup = new CmsSignupDraw();
				cmsSignup = drawService.findReapte(actvid,userid);
				if (null != cmsSignup) {
					System.out.println("i循环次数" + i + "cmsSignup!=null已经参与了不能再参与了");
					return AjaxJson.fail("您已参与，请勿重复参与！");
				}
				WxMeetingSignup meetingSignup = list.get(0);
				CmsSignupDraw cmsSignupDraw = new CmsSignupDraw();
				cmsSignupDraw.setActvId(actvid);
				cmsSignupDraw.setAddTime(new Date());
				cmsSignupDraw.setCompanyName(meetingSignup.getCompanyName());
				cmsSignupDraw.setMeetingId(meetingid);
				cmsSignupDraw.setUserId(userid);
				cmsSignupDraw.setUserName(meetingSignup.getRealName());
				cmsSignupDraw.setUserPhone(meetingSignup.getMobile());
				if (StringUtils.isNotEmpty(purchaserAccount.getAvatar())) {
					cmsSignupDraw.setAvatar(purchaserAccount.getAvatar());
				}
				System.out.println("保存参与人员前i" + i);
				drawService.save(cmsSignupDraw);
				System.out.println("保存参与人员后i" + i);
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("保存参与人员之后 最终i" + i);
		return AjaxJson.ok("参与成功，请等待开奖！");
	}

	/**
	 * @Title: seeDrawInfo
	 * @Description: TODO 查看中奖结果
	 * @param meetingid
	 * @param activityid
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年2月27日下午5:21:41
	 */
	@RequestMapping(value = "/seedrawinfo", method = RequestMethod.POST)
	public AjaxJson seeDrawInfo(@RequestParam(value = "activityid", required = true) String activityid)
			throws Exception {

		List<CmsWinPrize> list = winPrizeService.findDrawList(activityid,null,null,null);
		if (null == list || list.size() == 0) {
			return AjaxJson.fail("还未抽奖，请耐心等待抽奖");
		}
		return AjaxJson.ok(list);
	}

	/**
	 * @Title: panduan
	 * @Description: TODO判断是否有该会议的活动
	 * @param meetingid
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年3月6日上午11:27:47
	 */
	@RequestMapping(value = "/panduan", method = RequestMethod.POST)
	public AjaxJson panduan(@RequestParam(value = "meetingid", required = true) String meetingid) throws Exception {

		CmsActivity cmsActivity = new CmsActivity();
		cmsActivity.setMeetingId(meetingid);
		List<CmsActivity> list = cmsActivityService.findList(cmsActivity);
		if (list == null || list.size() == 0) {
			return AjaxJson.ok("没有活动");
		}

		return AjaxJson.ok(list);
	}

	/**
	 * @Title: signupor
	 * @Description: TODO 判断是否已经参与了此次活动 传入token
	 * @param actvid
	 * @param meetingid
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年3月6日下午12:04:47
	 */
	@RequestMapping(value = "/signupor", method = RequestMethod.POST)
	public AjaxJson signupor(@RequestParam(value = "actvid", required = true) String actvid,
			@RequestParam(value = "meetingid", required = true) String meetingid) throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userid = purchaserAccount.getId();
		CmsSignupDraw cmsSignupDraw = new CmsSignupDraw();
		cmsSignupDraw.setActvId(actvid);
		cmsSignupDraw.setMeetingId(meetingid);
		cmsSignupDraw.setUserId(userid);
		List<CmsSignupDraw> list = cmsSignupDrawService.findList(cmsSignupDraw);
		if (null == list || list.size() == 0) {
			return AjaxJson.ok("未参与此次活动");
		}
		return AjaxJson.ok(list);
	}

}
