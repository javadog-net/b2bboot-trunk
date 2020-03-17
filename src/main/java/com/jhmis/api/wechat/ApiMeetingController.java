/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.wechat;

import java.util.Date;
import java.util.List;

import com.jhmis.modules.wechat.entity.*;
import com.jhmis.modules.wechat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;

/**
 * 会议报名表Controller
 * @author lvyangzhuo 
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/wechat/meeting")
public class ApiMeetingController extends BaseController {

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
	/**
	 * 获取会议详情
	 */
	@RequestMapping(value = "detail/{id}")
	public AjaxJson detail(@PathVariable("id") String id) throws Exception{
		return AjaxJson.ok(wxMeetingService.get(id));
	}

	/**
	 * 获取会议列表
	 * status 获取
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxJson list(WxMeeting wxMeeting) throws Exception{
		List<WxMeeting> wxMeetingList =  wxMeetingService.findAllMeetingList(wxMeeting);
		return AjaxJson.ok(wxMeetingList);
	}

	
	/** 
	  * @Title: meetingdynamic 
	  * @Description: TODO  会议动态
	  * @param meetingid
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2018年12月17日上午9:57:37
	  */
	@RequestMapping(value="/meetingdynamic",method= RequestMethod.POST)
	public AjaxJson meetingdynamic(@RequestParam(value="meetingid",required=true)String meetingid){
		WxMeetingDirect wxMeetingDirect=new WxMeetingDirect();
		wxMeetingDirect.setMeetingId(meetingid);
	    List<WxMeetingDirect> list=wxMeetingDirectService.findList(wxMeetingDirect);
		return AjaxJson.ok(list);
	}

	/**
	 * 扫码参会
	 */
	@RequestMapping(value = "/scanAttend")
	@ResponseBody
	public AjaxJson scanAttend(String meetingId,String userId) throws Exception{
		if("".equals(meetingId)|| "".equals(userId)){
			return AjaxJson.fail("参数异常");
		}
		WxMeeting wxMeeting = wxMeetingService.get(meetingId);
		if(wxMeeting==null){
			return AjaxJson.fail("会议不存在");
		}
		WxMeetingSignup wxMeetingSignup = new WxMeetingSignup();
		wxMeetingSignup.setUserId(userId);
		wxMeetingSignup.setMeetingId(meetingId);
		List<WxMeetingSignup> wxMeetingSignupList = null;
		try{
			wxMeetingSignupList = wxMeetingSignupService.findListVo(wxMeetingSignup);
		}catch (Exception e){
			e.printStackTrace();
		}
		if(wxMeetingSignupList.size()==0){
			return AjaxJson.fail("您未报名此会议");
		}
		wxMeetingSignup = wxMeetingSignupList.get(0);
		if(WxMeetingSignup.SIGNSTATUS_ATTEND.equals(wxMeetingSignup.getSignStatus())){
			return AjaxJson.fail("您已参会");
		}
        if(WxMeetingSignup.SIGNSTATUS_REFUSE.equals(wxMeetingSignup.getSignStatus())){
            return AjaxJson.fail("您未审核通过");
        }
        if(WxMeetingSignup.SIGNSTATUS_CHECK.equals(wxMeetingSignup.getSignStatus())){
        	return AjaxJson.fail("请耐心等待管理员审核！");
        }
		wxMeetingSignup.setAttendTime(new Date());
		wxMeetingSignup.setSignStatus(WxMeetingSignup.SIGNSTATUS_ATTEND);
		wxMeetingSignupService.update(wxMeetingSignup);
		return AjaxJson.ok("参会成功,且您已加入该会议群聊。");
	}


    /**
     * 根据会议id查询群组id
     * meeting 获取
     */
    @RequestMapping(value = "findGroupIdByMeetingId")
    @ResponseBody
    public AjaxJson findGroupIdByMeetingId(String meetingId,String userId) throws Exception{
        WxGroup wxGroup = wxGroupService.findUniqueByProperty("source",meetingId);
        if(wxGroup==null){
            return AjaxJson.fail("此群不存在");
        }
        WxGroupUser wxGroupUser = new WxGroupUser();
        wxGroupUser.setGroupId(wxGroup.getId());
        wxGroupUser.setUserId(userId);
        List<WxGroupUser> wxGroupUserList = wxGroupUserService.findList(wxGroupUser);
        if(wxGroupUserList==null || wxGroupUserList.size()==0){
            return AjaxJson.fail("您未加入此会议群聊");
        }
        return AjaxJson.ok(wxGroup.getId());
    }
	
}