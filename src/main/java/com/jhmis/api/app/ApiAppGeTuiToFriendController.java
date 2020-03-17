package com.jhmis.api.app;

import java.util.List;

import javax.annotation.Resource;

import com.jhmis.common.utils.AppPushMsgByCidUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.wechat.entity.WxAppCid;
import com.jhmis.modules.wechat.service.WxAppCidService;


public class ApiAppGeTuiToFriendController {
	@Resource
	private WxAppCidService wxAppCidService;
	
	public static void pushToMsgByCid(String cId,String content,String title,String type,String url) {
		AppPushMsgByCidUtils appPushMsgByCidUtils = new AppPushMsgByCidUtils();
		appPushMsgByCidUtils.setCid(cId);
		appPushMsgByCidUtils.setContent(content);
		appPushMsgByCidUtils.setTitle(title);
		appPushMsgByCidUtils.setType(type);
		appPushMsgByCidUtils.setUrl(url);
		Thread t = new Thread(appPushMsgByCidUtils);
		t.start();
	}
	
	public  void pushToMsgByCidToInfo(String content,String title,String url) {
		/*AppPushMsgByCidUtils appPushMsgByCidUtils = new AppPushMsgByCidUtils();
		appPushMsgByCidUtils.setCid(cId);
		appPushMsgByCidUtils.setContent(content);
		appPushMsgByCidUtils.setTitle(title);
		appPushMsgByCidUtils.setType(type);
		appPushMsgByCidUtils.setUrl(url);
		Thread t = new Thread(appPushMsgByCidUtils);
		t.start();*/
		WxAppCid c= new WxAppCid();
		c.setCId(null);
	    List<WxAppCid>	list=wxAppCidService.findList(c);
		for(WxAppCid cid:list){
			if(StringUtils.isNotBlank(cid.getCId())&&StringUtils.isNotBlank(cid.getPhoneType())){
				pushToMsgByCid(cid.getCId(),content,title,cid.getPhoneType(),url);
			}
		}
		System.out.println("文章推送完成");
	}
	
}
