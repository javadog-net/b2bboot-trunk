/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.PersonalPortrait;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.service.WxGroupUserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 群组Controller
 * @author hdx
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/app/group")
public class AppApiGroupController extends BaseController {


    @Autowired
    private WxGroupUserService wxGroupUserService;
    /**
     * 获取用户群信息列表 。群聊
     * @param userId
     * @return
     */
    @ApiOperation(notes = "getGroupList", httpMethod = "POST", value = "获取用户群信息列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/getGroupList")
    public AjaxJson getGroupList(){
    	PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	return AjaxJson.fail("401","请重新登录！");
        }
        String userId = purchaserAccount.getId();

        List<WxGroupUser> list = wxGroupUserService.getGroupList(userId);
        return AjaxJson.ok(list);
    }


    /**
     * 获取用行业圈信息列表 行业圈
     * @return
     */
    @ApiOperation(notes = "getIndustryCircle", httpMethod = "GET", value = "获取用行业圈信息列表", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/getIndustryCircle")
    public AjaxJson getIndustryCircle(){
    	PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	return AjaxJson.fail("401","请重新登录！");
        }
        String userId = purchaserAccount.getId();

        List<WxGroupUser> list = wxGroupUserService.getIndustryCircle(userId);
        return AjaxJson.ok(list);
    }


    /**
     * 获取用户群列表（用于订阅群组信息）(目前没啥用)
     * @param userId
     * @return
     */
    @ApiOperation(notes = "getUserGroup", httpMethod = "POST", value = "获取用户群列表", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/getUserGroup")
    public AjaxJson getUserGroup(){
    	PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	return AjaxJson.fail("401","请重新登录！");
        }
        String userId = purchaserAccount.getId();

        //通过userId查询用户信息
        List<WxGroupUser> wxGroupUserList = wxGroupUserService.getUserGroup(userId);
        return AjaxJson.ok(wxGroupUserList);
    }

    /** 
      * @Title: getGroupUsers 
      * @Description: TODO  查询群成员（管理员 在首位）
      * @param groupId
      * @return 
      * @return AjaxJson
      * @author tc
      * @date 2019年6月19日下午2:57:55
      */
    @RequestMapping("/users")
    public AjaxJson getGroupUsers(String groupId){
    	PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	return AjaxJson.fail("401","请重新登录！");
        }
        String id = purchaserAccount.getId();
        logger.info("-------------------查询群成员开始----------------------");
        List<PersonalPortrait> personalsList = wxGroupUserService.getGroupUsers(id,groupId);
        logger.info("-------------------查询群成员结束----------------------");
        if(personalsList == null || personalsList.size()==0){
        	return AjaxJson.fail("该群无成员！");
        }
        /*AjaxJson ret=new AjaxJson();
        ret.setMsg("0");     //0 管理员  1 非管理员
        ret.setResult(personalsList);*/
    	return AjaxJson.ok(personalsList);
    }

    /** 
      * @Title: deleteGroupUser 
      * @Description: TODO  管理员删除群成员
      * @param groupId
      * @param userId
      * @return 
      * @return AjaxJson
      * @author tc
      * @date 2019年6月19日下午3:21:18
      */
    @RequestMapping("/deleteGroupUser")
    public AjaxJson deleteGroupUser(@RequestParam(value="groupId",required=true)String groupId,
    		@RequestParam(value="userId",required=true)String userId){
    	String result="网络原因请重试！";
    	PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	return AjaxJson.fail("401","请重新登录！");
        }
        WxGroupUser wxGroupUser= wxGroupUserService.findIsOrAdmin(groupId,purchaserAccount.getId());
        if(wxGroupUser==null){
        	return AjaxJson.fail("对不起，只有管理员方可进行此操作");
        }
        if(wxGroupUser.getIsAdmin().equals("1")){
        	return AjaxJson.fail("对不起，只有管理员方可进行此操作");
        } 
        if(purchaserAccount.getId().equals(userId)){
        	return AjaxJson.fail("抱歉，您已是管理员，不可删除自己，请对群成员负责！");
        }
        if(wxGroupUser.getIsAdmin().equals("0")){
        	wxGroupUserService.deleteGroupUser(groupId,userId);
        	result="删除成功！";
        }
        return AjaxJson.ok(result);
    	
    }
    
}