/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.wechat;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.wechat.entity.WxMeeting;
import com.jhmis.modules.wechat.entity.WxMeetingSchedule;
import com.jhmis.modules.wechat.entity.WxMeetingScheduleCopy;
import com.jhmis.modules.wechat.service.WxMeetingScheduleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 会议日程表Controller
 *
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/wechat/meetingSchedule")
public class ApiMeetingScheduleController extends BaseController {

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
	        	wxMeetingScheduleCopy.setType(ws.getType());
	        	
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
    @RequestMapping("/list_day/{id}")  
    public AjaxJson list_day(@PathVariable("id") String id) {
        WxMeetingSchedule wxMeetingSchedule = new WxMeetingSchedule();
        wxMeetingSchedule.setMeetingId(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> days = new ArrayList<String>();
        List<WxMeetingSchedule> wxMeetingSchedules = wxMeetingScheduleService.findMeetingScheduleList(wxMeetingSchedule);
    	String day = "";
    	
        for(WxMeetingSchedule ws : wxMeetingSchedules){
       	  String ConferenceDay = sdf.format(ws.getConferenceDay());
       	  if(!day.equals(ConferenceDay)){
       		 days.add(ConferenceDay);
       		 day = ConferenceDay;      		 
       	  }     	  
       }    
        return AjaxJson.ok(days);
    }
}