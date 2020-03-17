/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.mapper.UserMapper;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxGroup;
import com.jhmis.modules.wechat.entity.WxGroupMessage;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.entity.WxMeeting;
import com.jhmis.modules.wechat.entity.WxMeetingSchedule;
import com.jhmis.modules.wechat.mapper.WxGroupMapper;
import com.jhmis.modules.wechat.mapper.WxGroupMessageMapper;
import com.jhmis.modules.wechat.mapper.WxGroupUserMapper;
import com.jhmis.modules.wechat.mapper.WxMeetingMapper;
import com.jhmis.modules.wechat.mapper.WxMeetingScheduleMapper;


/**
 * 会议表Service
 * @author lvyangzhuo
 * @version 2018-11-23
 */
@Service
@Transactional(readOnly = true)
public class WxMeetingService extends CrudService<WxMeetingMapper, WxMeeting> {

	@Autowired
	private WxMeetingScheduleMapper wxMeetingScheduleMapper;
	@Autowired
	private WxGroupMapper wxGroupMapper;
	@Autowired
	private WxGroupUserMapper wxGroupUserMapper;

	@Autowired
	private WxMeetingMapper wxmeetingmapper;

	@Autowired
	private WxGroupMessageMapper wxGroupMessageMapper;
	@Autowired
	private UserMapper userMapper;
	public WxMeeting get(String id){
		WxMeeting wxMeeting = super.get(id);
		String meetingId = wxMeeting.getId();
		WxMeetingSchedule wxMeetingSchedule =  new WxMeetingSchedule();
		wxMeetingSchedule.setMeetingId(meetingId);
		wxMeeting.setWxMeetingScheduleList(wxMeetingScheduleMapper.findList(wxMeetingSchedule));
		return wxMeeting;
	}
	
	public List<WxMeeting> findList(WxMeeting wxMeeting) {
		return super.findList(wxMeeting);
	}
	
	public Page<WxMeeting> findPage(Page<WxMeeting> page, WxMeeting wxMeeting) {
		User currentUser = UserUtils.getUser();
		
		if(currentUser.getName().equals("tity") || currentUser.getName().equals("admin")||
				currentUser.getName().equals("01460962")){
			Page<WxMeeting> pages = super.findPage(page, wxMeeting);
			return pages;
		}else{
		
			/*List<WxMeeting> pgs = pages.getList();
			Page<WxMeeting> huiyis = new Page<WxMeeting>();
			List<WxMeeting> hys = new ArrayList<WxMeeting>();
			for(WxMeeting pg : pgs){
				if(pg.getCreateId().indexOf(currentUser.getId())!=-1){
					hys.add(pg);
				}
			}
			huiyis.setList(hys);
			return huiyis;*/
			//获取当前用户  的创建者 id
		String id=currentUser.getCreateBy().getId();
		System.out.println(id+"当前用户的创建人的id");
		//获取当前用户的userid
		String userid=currentUser.getId();
		System.out.println("userid 当前用户的id"+userid);
		Page<WxMeeting> pages=null;
			if(id.equals("1")){//当前用户是 父用户 （admin直接指定的帐号）
			  wxMeeting.setCreateId(userid);
			  pages = super.findPage(page, wxMeeting);
			  List<WxMeeting> listmeeting=pages.getList();
			  //查询该用户创建的所有用户
			  List<User> list= userMapper.findUserByCreateId(userid);
			  System.out.println(list+"父所创建的人");
			  //循环当前用户创建的所有用户，分别查询用户所创建的会议，add进 总pages里
				  for (User u:list) {
					wxMeeting.setCreateId(u.getId());
					Page<WxMeeting> p = super.findPage(page, wxMeeting);
					List<WxMeeting> lsitm=p.getList();
					System.out.println("父所创建的人的会议"+lsitm.size());
					//add进 listmeeting
					listmeeting.addAll(lsitm);           
				}
				  System.out.println("父的总会议集合"+listmeeting.size());
				  pages.setList(listmeeting);
			}else{//非admin直接指定的用户
				 wxMeeting.setCreateId(id);
				  pages = super.findPage(page, wxMeeting);
				  List<WxMeeting> listmeeting=pages.getList();
				  //通过创建者id查询所有创建的人，并把会议add进一个集合里 返回
				  List<User> list= userMapper.findUserByCreateId(id);
				  System.out.println(list+"父所创建的人---简介用户");
				  for (User u:list) {
						wxMeeting.setCreateId(u.getId());
						Page<WxMeeting> p = super.findPage(page, wxMeeting);
						List<WxMeeting> lsitm=p.getList();
						System.out.println("父所创建的人的会议--间接"+lsitm.size());
						//add进 listmeeting
						listmeeting.addAll(lsitm);           
					}
				  pages.setList(listmeeting);
				  System.out.println("listmeeting总会议 间接--"+listmeeting.size());
			}
			 return pages;
		}
			
			
	
	}
	
	/**
	 * 前端会议展示service
	 * by 
	 * @param page
	 * @param wxMeeting
	 * 2019年1月29日09:52:14
	 * @return
	 */
	public Page<WxMeeting> findApiPage(Page<WxMeeting> page, WxMeeting wxMeeting) {
		return super.findPage(page, wxMeeting);
	}
	
	
	@Transactional(readOnly = false)
	public void save(WxMeeting wxMeeting,PurchaserAccount purchaserAccount) {
		boolean newGroup = false;
		if(StringUtils.isBlank(wxMeeting.getId())){
			//证明是新增
			wxMeeting.setPersonAttendNum(0);
			wxMeeting.setPersonSignNum(0);
			wxMeeting.setStatus(WxMeeting.MEETING_DEFAULT_STATUS);
			User currentUser = UserUtils.getUser();
			wxMeeting.setCreateId(currentUser.getId());
			newGroup = true;			
		}
		super.save(wxMeeting);
		for (WxMeetingSchedule wxMeetingSchedule : wxMeeting.getWxMeetingScheduleList()){
			if (StringUtils.isBlank(wxMeetingSchedule.getId())){
				continue;
			}
			if (WxMeetingSchedule.DEL_FLAG_NORMAL.equals(wxMeetingSchedule.getDelFlag())){
				if (StringUtils.isBlank(wxMeetingSchedule.getId())){
					wxMeetingSchedule.setMeetingId(wxMeeting.getId());
					wxMeetingSchedule.preInsert();
					wxMeetingScheduleMapper.insert(wxMeetingSchedule);
				}else{
					wxMeetingSchedule.preUpdate();
					wxMeetingScheduleMapper.update(wxMeetingSchedule);
				}
			}else{
				wxMeetingScheduleMapper.delete(wxMeetingSchedule);
			}
		}
		if(newGroup){
			//创建群组
			WxGroup wxGroup = new WxGroup();
			wxGroup.preInsert();
			wxGroup.setGroupName(wxMeeting.getName().trim());
			wxGroup.setUserId(purchaserAccount.getId());
			wxGroup.setBuildTime(new Date());
			wxGroup.setStatus(WxGroup.STATUS_YES);
			wxGroup.setSource(wxMeeting.getId());
			wxGroup.setSourceStatus("0");
			wxGroupMapper.insert(wxGroup);
			//创建此群的管理员信息
			WxGroupUser wxGroupUser = new WxGroupUser();
			wxGroupUser.preInsert();
			wxGroupUser.setGroupId(wxGroup.getId());
			wxGroupUser.setUserId(purchaserAccount.getId());
			wxGroupUser.setJoinTime(new Date());
			wxGroupUser.setIsAdmin(wxGroupUser.ISADMIN_YES);
			wxGroupUserMapper.insert(wxGroupUser);
			//初始化消息
			WxGroupMessage wxGroupMessage = new WxGroupMessage();
			wxGroupMessage.setGroupId(wxGroup.getId());
			wxGroupMessage.setUserId(purchaserAccount.getId());
			wxGroupMessage.setContent("欢迎加入" + wxMeeting.getName().trim() +"群聊");
			wxGroupMessage.preInsert();
			wxGroupMessage.setImg("https://b2b.haier.com/resources/images/wechat/haier.png");
			wxGroupMessage.setSendTime(new Date());
			wxGroupMessage.setIsBack("0");
			wxGroupMessage.setIsDel("0");
			wxGroupMessage.setIsRead("0");
			wxGroupMessageMapper.insert(wxGroupMessage);
		}

	}
	
	@Transactional(readOnly = false)
	public void delete(WxMeeting wxMeeting) {
		super.delete(wxMeeting);
		wxMeetingScheduleMapper.delete(new WxMeetingSchedule(wxMeeting.getId()));
	}

	@Transactional(readOnly = false)
	public List<WxMeeting> findAllMeetingList(WxMeeting wxMeeting) {
		wxMeeting.setAimTime(new Date());
		return mapper.findAllMeetingList(wxMeeting);
	}
	@Transactional(readOnly = false)
	public void addsignupnum(String meetingid) {
	WxMeeting wxmeeting=wxmeetingmapper.get(meetingid);
    Integer personSignNum=wxmeeting.getPersonSignNum();
    Integer num = null;
    if(personSignNum==null){
    	 num=1;
    }else{
    	num=personSignNum+1;
    }
    wxmeetingmapper.addsignupnum(meetingid,num);
	}

	@Transactional(readOnly = false)
	public void update(WxMeeting wxMeeting) {
		mapper.update(wxMeeting);
	}
	@Transactional(readOnly = false)
	public List<WxMeeting> findlikemeeting(String text) {
		// TODO Auto-generated method stub
		
		return mapper.findlikemeeting(text);
	}

	/**
     * date2比date1多的天数
     * @param date1    
     * @param date2
     * @return    
     */
    public  String differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);     
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return String.valueOf(timeDistance+(day2-day1)+1) ;
        }
        else    //不同年
        {
            return String.valueOf(day2-day1+1);
        }
    }
	
}