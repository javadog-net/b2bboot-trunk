/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.app;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.WxGroup;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.entity.WxMeeting;
import com.jhmis.modules.wechat.entity.WxMeetingDirect;
import com.jhmis.modules.wechat.entity.WxMeetingFile;
import com.jhmis.modules.wechat.entity.WxMeetingSignup;
import com.jhmis.modules.wechat.entity.WxTravel;
import com.jhmis.modules.wechat.service.WxGroupService;
import com.jhmis.modules.wechat.service.WxGroupUserService;
import com.jhmis.modules.wechat.service.WxMeetingDirectService;
import com.jhmis.modules.wechat.service.WxMeetingFileService;
import com.jhmis.modules.wechat.service.WxMeetingService;
import com.jhmis.modules.wechat.service.WxMeetingSignupService;
import com.jhmis.modules.wechat.service.WxTravelService;

/**
 * 会议报名表Controller
 * 
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/app/meeting")
public class AppApiMeetingController extends BaseController {

	@Autowired
	WxTravelService wxTravelService;

	@Autowired
	private WxMeetingService wxMeetingService;
	@Autowired
	private WxMeetingDirectService wxMeetingDirectService;
	@Autowired
	private WxMeetingSignupService wxMeetingSignupService;
	@Autowired
	private WxGroupService wxGroupService;
	@Autowired
	private WxGroupUserService wxGroupUserService;
	@Autowired
	private WxMeetingFileService wxmeetingfileservice;
	@Autowired
	private PurchaserAccountService purchaseraccountservice;

	/**
	 * 获取会议详情
	 */
	@RequestMapping(value = "/detail")
	public AjaxJson detail(@RequestParam(value = "id", required = true) String id) throws Exception {
		return AjaxJson.ok(wxMeetingService.get(id));
	}

	/**
	 * 获取会议列表 status 获取
	 */
	@RequestMapping(value = "/listP")
	@ResponseBody
	public AjaxJson list(WxMeeting wxMeeting, int pageNo, int pageSize) throws Exception {
		wxMeeting.setIsDel("0");
		Page<WxMeeting> pageList = wxMeetingService.findApiPage(new Page<WxMeeting>(pageNo, pageSize), wxMeeting);
		return AjaxJson.ok(pageList);
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public AjaxJson list(WxMeeting wxMeeting, HttpServletRequest request) throws Exception {
		wxMeeting.setIsDel("0");
		List<WxMeeting> wxMeetingList = wxMeetingService.findAllMeetingList(wxMeeting);
		
		return AjaxJson.ok(wxMeetingList);
	}

	/**
	 * @Title: meetingdynamic
	 * @Description: TODO 会议动态
	 * @param meetingid
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2018年12月17日上午9:57:37
	 */
	@RequestMapping(value = "/meetingdynamic", method = RequestMethod.POST)
	public AjaxJson meetingdynamic(@RequestParam(value = "meetingid", required = true) String meetingid) {
		WxMeetingDirect wxMeetingDirect = new WxMeetingDirect();
		wxMeetingDirect.setMeetingId(meetingid);
		List<WxMeetingDirect> list = wxMeetingDirectService.findList(wxMeetingDirect);
		Map<String, Object> hm = new HashMap<>();
		hm.put("directlist", list);
		hm.put("meeting", wxMeetingService.get(meetingid));

		return AjaxJson.ok(hm);
	}

	/**
	 * 扫码参会
	 */
	@RequestMapping(value = "/scanAttend")
	@ResponseBody
	public AjaxJson scanAttend(String meetingId) throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaserAccount.getId();

		WxMeeting wxMeeting = wxMeetingService.get(meetingId);
		if (wxMeeting == null) {
			return AjaxJson.fail("会议不存在");
		}
		WxMeetingSignup wxMeetingSignup = new WxMeetingSignup();
		wxMeetingSignup.setUserId(userId);
		wxMeetingSignup.setMeetingId(meetingId);
		List<WxMeetingSignup> wxMeetingSignupList = null;
		try {
			wxMeetingSignupList = wxMeetingSignupService.findListVo(wxMeetingSignup);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (wxMeetingSignupList.size() == 0 || null == wxMeetingSignupList) {
			return AjaxJson.fail("您未报名此会议");
		}
		wxMeetingSignup = wxMeetingSignupList.get(0);
		if (WxMeetingSignup.SIGNSTATUS_ATTEND.equals(wxMeetingSignup.getSignStatus())) {
			return AjaxJson.fail("您已参会");
		}
		if (WxMeetingSignup.SIGNSTATUS_REFUSE.equals(wxMeetingSignup.getSignStatus())) {
			return AjaxJson.fail("您未审核通过");
		}
		if (WxMeetingSignup.SIGNSTATUS_CHECK.equals(wxMeetingSignup.getSignStatus())) {
			return AjaxJson.fail("请耐心等待管理员审核！");
		}
		wxMeetingSignup.setAttendTime(new Date());
		wxMeetingSignup.setSignStatus(WxMeetingSignup.SIGNSTATUS_ATTEND);
		wxMeetingSignupService.update(wxMeetingSignup);
		return AjaxJson.ok("参会成功。");
	}

	/**
	 * 根据会议id查询群组id meeting 获取
	 */
	@RequestMapping(value = "/findGroupIdByMeetingId")
	@ResponseBody
	public AjaxJson findGroupIdByMeetingId(String meetingId) throws Exception {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String userId = purchaserAccount.getId();

		WxGroup wxGroup = wxGroupService.findUniqueByProperty("source", meetingId);
		if (wxGroup == null) {
			return AjaxJson.fail("此群不存在");
		}
		WxGroupUser wxGroupUser = new WxGroupUser();
		wxGroupUser.setGroupId(wxGroup.getId());
		wxGroupUser.setUserId(userId);
		List<WxGroupUser> wxGroupUserList = wxGroupUserService.findList(wxGroupUser);
		if (wxGroupUserList == null || wxGroupUserList.size() == 0) {
			return AjaxJson.fail("您未加入此会议群聊");
		}
		return AjaxJson.ok(wxGroup.getId());
	}

	/**
	 * @Title: findlikemeeting
	 * @Description: TODO 模糊查询会议
	 * @param text
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年1月8日下午3:45:39
	 */
	@RequestMapping(value = "/findlikemeeting", method = RequestMethod.POST)
	public AjaxJson findlikemeeting(@RequestParam(value = "text", required = true) String text) {
		List<WxMeeting> list = wxMeetingService.findlikemeeting(text);
		return AjaxJson.ok(list);
	}

	/**
	 * @Title: findAboutMyMeetingProfile
	 * @Description: TODO 会议中 与我相关的
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年2月15日上午10:37:38
	 */
	@RequestMapping("/findAboutMyMeetingProfile")
	public AjaxJson findAboutMyMeetingProfile(@RequestParam(value = "meetingid", required = true) String meetingid) {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		Map<String, Object> map = new HashMap<>();
		logger.info("listsign 前");
		try {
			List<WxMeetingSignup> listsign = wxMeetingSignupService.findMeetingByIdAndIdtwo(purchaserAccount.getId(),
					meetingid);
			if (listsign == null || listsign.size() == 0) {
				return AjaxJson.fail("数据有误！");
			}
			logger.info("listsign 后" + listsign);
			WxMeetingSignup wxMeetingSignup = listsign.get(0);
			logger.info("wxMeetingSignup" + wxMeetingSignup);
			WxTravel wxTravel = new WxTravel();
			wxTravel.setMeetingSignupId(wxMeetingSignup.getId());
			wxTravel.setMeetingId(meetingid);
			wxTravel.setIsDel("0");
			logger.info("listsignup 前");
			List<WxTravel> listsignup = wxTravelService.findList(wxTravel);
			if (null == listsignup || listsignup.size() == 0) {
				logger.info("listsignup travel is 0");
				map.put("signmeeting", wxMeetingSignup);
			} else {
				logger.info("listsignup" + listsignup);
				WxTravel wxTravel1 = new WxTravel();
				wxTravel1 = listsignup.get(0);
				logger.info("wxTravel1" + wxTravel1);
				map.put("signmeeting", wxMeetingSignup);
				map.put("wxTravel1", wxTravel1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return AjaxJson.ok(map);
	}

	/**
	 * @Title: findmeetingfile
	 * @Description: TODO 查找会议相关的附件
	 * @param meetingid
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年4月3日下午3:28:19
	 */
	@RequestMapping("/findmeetingfile")
	public AjaxJson findmeetingfile(@RequestParam(value = "meetingid", required = true) String meetingid) {
		WxMeetingFile wxMeetingFile = new WxMeetingFile();
		if (StringUtils.isEmpty(meetingid)) {
			return AjaxJson.fail("参数不全");
		}
		wxMeetingFile.setMeetingId(meetingid);
		List<WxMeetingFile> list = wxmeetingfileservice.findList(wxMeetingFile);
		if (list == null || list.size() == 0) {
			return AjaxJson.ok("无会议附件");
		}
		for (WxMeetingFile file : list) {
			String name = file.getRemark().trim();
			name = name.substring(name.lastIndexOf(".") + 1);
			if (name.equals("jpg") || name.equals("jpeg") || name.equals("png")) {
				file.setType("3");
			}
			if (name.equals("xls") || name.equals("xlsx")) {
				file.setType("2");
			}
			if (name.equals("doc") || name.equals("docx")) {
				file.setType("1");
			}
		}
		return AjaxJson.ok(list);
	}

	@RequestMapping("/findMyMeetingProfilelast")
	public AjaxJson findMyMeetingProfilelast() {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		purchaserAccount = purchaseraccountservice.get(purchaserAccount.getId());
		String id = purchaserAccount.getId();
		List<WxMeetingSignup> list = wxMeetingSignupService.findlastmymeeting(id);
		PurchaserAccount aco = new PurchaserAccount();
		if (list != null && list.size() > 0) {
			aco.setDepartName(list.get(0).getDepartName());
			aco.setDetailAddress(list.get(0).getAddress());
			aco.setEmail(list.get(0).getEmail());
			aco.setMobile(list.get(0).getMobile());
			aco.setPurchaserName(list.get(0).getCompanyName());
			aco.setWxindustryname(list.get(0).getIndustryName());
			aco.setProId(list.get(0).getProvinceId());
			aco.setCityId(list.get(0).getCityId());
			aco.setNickName(list.get(0).getRealName());
			return AjaxJson.ok(aco);
		}else{
			return AjaxJson.ok(purchaserAccount);
		}
	}

}