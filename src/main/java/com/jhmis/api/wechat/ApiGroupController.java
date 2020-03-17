/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.wechat;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.service.WxGroupUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 群组Controller
 * @author hdx
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/wechat/group")
public class ApiGroupController extends BaseController {


    @Autowired
    private WxGroupUserService wxGroupUserService;
    /**
     * 获取用户群信息列表
     * @param userId
     * @return
     */
    @ApiOperation(notes = "getGroupList", httpMethod = "POST", value = "获取用户群信息列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/getGroupList")
    public AjaxJson getGroupList(String userId){
        if("".equals(userId)){
            return AjaxJson.fail("参数异常");
        }
        List<WxGroupUser> list = wxGroupUserService.getGroupList(userId);
        return AjaxJson.ok(list);
    }


    /**
     * 获取用行业圈信息列表
     * @param userId
     * @return
     */
    @ApiOperation(notes = "getIndustryCircle", httpMethod = "POST", value = "获取用行业圈信息列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/getIndustryCircle")
    public AjaxJson getIndustryCircle(String userId){
        if("".equals(userId)){
            return AjaxJson.fail("参数异常");
        }
        List<WxGroupUser> list = wxGroupUserService.getIndustryCircle(userId);
        return AjaxJson.ok(list);
    }


    /**
     * 获取用户群列表（用于订阅群组信息）
     * @param userId
     * @return
     */
    @ApiOperation(notes = "getUserGroup", httpMethod = "POST", value = "获取用户群列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/getUserGroup")
    public AjaxJson getUserGroup(String userId){
        if("".equals(userId)){
            return AjaxJson.fail("参数异常");
        }
        //通过userId查询用户信息
        List<WxGroupUser> wxGroupUserList = wxGroupUserService.getUserGroup(userId);
        return AjaxJson.ok(wxGroupUserList);
    }



}