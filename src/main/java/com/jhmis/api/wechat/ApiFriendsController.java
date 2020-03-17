/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.wechat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.tools.utils.PinyinTool;
import com.jhmis.modules.wechat.entity.WxFriend;
import com.jhmis.modules.wechat.entity.WxFriendMessage;
import com.jhmis.modules.wechat.entity.WxMember;
import com.jhmis.modules.wechat.entity.WxPending;
import com.jhmis.modules.wechat.mapper.WxPendingMapper;
import com.jhmis.modules.wechat.service.WxFriendMessageService;
import com.jhmis.modules.wechat.service.WxFriendService;
import com.jhmis.modules.wechat.service.WxMemberService;

/**
 * 好友相关Controller
 *
 * @author hdx
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/wechat/friends")
public class ApiFriendsController extends BaseController {

    @Autowired
    private WxMemberService wxMemberService;

    @Autowired
    private PurchaserService purchaserService;

    @Autowired
    private PurchaserAccountService purchaserAccountService;

    @Autowired
    private WxFriendService wxFriendService;
    
    @Autowired
    private WxFriendMessageService wxFriendMessageService;
    
	@Autowired
    private WxPendingMapper wxPendingMapper;


    /**
     * 根据手机号查询好友
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "getList/{mobile}")
    public AjaxJson getList(@PathVariable("mobile") String mobile) throws Exception {
        WxMember wxMember = wxMemberService.findUniqueByProperty("user_mobile", mobile);
        //查重是否已经是好友
        return AjaxJson.ok(wxMember);
    }

    /**
     * 根据UserId查询好友
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "getPurchaserByUserId")
    @ResponseBody
    public AjaxJson getPurchaserByUserId(String userId,String id) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //是否是自己
        map.put("isMine",0);
        boolean isFriends = false;
        //获取采购商信息
        PurchaserAccount currentAccount = purchaserAccountService.get(userId);
        Purchaser purchaser = purchaserService.get(currentAccount.getPurchaserId());
        //获取基本信息
        WxMember wxMember = wxMemberService.findUniqueByProperty("user_id",userId);
        //查看是否已经是好友
        WxFriend wxFriend = new WxFriend();
        wxFriend.setUserId(id);
        wxFriend.setFriendId(userId);
        wxFriend.setTypeId("1");
        List<WxFriend> wxFriendList = wxFriendService.findList(wxFriend);
        if(wxFriendList!=null && wxFriendList.size()>0){
            isFriends = true;
            map.put("wxFriend",wxFriendList.get(0));
        }
        if(userId.equals(id)){
            //查询自己
            map.put("isMine",1);
        }
        map.put("isFriends",isFriends);
        map.put("currentAccount",currentAccount);
        map.put("purchaser",purchaser);
        map.put("wxMember",wxMember);
        return AjaxJson.ok(map);
    }


    /**
     * 添加好友
     *
     * @param userId
     * @param nickName
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public AjaxJson list(String id,String userId,String nickName) throws Exception{
        if("".equals(userId)){
            return AjaxJson.fail("参数异常");
        }
        WxFriend wf = new WxFriend();
        wf.setFriendId(userId);
        wf.setUserId(id);
        List<WxFriend> lsitWxFriend = wxFriendService.findList(wf);
        //创建待处理对象
        WxPending pending = new WxPending();
        pending.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        pending.setUserId(userId);
		pending.setType("2");
		pending.setTypeId(id);
		pending.setStatus("0");
		pending.setTime(new Date());
		pending.setSignStatus(purchaserAccountService.getAvatarById(id));
		
        if(lsitWxFriend!=null && lsitWxFriend.size()>0){
            if("2".equals(lsitWxFriend.get(0).getTypeId())){
                //添加好友被拒绝忽略
                WxFriend refuseWx = lsitWxFriend.get(0);
                refuseWx.setTypeId("0");
                wxFriendService.update(refuseWx);
                pending.setMeetingName("请求添加您为好友");
                wxPendingMapper.insert(pending);
                return AjaxJson.fail("您已再次申请");
            }
            return AjaxJson.fail("您已申请请耐心等待");
        }
        WxFriend wxFriend = new WxFriend();
        //没有设置昵称，则取realName
        if("".equals(nickName)){
            //获取采要添加购商信息
            PurchaserAccount currentAccount = purchaserAccountService.get(userId);
            nickName = currentAccount.getRealName();
            //获取本人信息
            PurchaserAccount ownPurchaserAccount = purchaserAccountService.get(id);
            wxFriend.setUserName(ownPurchaserAccount.getRealName());
        }
        //待处理状态
        wxFriend.setTypeId("0");
        wxFriend.setUserId(id);
        wxFriend.setFriendId(userId);
        wxFriend.setAddTime(new Date());
        wxFriend.setAddPerson(id);
        wxFriend.setNickName(nickName);
        //获取添加者用户名称
        PurchaserAccount ca = purchaserAccountService.get(id);
        if(ca==null){
            return AjaxJson.fail("您不是企业购用户");
        }
        wxFriend.setUserName(ca.getRealName());
        //主动添加标识
        wxFriend.setStatus("0");
        //昵称首字母排序转化
        PinyinTool tool = new PinyinTool();
        String nameLetter = tool.toPinYin(nickName, "", PinyinTool.Type.LOWERCASE);
        wxFriend.setNameLetter(nameLetter);
        wxFriendService.save(wxFriend);
        pending.setMeetingName("请求添加您为好友");
        wxPendingMapper.insert(pending);
        return AjaxJson.ok();
    }


    /**
     * 根据用户id查询好友列表
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "getUserFriends/{userId}")
    public AjaxJson getUserFriends(@PathVariable("userId") String userId) throws Exception {
        if("".equals(userId)){
            return AjaxJson.fail("参数异常");
        }
        WxFriend wxFriend = new WxFriend();
        wxFriend.setUserId(userId);
        wxFriend.setTypeId("1");
        List<WxFriend> wxFriendList= wxFriendService.findList(wxFriend);
        return AjaxJson.ok(wxFriendList);
    }

    /**
     * 根据用户id查询新的朋友信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "getUserNewInfo/{userId}")
    public AjaxJson getUserNewInfo(@PathVariable("userId") String userId) throws Exception {
        if("".equals(userId)){
            return AjaxJson.fail("参数异常");
        }
        List<WxFriend> wxFriendList = wxFriendService.getUserNewInfo(userId);
        return AjaxJson.ok(wxFriendList);
    }


    /**
     * 接受或者忽略用户请求
     *
     * @param userId
     * @param friendId
     * @param tag
     * @return
     */
    @RequestMapping(value = "handle")
    public AjaxJson handel( String userId,String friendId,String tag) throws Exception {
        if("".equals(userId) || "".equals(friendId)|| "".equals(tag)){
            return AjaxJson.fail("参数异常");
        }
        Boolean flag = wxFriendService.handle(userId,friendId,tag);
        return AjaxJson.ok();
    }


    /**
     * 查询好友聊天列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "findFriendMsgInfoList/{userId}")
    public AjaxJson findFriendMsgInfoList(@PathVariable("userId") String userId) throws Exception {
        List<WxFriendMessage> WxFriendMessageList = wxFriendMessageService.findFriendMsgInfoList(userId);
        //查重是否已经是好友
        return AjaxJson.ok(WxFriendMessageList);
    }
    
    

    
}