package com.jhmis.api.hps;

import cn.hutool.http.HttpRequest;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.customer.entity.HpsMessageReminder;
import com.jhmis.modules.customer.service.HpsMessageReminderService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.api.customer
 * @Author: hdx
 * @CreateTime: 2020-02-04 09:40
 * @Description: 提供给hps接口
 */

@Api(value = "ApiHpsController", description = "提供给hps接口")
@RestController
@RequestMapping("/api/hps")
public class ApiHpsController extends BaseController {

    @Autowired
    private ShopMsgService shopMsgService;
    @Autowired
    private HpsMessageReminderService hpsMessageReminderService;

    @ApiOperation(notes = "send_msg", httpMethod = "POST", value = "发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "校验身份", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "send_msg",method = RequestMethod.POST)
    @SysLog(desc = "发送短信接口")
    public AjaxJson send_msg(String key,String mobile, String content){
        try {
            shopMsgService.hpsSendMsg(key, mobile, content);
        }catch (ShopMsgException e){
            logger.error(mobile + "-发送短信失败:" + e.getMessage());
            return AjaxJson.fail("发送短信失败:" + e.getMessage());
        }
        return AjaxJson.ok("短信发送成功");
    }


    @ApiOperation(notes = "send_msg_code", httpMethod = "POST", value = "根据88码发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "校验身份", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cusCode", value = "手机号", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "send_msg_code",method = RequestMethod.POST)
    @SysLog(desc = "根据88码发送短信")
    public AjaxJson send_msg_code(String key,String cusCode, String content){
        Map<String,Object> map = new HashMap<>();
        String mobile = "";
        try {
             mobile = shopMsgService.hpsSendMsgByCode(key, cusCode, content);
        }catch (ShopMsgException e){
            logger.error(mobile + "-发送短信失败:" + e.getMessage());
            return AjaxJson.fail("发送短信失败:" + e.getMessage());
        }
        //手机号
        map.put("mobile",mobile);
        //内容
        map.put("content",content);
        //返回值
        map.put("msg","短信发送成功");
        return AjaxJson.ok(map);
    }
    
    @ApiOperation(notes = "sendMessage", httpMethod = "POST", value = "消息通知推送接口")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "key", value = "校验身份", required = true, paramType = "form", dataType = "String"),   
        @ApiImplicitParam(name = "code88", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "type", value = "消息类型", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "content", value = "消息内容", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "projectCode", value = "项目编码", required = false, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "brownCode", value = "工程版号", required = false, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "sendMessage",method = RequestMethod.POST)
    @SysLog(desc = "消息通知")
    public AjaxJson sendMessage(@RequestParam(value = "key", required = true) String key,
    		@RequestParam(value = "code88", required = true) String code88,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "content", required = true) String content, 
			@RequestParam(value = "projectCode", required = false) String projectCode, 	
			@RequestParam(value = "brownCode", required = false) String brownCode) {
    	
    	if(StringUtils.isBlank(key)){
        	return AjaxJson.fail("秘钥不能为空");
        }else {
        	if(!"HaierHps20200204".equals(key)) {
        		return AjaxJson.fail("秘钥错误");
        	}
        }
    	if(StringUtils.isBlank(code88)){
        	return AjaxJson.fail("经销商编码缺失");
        }
    	if(StringUtils.isBlank(type)){
    		return AjaxJson.fail("消息类型缺失");
        }
    	if(StringUtils.isBlank(content)){
    		return AjaxJson.fail("消息内容缺失");
        }
    	HpsMessageReminder hpsMessageReminder = new HpsMessageReminder();
    	hpsMessageReminder.setCode88(code88);
    	hpsMessageReminder.setType(type);
    	hpsMessageReminder.setContent(content);
    	if(StringUtils.isNotBlank(projectCode)){
    		hpsMessageReminder.setProjectCode(projectCode);
        }
    	if(StringUtils.isNotBlank(brownCode)){
    		hpsMessageReminder.setBrownCode(brownCode);
        }
    	hpsMessageReminder.setCreateTime(new Date());
    	hpsMessageReminder.setIsRead("0");
    	hpsMessageReminderService.save(hpsMessageReminder);
    	    	
    	return AjaxJson.ok("推送消息成功！");
    	
    
    
    }
    
    
}
