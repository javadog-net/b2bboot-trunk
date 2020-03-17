/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.wechat.entity.WxMeetingSchedule;
import com.jhmis.modules.wechat.entity.WxMeetingScheduleCopy;
import com.jhmis.modules.wechat.service.WxMeetingScheduleService;

/**
 * 会议日程表Controller
 *
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/app/meetingSchedule")
public class AppApiMeetingScheduleController extends BaseController {

    @Autowired
    private WxMeetingScheduleService wxMeetingScheduleService;
    

    /**
     * 查询日程列表
     * @param id 会议id
     * @param day 会议日期
     * @return
     * @throws ParseException
     */
    @RequestMapping("/list")
    public AjaxJson list(String id,String day) throws ParseException {

        WxMeetingSchedule wxMeetingSchedule = new WxMeetingSchedule();
        List<WxMeetingScheduleCopy> wxMeetingScheduleCopys = new ArrayList<WxMeetingScheduleCopy>();
        wxMeetingSchedule.setMeetingId(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
            
        List<WxMeetingSchedule> wxMeetingSchedules = wxMeetingScheduleService.findMeetingScheduleList(wxMeetingSchedule);
        
        for(WxMeetingSchedule ws : wxMeetingSchedules){
        	String daychange = sdf.format(ws.getConferenceDay());

        	if( daychange.equals(day)){
	        	WxMeetingScheduleCopy wxMeetingScheduleCopy = new WxMeetingScheduleCopy();
	        	wxMeetingScheduleCopy.setTime(sf.format(ws.getStartTime()) + " — " +sf.format(ws.getEndTime()));        	
	        	wxMeetingScheduleCopy.setContent(ws.getContent());
	        	if("1".equals(ws.getType())){
	        		wxMeetingScheduleCopy.setType("签到篇");
	        	}else if("2".equals(ws.getType())){
	        		wxMeetingScheduleCopy.setType("开场篇");
	        	}else if("3".equals(ws.getType())){
	        		wxMeetingScheduleCopy.setType("推介篇");
	        	}else if("4".equals(ws.getType())){
	        		wxMeetingScheduleCopy.setType("结束篇");
	        	}else if("5".equals(ws.getType())){
	        		wxMeetingScheduleCopy.setType(ws.getMyType());
	        	}else{
	        		wxMeetingScheduleCopy.setType(ws.getType());
	        	}
	        	
	        	wxMeetingScheduleCopys.add(wxMeetingScheduleCopy);
        	}
        }
        return AjaxJson.ok(wxMeetingScheduleCopys);
    }
    
    /**
     * 获取本次会议的日期 yyyy-mm-dd
     * 
     * @param id
     * @return
     */
    @RequestMapping("/list_day")  
    public AjaxJson list_day(@RequestParam(value="id",required=true) String id) {
        WxMeetingSchedule wxMeetingSchedule = new WxMeetingSchedule();
        wxMeetingSchedule.setMeetingId(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> days = new ArrayList<String>();
        List<WxMeetingSchedule> wxMeetingSchedules = wxMeetingScheduleService.findMeetingScheduleList(wxMeetingSchedule);
        String day = "";
        boolean newDay=true;
    	
        for(WxMeetingSchedule ws : wxMeetingSchedules){
       	  String ConferenceDay = sdf.format(ws.getConferenceDay());
       	  for(String d : days){
       		  if(d.equals(ConferenceDay)){
       			 newDay=false; 
       		  }
       	  }
       	  if(newDay){
       		day = ConferenceDay;
       		days.add(ConferenceDay);
       	  }
       	newDay=true;
       }    
        return AjaxJson.ok(days);
    }
}