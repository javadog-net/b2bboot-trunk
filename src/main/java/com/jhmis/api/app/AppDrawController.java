package com.jhmis.api.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.wechat.entity.CmsPrize;
import com.jhmis.modules.wechat.entity.CmsSignupDraw;
import com.jhmis.modules.wechat.entity.CmsWinPrize;
import com.jhmis.modules.wechat.service.CmsActivityService;
import com.jhmis.modules.wechat.service.CmsPrizeService;
import com.jhmis.modules.wechat.service.CmsSignupDrawService;
import com.jhmis.modules.wechat.service.CmsWinPrizeService;

/**
 * <p>
 * Title: AppDrawController
 * </p>
 * <p>
 * Description:web端抽奖操作
 * </p>
 * 
 * @author tc
 * @date 2019年3月8日 下午2:13:39
 */
@RestController
@RequestMapping(value = "/api/web/app/draw")
public class AppDrawController extends BaseController {

	@Autowired
	private CmsPrizeService prizeService;
	@Autowired
	private CmsSignupDrawService signupDrawService;
	@Autowired
	private CmsWinPrizeService winPrizeService;
	@Autowired
	private CmsActivityService activityService;

	/*
	 * @ModelAttribute public AjaxJson check(){ User currentUser =
	 * UserUtils.getUser(); if(currentUser==null){ return
	 * AjaxJson.fail("请先登录！"); } System.out.println("登录了"); return null; }
	 */
	/**
	 * @Title: initDraw
	 * @Description: TODO 初始化抽奖页面
	 * @param actvid
	 * @param meetingid
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年2月27日下午5:36:48
	 */
	@RequestMapping(value = "/initdraw", method = RequestMethod.POST)
	public AjaxJson initDraw(@RequestParam(value = "actvid", required = true) String actvid,
			@RequestParam(value = "meetingid", required = true) String meetingid) {
		System.out.println("actvid" + actvid + "===" + "meetingid" + meetingid);
		CmsPrize cmsPrize = new CmsPrize();
		cmsPrize.setMeetingId(meetingid);
		cmsPrize.setActvId(actvid);
		// cmsPrize.setPrizeTab("0");
		List<CmsPrize> list = null;
		try {
			list = prizeService.findList(cmsPrize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size() + "奖项长度");
		if (null == list || list.size() == 0) {
			return AjaxJson.fail("奖项有误，请重新设置奖项。");
		}
		return AjaxJson.ok(list);
	}

	/**
	 * @Title: startDraw
	 * @Description: TODO 开始抽奖 并去除已中奖人员
	 * @param actvId
	 * @param meetingId
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年3月4日下午5:05:41
	 */
	@RequestMapping(value = "/startdraw", method = RequestMethod.POST)
	public AjaxJson startDraw(@RequestParam(value = "actvid", required = true) String actvId,
			@RequestParam(value = "meetingid", required = true) String meetingId) {
		List<CmsSignupDraw> list = null;
		CmsSignupDraw cmsSignupDraw = new CmsSignupDraw();
		cmsSignupDraw.setActvId(actvId);
		cmsSignupDraw.setMeetingId(meetingId);
		try {

			list = signupDrawService.findList(cmsSignupDraw);
			if (null == list || list.size() == 0) {
				return AjaxJson.fail("未有人参与此次活动！");
			}
			CmsWinPrize cmsWinPrize = new CmsWinPrize();
			cmsWinPrize.setActvId(actvId);
			cmsWinPrize.setMeetingId(meetingId);
			List<CmsWinPrize> winList = winPrizeService.findList(cmsWinPrize);

			// 创建新的集合临时存储参加的人员中已中奖的
			List<CmsSignupDraw> slist = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				System.out.println("循环参与人数list.size()" + list.size());
				if (null != winList && winList.size() > 0) {
					for (int a = 0; a < winList.size(); a++) {
						System.out.println("循环中奖人winList.size()" + winList.size());
						if (list.get(i).getUserId().equals(winList.get(a).getUserId())) {
							System.out.println("进入比较中奖，从参与人员中剔除已中奖人员");
							// list.remove(i);此处remove容易出现下标越界异常
							slist.add(list.get(i));
						}
					}
				}
			}
			// 在参与人员的集合中removeAll上面新建的临时集合。
			list.removeAll(slist);
			// 打乱返回集合的顺序(用于前端随机获取)
			Collections.shuffle(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("剔除中奖人后，剩余参与人员集合长度" + list.size());
		return AjaxJson.ok(list);

	}

	/**
	 * @Title: insertWinDraw
	 * @Description: TODO 中奖人员入库 并 修改奖项无效 。判断奖项是否已经都抽完了--修改活动是否有效
	 * @param actvId
	 * @param meetingId
	 * @param userId
	 * @param prizeId
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年3月4日下午5:17:14
	 */
	@RequestMapping(value = "/insertWinDraw", method = RequestMethod.POST)
	public AjaxJson insertWinDraw(@RequestParam(value = "actvid", required = true) String actvId,
			@RequestParam(value = "meetingid", required = true) String meetingId,
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "prizeId", required = true) String prizeId) {
		logger.info("userId" + userId);
		logger.info("***保存中奖人员开始****");
		logger.info("actvid" + actvId + "***" + "meetingid" + meetingId + "***" + "userId" + userId + "***" + "prizeId"
				+ prizeId + "***");

		String[] result = userId.split(",");
		if (result.length > 0) {
			for (int i = 0; i < result.length; i++) {
				logger.info("拆除后的循环的 userid" + result[i]);
				CmsWinPrize cmsWinPrize = new CmsWinPrize();
				cmsWinPrize.setUserId(result[i].trim());
				cmsWinPrize.setActvId(actvId);
				cmsWinPrize.setMeetingId(meetingId);
				cmsWinPrize.setPrizeId(prizeId);
				cmsWinPrize.setAddTime(new Date());
				winPrizeService.save(cmsWinPrize);// 入库中奖人员名单
				System.out.println("循环的次数");
			}
		}else{
			return AjaxJson.fail("提交中奖人为空。请重新提交！");
		}
		try {
			prizeService.updatePrizeStatus(prizeId, "1");// 修改奖项无效
			// 判断该项活动是否都已经抽完奖项 ，是 则让活动失效，否 则活动继续。
			CmsPrize cp = new CmsPrize();
			cp.setActvId(actvId);
			List<CmsPrize> cplist = prizeService.findList(cp);
			if (null != cplist && cplist.size() > 0) {
				int i = 0;
				int size = cplist.size();
				System.out.println("奖项集合的长度cplist.size()" + size);
				for (CmsPrize p : cplist) {
					if ("1".equals(p.getPrizeTab())) {
						i = i + 1;
						System.out.println("循环比较prizetab次数");
					}
				}
				System.out.println("循环完成后i的长度" + i);
				if (i == size) {
					activityService.updateActivityStatus(actvId, "1");// 修改活动无效
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxJson.ok("入库成功！");
	}

}
