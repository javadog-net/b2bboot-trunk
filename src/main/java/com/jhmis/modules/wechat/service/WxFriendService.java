/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jhmis.common.utils.IdGen;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserAccountMapper;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.tools.utils.PinyinTool;

import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.WxFriend;
import com.jhmis.modules.wechat.entity.WxPending;
import com.jhmis.modules.wechat.mapper.WxFriendMapper;
import com.jhmis.modules.wechat.mapper.WxPendingMapper;

/**
 * 聊天好友表Service
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly = true)
public class WxFriendService extends CrudService<WxFriendMapper, WxFriend> {
	@Autowired
    SimpMessageSendingOperations simpMessageSendingOperations;
	@Autowired
    PurchaserAccountService accountService;
    @Autowired
    PurchaserAccountMapper accountMapper;
    @Autowired
    WxPendingMapper wxPendingMapper;
	@Autowired
	WxMemberService wxMemberService;
	@Autowired
	WxFriendMapper wxFriendMapper;

	public WxFriend get(String id) {
		return super.get(id);
	}
	
	public List<WxFriend> findList(WxFriend wxFriend) {
		return super.findList(wxFriend);
	}
	
	public Page<WxFriend> findPage(Page<WxFriend> page, WxFriend wxFriend) {
		return super.findPage(page, wxFriend);
	}
	
	@Transactional(readOnly = false)
	public void save(WxFriend wxFriend) {
		super.save(wxFriend);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxFriend wxFriend) {
		super.delete(wxFriend);
	}

    @Transactional(readOnly = false)
    public List<WxFriend> getUserNewInfo(String userId) {
        //查询新朋友
        WxFriend wxFriend = new WxFriend();
        wxFriend.setUserId(userId);
        wxFriend.setTypeId("1");
        List<WxFriend> wxFriendList= mapper.findList(wxFriend);
        //查询新朋友
        WxFriend wxRefuse = new WxFriend();
        wxRefuse.setFriendId(userId);
        wxRefuse.setTypeId("2");
        List<WxFriend> wxRefuseList= mapper.findList(wxRefuse);
        //查询被添加信息
        WxFriend wxPassive = new WxFriend();
        wxPassive.setFriendId(userId);
        wxPassive.setTypeId("0");
        List<WxFriend> wxPassiveList= mapper.findList(wxPassive);
        wxFriendList.addAll(wxPassiveList);
        wxFriendList.addAll(wxRefuseList);
        return wxFriendList;
    }
    @Transactional(readOnly = false)
    public void update(WxFriend wxFriend) {
         mapper.update(wxFriend);
    }
    @Transactional(readOnly = false)
    public boolean handle(String userId,String friendId,String tag) {
    	
        //如果tag为0接受邀请，1为拒绝邀请
        WxFriend wxFriend = new WxFriend();
        wxFriend.setUserId(userId);
        wxFriend.setFriendId(friendId);
        //互相加好友的情况
        WxFriend allWxFriend = new WxFriend();
        allWxFriend.setUserId(friendId);
        allWxFriend.setFriendId(userId);
        List<WxFriend> wxFriendList = mapper.findList(wxFriend);
        List<WxFriend> allWxFriendList = mapper.findList(allWxFriend);
        if(wxFriendList!=null && wxFriendList.size()>0){
            wxFriend = wxFriendList.get(0);
            WxFriend addWxFriend = wxFriendList.get(0);
            //创建待处理对象
            WxPending pending = new WxPending();
            pending.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            pending.setUserId(userId);
			pending.setType("2");
			pending.setTypeId(friendId);
			pending.setStatus("0");
			pending.setTime(new Date());			
			pending.setSignStatus(accountService.getAvatarById(friendId));			
            if("0".equals(tag)){
                //为接受邀请
                wxFriend.setTypeId("1");
                //并添加相互好友
                WxFriend newFriends = new WxFriend();
                newFriends.setFriendId(userId);
                newFriends.setTypeId("1");
                newFriends.setUserId(friendId);
                newFriends.setStatus("1");
                newFriends.setAddTime(new Date());
                pending.setTime(newFriends.getAddTime());
                //获取我的信息
                PurchaserAccount purchaserAccount = accountMapper.get(friendId);
                if(purchaserAccount==null){
                    return false;
                }
                newFriends.setUserName(purchaserAccount.getRealName());
                //真实名称的拼音码
                PinyinTool tool = new PinyinTool();
                try {
                    newFriends.setUserName(purchaserAccount.getRealName());
                    String nameLetter = tool.toPinYin(addWxFriend.getUserName(), "", PinyinTool.Type.LOWERCASE);
                    newFriends.setNameLetter(nameLetter);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
                newFriends.setAddPerson(friendId);
                newFriends.setNickName(addWxFriend.getUserName());
                newFriends.setId(IdGen.uuid());
                //如果双方都加好友
                if(allWxFriendList!=null && allWxFriendList.size()>0){
                    //先删后插
                    mapper.delete(allWxFriendList.get(0));
                }
                mapper.insert(newFriends);           
                pending.setFriendName(accountService.getNicknameById(friendId));
                pending.setMeetingName("已通过您的好友申请，跟他聊两句吧！");
                
            }else{
                //忽略
                //未接受邀请
                wxFriend.setTypeId("2");
                pending.setFriendName(accountService.getNicknameById(friendId));
                pending.setMeetingName("未通过您的好友申请。");
                
            }
            mapper.update(wxFriend);
            wxPendingMapper.insert(pending);
            JSONObject pendingJsonObject = JSONObject.fromObject(pending);//将java对象转换为json对象
            String pendingJson=pendingJsonObject.toString();
            simpMessageSendingOperations.convertAndSendToUser(friendId, "/pending", pendingJson);

        }
        return true;
    }
    @Transactional(readOnly = false)
	public int deletebyuserid(String id) {
		return wxFriendMapper.deletebuuserid(id);
	}
    @Transactional(readOnly = false)
	public int deletebufriendid(String id) {
     return	wxFriendMapper.deletebufriendid(id);
	}
    @Transactional(readOnly = false)
	public int deleteaddperson(String id) {
		// TODO Auto-generated method stub
    	  return wxFriendMapper.deleteaddperson(id);
	}

	public List<WxFriend> findListMyFriendByFriendId(WxFriend wxFriend) {
		// TODO Auto-generated method stub
		return wxFriendMapper.findListMyFriendByFriendId(wxFriend);
	}

	public List<WxFriend> findMyFriendList(String userId, String tab) {
		

		return wxFriendMapper.findMyFriendList(userId,tab);
	}
  

}