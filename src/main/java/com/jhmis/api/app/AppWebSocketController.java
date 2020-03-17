package com.jhmis.api.app;

import com.alibaba.fastjson.JSON;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.WxFriend;
import com.jhmis.modules.wechat.entity.WxFriendMessage;
import com.jhmis.modules.wechat.entity.WxGroupMessage;
import com.jhmis.modules.wechat.entity.WxMessage;
import com.jhmis.modules.wechat.service.WxFriendMessageService;
import com.jhmis.modules.wechat.service.WxFriendService;
import com.jhmis.modules.wechat.service.WxGroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * webSocket
 *
 * @author lihy
 * @version 2018/6/15
 */
@Controller
public class AppWebSocketController extends BaseController {

    @Autowired
    SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    WxGroupMessageService wxGroupMessageService;

    @Autowired
    WxFriendMessageService wxFriendMessageService;

    @Autowired
    WxFriendService wxFriendService;


    /**
     * 定时推送消息
     *
     * @author lihy
     */
    @Scheduled(fixedRate = 1000)
    public void callback() {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpMessageSendingOperations.convertAndSend("/topic/greetings", "广播消息" + df.format(new Date()));
    }

    /**
     * 发送给单一客户端
     * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message” ,这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行。
     *
     * @return java.lang.String
     * @author lihy
     */
    @RequestMapping(path = "/appSendToUser")
    public AjaxJson sendToUser(String msg) {
        String openid = "12312";
        try{
            simpMessageSendingOperations.convertAndSendToUser(openid, "/message", "你好" + openid);
        }catch (Exception e){
            e.getMessage();
            return AjaxJson.fail("未发送成功");
        }
        return AjaxJson.ok();
    }

    /**
     * 接收客户端发来的群发消息
     *
     * @author hdx
     */
    // 群聊天
    @MessageMapping("/appNotice/{groupId}")
    public AjaxJson notice(@DestinationVariable("groupId")String groupId, String msg, String type) {
    	logger.info("-------------------群聊开始----------------------");
        logger.info("msg = [" + msg + "]");
        WxMessage wxMessage = JSON.parseObject(msg,WxMessage.class);
        if(!"".equals(groupId)){
        	logger.info("-------------------1----------------------");
            WxGroupMessage wxGroupMessage = new WxGroupMessage();
            logger.info("-------------------2----------------------");
            wxGroupMessage.setContent(filterEmoji(wxMessage.getMessage()));
            logger.info("-------------------3----------------------");
            wxGroupMessage.setGroupId(groupId);
            logger.info("-------------------4----------------------");
            wxGroupMessage.setSendTime(new Date());
            logger.info("-------------------5----------------------");
            wxGroupMessage.setUserId(wxMessage.getUserId());
            logger.info("-------------------5----------------------");
            wxGroupMessage.setImg(wxMessage.getUrl());
            logger.info("-------------------6----------------------");
            wxGroupMessage.setType(wxMessage.getMessageType());
            logger.info("-------------------7----------------------");
            wxGroupMessageService.save(wxGroupMessage);
            logger.info("-------------------8----------------------");
        }
        logger.info("groupId = "+groupId);
        // 发送消息给订阅 "/topic/notice" 且在线的用户
        simpMessageSendingOperations.convertAndSend("/topic/notice/"+groupId, msg);
        return AjaxJson.ok();
    }

    /**
     * 接收客户端发来的消息
     *
     * @author hdx
     */
    @MessageMapping("/appSendFriendsMsg")
    public AjaxJson receiver(String msg) {
    	logger.info("-------------------单聊开始----------------------");    	

        logger.info("msg = [" + msg + "]");
        WxMessage wxMessage = JSON.parseObject(msg,WxMessage.class);
        if(!"".equals(wxMessage.getFriendId())){
        	logger.info("-------------------1----------------------");
            //证明有此好友
            WxFriendMessage wxFriendMessage = new WxFriendMessage();
            logger.info("-------------------2----------------------");
            wxFriendMessage.setFromUserId(wxMessage.getUserId());
            logger.info("-------------------3----------------------");
            wxFriendMessage.setToUserId(wxMessage.getFriendId()); 
            logger.info("-------------------4----------------------");
            wxFriendMessage.setContent(wxMessage.getMessage());
            logger.info("-------------------5----------------------");
            wxFriendMessage.setSendTime(new Date());
            logger.info("-------------------6----------------------");
            wxFriendMessage.setImg(wxMessage.getUrl());
            logger.info("-------------------7----------------------");
            wxFriendMessage.setIsRead("0");
            logger.info("-------------------8----------------------");
            wxFriendMessage.setType(wxMessage.getMessageType());
            logger.info("-------------------9----------------------");
            wxFriendMessageService.save(wxFriendMessage);
            logger.info("-------------------10----------------------");
        }
        
        logger.info("msg = [" + msg + "]");
        simpMessageSendingOperations.convertAndSendToUser(wxMessage.getFriendId(), "/message", msg);
    
        return AjaxJson.ok();
    }


}

