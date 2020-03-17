/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.wechat;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.WxFriendMessage;
import com.jhmis.modules.wechat.entity.WxGroupMessage;
import com.jhmis.modules.wechat.service.WxFriendMessageService;
import com.jhmis.modules.wechat.service.WxGroupMessageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 信息相关Controller
 * @author hdx
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/wechat/message")
public class ApiMessageController extends BaseController {


    @Autowired
    private WxFriendMessageService wxFriendMessageService;

    @Autowired
    private WxGroupMessageService wxGroupMessageService;
    /**
     * 获取聊天信息列表
     * @param userId
     * @param friendId
     * @return
     */
    @ApiOperation(notes = "getMsgList", httpMethod = "POST", value = "获取聊天信息列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "friendId", value = "朋友id", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/getMsgList")
    public AjaxJson getMsgList(String userId, String friendId, HttpServletRequest request, HttpServletResponse response){
        Page<WxFriendMessage> page = new Page<WxFriendMessage>(request, response,-1);
        page.setOrderBy("a.send_time");
        WxFriendMessage wxFriendMessage = new WxFriendMessage();
        wxFriendMessage.setToUserId(userId);
        wxFriendMessage.setFromUserId(friendId);
        Page<WxFriendMessage> wxFriendMessagePage = wxFriendMessageService.findPage(page,wxFriendMessage);
        return AjaxJson.ok(wxFriendMessagePage);
    }

    /**
     * 获取群信息列表
     * @param userId
     * @param groupId
     * @return
     */
    @ApiOperation(notes = "getGroupMsgList", httpMethod = "POST", value = "获取群信息列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "群组id", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/getGroupMsgList")
    public AjaxJson getGroupMsgList(String userId, String groupId, HttpServletRequest request, HttpServletResponse response){
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        Page<WxGroupMessage> page = new Page<WxGroupMessage>(request, response,-1);
        page.setOrderBy("a.send_time");
        WxGroupMessage wxGroupMessage = new WxGroupMessage();
        wxGroupMessage.setGroupId(groupId);
        Page<WxGroupMessage> wxGroupMessagePage = wxGroupMessageService.findPage(page,wxGroupMessage);
        return AjaxJson.ok(wxGroupMessagePage);
    }


}