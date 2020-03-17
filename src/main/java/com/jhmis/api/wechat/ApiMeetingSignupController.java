/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.wechat;

import java.util.Date;
import java.util.List;

import com.jhmis.api.store.ApiStoreGoodsController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.wechat.entity.WxMeetingSignup;
import com.jhmis.modules.wechat.service.WxMeetingService;
import com.jhmis.modules.wechat.service.WxMeetingSignupService;


/**   
 * <p>Title: ApiMeetingSignupController</p>  
 * <p>Description: </p>  
 * @author tc  
 * @date 2018年12月14日 下午2:37:01
 */ 
@RestController
@RequestMapping("/api/wechat/meetingSignup")
public class ApiMeetingSignupController extends BaseController {
	protected Logger logger = LoggerFactory.getLogger(ApiMeetingSignupController.class);

	@Autowired
	private WxMeetingSignupService wxMeetingSignupService;
	@Autowired
	private WxMeetingService wxMeetingService;

	/**
	 * 保存会议报名表
	 */
	@RequestMapping(value = "save")
	public AjaxJson save(WxMeetingSignup wxMeetingSignup) throws Exception{
		wxMeetingSignupService.save(wxMeetingSignup);
		return AjaxJson.ok();
	}

	/** 
	  * @Title: meetingStatus 
	  * @Description: TODO  
	  * @param pruchaser_accountid
	  * @param status 如果status是0，则要把状态是2的也查询出来。
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2018年12月14日上午10:55:14
	  */
	@RequestMapping(value="/meetingstatus",method= RequestMethod.POST)
	public AjaxJson meetingStatus(@RequestParam(value="purchaseraccountid",required=true) String pruchaser_accountid,
			             @RequestParam(value="status",required=true) String status){
		
	List<WxMeetingSignup>	list=wxMeetingSignupService.findmeetingsignupstatus(status, pruchaser_accountid);
	
	if(status.equals("0")){
		List<WxMeetingSignup> list2=wxMeetingSignupService.findmeetingsignupstatus("2", pruchaser_accountid);
		list.addAll(list2);
 	}
	return AjaxJson.ok(list);
	}
	
		
	/** 
	  * @Title: signupmeeting 
	  * @Description: TODO 在线报名 修改Depart  
	  * @param purchaseraccountid
	  * @param departname
	  * @param meetingid
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2018年12月15日下午3:44:50
	  */
	@RequestMapping(value="/signupmeeting",method= RequestMethod.POST)
	public AjaxJson signupmeeting(@RequestParam(value="purchaseraccountid",required=true)String purchaseraccountid,
			        @RequestParam(value="meetingid",required=true)String meetingid){
			logger.info("/signupmeeting开始");
            WxMeetingSignup wxMeetingSignup=new WxMeetingSignup();

       		List<String> id=wxMeetingSignupService.findMeetingByIdAndId(purchaseraccountid, meetingid);
			logger.info("id=" + id);
       		System.out.println("id"+id);
       		if(id.size()!=0){
				logger.info("你已报名，请勿重复报名！");
       			return	AjaxJson.fail("你已报名，请勿重复报名！");
       		}
			wxMeetingSignup = new WxMeetingSignup();
			wxMeetingSignup.setUserId(purchaseraccountid);
       		wxMeetingSignup.setMeetingId(meetingid);
            wxMeetingSignup.setSignStatus("0");
            wxMeetingSignup.setSignTime(new Date());
            wxMeetingSignup.setSignWay("0");
            try{
				wxMeetingSignupService.save(wxMeetingSignup);
				wxMeetingService.addsignupnum(meetingid);
			}catch (Exception e){
            	e.printStackTrace();
			}
		return AjaxJson.ok("报名成功，等待管理员的审核！");
	}
	
	
}